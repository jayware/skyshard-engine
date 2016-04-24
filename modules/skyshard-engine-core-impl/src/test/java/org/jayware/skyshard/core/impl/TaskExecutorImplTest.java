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

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.jayware.skyshard.core.api.Task;
import org.jayware.skyshard.core.api.TaskConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;


public class TaskExecutorImplTest
{
    private TaskExecutorImpl testee;

    private @Mocked Task testTask;
    private @Mocked Executor testExecutor;
    private @Mocked TaskConfiguration testConfiguration;
    private @Mocked Map<String, String> testProperties;

    @BeforeMethod
    public void setUp()
    throws Exception
    {
        testee = new TaskExecutorImpl(testExecutor, testProperties);
    }

    @Test
    public void test_execute_Passes_Task_to_Executor()
    throws Exception
    {
        testee.execute(testTask, testConfiguration);

        new Verifications()
        {{
            testExecutor.execute((Runnable) any); times = 1;
        }};
    }

    @Test
    public void test_execute_Returns_not_null()
    throws Exception
    {
        assertThat(testee.execute(testTask, testConfiguration)).isNotNull();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_execute_Throws_IllegalArgumentException_if_no_Task_is_passed()
    throws Exception
    {
        testee.execute(null, testConfiguration);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_execute_Throws_IllegalArgumentException_if_no_TaskConfiguration_is_passed()
    throws Exception
    {
        testee.execute(testTask, null);
    }

    @Test
    public void test_matches_Returns_true_if_configuration_matches()
    throws Exception
    {
        new Expectations()
        {{
            testConfiguration.matches((TaskConfiguration) any); result = true;
        }};

        assertThat(testee.matches(testConfiguration)).isTrue();
    }

    @Test
    public void test_matches_Returns_false_if_configuration_does_not_matches()
    throws Exception
    {
        new Expectations()
        {{
            testConfiguration.matches((TaskConfiguration) any); result = false;
        }};

        assertThat(testee.matches(testConfiguration)).isFalse();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_matches_Throws_IllegalArgumentException_if_null_is_passed()
    {
        testee.matches(null);
    }

    @Test
    public void test_uses_Returns_true_if_the_right_Executor_is_passed()
    throws Exception
    {
        assertThat(testee.uses(testExecutor)).isTrue();
    }

    @Test
    public void test_uses_Returns_false_if_null_is_passed()
    throws Exception
    {
        assertThat(testee.uses(null)).isFalse();
    }

    @Test
    public void test_uses_Returns_true_if_any_Executor_is_passed()
    throws Exception
    {
        assertThat(testee.uses(any())).isFalse();
    }

}