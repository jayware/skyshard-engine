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
 * A 3-element vector that is represented by single-precision floating point 
 * x,y,z coordinates.
 * 
 * @author Elmar Schug
 * @since April 7, 2013
 */
public interface Vector3f
{
    /**
     * Sets the value of this vector to the vector sum of vector v1 and v2.
     * 
     * @param v1 the first vector.
     * @param v2 the second vector.
     */
    public void add(Vector3f v1, Vector3f v2);

    /**  
     * Sets the value of this vector to the vector sum of itself and vector vec.
     * 
     * @param vec the other vector.
     */  
    public void add(Vector3f vec);

    /**
     * Sets the value of this vector to the vector difference
     * of vector v1 and v2 (this = v1 - v2).
     * 
     * @param v1 the first vector.
     * @param v2 the second vector.
     */
    public void sub(Vector3f v1, Vector3f v2);

    /**  
     * Sets the value of this tuple to the vector difference of
     * itself and vector vec (this = this - vec)
     * .
     * @param vec the other vector.
     */  
    public void sub(Vector3f vec);

    /**
     * Sets the value of this vector to the scalar multiplication
     * of the scale factor with this.
     * 
     * @param s the scalar value.
     */
    public void scale(float s);

    /**
     * Sets this vector to be the vector cross product of vectors v1 and v2.
     * 
     * @param v1 the first vector.
     * @param v2 the second vector.
     */
    public void cross(Vector3f v1, Vector3f v2);

    /**
     * Computes the dot product of this vector and vector v1.
     * 
     * @param v1 the other vector.
     * 
     * @return the dot product of this vector and v1.
     */
    public float dot(Vector3f v1);

    /**
     * Sets the value of this vector to the normalization of vector v1.
     * 
     * @param v1 the un-normalized vector.
     */
    public void normalize(Vector3f v1);

    /**
     * Normalizes this vector in place.
     */
    public void normalize();

    /**
     * Negates the value of this vector in place.
     */
    public void negate();

    /**
     * Sets the value of this vector to the negation of vector vec.
     * 
     * @param vec the source tuple.
     */
    public void negate(Vector3f vec);

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
     * @param v1 the other vector.
     * 
     * @return the angle in radians in the range [0,PI].
     */
    public float angle(Vector3f v1);

    /**
     * Gets the value of this vector and copies the values into vector vec.
     * 
     * @param vec the Vector3f object into which the
     * 		  values of this Vector3f are copied.
     * 
     * @return vector vec containing the copied values.
     */
    public Vector3f get(Vector3f vec);

    /**
     * Sets the value of this vector to the value of vector vec.
     * 
     * @param vec the vector to be copied.
     */
    public void set(Vector3f vec);

    /**
     * Sets the value of this vector to the specified xyz coordinates.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     */
    public void set(float x, float y, float z);

    /**
	 * Get the <i>x</i> coordinate.
	 * 
	 * @return the <i>x</i> coordinate.
	 */
	public float getX();

	/**
	 * Set the <i>x</i> coordinate.
	 * 
	 * @param x value to <i>x</i> coordinate.
	 */
	public void setX(float x);

    /**
	 * Get the <i>y</i> coordinate.
	 * 
	 * @return the <i>y</i> coordinate.
	 */
	public float getY();

	/**
	 * Set the <i>y</i> coordinate.
	 * 
	 * @param y value to <i>y</i> coordinate.
	 */
	public void setY(float y);

	/**
	 * Get the <i>z</i> coordinate.
	 * 
	 * @return the <i>z</i> coordinate.
	 */
	public float getZ();

	/**
	 * Set the <i>z</i> coordinate.
	 * 
	 * @param z value to <i>z</i> coordinate.
	 */
	public void setZ(float z);
}
