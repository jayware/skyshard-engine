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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.jayware.solid.utilities.GlobUtil.toRegex;
import static org.jayware.solid.utilities.Preconditions.checkNotNull;


public class TaskConfigurationImpl
implements TaskConfiguration
{
    private final Map<String, PatternEntry> myConfiguration;

    public TaskConfigurationImpl(Map<String, String> configuration)
    {
        myConfiguration = new HashMap<>(configuration.size());

        for (Map.Entry<String, String> entry : configuration.entrySet())
        {
            final String key = entry.getKey();
            final String value = entry.getValue();

            myConfiguration.put(key, new PatternEntry(value));
        }
    }

    @Override
    public boolean containsProperty(String name)
    {
        return myConfiguration.containsKey(name);
    }

    @Override
    public String getProperty(String name)
    {
        checkNotNull(name);

        final PatternEntry entry = myConfiguration.get(name);

        if (entry != null)
        {
            return entry.getValue();
        }

        return null;
    }

    @Override
    public String getOrDefaultProperty(String name, String value)
    {
        final String result = getProperty(name);

        if (result != null)
        {
            return result;
        }

        return value;
    }

    @Override
    public Set<String> getNames()
    {
        return myConfiguration.keySet();
    }

    @Override
    public boolean satisfies(TaskConfiguration other)
    {
        checkNotNull(other);

        for (String name : other.getNames())
        {
            final PatternEntry entry = myConfiguration.get(name);
            final String value;

            if (entry != null)
            {
                value = entry.getValue();
            }
            else
            {
                value = null;
            }

            if (!other.satisfies(name, value))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean satisfies(String name, String value)
    {
        checkNotNull(name);

        final PatternEntry entry = myConfiguration.get(name);

        return entry == null || entry.matches(value);
    }

    @Override
    public String toString()
    {
        final StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, PatternEntry> entry : myConfiguration.entrySet())
        {
            final PatternEntry patternEntry = entry.getValue();
            final String key = entry.getKey();

            stringBuilder.append('\"').append(key).append("\":").append('\"');

            if (patternEntry == null)
            {
                stringBuilder.append("null");

            }
            else
            {
                stringBuilder.append(patternEntry.getValue());

            }

            stringBuilder.append("\", ");
        }

        String result = stringBuilder.toString();
        result = result.substring(0, result.length() - 2);

        return "\"TaskConfiguration\": {" + result + '}';
    }

    private static class PatternEntry
    {
        private final String myValue;
        private final Pattern myPatter;

        public PatternEntry(String value)
        {
            myValue = value;
            myPatter = compile(toRegex(value));
        }

        public String getValue()
        {
            return myValue;
        }

        public boolean matches(String value)
        {
            return myPatter.matcher(value).matches();
        }

        @Override
        public String toString()
        {
            return "{ value=\'" + myValue + "\', regex=\'" + myPatter + "\' }";
        }
    }
}
