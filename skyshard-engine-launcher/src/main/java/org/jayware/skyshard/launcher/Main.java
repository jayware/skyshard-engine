/**
 * Skyshard Engine -- A 3D game engine written in Java
 *
 * Copyright (C) 2016 Markus Neubauer <markus.neubauer@jayware.org>,
 *                    Alexander Haumann <alexander.haumann@jayware.org>,
 *                    Manuel Hinke <manuel.hinke@jayware.org>,
 *                    Marina Schilling <marina.schilling@jayware.org>,
 *                    Elmar Schug <elmar.schug@jayware.org>,
 *
 *     This file is part of the Skyshard Engine.
 *
 *     The Skyshard Engine is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public License
 *     as published by the Free Software Foundation, either version 3 of
 *     the License, or any later version.
 *
 *     The Skyshard Engine is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jayware.skyshard.launcher;


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.service.startlevel.StartLevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.valueOf;
import static java.lang.Runtime.getRuntime;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.regex.Pattern.compile;
import static org.osgi.framework.FrameworkEvent.STOPPED_UPDATE;


public class Main
{
    public static final String FRAMEWORK_FACTORY_CLASS_PATH = "META-INF/services/org.osgi.framework.launch.FrameworkFactory";

    public static final String LAUNCHER_CONFIGURATION_CLASS_PATH = "/META-INF/launcher-default.properties";

    public static final String LAUNCHER_AUTO_DEPLOY_PROPERTY = "launcher.auto.deploy.dir";

    public static final String LAUNCHER_AUTO_STARTLEVEL_PROPERTY = "launcher.auto.deploy.startlevel";

    public static final String LAUNCHER_CONFIG_FILE_PROPERTY = "launcher.config.file";

    public static final String BUNDLE_STARTLEVEL_HEADER = "Bundle-Startlevel";

    public static void main(String[] args)
    throws Exception
    {
        final FrameworkFactory frameworkFactory = getFrameworkFactory();
        final Map<String, String> configuration = loadConfiguration(args);
        final Framework framework = frameworkFactory.newFramework(configuration);
        final MainThreadExecutionService service = new MainThreadExecutionService();

        addShutdownHook(framework, service);
        initializeFramework(framework);
        deployBundles(framework, configuration);
        startFramework(framework);
        offerExecutionService(framework, service);
    }

    static FrameworkFactory getFrameworkFactory() throws Exception
    {
        final String resource = FRAMEWORK_FACTORY_CLASS_PATH;
        final ClassLoader classLoader = Main.class.getClassLoader();
        final URL url = classLoader.getResource(resource);

        if (url != null)
        {
            try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("UTF-8"))))
            {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine())
                {
                    line = line.trim();
                    if ((line.length() > 0) && (line.charAt(0) != '#'))
                    {
                        return (FrameworkFactory) Class.forName(line).newInstance();
                    }
                }
            }
        }

        throw new Exception("Could not find framework factory.");
    }

    static Map<String, String> loadConfiguration(String[] args)
    {
        final Map<String, String> result = new Hashtable<>();
        final Properties properties = new Properties();

        loadDefaultConfiguration(properties);
        loadSystemConfiguration(properties);
        loadExternalConfiguration(properties);

        for (Map.Entry<Object, Object> entry : properties.entrySet())
        {
            result.put((String) entry.getKey(), (String) entry.getValue());
        }

        resolveConfiguration(result);

        return result;
    }

    static void loadDefaultConfiguration(Properties properties)
    {
        try (final InputStream inputStream = Main.class.getResourceAsStream(LAUNCHER_CONFIGURATION_CLASS_PATH))
        {
            properties.load(inputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    static void loadSystemConfiguration(Properties properties)
    {
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet())
        {
            properties.put(entry.getKey(), entry.getValue());
        }
    }

    static void loadExternalConfiguration(Properties properties)
    {
        if (properties.containsKey(LAUNCHER_CONFIG_FILE_PROPERTY))
        {
            final Path configFilePath = Paths.get(properties.getProperty(LAUNCHER_CONFIG_FILE_PROPERTY));
            final File configFile = configFilePath.toFile();

            if (configFile.exists())
            {
                try (final InputStream inputStream = new FileInputStream(configFile))
                {
                    properties.load(inputStream);
                }
                catch (Exception e)
                {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    static void resolveConfiguration(Map<String, String> configuration)
    {
        final Pattern pattern = compile("\\$\\{[\\w|\\.|-]*\\}");
        final Set<Map.Entry<String, String>> properties = configuration.entrySet();
        final Matcher matcher = pattern.matcher("");

        for (Map.Entry<String, String> entry : properties)
        {
            try
            {
                final String key = entry.getKey();
                final String value = entry.getValue();
                final StringBuilder builder = new StringBuilder();

                boolean replaceProperty = false;
                int lastStart = 0, lastEnd = 0;

                matcher.reset(value);
                while (matcher.find())
                {
                    final String resolve = matcher.group().substring(2, matcher.group().length() - 1);
                    final String replacement = configuration.get(resolve);
                    final int start = matcher.start();

                    if (replacement == null)
                    {
                        throw new RuntimeException("Missing property: " + resolve);
                    }

                    if (start - lastStart > 0)
                    {
                        builder.append(value.substring(lastStart, start));
                    }

                    builder.append(replacement);

                    lastStart = start;
                    lastEnd = matcher.end();
                    replaceProperty = true;
                }

                if (lastEnd < value.length())
                {
                    builder.append(value.substring(lastEnd, value.length()));
                }

                if (replaceProperty)
                {
                    configuration.put(key, builder.toString());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace(System.err);
            }
        }
    }

    static void addShutdownHook(final Framework framework, MainThreadExecutionService service)
    {
        getRuntime().addShutdownHook(new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    service.shutdown();
                    framework.stop();
                    framework.waitForStop(0);
                }
                catch (Exception e)
                {
                    e.printStackTrace(System.err);
                }
            }
        }, "framework-shutdown-hook"));
    }

    static void initializeFramework(Framework framework)
    throws BundleException
    {
        framework.init();
    }

    static void startFramework(Framework framework)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    FrameworkEvent event;
                    do
                    {
                        framework.start();
                        event = framework.waitForStop(0);
                    }
                    while (event.getType() == STOPPED_UPDATE);

                    getRuntime().exit(0);
                }
                catch (Exception e)
                {
                    e.printStackTrace(System.err);
                    getRuntime().exit(-1);
                }
            }
        }, "framework-launcher").start();
    }

    static void deployBundles(Framework framework, Map<String, String> configuration)
    {
        final BundleContext context = framework.getBundleContext();
        final StartLevel startLevelService = context.getService(context.getServiceReference(StartLevel.class));
        final Map<URI, Bundle> installedBundles = getInstalledBundles(context);
        final List<Bundle> bundlesToStart = new ArrayList<>();
        final List<URI> autoDeployBundleFiles = getAutoDeployBundleFiles(configuration);

        for (URI uri : autoDeployBundleFiles)
        {
            Bundle bundle = installedBundles.remove(uri);

            try
            {
                if (bundle == null)
                {
                    bundle = context.installBundle(uri.toString());
                }
                else
                {
                    bundle.update();
                }

                bundlesToStart.add(bundle);
                startLevelService.setBundleStartLevel(bundle, determineBundleStartLevel(bundle, configuration));
            }
            catch (BundleException e)
            {
                e.printStackTrace(System.err);
            }
        }

        // Uninstall all bundles not in the auto-deploy directory
        for (Bundle bundle : installedBundles.values())
        {
            try
            {
                bundle.uninstall();
            }
            catch (BundleException e)
            {
                e.printStackTrace(System.err);
            }
        }

        for (Bundle bundle : bundlesToStart)
        {
            try
            {
                bundle.start();
            }
            catch (BundleException e)
            {
                e.printStackTrace(System.err);
            }
        }

    }

    static Map<URI, Bundle> getInstalledBundles(BundleContext context)
    {
        final Map<URI, Bundle> installBundles = new HashMap<>();
        for (Bundle bundle : context.getBundles())
        {
            final String location = bundle.getLocation();
            if (!location.equalsIgnoreCase("System Bundle"))
            {
                installBundles.put(URI.create(location), bundle);
            }
        }

        return installBundles;
    }

    static List<URI> getAutoDeployBundleFiles(Map<String, String> configuration)
    {
        final String autoDeployDir = configuration.get(LAUNCHER_AUTO_DEPLOY_PROPERTY);
        final File[] files = new File(autoDeployDir).listFiles();
        final List<URI> autoDeployBundleFiles = new ArrayList<>();

        if (files != null)
        {
            for (File file : files)
            {
                if (file.getName().endsWith(".jar"))
                {
                    autoDeployBundleFiles.add(file.toURI());
                }
            }
        }

        return autoDeployBundleFiles;
    }

    static int determineBundleStartLevel(Bundle bundle, Map<String, String> configuration)
    {
        final Dictionary<String, String> headers = bundle.getHeaders();
        final String startLevelHeader = headers.get(BUNDLE_STARTLEVEL_HEADER);

        return startLevelHeader != null ? valueOf(startLevelHeader) : valueOf(configuration.get(LAUNCHER_AUTO_STARTLEVEL_PROPERTY));
    }

    static void offerExecutionService(Framework framework, MainThreadExecutionService service)
    {
        final Dictionary<String, String > parameters = new Hashtable<>();
        final BundleContext bundleContext = framework.getBundleContext();
        final ServiceRegistration<Executor> registration;
        final Thread thread = currentThread();

        parameters.put("executor.id", String.valueOf(thread.getId()));
        parameters.put("executor.name", thread.getName());
        parameters.put("executor.group", thread.getThreadGroup().getName());
        parameters.put("executor.type", "single");

        registration = bundleContext.registerService(Executor.class, service, parameters);

        service.run();

        registration.unregister();
    }

    static class MainThreadExecutionService
    implements Executor
    {
        private static final int EXECUTION_QUEUE_SIZE = 64;
        private static final long EXECUTION_OFFER_TIMEOUT_IN_SECONDS = 10;

        private final BlockingQueue<Runnable> myWorkQueue;
        private final AtomicBoolean myKeepRunningFlag;
        private final Thread myThread;

        private final Lock myLock = new ReentrantLock();
        private final Condition myTerminationCondition = myLock.newCondition();

        MainThreadExecutionService()
        {
            myWorkQueue = new ArrayBlockingQueue<Runnable>(EXECUTION_QUEUE_SIZE);
            myKeepRunningFlag = new AtomicBoolean(false);
            myThread = currentThread();
        }

        void run()
        {
            myKeepRunningFlag.set(true);
            while (myKeepRunningFlag.get())
            {
                try
                {
                    final Runnable runnable = myWorkQueue.take();

                    try
                    {
                        runnable.run();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace(System.err);
                    }
                }
                catch (InterruptedException ignored) {}
            }

            myLock.lock();
            try
            {
                myTerminationCondition.signalAll();
            }
            finally
            {
                myLock.unlock();
            }
        }

        void shutdown()
        throws InterruptedException
        {
            myLock.lock();
            try
            {
                if (myKeepRunningFlag.get())
                {
                    myKeepRunningFlag.set(false);
                    myThread.interrupt();

                    if (!myTerminationCondition.await(30, SECONDS))
                    {
                        System.err.println("MainThreadExecutionService did not shutdown within 30s.");
                    }
                }
            }
            finally
            {
                myLock.unlock();
            }
        }

        @Override
        public void execute(Runnable runnable)
        {
            if (runnable == null)
            {
                return;
            }

            try
            {
                if (!myWorkQueue.offer(runnable, EXECUTION_OFFER_TIMEOUT_IN_SECONDS, SECONDS))
                {
                    throw new TimeoutException();
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException("Failed to execute: " + runnable, e);
            }
        }
    }
}
