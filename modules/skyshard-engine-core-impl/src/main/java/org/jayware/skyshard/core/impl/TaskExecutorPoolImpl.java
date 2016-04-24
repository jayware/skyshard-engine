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
package org.jayware.skyshard.core.impl;

import org.jayware.skyshard.core.api.Task;
import org.jayware.skyshard.core.api.TaskConfiguration;
import org.jayware.skyshard.core.api.TaskExecutor;
import org.jayware.skyshard.core.api.TaskExecutorPool;
import org.jayware.skyshard.core.api.TaskResult;
import org.jayware.skyshard.core.api.TaskSchedulingException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.osgi.service.component.annotations.ReferenceCardinality.AT_LEAST_ONE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ServiceScope.SINGLETON;


@Component(scope = SINGLETON)
public class TaskExecutorPoolImpl
implements TaskExecutorPool
{
    private static final Logger log = LoggerFactory.getLogger(TaskExecutorPool.class);

    private final Set<TaskExecutor> myExecutors = new HashSet<>();

    private final ReadWriteLock myLock = new ReentrantReadWriteLock();
    private final Lock myReadLock = myLock.readLock();
    private final Lock myWriteLock = myLock.writeLock();

    @Override
    public TaskResult execute(Task task, TaskConfiguration configuration)
    {
        myReadLock.lock();
        try
        {
            final TaskExecutor executor = findTaskExecutor(configuration);

            if (executor != null)
            {
                return executor.execute(task, configuration);
            }

            throw new TaskSchedulingException(task, "There is no TaskExecutor available!");
        }
        finally
        {
            myReadLock.unlock();
        }
    }

    TaskExecutor findTaskExecutor(TaskConfiguration configuration)
    {
        for (TaskExecutor executor : myExecutors)
        {
            if (executor.matches(configuration))
            {
                return executor;
            }
        }

        return null;
    }

    @Reference(policy = DYNAMIC, cardinality = AT_LEAST_ONE)
    public void bindExecutor(Executor executor, Map<String, ?> properties)
    {
        final Map<String, String> executorProperties = new HashMap<>();
        for (Map.Entry<String, ?> entry : properties.entrySet())
        {
            final Object value = entry.getValue();
            if (value != null && value instanceof String)
            {
                executorProperties.put(entry.getKey(), (String) value);
            }
        }

        myWriteLock.lock();
        try
        {
            final TaskExecutorImpl taskExecutor = new TaskExecutorImpl(executor, executorProperties);
            myExecutors.add(taskExecutor);
            log.debug("Executor joined pool: {}", taskExecutor);
        }
        finally
        {
            myWriteLock.unlock();
        }
    }

    public void unbindExecutor(Executor executor)
    {
        myWriteLock.lock();
        try
        {
            for (TaskExecutor taskExecutor : myExecutors)
            {
                if (taskExecutor.uses(executor))
                {
                    myExecutors.remove(taskExecutor);
                    log.debug("Executor leaved pool: {}", taskExecutor);
                    return;
                }
            }
        }
        finally
        {
            myWriteLock.unlock();
        }
    }
}
