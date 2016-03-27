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

public interface AxisAngle4f
{
	/**
	 * Sets the value of this axis-angle to the specified x,y,z,angle.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 * @param z the z coordinate.
	 * @param angle the angle of rotation in radians.
	 */
	public void set(float x, float y, float z, float angle);

	/**
	 * Sets the value of this axis-angle to the specified values in the array.
	 * 
	 * @param array the array containing x, y, z, angle in order.
	 */
	public void set(float[] array);

	/**
	 * Sets the value of this axis-angle to the value of axis-angle a.
	 * 
	 * @param a the axis-angle to be copied.
	 */
	public void set(AxisAngle4f a);

	/**
	 * Sets the value of this AxisAngle4f to the specified axis of the given
	 * {@link Vector3f} and specified angle.
	 * 
	 * @param axis the axis.
	 * @param angle the angle of rotation in radians.
	 * 
	 */
	public void set(Vector3f axis, float angle);

	/**
	 * Copies the value of this axis-angle into the specified array.
	 * 
	 * @param array the array to copy this axis-angle values into.
	 */
	public void get(float[] array);

	/**
	 * Sets the value of this axis-angle to the rotational equivalent of the
	 * passed {@link Quaternion4f}.
	 * <br><br>
	 * If the specified {@link Quaternion4f} has no rotational component, the
	 * value of this AxisAngle4f is set to an angle of 0 about an axis of
	 * (0,1,0).
	 * 
	 * @param q the Quaternion4f
	 */
	public void set(Quaternion4f q);

	/**
	 * Sets the value of this axis-angle to the rotational component of the
	 * passed matrix.
	 * <br><br>
	 * If the specified {@link Matrix4f} has no rotational component, the value
	 * of this AxisAngle4f is set to an angle of 0 about an axis of (0,1,0).
	 * 
	 * @param m the matrix4f.
	 */
	public void set(Matrix4f m);

	/**
	 * Get the axis angle, in radians.<br>
	 * An axis angle is a rotation angle about the vector (x,y,z).
	 * 
	 * @return the angle, in radians.
	 */
	public float getAngle();

	/**
	 * Set the axis angle, in radians.<br>
	 * An axis angle is a rotation angle about the vector (x,y,z).
	 * 
	 * @param angle The angle to set, in radians.
	 */
	public void setAngle(float angle);

	/**
	 * Get value of <i>x</i> coordinate.
	 * 
	 * @return the <i>x</i> coordinate.
	 */
	public float getX();

	/**
	 * Set a new value for <i>x</i> coordinate.
	 * 
	 * @param x the <i>x</i> coordinate.
	 */
	public void setX(float x);

	/**
	 * Get value of <i>y</i> coordinate.
	 * 
	 * @return the <i>y</i> coordinate
	 */
	public float getY();

	/**
	 * Set a new value for <i>y</i> coordinate.
	 * 
	 * @param y the <i>y</i> coordinate.
	 */
	public void setY(float y);

	/**
	 * Get value of <i>z</i> coordinate.
	 * 
	 * @return the <i>z</i> coordinate.
	 */
	public float getZ();

	/**
	 * Set a new value for <i>z</i> coordinate.
	 * 
	 * @param z the <i>z</i> coordinate.
	 */
	public void setZ(float z);
}
