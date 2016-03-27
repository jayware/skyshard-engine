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

public class DefaultAxisAngle4f
implements AxisAngle4f
{
	private float x, y, z, angle;

	@Override
	public void set(float x, float y, float z, float angle)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.angle = angle;
	}

	@Override
	public void set(float[] array)
	{
		this.x = array[0];
		this.y = array[1];
		this.z = array[2];
		this.angle = array[3];
	}

	@Override
	public void set(AxisAngle4f a)
	{
		this.x = a.getX();
		this.y = a.getY();
		this.z = a.getZ();
		this.angle = a.getAngle();
	}

	@Override
	public void set(Vector3f axis, float angle)
	{
		this.x = axis.getX();
		this.y = axis.getY();
		this.z = axis.getZ();
		this.angle = angle;
	}

	@Override
	public void get(float[] array)
	{
		array[0] = x;
		array[1] = y;
		array[2] = z;
		array[3] = angle;
	}

	@Override
	public void set(Quaternion4f q)
	{
		float x = q.getX(),
			  y = q.getY(),
			  z = q.getZ(),
			  w = q.getW();

		double mag = x * x + y * y + z * z;  

		if (mag > MathUtil.EPS)
		{
			mag = Math.sqrt(mag);
			double invMag = 1.0/mag;

			this.x = (float)(x * invMag);
			this.y = (float)(y * invMag);
			this.z = (float)(z * invMag);
			this.angle = (float)(2.0 * Math.atan2(mag, w)); 
		}
		else
		{
			this.x = 0.0f;
			this.y = 1.0f;
			this.z = 0.0f;
			this.angle = 0.0f;
		}
	}

	@Override
	public void set(Matrix4f m)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public float getAngle()
	{
		return angle;
	}

	@Override
	public void setAngle(float angle)
	{
		this.angle = angle;
	}

	@Override
	public float getX()
	{
		return x;
	}

	@Override
	public void setX(float x)
	{
		this.x = x;
	}

	@Override
	public float getY()
	{
		return y;
	}

	@Override
	public void setY(float y)
	{
		this.y = y;
	}

	@Override
	public float getZ()
	{
		return z;
	}

	@Override
	public void setZ(float z)
	{
		this.z = z;
	}
}
