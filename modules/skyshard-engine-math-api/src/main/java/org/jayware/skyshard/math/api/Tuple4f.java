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

public interface Tuple4f
{
	public void setZero();

    /**
     * Sets the value of this tuple to the tuple sum of tuple t1 and t2.
     * 
     * @param t1 the first tuple.
     * @param t2 the second tuple.
     */
    public void add(Tuple4f t1, Tuple4f t2);

    /**  
     * Sets the value of this tuple to the tuple sum of itself and tuple t.
     * 
     * @param t the other tuple.
     */  
    public void add(Tuple4f t);

    /**
     * Sets the value of this tuple to the tuple difference
     * of tuple t1 and t2 (this = t1 - t2).
     * 
     * @param t1 the first tuple.
     * @param t2 the second tuple.
     */
    public void sub(Tuple4f t1, Tuple4f t2);

    /**  
     * Sets the value of this tuple to the tuple difference of
     * itself and tuple t (this = this - t)
     * .
     * @param t the other tuple.
     */  
    public void sub(Tuple4f t);

    /**
     * Sets the value of this tuple to the scalar multiplication of tuple t.
     * 
     * @param s the scalar value
     * @param t the source tuple
     */
    public void scale(float s, Tuple4f t);

    /**
     * Sets the value of this tuple to the scalar multiplication
     * of the scale factor with this.
     * 
     * @param s the scalar value.
     */
    public void scale(float s);

    /**
     * Negates the value of this tuple in place.
     */
	public void negate();

    /**
     * Sets the value of this tuple to the negation of tuple t.
     * 
     * @param t the source tuple.
     */
	public void negate(Tuple4f t);

    /**
     * Gets the value of this tuple and copies the values into the specified
     * array.
     * 
     * @param values an array to put the values.
     * 
     * @return the passed array containing this tuples values.
     */
    public float[] get(float[] values);

    /**
     * Sets the values of this tuple to the values of the specified array.
     * 
     * @param values an array containing the values to set.
     */
    public void set(float[] values);

    /**
     * Gets the value of this tuple and copies the values into tuple t.
     * 
     * @param t the Tuple4f object into which the
     * 		  values of this Tuple4f are copied.
     * 
     * @return tuple t containing the copied values.
     */
    public Tuple4f get(Tuple4f t);

    /**
     * Sets the value of this tuple to the value of tuple t.
     * 
     * @param t the tuple to be copied.
     */
    public void set(Tuple4f t);

    /**
     * Sets the value of this tuple to the specified xyz coordinates.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     * @param w the w coordinate.
     */
    public void set(float x, float y, float z, float w);

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

	/**
	 * Get the <i>w</i> coordinate.
	 * 
	 * @return the <i>w</i> coordinate.
	 */
	public float getW();

	/**
	 * Set the <i>w</i> coordinate.
	 * 
	 * @param w value to <i>w</i> coordinate.
	 */
	public void setW(float w);
}
