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

import mockit.Capturing;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.jayware.skyshard.core.api.Task;
import org.jayware.skyshard.core.api.TaskConfiguration;
import org.jayware.skyshard.core.api.TaskExecutor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;


public class TaskExecutorPoolImplTest
{
    private @Tested TaskExecutorPoolImpl testee;

    private @Capturing TaskExecutor testTaskExecutor;
    private @Mocked TaskConfiguration testConfiguration;
    private @Mocked Executor testExecutor;
    private @Mocked Task testTask;

    @BeforeMethod
    public void setUp()
    throws Exception
    {
        testee = new TaskExecutorPoolImpl();
    }

    @Test
    public void testExecute()
    throws Exception
    {
        new Expectations()
        {{
            testTaskExecutor.satisfies(testConfiguration); result = true;
        }};

        testee.bindExecutor(testExecutor, new HashMap<>());

        testee.execute(testTask, testConfiguration);

        new Verifications()
        {{
            Task task;
            TaskConfiguration configuration;
            testTaskExecutor.execute(task = withCapture(), configuration = withCapture()); times = 1;

            assertThat(task).isEqualTo(testTask);
            assertThat(configuration).isEqualTo(testConfiguration);
        }};
    }

    @Test
    public void test_findTaskExecutor_Returns_null_if_there_are_no_TaskExecutors()
    throws Exception
    {
        assertThat(testee.findTaskExecutor(testConfiguration)).isNull();
    }

    @Test
    public void test_findTaskExecutor_Returns_not_null_if_a_matching_TaskExecutor_is_available()
    throws Exception
    {
        testee.bindExecutor(testExecutor, new HashMap<>());

        new Expectations()
        {{
            testTaskExecutor.satisfies(testConfiguration); result = true;
        }};

        assertThat(testee.findTaskExecutor(testConfiguration)).isNotNull();
    }

}