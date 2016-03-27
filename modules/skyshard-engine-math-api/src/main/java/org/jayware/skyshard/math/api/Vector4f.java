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
package org.jayware.skyshard.math.api;

/**
 * A 4-element vector that is represented by single-precision floating point 
 * x, y, z, w coordinates.
 * 
 * @author Elmar Schug
 * @since April 12, 2013
 */
public interface Vector4f
extends Tuple4f
{
    /**
     * Computes the dot product of this vector and vector vec.
     * 
     * @param vec the other vector.
     * 
     * @return the dot product of this vector and vec.
     */
    public float dot(Vector4f vec);

    /**
     * Normalizes this vector in place.
     */
    public void normalize();

    /**
     * Sets the value of this vector to the normalization of vector vec.
     * 
     * @param vec the un-normalized vector.
     */
    public void normalize(Vector4f vec);

    /**
     * Returns the length of this vector.
     * 
     * @return the length of this vector.
     */
    public float length();

    /**
     * Returns the squared length of this vector.
     * 
     * @return the squared length of this vector.
     */
    public float lengthSquared();

    /**
     * Returns the angle in radians between this vector and the vector
     * parameter; the return value is constrained to the range [0,PI].
     * 
     * @param vec the other vector.
     * 
     * @return the angle in radians in the range [0,PI].
     */
    public float angle(Vector4f vec);
}
