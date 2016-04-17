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

import java.util.Map;


public class TaskConfigurationImpl
implements TaskConfiguration
{
    private final Map<String, String> myConfiguration;

    public TaskConfigurationImpl(Map<String, String> configuration)
    {
        myConfiguration = configuration;
    }

    @Override
    public boolean containsProperty(String name)
    {
        return myConfiguration.containsKey(name);
    }

    @Override
    public String getProperty(String name)
    {
        return myConfiguration.get(name);
    }

    @Override
    public String getOrDefaultProperty(String name, String value)
    {
        if (myConfiguration.containsKey(name))
        {
            return myConfiguration.get(name);
        }

        return value;
    }
}
