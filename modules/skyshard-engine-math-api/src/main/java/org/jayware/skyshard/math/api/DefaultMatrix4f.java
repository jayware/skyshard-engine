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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Objects;


public class DefaultMatrix4f
implements Matrix4f
{
	private static final float[] ZERO_VALUES = 	   {0.0f, 0.0f, 0.0f, 0.0f,
												    0.0f, 0.0f, 0.0f, 0.0f,
												    0.0f, 0.0f, 0.0f, 0.0f,
												    0.0f, 0.0f, 0.0f, 0.0f};

	private static final float[] IDENTITY_VALUES = {1.0f, 0.0f, 0.0f, 0.0f,
	    											0.0f, 1.0f, 0.0f, 0.0f,
	    											0.0f, 0.0f, 1.0f, 0.0f,
	    											0.0f, 0.0f, 0.0f, 1.0f};

	private static final int ELEMENT_COUNT = 16;
	private static final int ROW_COUNT = 4;
	private static final int COLUMN_COUNT = 4;

	private FloatBuffer buffer;
    private float[] cache = new float[ELEMENT_COUNT];


    public DefaultMatrix4f()
	{
		this.buffer = ByteBuffer.allocate(ELEMENT_COUNT * 4).asFloatBuffer();
		this.setZero();
	}

	@Override
	public float getElement(int index)
	{
		return buffer.get(index);
	}

	@Override
	public void setElement(int index, float value)
	{
		buffer.put(index, value);
	}

	@Override
	public float getElement(int row, int column)
	{
		final int index = COLUMN_COUNT * row + column;
		return buffer.get(index);
	}

	@Override
	public void setElement(int row, int column, float value)
	{
		final int index = COLUMN_COUNT * row + column;
		buffer.put(index, value);
	}

	@Override
	public float[] getElements(float[] array)
	{
		buffer.get(array);
		buffer.rewind();
        return array;
    }

	@Override
	public void setIdentity()
	{
		buffer.clear();
		buffer.put(IDENTITY_VALUES);
		buffer.flip();
	}

	@Override
	public void setZero()
	{
		buffer.clear();
		buffer.put(ZERO_VALUES);
		buffer.flip();
	}

	@Override
	public void add(float scalar)
	{
		buffer.put( 0, buffer.get( 0) + scalar);
		buffer.put( 1, buffer.get( 1) + scalar);
		buffer.put( 2, buffer.get( 2) + scalar);
		buffer.put( 3, buffer.get( 3) + scalar);
		buffer.put( 4, buffer.get( 4) + scalar);
		buffer.put( 5, buffer.get( 5) + scalar);
		buffer.put( 6, buffer.get( 6) + scalar);
		buffer.put( 7, buffer.get( 7) + scalar);
		buffer.put( 8, buffer.get( 8) + scalar);
		buffer.put( 9, buffer.get( 9) + scalar);
		buffer.put(10, buffer.get(10) + scalar);
		buffer.put(11, buffer.get(11) + scalar);
		buffer.put(12, buffer.get(12) + scalar);
		buffer.put(13, buffer.get(13) + scalar);
		buffer.put(14, buffer.get(14) + scalar);
		buffer.put(15, buffer.get(15) + scalar);
	}

	@Override
	public void add(float scalar, Matrix4f m)
	{
		buffer.put( 0, scalar + m.getElement( 0));
		buffer.put( 1, scalar + m.getElement( 1));
		buffer.put( 2, scalar + m.getElement( 2));
		buffer.put( 3, scalar + m.getElement( 3));
		buffer.put( 4, scalar + m.getElement( 4));
		buffer.put( 5, scalar + m.getElement( 5));
		buffer.put( 6, scalar + m.getElement( 6));
		buffer.put( 7, scalar + m.getElement( 7));
		buffer.put( 8, scalar + m.getElement( 8));
		buffer.put( 9, scalar + m.getElement( 9));
		buffer.put(10, scalar + m.getElement(10));
		buffer.put(11, scalar + m.getElement(11));
		buffer.put(12, scalar + m.getElement(12));
		buffer.put(13, scalar + m.getElement(13));
		buffer.put(14, scalar + m.getElement(14));
		buffer.put(15, scalar + m.getElement(15));
	}

	@Override
	public void add(Matrix4f m1, Matrix4f m2)
	{
		buffer.put( 0, m1.getElement( 0) + m2.getElement( 0));
		buffer.put( 1, m1.getElement( 1) + m2.getElement( 1));
		buffer.put( 2, m1.getElement( 2) + m2.getElement( 2));
		buffer.put( 3, m1.getElement( 3) + m2.getElement( 3));
		buffer.put( 4, m1.getElement( 4) + m2.getElement( 4));
		buffer.put( 5, m1.getElement( 5) + m2.getElement( 5));
		buffer.put( 6, m1.getElement( 6) + m2.getElement( 6));
		buffer.put( 7, m1.getElement( 7) + m2.getElement( 7));
		buffer.put( 8, m1.getElement( 8) + m2.getElement( 8));
		buffer.put( 9, m1.getElement( 9) + m2.getElement( 9));
		buffer.put(10, m1.getElement(10) + m2.getElement(10));
		buffer.put(11, m1.getElement(11) + m2.getElement(11));
		buffer.put(12, m1.getElement(12) + m2.getElement(12));
		buffer.put(13, m1.getElement(13) + m2.getElement(13));
		buffer.put(14, m1.getElement(14) + m2.getElement(14));
		buffer.put(15, m1.getElement(15) + m2.getElement(15));
	}

	@Override
	public void add(Matrix4f m)
	{
		buffer.put( 0, buffer.get( 0) + m.getElement( 0));
		buffer.put( 1, buffer.get( 1) + m.getElement( 1));
		buffer.put( 2, buffer.get( 2) + m.getElement( 2));
		buffer.put( 3, buffer.get( 3) + m.getElement( 3));
		buffer.put( 4, buffer.get( 4) + m.getElement( 4));
		buffer.put( 5, buffer.get( 5) + m.getElement( 5));
		buffer.put( 6, buffer.get( 6) + m.getElement( 6));
		buffer.put( 7, buffer.get( 7) + m.getElement( 7));
		buffer.put( 8, buffer.get( 8) + m.getElement( 8));
		buffer.put( 9, buffer.get( 9) + m.getElement( 9));
		buffer.put(10, buffer.get(10) + m.getElement(10));
		buffer.put(11, buffer.get(11) + m.getElement(11));
		buffer.put(12, buffer.get(12) + m.getElement(12));
		buffer.put(13, buffer.get(13) + m.getElement(13));
		buffer.put(14, buffer.get(14) + m.getElement(14));
		buffer.put(15, buffer.get(15) + m.getElement(15));
	}

	@Override
	public void sub(Matrix4f m1, Matrix4f m2)
	{
		buffer.put( 0, m1.getElement( 0) - m2.getElement( 0));
		buffer.put( 1, m1.getElement( 1) - m2.getElement( 1));
		buffer.put( 2, m1.getElement( 2) - m2.getElement( 2));
		buffer.put( 3, m1.getElement( 3) - m2.getElement( 3));
		buffer.put( 4, m1.getElement( 4) - m2.getElement( 4));
		buffer.put( 5, m1.getElement( 5) - m2.getElement( 5));
		buffer.put( 6, m1.getElement( 6) - m2.getElement( 6));
		buffer.put( 7, m1.getElement( 7) - m2.getElement( 7));
		buffer.put( 8, m1.getElement( 8) - m2.getElement( 8));
		buffer.put( 9, m1.getElement( 9) - m2.getElement( 9));
		buffer.put(10, m1.getElement(10) - m2.getElement(10));
		buffer.put(11, m1.getElement(11) - m2.getElement(11));
		buffer.put(12, m1.getElement(12) - m2.getElement(12));
		buffer.put(13, m1.getElement(13) - m2.getElement(13));
		buffer.put(14, m1.getElement(14) - m2.getElement(14));
		buffer.put(15, m1.getElement(15) - m2.getElement(15));
	}

	@Override
	public void sub(Matrix4f m)
	{
		buffer.put( 0, buffer.get( 0) - m.getElement( 0));
		buffer.put( 1, buffer.get( 1) - m.getElement( 1));
		buffer.put( 2, buffer.get( 2) - m.getElement( 2));
		buffer.put( 3, buffer.get( 3) - m.getElement( 3));
		buffer.put( 4, buffer.get( 4) - m.getElement( 4));
		buffer.put( 5, buffer.get( 5) - m.getElement( 5));
		buffer.put( 6, buffer.get( 6) - m.getElement( 6));
		buffer.put( 7, buffer.get( 7) - m.getElement( 7));
		buffer.put( 8, buffer.get( 8) - m.getElement( 8));
		buffer.put( 9, buffer.get( 9) - m.getElement( 9));
		buffer.put(10, buffer.get(10) - m.getElement(10));
		buffer.put(11, buffer.get(11) - m.getElement(11));
		buffer.put(12, buffer.get(12) - m.getElement(12));
		buffer.put(13, buffer.get(13) - m.getElement(13));
		buffer.put(14, buffer.get(14) - m.getElement(14));
		buffer.put(15, buffer.get(15) - m.getElement(15));
	}

	@Override
	public void transpose()
	{
		float[] temp = new float[16];

		buffer.get(temp);
		buffer.rewind();

		buffer.put( 0, temp[ 0]);
		buffer.put( 1, temp[ 4]);
		buffer.put( 2, temp[ 8]);
		buffer.put( 3, temp[12]);
		buffer.put( 4, temp[ 1]);
		buffer.put( 5, temp[ 5]);
		buffer.put( 6, temp[ 9]);
		buffer.put( 7, temp[13]);
		buffer.put( 8, temp[ 2]);
		buffer.put( 9, temp[ 6]);
		buffer.put(10, temp[10]);
		buffer.put(11, temp[14]);
		buffer.put(12, temp[ 3]);
		buffer.put(13, temp[ 7]);
		buffer.put(14, temp[11]);
		buffer.put(15, temp[15]);
	}

	@Override
	public void transpose(Matrix4f m)
	{
		buffer.put( 0, m.getElement( 0));
		buffer.put( 1, m.getElement( 4));
		buffer.put( 2, m.getElement( 8));
		buffer.put( 3, m.getElement(12));
		buffer.put( 4, m.getElement( 1));
		buffer.put( 5, m.getElement( 5));
		buffer.put( 6, m.getElement( 9));
		buffer.put( 7, m.getElement(13));
		buffer.put( 8, m.getElement( 2));
		buffer.put( 9, m.getElement( 6));
		buffer.put(10, m.getElement(10));
		buffer.put(11, m.getElement(14));
		buffer.put(12, m.getElement( 3));
		buffer.put(13, m.getElement( 7));
		buffer.put(14, m.getElement(11));
		buffer.put(15, m.getElement(15));
	}

	@Override
	public void invert()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void invert(Matrix4f m1)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public float determinant()
	{
		float[] matrix = new float[16];
		float det;

		buffer.get(matrix);
		buffer.rewind();

		det  = matrix[0]*(matrix[5]*matrix[10]*matrix[15]+ matrix[6]*matrix[11]*matrix[13] + matrix[7]*matrix[9]*matrix[14] - matrix[7]*matrix[10]*matrix[13] -matrix[5]*matrix[11]*matrix[14] - matrix[6]*matrix[9]*matrix[15]);
		det -= matrix[1]*(matrix[0]*matrix[10]*matrix[15]+ matrix[6]*matrix[11]*matrix[12] + matrix[7]*matrix[8]*matrix[14] - matrix[7]*matrix[10]*matrix[12] -matrix[4]*matrix[11]*matrix[14] - matrix[6]*matrix[8]*matrix[15]);
		det += matrix[2]*(matrix[4]*matrix[9]*matrix[15]+ matrix[5]*matrix[11]*matrix[12] + matrix[7]*matrix[8]*matrix[13] - matrix[7]*matrix[9]*matrix[12] -matrix[4]*matrix[11]*matrix[13] - matrix[5]*matrix[8]*matrix[15]);
		det -= matrix[3]*(matrix[4]*matrix[9]*matrix[14]+ matrix[5]*matrix[10]*matrix[12] + matrix[6]*matrix[8]*matrix[13] - matrix[6]*matrix[9]*matrix[12] -matrix[4]*matrix[10]*matrix[13] - matrix[5]*matrix[8]*matrix[14]);

		return det;
	}

	@Override
	public void setRotation(Quaternion4f q)
	{
		float x = q.getX(),
			  y = q.getY(),
			  z = q.getZ(),
			  w = q.getW();

		double[] tmp_rot = new double[9];
		double[] tmp_scale = new double[3];

		getScaleRotate(tmp_scale, tmp_rot);

		buffer.put( 0, (float)((1.0f - 2.0f * y * y - 2.0f * z * z) * tmp_scale[0]));
		buffer.put( 4, (float)((2.0f * (x * y + w * z)) * tmp_scale[0]));
		buffer.put( 8, (float)((2.0f * (x * z - w * y)) * tmp_scale[0]));
		buffer.put( 1, (float)((2.0f * (x * y - w * z)) * tmp_scale[1]));
		buffer.put( 5, (float)((1.0f - 2.0f * x * x - 2.0f * z * z) * tmp_scale[1]));
		buffer.put( 9, (float)((2.0f * (y * z + w * x)) * tmp_scale[1]));
		buffer.put( 2, (float)((2.0f * (x * z + w * y)) * tmp_scale[2]));
		buffer.put( 6, (float)((2.0f * (y * z - w * x)) * tmp_scale[2]));
		buffer.put(10, (float)((1.0f - 2.0f * x * x - 2.0f * y * y) * tmp_scale[2]));
	}

	@Override
	public void setTranslation(float x, float y, float z)
	{
		buffer.put( 3, x);
		buffer.put( 7, y);
		buffer.put(11, z);
	}

	@Override
	public void setTranslation(Vector3f vec)
	{
		buffer.put( 3, vec.getX());
		buffer.put( 7, vec.getY());
		buffer.put(11, vec.getZ());
	}

	@Override
	public void setTranslation(Vector4f vec)
	{
		buffer.put( 3, vec.getX());
		buffer.put( 7, vec.getY());
		buffer.put(11, vec.getZ());
	}

	@Override
	public void rotX(float angle)
	{
		float	sinAngle, cosAngle;

		sinAngle = (float) Math.sin((double) angle);
		cosAngle = (float) Math.cos((double) angle);

		buffer.put( 0, 1.0f);
		buffer.put( 1, 0.0f);
		buffer.put( 2, 0.0f);
		buffer.put( 3, 0.0f);

		buffer.put( 4, 0.0f);
		buffer.put( 5, cosAngle);
		buffer.put( 6, -sinAngle);
		buffer.put( 7, 0.0f);

		buffer.put( 8, 0.0f);
		buffer.put( 9, sinAngle);
		buffer.put(10, cosAngle);
		buffer.put(11, 0.0f);

		buffer.put(12, 0.0f);
		buffer.put(13, 0.0f);
		buffer.put(14, 0.0f);
		buffer.put(15, 1.0f);
	}

	@Override
	public void rotY(float angle)
	{
		float	sinAngle, cosAngle;

		sinAngle = (float) Math.sin((double) angle);
		cosAngle = (float) Math.cos((double) angle);

		buffer.put( 0, cosAngle);
		buffer.put( 1, 0.0f);
		buffer.put( 2, sinAngle);
		buffer.put( 3, 0.0f);

		buffer.put( 4, 0.0f);
		buffer.put( 5, 1.0f);
		buffer.put( 6, 0.0f);
		buffer.put( 7, 0.0f);

		buffer.put( 8, -sinAngle);
		buffer.put( 9, 0.0f);
		buffer.put(10, cosAngle);
		buffer.put(11, 0.0f);

		buffer.put(12, 0.0f);
		buffer.put(13, 0.0f);
		buffer.put(14, 0.0f);
		buffer.put(15, 1.0f);
	}

	@Override
	public void rotZ(float angle)
	{
		float	sinAngle, cosAngle;

		sinAngle = (float) Math.sin((double) angle);
		cosAngle = (float) Math.cos((double) angle);

		buffer.put( 0, cosAngle);
		buffer.put( 1, -sinAngle);
		buffer.put( 2, 0.0f);
		buffer.put( 3, 0.0f);

		buffer.put( 4, sinAngle);
		buffer.put( 5, cosAngle);
		buffer.put( 6, 0.0f);
		buffer.put( 7, 0.0f);

		buffer.put( 8, 0.0f);
		buffer.put( 9, 0.0f);
		buffer.put(10, 1.0f);
		buffer.put(11, 0.0f);

		buffer.put(12, 0.0f);
		buffer.put(13, 0.0f);
		buffer.put(14, 0.0f);
		buffer.put(15, 1.0f);
	}

	@Override
	public void mul(float scalar)
	{
		buffer.put( 0, buffer.get( 0) * scalar);
		buffer.put( 1, buffer.get( 1) * scalar);
		buffer.put( 2, buffer.get( 2) * scalar);
		buffer.put( 3, buffer.get( 3) * scalar);
		buffer.put( 4, buffer.get( 4) * scalar);
		buffer.put( 5, buffer.get( 5) * scalar);
		buffer.put( 6, buffer.get( 6) * scalar);
		buffer.put( 7, buffer.get( 7) * scalar);
		buffer.put( 8, buffer.get( 8) * scalar);
		buffer.put( 9, buffer.get( 9) * scalar);
		buffer.put(10, buffer.get(10) * scalar);
		buffer.put(11, buffer.get(11) * scalar);
		buffer.put(12, buffer.get(12) * scalar);
		buffer.put(13, buffer.get(13) * scalar);
		buffer.put(14, buffer.get(14) * scalar);
		buffer.put(15, buffer.get(15) * scalar);
	}

	@Override
	public void mul(float scalar, Matrix4f m)
	{
		buffer.put( 0, m.getElement( 0) * scalar);
		buffer.put( 1, m.getElement( 1) * scalar);
		buffer.put( 2, m.getElement( 2) * scalar);
		buffer.put( 3, m.getElement( 3) * scalar);
		buffer.put( 4, m.getElement( 4) * scalar);
		buffer.put( 5, m.getElement( 5) * scalar);
		buffer.put( 6, m.getElement( 6) * scalar);
		buffer.put( 7, m.getElement( 7) * scalar);
		buffer.put( 8, m.getElement( 8) * scalar);
		buffer.put( 9, m.getElement( 9) * scalar);
		buffer.put(10, m.getElement(10) * scalar);
		buffer.put(11, m.getElement(11) * scalar);
		buffer.put(12, m.getElement(12) * scalar);
		buffer.put(13, m.getElement(13) * scalar);
		buffer.put(14, m.getElement(14) * scalar);
		buffer.put(15, m.getElement(15) * scalar);
	}

	@Override
	public void mul(Matrix4f m)
	{
		float[] valuesA = new float[16];
		float[] valuesB = new float[16];

		buffer.get(valuesA);
		buffer.rewind();

		m.getElements(valuesB);

		buffer.put(0,  valuesA[ 0] * valuesB[ 0] + valuesA[ 1] * valuesB[ 4] + 
						    valuesA[ 2] * valuesB[ 8] + valuesA[ 3] * valuesB[12]);
		buffer.put(1,  valuesA[ 0] * valuesB[ 1] + valuesA[ 1] * valuesB[ 5] + 
						    valuesA[ 2] * valuesB[ 9] + valuesA[ 3] * valuesB[13]);
		buffer.put(2,  valuesA[ 0] * valuesB[ 2] + valuesA[ 1] * valuesB[ 6] + 
						    valuesA[ 2] * valuesB[10] + valuesA[ 3] * valuesB[14]);
		buffer.put(3,  valuesA[ 0] * valuesB[ 3] + valuesA[ 1] * valuesB[ 7] + 
						    valuesA[ 2] * valuesB[11] + valuesA[ 3] * valuesB[15]);

		buffer.put(4,  valuesA[ 4] * valuesB[ 0] + valuesA[ 5] * valuesB[ 4] + 
						    valuesA[ 6] * valuesB[ 8] + valuesA[ 7] * valuesB[12]); 
		buffer.put(5,  valuesA[ 4] * valuesB[ 1] + valuesA[ 5] * valuesB[ 5] + 
        				    valuesA[ 6] * valuesB[ 9] + valuesA[ 7] * valuesB[13]);
		buffer.put(6,  valuesA[ 4] * valuesB[ 2] + valuesA[ 5] * valuesB[ 6] + 
        				    valuesA[ 6] * valuesB[10] + valuesA[ 7] * valuesB[14]);
		buffer.put(7,  valuesA[ 4] * valuesB[ 3] + valuesA[ 5] * valuesB[ 7] + 
        				    valuesA[ 6] * valuesB[11] + valuesA[ 7] * valuesB[15]);

		buffer.put(8,  valuesA[ 8] * valuesB[ 0] + valuesA[ 9] * valuesB[ 4] + 
        				    valuesA[10] * valuesB[ 8] + valuesA[11] * valuesB[12]); 
		buffer.put(9,  valuesA[ 8] * valuesB[ 1] + valuesA[ 9] * valuesB[ 5] + 
        				    valuesA[10] * valuesB[ 9] + valuesA[11] * valuesB[13]);
		buffer.put(10, valuesA[ 8] * valuesB[ 2] + valuesA[ 9] * valuesB[ 6] + 
        				    valuesA[10] * valuesB[10] + valuesA[11] * valuesB[14]);
		buffer.put(11, valuesA[ 8] * valuesB[ 3] + valuesA[ 9] * valuesB[ 7] + 
        				    valuesA[10] * valuesB[11] + valuesA[11] * valuesB[15]);

		buffer.put(12, valuesA[12] * valuesB[ 0] + valuesA[13] * valuesB[ 4] + 
        				    valuesA[14] * valuesB[ 8] + valuesA[15] * valuesB[12]); 
		buffer.put(13, valuesA[12] * valuesB[ 1] + valuesA[13] * valuesB[ 5] + 
        				    valuesA[14] * valuesB[ 9] + valuesA[15] * valuesB[13]);
		buffer.put(14, valuesA[12] * valuesB[ 2] + valuesA[13] * valuesB[ 6] + 
        				    valuesA[14] * valuesB[10] + valuesA[15] * valuesB[14]);
		buffer.put(15, valuesA[12] * valuesB[ 3] + valuesA[13] * valuesB[ 7] + 
        				    valuesA[14] * valuesB[11] + valuesA[15] * valuesB[15]);
	}

	@Override
	public void mul(Matrix4f m1, Matrix4f m2)
	{
		float[] valuesA = new float[16];
		float[] valuesB = new float[16];

		m1.getElements(valuesA);
		m2.getElements(valuesB);

		buffer.put(0,  valuesA[ 0] * valuesB[ 0] + valuesA[ 1] * valuesB[ 4] + 
						    valuesA[ 2] * valuesB[ 8] + valuesA[ 3] * valuesB[12]);
		buffer.put(1,  valuesA[ 0] * valuesB[ 1] + valuesA[ 1] * valuesB[ 5] + 
						    valuesA[ 2] * valuesB[ 9] + valuesA[ 3] * valuesB[13]);
		buffer.put(2,  valuesA[ 0] * valuesB[ 2] + valuesA[ 1] * valuesB[ 6] + 
						    valuesA[ 2] * valuesB[10] + valuesA[ 3] * valuesB[14]);
		buffer.put(3,  valuesA[ 0] * valuesB[ 3] + valuesA[ 1] * valuesB[ 7] + 
						    valuesA[ 2] * valuesB[11] + valuesA[ 3] * valuesB[15]);

		buffer.put(4,  valuesA[ 4] * valuesB[ 0] + valuesA[ 5] * valuesB[ 4] + 
						    valuesA[ 6] * valuesB[ 8] + valuesA[ 7] * valuesB[12]); 
		buffer.put(5,  valuesA[ 4] * valuesB[ 1] + valuesA[ 5] * valuesB[ 5] + 
        				    valuesA[ 6] * valuesB[ 9] + valuesA[ 7] * valuesB[13]);
		buffer.put(6,  valuesA[ 4] * valuesB[ 2] + valuesA[ 5] * valuesB[ 6] + 
        				    valuesA[ 6] * valuesB[10] + valuesA[ 7] * valuesB[14]);
		buffer.put(7,  valuesA[ 4] * valuesB[ 3] + valuesA[ 5] * valuesB[ 7] + 
        				    valuesA[ 6] * valuesB[11] + valuesA[ 7] * valuesB[15]);

		buffer.put(8,  valuesA[ 8] * valuesB[ 0] + valuesA[ 9] * valuesB[ 4] + 
        				    valuesA[10] * valuesB[ 8] + valuesA[11] * valuesB[12]); 
		buffer.put(9,  valuesA[ 8] * valuesB[ 1] + valuesA[ 9] * valuesB[ 5] + 
        				    valuesA[10] * valuesB[ 9] + valuesA[11] * valuesB[13]);
		buffer.put(10, valuesA[ 8] * valuesB[ 2] + valuesA[ 9] * valuesB[ 6] + 
        				    valuesA[10] * valuesB[10] + valuesA[11] * valuesB[14]);
		buffer.put(11, valuesA[ 8] * valuesB[ 3] + valuesA[ 9] * valuesB[ 7] + 
        				    valuesA[10] * valuesB[11] + valuesA[11] * valuesB[15]);

		buffer.put(12, valuesA[12] * valuesB[ 0] + valuesA[13] * valuesB[ 4] + 
        				    valuesA[14] * valuesB[ 8] + valuesA[15] * valuesB[12]); 
		buffer.put(13, valuesA[12] * valuesB[ 1] + valuesA[13] * valuesB[ 5] + 
        				    valuesA[14] * valuesB[ 9] + valuesA[15] * valuesB[13]);
		buffer.put(14, valuesA[12] * valuesB[ 2] + valuesA[13] * valuesB[ 6] + 
        				    valuesA[14] * valuesB[10] + valuesA[15] * valuesB[14]);
		buffer.put(15, valuesA[12] * valuesB[ 3] + valuesA[13] * valuesB[ 7] + 
        				    valuesA[14] * valuesB[11] + valuesA[15] * valuesB[15]);
	}

	@Override
	public void negate()
	{
		buffer.put( 0, -buffer.get( 0));
		buffer.put( 1, -buffer.get( 1));
		buffer.put( 2, -buffer.get( 2));
		buffer.put( 3, -buffer.get( 3));
		buffer.put( 4, -buffer.get( 4));
		buffer.put( 5, -buffer.get( 5));
		buffer.put( 6, -buffer.get( 6));
		buffer.put( 7, -buffer.get( 7));
		buffer.put( 8, -buffer.get( 8));
		buffer.put( 9, -buffer.get( 9));
		buffer.put(10, -buffer.get(10));
		buffer.put(11, -buffer.get(11));
		buffer.put(12, -buffer.get(12));
		buffer.put(13, -buffer.get(13));
		buffer.put(14, -buffer.get(14));
		buffer.put(15, -buffer.get(15));
	}

	@Override
	public void negate(Matrix4f m)
	{
		buffer.put( 0, -m.getElement( 0));
		buffer.put( 1, -m.getElement( 1));
		buffer.put( 2, -m.getElement( 2));
		buffer.put( 3, -m.getElement( 3));
		buffer.put( 4, -m.getElement( 4));
		buffer.put( 5, -m.getElement( 5));
		buffer.put( 6, -m.getElement( 6));
		buffer.put( 7, -m.getElement( 7));
		buffer.put( 8, -m.getElement( 8));
		buffer.put( 9, -m.getElement( 9));
		buffer.put(10, -m.getElement(10));
		buffer.put(11, -m.getElement(11));
		buffer.put(12, -m.getElement(12));
		buffer.put(13, -m.getElement(13));
		buffer.put(14, -m.getElement(14));
		buffer.put(15, -m.getElement(15));
	}

	@Override
    public void set(Quaternion4f rotation, Vector4f translation)
    {
        float x = rotation.getX();
        float y = rotation.getY();
        float z = rotation.getZ();
        float w = rotation.getW();

        buffer.put(0, (float) (1.0 - 2.0 * y * y - 2.0 * z * z));
        buffer.put(4, (float) (2.0 * (x * y + w * z)));
        buffer.put(8, (float) (2.0 * (x * z - w * y)));

        buffer.put(1, (float) (2.0 * (x * y - w * z)));
        buffer.put(5, (float) (1.0 - 2.0 * x * x - 2.0 * z * z));
        buffer.put(9, (float) (2.0 * (y * z + w * x)));

        buffer.put(2, (float) (2.0 * (x * z + w * y)));
        buffer.put(6, (float) (2.0 * (y * z - w * x)));
        buffer.put(10,(float) (1.0 - 2.0 * x * x - 2.0 * y * y));

        buffer.put(3,  translation.getX());
        buffer.put(7,  translation.getY());
        buffer.put(11, translation.getZ());

        buffer.put(12, 0.0f);
        buffer.put(13, 0.0f);
        buffer.put(14, 0.0f);
        buffer.put(15, 1.0f);
    }

    @Override
    public void set(Quaternion4f rotation, Vector4f translation, float s)
    {
        Quaternion4f q1 = rotation;
        Vector4f t1 = translation;

        float x = q1.getX();
        float y = q1.getY();
        float z = q1.getZ();
        float w = q1.getW();

        buffer.put(0, (float) (s * (1.0 - 2.0 * y * y - 2.0 * z * z)));
        buffer.put(4, (float) (s * (2.0 * (x * y + w * z))));
        buffer.put(8, (float) (s * (2.0 * (x * z - w * y))));

        buffer.put(1, (float) (s * (2.0 * (x * y - w * z))));
        buffer.put(5, (float) (s * (1.0 - 2.0 * x * x - 2.0 * z * z)));
        buffer.put(9, (float) (s * (2.0 * (y * z + w * x))));

        buffer.put(2, (float) (s * (2.0 * (x * z + w * y))));
        buffer.put(6, (float) (s * (2.0 * (y * z - w * x))));
        buffer.put(10,(float) (s * (1.0 - 2.0 * x * x - 2.0 * y * y)));

        buffer.put(3, t1.getX());
        buffer.put(7, t1.getY());
        buffer.put(11, t1.getZ());

        buffer.put(12, 0.0f);
        buffer.put(13, 0.0f);
        buffer.put(14, 0.0f);
        buffer.put(15, 1.0f);
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ELEMENT_COUNT; ++i)
        {
            result.append(buffer.get(i));

            if ((i + 1) % 4 == 0) result.append("\n");
            else result.append(" ");
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Matrix4f)
        {
            Matrix4f other = (Matrix4f) obj;
            for (int i = 0; i < ELEMENT_COUNT; ++i)
            {
                if (other.getElement(i) != this.getElement(i))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getElements(cache));
    }

    private void getScaleRotate(double scales[], double rots[])
	{
		double[]    tmp = new double[9];

		tmp[0] = buffer.get(0);
		tmp[1] = buffer.get(1);
		tmp[2] = buffer.get(2);

		tmp[3] = buffer.get(4);
		tmp[4] = buffer.get(5);
		tmp[5] = buffer.get(6);

		tmp[6] = buffer.get(8);
		tmp[7] = buffer.get(9);
		tmp[8] = buffer.get(10);

		MathUtil.compute_svd(tmp, scales, rots);
	}
}
