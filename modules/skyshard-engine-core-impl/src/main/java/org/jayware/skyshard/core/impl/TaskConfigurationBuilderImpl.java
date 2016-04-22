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
import org.jayware.skyshard.core.api.TaskConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.jayware.solid.utilities.Preconditions.checkNotNull;


public class TaskConfigurationBuilderImpl
implements TaskConfigurationBuilder
{
    private Map<String, String> myConfiguration = new HashMap<>();

    @Override
    public TaskConfigurationBuilder with(String name, String value)
    {
        checkNotNull(name);

        myConfiguration.put(name, value);
        return this;
    }

    @Override
    public TaskConfiguration build()
    {
        return new TaskConfigurationImpl(new HashMap<>(myConfiguration));
    }

    @Override
    public void clear()
    {
        myConfiguration.clear();
    }

}
