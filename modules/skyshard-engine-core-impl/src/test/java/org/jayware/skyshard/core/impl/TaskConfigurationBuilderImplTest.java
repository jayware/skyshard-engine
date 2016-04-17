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

import org.jayware.skyshard.core.api.TaskConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TaskConfigurationBuilderImplTest
{
    private TaskConfigurationBuilderImpl testee;

    @BeforeMethod
    public void setUp()
    throws Exception
    {
        testee = new TaskConfigurationBuilderImpl();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_with_Throws_IllegalArgumentException_if_name_is_null()
    {
        testee.with(null, "");
    }

    @Test
    public void test_with_Does_not_throw_IllegalArgumentException_if_value_is_null()
    {
        testee.with("fubar", null);
    }

    @Test
    public void test_build_Returns_filled_configuration()
    {
        final TaskConfiguration configuration;

        testee.with("foo", "bar");
        testee.with("muh", "kuh");

        configuration = testee.build();

        assertThat(configuration).isNotNull();
        assertThat(configuration.getProperty("foo")).isEqualTo("bar");
        assertThat(configuration.getProperty("muh")).isEqualTo("kuh");
    }

    @Test
    public void test_clear_Removes_all_configuration()
    {
        final TaskConfiguration configuration;

        testee.with("foo", "bar");
        testee.clear();

        configuration = testee.build();

        assertThat(configuration.containsProperty("foo")).isFalse();
        assertThat(configuration.getProperty("foo")).isNull();
    }
}