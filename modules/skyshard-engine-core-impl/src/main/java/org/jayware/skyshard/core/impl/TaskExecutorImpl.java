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
import org.jayware.skyshard.core.api.TaskContext;
import org.jayware.skyshard.core.api.TaskExecutor;
import org.jayware.skyshard.core.api.TaskResult;

import java.util.Map;
import java.util.concurrent.Executor;

import static org.jayware.solid.utilities.Preconditions.checkNotNull;


public class TaskExecutorImpl
implements TaskExecutor
{
    private final Executor myExecutor;
    private final TaskConfiguration myConfiguration;

    TaskExecutorImpl(Executor executor, Map<String, String> configuration)
    {
        myExecutor = executor;
        myConfiguration = new TaskConfigurationImpl(configuration);
    }

    @Override
    public TaskResult execute(Task task, TaskConfiguration configuration)
    {
        checkNotNull(task);
        checkNotNull(configuration);

        final TaskRunnable taskRunnable = new TaskRunnable(task, configuration);

        myExecutor.execute(taskRunnable);

        return taskRunnable;
    }

    @Override
    public boolean matches(TaskConfiguration configuration)
    {
        checkNotNull(configuration);

        return configuration.matches(myConfiguration);
    }

    @Override
    public boolean uses(Executor executor)
    {
        return myExecutor == executor;
    }

    private static class TaskRunnable
    implements Runnable, TaskResult
    {
        private final Task myTask;
        private final TaskContext myContext;
        private final TaskConfiguration myConfiguration;

        private TaskRunnable(Task task, TaskConfiguration configuration)
        {
            myTask = task;
            myContext = null;
            myConfiguration = configuration;
        }

        @Override
        public void run()
        {
            myTask.execute(myContext);
        }
    }
}
