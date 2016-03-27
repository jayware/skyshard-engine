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


public class DefaultQuaternion4f
implements Quaternion4f
{
	private float x, y, z, w;

    @Override
    public void setIdentity()
    {
        this.x = 1;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }

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
	public void conjugate(Quaternion4f q)
	{
		this.x = -q.getX();
		this.y = -q.getY();
		this.z = -q.getZ();
		this.w =  q.getW();
	}

	@Override
	public void conjugate()
	{
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
	}


	@Override
	public void mul(Quaternion4f q1, Quaternion4f q2)
	{
		float x1 = q1.getX(), x2 = q2.getX(),
			  y1 = q1.getY(), y2 = q2.getY(),
			  z1 = q1.getZ(), z2 = q2.getZ(),
			  w1 = q1.getW(), w2 = q2.getW();

		this.x = w1 * x2 + w2 * x1 + y1 * z2 - z1 * y2;
		this.y = w1 * y2 + w2 * y1 - x1 * z2 + z1 * x2;
		this.z = w1 * z2 + w2 * z1 + x1 * y2 - y1 * x2;
		this.w = w1 * w2 - x1 * x2 - y1 * y2 - z1 * z2;
	}

	@Override
	public void mul(Quaternion4f q)
	{
		float x1 = this.x, x2 = q.getX(),
			  y1 = this.y, y2 = q.getY(),
			  z1 = this.z, z2 = q.getZ(),
			  w1 = this.w, w2 = q.getW();

		this.x = w1 * x2 + w2 * x1 + y1 * z2 - z1 * y2;
		this.y = w1 * y2 + w2 * y1 - x1 * z2 + z1 * x2;
		this.z = w1 * z2 + w2 * z1 + x1 * y2 - y1 * x2;
		this.w = w1 * w2 - x1 * x2 - y1 * y2 - z1 * z2;
	}

	@Override
	public void inverse(Quaternion4f q)
	{
	    float x = q.getX(),
		      y = q.getY(),
			  z = q.getZ(),
			  w = q.getW(),
			  norm;  

		norm = 1.0f/(w * w + x * x + y * y + z * z);

		this.x = x * -norm;
		this.y = y * -norm;
		this.z = z * -norm;
		this.w = w *  norm;
	}

	@Override
	public void inverse()
	{
	    float norm = 1.0f/(w * w + x * x + y * y + z * z);

	    this.x *= -norm;
	    this.y *= -norm;
	    this.z *= -norm;
	    this.w *=  norm;
	}

	@Override
	public void normalize(Quaternion4f q)
	{
	    float x = q.getX(),
	    	  y = q.getY(),
	    	  z = q.getZ(),
	    	  w = q.getW(),
	    	  norm;

	    norm = (x * x + y * y + z * z + w * w);

	    if (norm > 0.0)
	    {
	    	norm = 1.0f/(float)Math.sqrt(norm);
	    	this.x = x * norm;
	    	this.y = y * norm;
	    	this.z = z * norm;
	    	this.w = w * norm;
	    }
	    else
	    {
	    	this.x = 0.0f;
	    	this.y = 0.0f;
	    	this.z = 0.0f;
	    	this.w = 0.0f;
	    }
	}

	@Override
	public void normalize()
	{
	    float norm = (x * x + y * y + z * z + w * w);

	    if (norm > 0.0)
	    {
	    	norm = 1.0f/(float)Math.sqrt(norm);
	    	this.x *= norm;
	    	this.y *= norm;
	    	this.z *= norm;
	    	this.w *= norm;
	    }
	    else
	    {
	    	this.x = 0.0f;
	    	this.y = 0.0f;
	    	this.z = 0.0f;
	    	this.w = 0.0f;
	    }
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
	public void interpolate(Quaternion4f q, float alpha)
	{
		double x1 = this.x, x2 = q.getX(),
			   y1 = this.y, y2 = q.getY(),
			   z1 = this.z, z2 = q.getZ(),
			   w1 = this.w, w2 = q.getW(),
			   dot, s1, s2, om, sinom;

		dot = x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;

		if (dot < 0)
		{
			x2 = -x2;
			y2 = -y2;
			z2 = -z2;
			w2 = -w2;

			dot = -dot;
		}

		if ((1.0f - dot) > MathUtil.EPS)
		{
			om = Math.acos(dot);
			sinom = Math.sin(om);

			s1 = Math.sin((1.0 - alpha) * om) / sinom;
			s2 = Math.sin(alpha * om) / sinom;
		}
		else
		{
			s1 = 1.0 - alpha;
			s2 = alpha;
		}

		this.x = (float) (s1 * x1 + s2 * x2);
		this.y = (float) (s1 * y1 + s2 * y2);
		this.z = (float) (s1 * z1 + s2 * z2);
		this.w = (float) (s1 * w1 + s2 * w2);
	}

	@Override
	public void interpolate(Quaternion4f q1, Quaternion4f q2, float alpha)
	{
		double x1 = q1.getX(), x2 = q2.getX(),
			   y1 = q1.getY(), y2 = q2.getY(),
			   z1 = q1.getZ(), z2 = q2.getZ(),
			   w1 = q1.getW(), w2 = q2.getW(),
			   dot, s1, s2, om, sinom;

		dot = x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;

		if (dot < 0)
		{
			x2 = -x2;
			y2 = -y2;
			z2 = -z2;
			w2 = -w2;
			
			dot = -dot;
		}

		if ((1.0f - dot) > MathUtil.EPS)
		{
			om = Math.acos(dot);
			sinom = Math.sin(om);
			
			s1 = Math.sin((1.0 - alpha) * om) / sinom;
			s2 = Math.sin(alpha * om) / sinom;
		}
		else
		{
			s1 = 1.0 - alpha;
			s2 = alpha;
		}

		this.x = (float) (s1 * x1 + s2 * x2);
		this.y = (float) (s1 * y1 + s2 * y2);
		this.z = (float) (s1 * z1 + s2 * z2);
		this.w = (float) (s1 * w1 + s2 * w2);
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
	public boolean equals(Object obj)
	{
		if (obj instanceof Quaternion4f)
		{
			Quaternion4f vec = (Quaternion4f) obj;
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
