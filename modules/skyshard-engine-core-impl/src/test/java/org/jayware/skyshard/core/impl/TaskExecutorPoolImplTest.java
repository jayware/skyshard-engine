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
            testTaskExecutor.matches(testConfiguration); result = true;
        }};

        testee.bindExecutor(testExecutor, null);

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
        testee.bindExecutor(testExecutor, null);

        new Expectations()
        {{
            testTaskExecutor.matches(testConfiguration); result = true;
        }};

        assertThat(testee.findTaskExecutor(testConfiguration)).isNotNull();
    }

}