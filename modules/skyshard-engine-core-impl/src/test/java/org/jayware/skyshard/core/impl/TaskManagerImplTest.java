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

import mockit.Mocked;
import mockit.Verifications;
import org.jayware.skyshard.core.api.Task;
import org.jayware.skyshard.core.api.TaskConfiguration;
import org.jayware.skyshard.core.api.TaskContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TaskManagerImplTest
{
    private TaskManagerImpl testee;

    private @Mocked TestTask testTask;
    private @Mocked TaskConfiguration testConfiguration;
    private @Mocked
    TaskExecutorPoolImpl testExecutorPool;

    @BeforeMethod
    public void setUp()
    throws Exception
    {
        testee = new TaskManagerImpl();
        testee.bindTaskExecutorPool(testExecutorPool);
    }

    @Test
    public void test_createConfiguration_Returns_NotNull()
    {
        assertThat(testee.createConfiguration()).isNotNull();
    }

    @Test
    public void test_submit_Task_Returns_not_null()
    {
        assertThat(testee.submit(testTask)).isNotNull();
    }

    @Test
    public void test_submit_Task_Passes_Task_To_ExecutorPool()
    {
        testee.submit(testTask);

        new Verifications()
        {{
            final Task task;
            final TaskConfiguration configuration;

            testExecutorPool.execute(task = withCapture(), configuration = withCapture()); times = 1;

            assertThat(task).isEqualTo(testTask);
            assertThat(configuration).isNotNull();
        }};
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_submit_Task_Throws_IllegalArgumentException_when_null_is_passed()
    {
        testee.submit((Task) null);
    }

    @Test
    public void test_submit_Task_with_Configuration_Passes_Task_and_Configuration_to_ExecutorPool()
    {
        testee.submit(testTask, testConfiguration);

        new Verifications()
        {{
            final Task task;
            final TaskConfiguration configuration;

            testExecutorPool.execute(task = withCapture(), configuration = withCapture()); times = 1;

            assertThat(task).isEqualTo(testTask);
            assertThat(configuration).isEqualTo(testConfiguration);
        }};
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_submit_TaskWithConfiguration_Throws_IllegalArgumentException_when_no_task_is_passed()
    {
        testee.submit(null, testConfiguration);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_submit_TaskWithConfiguration_Throws_IllegalArgumentException_when_no_configuration_is_passed()
    {
        testee.submit(testTask, null);
    }

    public static class TestTask
    implements Task
    {
        @Override
        public void execute(TaskContext context)
        {

        }
    }
}
