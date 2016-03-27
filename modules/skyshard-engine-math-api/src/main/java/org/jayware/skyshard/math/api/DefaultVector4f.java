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

import java.util.Objects;

import static java.lang.Math.abs;
import static org.jayware.skyshard.math.api.MathUtil.EPS;


public class DefaultVector4f
implements Vector4f
{
	private float x, y, z, w;

	@Override
	public void setZero()
	{
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
		this.w = 0f;
	}

	@Override
	public void add(Tuple4f t1, Tuple4f t2)
	{
	    this.x = t1.getX() + t2.getX();
	    this.y = t1.getY() + t2.getY();
	    this.z = t1.getZ() + t2.getZ();
	    this.w = t1.getW() + t2.getW();
	}

	@Override
	public void add(Tuple4f t)
	{
		this.x += t.getX();
		this.y += t.getY();
		this.z += t.getZ();
		this.w += t.getW();
	}

	@Override
	public void sub(Tuple4f t1, Tuple4f t2)
	{
	    this.x = t1.getX() - t2.getX();
	    this.y = t1.getY() - t2.getY();
	    this.z = t1.getZ() - t2.getZ();
	    this.w = t1.getW() - t2.getW();
	}

	@Override
	public void sub(Tuple4f t)
	{
		this.x -= t.getX();
		this.y -= t.getY();
		this.z -= t.getZ();
		this.w -= t.getW();
	}

	@Override
	public void scale(float s, Tuple4f t)
	{
	    this.x = s * t.getX();
	    this.y = s * t.getY();
	    this.z = s * t.getZ();
	    this.w = s * t.getW();
	}

	@Override
	public void scale(float s)
	{
	    this.x *= s;
	    this.y *= s;
	    this.z *= s;
	    this.w *= s;
	}

	@Override
	public float[] get(float[] values)
	{
		values[0] = x;
		values[1] = y;
		values[2] = z;
		values[3] = w;

		return values;
	}

	@Override
	public void set(float[] values)
	{
		x = values[0];
		y = values[1];
		z = values[2];
		w = values[3];
	}

	@Override
	public Tuple4f get(Tuple4f t)
	{
		float[] tmp = {x, y, z, w};

		t.set(tmp);

		return t;
	}

	@Override
	public void set(Tuple4f t)
	{
		x = t.getX();
		y = t.getY();
		z = t.getZ();
		w = t.getW();
	}

	@Override
	public void set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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

	@Override
	public float getW()
	{
		return w;
	}

	@Override
	public void setW(float w)
	{
		this.w = w;
	}

	@Override
	public float dot(Vector4f vec)
	{
		return (this.x * vec.getX() + this.y * vec.getY() +
				this.z * vec.getZ() + this.w * vec.getW());
	}

	@Override
	public void normalize(Vector4f vec)
	{
	    float x = vec.getX(),
	    	  y = vec.getY(),
	    	  z = vec.getZ(),
	    	  w = vec.getW(),
	    	  norm;

	    norm = (float) (1.0/Math.sqrt(x * x + y * y + z * z + w * w));

	    this.x = x * norm;
	    this.y = y * norm;
	    this.z = z * norm;
	    this.w = w * norm;
	}

	@Override
	public void normalize()
	{
	    float norm = (float) (1.0/Math.sqrt(x * x + y * y + z * z + w * w));

	    this.x *= norm;
	    this.y *= norm;
	    this.z *= norm;
	    this.w *= norm;
	}

	@Override
	public void negate()
	{
	    this.x = -this.x;
	    this.y = -this.y;
	    this.z = -this.z;
	    this.w = -this.w;
	}

	@Override
	public void negate(Tuple4f t)
	{
	    this.x = -t.getX();
	    this.y = -t.getY();
	    this.z = -t.getZ();
	    this.w = -t.getW();
	}

	@Override
	public float length()
	{
	    return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	@Override
	public float lengthSquared()
	{
		return (x * x + y * y + z * z + w * w);
	}

	@Override
	public float angle(Vector4f vec)
	{
		double vDot = this.dot(vec) / (this.length() * vec.length());

		if( vDot < -1.0) vDot = -1.0;
		if( vDot >  1.0) vDot =  1.0;

		return((float) (Math.acos( vDot )));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Vector4f)
		{
			Vector4f vec = (Vector4f) obj;
			return (abs(this.x - vec.getX()) < EPS && abs(this.y - vec.getY()) < EPS &&
	                abs(this.z - vec.getZ()) < EPS && abs(this.w - vec.getW()) < EPS);
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(x, y, z, w);
	}
}
