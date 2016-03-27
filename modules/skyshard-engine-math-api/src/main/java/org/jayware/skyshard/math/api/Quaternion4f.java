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
 * A 4 element unit quaternion represented by single precision floating 
 * point x,y,z,w coordinates.  The quaternion is always normalized.
 */
public interface Quaternion4f
extends Tuple4f
{
    /**
     * Sets this {@link Quaternion4f} to identity.
     */
    public void setIdentity();

    /**
     * Sets the value of this quaternion to the quaternion product of
     * quaternions q1 and q2 (this = q1 * q2).  
     * Note that this is safe for aliasing (e.g. this can be q1 or q2).
     * 
     * @param q1 the first quaternion
     * @param q2 the second quaternion
     */
    public void mul(Quaternion4f q1, Quaternion4f q2);

    /**
     * Sets the value of this quaternion to the quaternion product of
     * itself and q1 (this = this * q).
     * 
     * @param q the other quaternion.
     */
    public void mul(Quaternion4f q);

    /**
     * Sets the value of this quaternion to quaternion inverse of quaternion q.
     * 
     * @param q the quaternion to be inverted.
     */
    public void inverse(Quaternion4f q);

    /**
     * Sets the value of this quaternion to the quaternion inverse of itself.
     */
    public void inverse();

	/**
	 * Sets the value of this quaternion to the conjugate of quaternion q.
	 * 
	 * @param q the source vector
	 */
	public void conjugate(Quaternion4f q);

	/**
	 * Sets the value of this quaternion to the conjugate of itself.
	 */
	public void conjugate();

	/**
	 * Performs a great circle interpolation between this quaternion
	 * and the quaternion parameter and places the result into this
	 * quaternion.<br><br>
	 * 
	 * <i>
	 * From "Advanced Animation and Rendering Techniques"
	 * by Watt and Watt pg. 364, function as implemented appeared to be 
	 * incorrect. Fails to choose the same quaternion for the double
	 * covering. Resulting in change of direction for rotations.
	 * Fixed function to negate the first quaternion in the case that the
	 * dot product of q1 and this is negative. Second case was not needed. 
	 * </i>
	 * @param q the other quaternion
	 * @param alpha the alpha interpolation parameter
	 */
	public void interpolate(Quaternion4f q, float alpha);

	/** 
	 * Performs a great circle interpolation between quaternion q1
	 * and quaternion q2 and places the result into this quaternion.
	 * <br><br>
	 * <i>
	 * From "Advanced Animation and Rendering Techniques"
	 * by Watt and Watt pg. 364, function as implemented appeared to be
	 * incorrect.  Fails to choose the same quaternion for the double
	 * covering. Resulting in change of direction for rotations.
	 * Fixed function to negate the first quaternion in the case that the
	 * dot product of q1 and this is negative. Second case was not needed.
	 * </i>
	 * 
	 * @param q1 the first quaternion
	 * @param q2 the second quaternion
	 * @param alpha the alpha interpolation parameter 
	 */   
	public void interpolate(Quaternion4f q1, Quaternion4f q2, float alpha); 
	
	/**
	 * Sets the value of this quaternion to the normalized value
	 * of quaternion q.
	 * 
	 * @param q the quaternion to be normalized.
	 */
	public void normalize(Quaternion4f q);

	/**
	 * Normalizes the value of this quaternion in place.
	 */
	public void normalize();
}
