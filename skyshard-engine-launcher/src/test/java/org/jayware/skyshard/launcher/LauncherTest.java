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


import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.launch.Framework;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.jayware.skyshard.launcher.Launcher.LAUNCHER_CONFIGURATION_CLASS_PATH;
import static org.jayware.skyshard.launcher.Launcher.LAUNCHER_CONFIG_FILE_PROPERTY;
import static org.jayware.skyshard.launcher.Launcher.LAUNCHER_FRAMEWORK_FACTORY_PROPERTY;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.osgi.framework.FrameworkEvent.ERROR;


public class LauncherTest
{
    public void testMain()
    throws Exception
    {
        Launcher.main(new String[0]);
    }

    @Test
    public void testGetFrameworkFactory()
    throws Exception
    {
        final Map<String, String> configuration = new HashMap<>();
        configuration.put(LAUNCHER_FRAMEWORK_FACTORY_PROPERTY, "org.jayware.skyshard.launcher.test.FrameworkFactory");

        assertThat(Launcher.getFrameworkFactory(configuration)).isNotNull();
    }

    @Test
    public void testLoadDefaultConfiguration()
    throws Exception
    {
        final Properties defaultConfiguration = new Properties();
        final Properties probe = new Properties();
        try (final InputStream inputStream = getClass().getResourceAsStream(LAUNCHER_CONFIGURATION_CLASS_PATH))
        {
            defaultConfiguration.load(inputStream);
        }

        Launcher.loadDefaultConfiguration(probe);

        final Set<Entry<Object, Object>> entries = defaultConfiguration.entrySet();
        assertThat(probe).contains(entries.toArray(new Entry[entries.size()]));
    }

    @Test
    public void testLoadSystemConfiguration()
    {
        final Properties probe = new Properties();
        final Set<Entry<Object, Object>> systemProperties = System.getProperties().entrySet();

        Launcher.loadSystemConfiguration(probe);

        assertThat(probe).contains(systemProperties.toArray(new Entry[systemProperties.size()]));
    }

    @Test
    public void testLoadExternalConfiguration()
    throws Exception
    {
        final Properties probe = new Properties();
        final Path path = Paths.get(getClass().getResource("/dummy.launcher.properties").toURI());
        final Properties expectedConfiguration = new Properties();
        final Set<Entry<Object, Object>> expectedConfigurationEntries = expectedConfiguration.entrySet();

        expectedConfiguration.put("foo", "bar");

        probe.put(LAUNCHER_CONFIG_FILE_PROPERTY, path.toString());
        Launcher.loadExternalConfiguration(probe);

        assertThat(probe).contains(expectedConfigurationEntries.toArray(new Entry[expectedConfigurationEntries.size()]));
    }

    @Test
    public void testResolveConfiguration()
    {
        final Map<String, String> configuration = new HashMap<>();
        configuration.put("foo", "${bar}");
        configuration.put("bar", "fubar");

        Launcher.resolveConfiguration(configuration);

        assertThat(configuration).containsEntry("foo", "fubar");
    }

    @Test
    public void testInitializeFramework()
    throws Exception
    {
        final Framework framework = mock(Framework.class);
        Launcher.initializeFramework(framework);

        verify(framework, times(1)).init();
    }

    @Test
    public void testStartFramework()
    throws Exception
    {
        final ThreadFactory threadFactory = mock(ThreadFactory.class);
        final Framework framework = mock(Framework.class);
        final AtomicReference<Runnable> runnable = new AtomicReference<>();
        final Thread thread = mock(Thread.class);
        final Runtime runtime = mock(Runtime.class);
        final FrameworkEvent stoppedUpdateEvent = mock(FrameworkEvent.class);
        final FrameworkEvent stopEvent = mock(FrameworkEvent.class);

        when(threadFactory.newThread(any())).then(new Answer<Thread>()
        {
            @Override
            public Thread answer(InvocationOnMock invocation)
            throws Throwable
            {
                runnable.set((Runnable) invocation.getArguments()[0]);
                return thread;
            }
        });

        when(framework.waitForStop(0)).thenReturn(stoppedUpdateEvent).thenReturn(stopEvent);

        when(stoppedUpdateEvent.getType()).thenReturn(FrameworkEvent.STOPPED_UPDATE);
        when(stopEvent.getType()).thenReturn(ERROR);

        doThrow(ThreadStartedException.class).when(thread).start();

        try
        {
            Launcher.startFramework(framework, runtime, threadFactory);

            fail("Framework thread was not started!");
        }
        catch (ThreadStartedException e)
        {
            runnable.get().run();

            verify(framework, times(2)).start();
            verify(framework, times(2)).waitForStop(0);
            verify(runtime, times(1)).exit(0);
        }
    }

    private static class ThreadStartedException
    extends RuntimeException
    {

    }
}
