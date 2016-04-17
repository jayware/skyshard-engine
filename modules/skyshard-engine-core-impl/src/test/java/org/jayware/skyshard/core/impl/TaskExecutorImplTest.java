package org.jayware.skyshard.core.impl;

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

    @Test
    public void testMatches()
    throws Exception
    {

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