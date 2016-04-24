package org.jayware.skyshard.core.impl;

import mockit.Expectations;
import mockit.Mocked;
import org.jayware.skyshard.core.api.TaskConfiguration;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jayware.skyshard.core.api.TaskExecutor.EXECUTOR_GROUP;
import static org.jayware.skyshard.core.api.TaskExecutor.EXECUTOR_NAME;


public class TaskConfigurationImplTest
{
    private @Mocked TaskConfiguration testConfiguration;

    @Test
    public void test_containsProperty_Returns_true_if_configuration_does_contain_the_specified_property()
    throws Exception
    {
        final TaskConfiguration testee;
        final Map<String, String> configuration = new HashMap<>();
        configuration.put("foo", "bar");

        testee = new TaskConfigurationImpl(configuration);

        assertThat(testee.containsProperty("foo")).isTrue();
    }

    @Test
    public void test_containsProperty_Returns_false_if_configuration_does_not_contain_the_specified_property()
    throws Exception
    {
        final TaskConfiguration testee;
        final Map<String, String> configuration = new HashMap<>();

        testee = new TaskConfigurationImpl(configuration);

        assertThat(testee.containsProperty("foo")).isFalse();
    }

    @Test
    public void test_getProperty_Returns_the_value_of_the_specified_property()
    throws Exception
    {
        final TaskConfiguration testee;
        final Map<String, String> configuration = new HashMap<>();
        configuration.put("key", "value");

        testee = new TaskConfigurationImpl(configuration);

        assertThat(testee.getProperty("key")).isEqualTo("value");
    }

    @Test
    public void test_getProperty_Returns_null_if_the_configuration_does_not_contain_the_specified_property()
    throws Exception
    {
        final TaskConfiguration testee = new TaskConfigurationImpl(new HashMap<>());

        assertThat(testee.getProperty("key")).isNull();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_getProperty_Throws_IllegalArgumentException_if_null_is_passed()
    throws Exception
    {
        final TaskConfiguration testee = new TaskConfigurationImpl(new HashMap<>());

        testee.getProperty(null);
    }

    @Test
    public void test_getOrDefaultProperty_Returns_the_default_value_if_the_configuration_does_not_contain_the_specified_property()
    throws Exception
    {
        final TaskConfiguration testee;
        final Map<String, String> configuration = new HashMap<>();

        testee = new TaskConfigurationImpl(configuration);

        assertThat(testee.getOrDefaultProperty("foo", "default")).isEqualTo("default");
    }

    @Test
    public void test_getOrDefaultProperty_Returns_the_default_value_if_the_configuration_does_contain_the_specified_property()
    throws Exception
    {
        final TaskConfiguration testee;
        final Map<String, String> configuration = new HashMap<>();
        configuration.put("foo", "fubar");

        testee = new TaskConfigurationImpl(configuration);

        assertThat(testee.getOrDefaultProperty("foo", "default")).isEqualTo("fubar");
    }

    @Test
    public void test_matches_Configuration_Returns_true_if_other_configuration_matches()
    throws Exception
    {
        final TaskConfiguration testee;
        final Map<String, String> configuration = new HashMap<>();
        configuration.put(EXECUTOR_NAME, "main");
        configuration.put(EXECUTOR_GROUP, "fubar");

        testee = new TaskConfigurationImpl(configuration);

        new Expectations()
        {{
            testConfiguration.matches(EXECUTOR_NAME, "main"); result = true;
            testConfiguration.matches(EXECUTOR_GROUP, "fubar"); result = true;
        }};

        assertThat(testee.matches(testConfiguration)).isTrue();
    }

    @Test
    public void test_matches_KeyValue_Returns_true_on_exact_match()
    throws Exception
    {
        final TaskConfiguration testee;
        final Map<String, String> configuration = new HashMap<>();
        configuration.put(EXECUTOR_NAME, "main");

        testee = new TaskConfigurationImpl(configuration);

        assertThat(testee.matches(EXECUTOR_NAME, "main")).isTrue();
    }

    @Test
    public void test_matches_KeyValue_Returns_true_on_any_match()
    throws Exception
    {
        final TaskConfiguration testee;
        final Map<String, String> configuration = new HashMap<>();
        configuration.put(EXECUTOR_GROUP, "*");

        testee = new TaskConfigurationImpl(configuration);

        assertThat(testee.matches(EXECUTOR_GROUP, "any")).isTrue();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_matches_Throws_IllegalArgumentException_if_null_is_passed()
    throws Exception
    {
        final TaskConfiguration testee = new TaskConfigurationImpl(new HashMap<>());

        testee.matches(null);
    }
}