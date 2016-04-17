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
import org.jayware.skyshard.core.api.TaskConfigurationBuilder;
import org.jayware.skyshard.core.api.TaskExecutorPool;
import org.jayware.skyshard.core.api.TaskManager;
import org.jayware.skyshard.core.api.TaskResult;
import org.jayware.skyshard.core.api.TaskSchedulingException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static org.jayware.skyshard.util.Preconditions.checkNotNull;


@Component
public class TaskManagerImpl
implements TaskManager
{
    private final AtomicReference<TaskExecutorPool> myExecutorPool = new AtomicReference<>();

    @Override
    public TaskConfigurationBuilder createConfiguration()
    {
        return new TaskConfigurationBuilderImpl();
    }

    @Override
    public TaskResult submit(Task task)
    {
        checkNotNull(task);

        return submit(task, defaultConfiguration());
    }

    @Override
    public TaskResult submit(Task task, TaskConfiguration configuration)
    {
        checkNotNull(task);
        checkNotNull(configuration);

        final TaskExecutorPool executorPool = myExecutorPool.get();
        if (executorPool != null)
        {
            return executorPool.execute(task, configuration);
        }

        throw new TaskSchedulingException(task, "There is no ExecutorPool available!");
    }

    @Reference
    void bindExecutorPool(TaskExecutorPool pool)
    {
        myExecutorPool.set(pool);
    }

    void unbindExecutorPool(TaskExecutorPool pool)
    {
        myExecutorPool.compareAndSet(pool, null);
    }

    private static TaskConfiguration defaultConfiguration()
    {
        return new TaskConfigurationImpl(new HashMap<>());
    }
}
