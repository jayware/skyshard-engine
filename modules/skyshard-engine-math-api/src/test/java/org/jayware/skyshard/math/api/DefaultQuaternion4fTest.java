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

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


public class DefaultQuaternion4fTest
{
	private final float xValueA = 0.73f;
	private final float yValueA = 46.2f;
	private final float zValueA = 48.8f;
	private final float wValueA = 13.37f;

	private final float xValueB = 12.12f;
	private final float yValueB = -3.14f;
	private final float zValueB = 24.88f;
	private final float wValueB = -5.01f;

	private final float[] valuesA = {xValueA, yValueA, zValueA, wValueA};
	private final float[] valuesB = {xValueB, yValueB, zValueB, wValueB};

	private final float scaleValueA = (float) Math.PI;
	private final float alphaValueA = (float) Math.E;

	private DefaultQuaternion4f quatUnderTest;

	private Field xField;
	private Field yField;
	private Field zField;
	private Field wField;

	@Mock
	private Quaternion4f quatToTestA;

	@Mock
	private Quaternion4f quatToTestB;

	private javax.vecmath.Quat4f quatToValidateA;
	private javax.vecmath.Quat4f quatToValidateB;
	private javax.vecmath.Quat4f quatToValidateC;

	private float[] tmp;

	@BeforeTest
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		quatUnderTest = new DefaultQuaternion4f();

		@SuppressWarnings("rawtypes")
		Class vectorClass = DefaultQuaternion4f.class;
		xField = vectorClass.getDeclaredField("x");
		xField.setAccessible(true);
		yField = vectorClass.getDeclaredField("y");
		yField.setAccessible(true);
		zField = vectorClass.getDeclaredField("z");
		zField.setAccessible(true);
		wField = vectorClass.getDeclaredField("w");
		wField.setAccessible(true);

		when(quatToTestA.getX()).thenReturn(xValueA);
		when(quatToTestA.getY()).thenReturn(yValueA);
		when(quatToTestA.getZ()).thenReturn(zValueA);
		when(quatToTestA.getW()).thenReturn(wValueA);

		doAnswer(new Answer<float[]>()
		{
			@Override
			public float[] answer(InvocationOnMock invocation) throws Throwable
			{
				float[] array = (float[]) invocation.getArguments()[0];
				quatToValidateA.set(array);
				return array;
			}
		}).when(quatToTestA).set(any(float[].class));

		when(quatToTestB.getX()).thenReturn(xValueB);
		when(quatToTestB.getY()).thenReturn(yValueB);
		when(quatToTestB.getZ()).thenReturn(zValueB);
		when(quatToTestB.getW()).thenReturn(wValueB);

		quatToValidateA = new javax.vecmath.Quat4f();
	    quatToValidateB = new javax.vecmath.Quat4f();
        quatToValidateC = new javax.vecmath.Quat4f();
 
        tmp = new float[4];
	}

	@AfterTest
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testSetZero()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.setZero();
		quatToValidateB.set(0f, 0f, 0f, 0f);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							yField.getFloat(quatUnderTest),
							zField.getFloat(quatUnderTest),
							wField.getFloat(quatUnderTest));

		assertEquals(quatToValidateA, quatToValidateB);
	}

	@Test
	public void testAddTuple4fTuple4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.add(quatToTestA, quatToTestB);

		quatToValidateA.set(valuesA);
		quatToValidateB.set(valuesB);
		quatToValidateC.add(quatToValidateA, quatToValidateB);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		assertEquals(quatToValidateC, quatToValidateA);
	}

	@Test
	public void testAddTuple4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.add(quatToTestB);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateC.set(valuesB);
		quatToValidateB.add(quatToValidateC);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testSubTuple4fTuple4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.sub(quatToTestA, quatToTestB);

		quatToValidateA.set(valuesA);
		quatToValidateB.set(valuesB);
		quatToValidateC.sub(quatToValidateA, quatToValidateB);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		assertEquals(quatToValidateC, quatToValidateA);
	}

	@Test
	public void testSubTuple4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.sub(quatToTestB);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateC.set(valuesB);
		quatToValidateB.sub(quatToValidateC);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testScaleTuple()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.scale(scaleValueA, quatToTestA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateC.set(valuesA);
		quatToValidateB.scale(scaleValueA, quatToValidateC);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testScale()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.scale(scaleValueA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateB.scale(scaleValueA);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testConjugateQuaternion()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.conjugate(quatToTestA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateC.conjugate(quatToValidateB);

		assertEquals(quatToValidateC, quatToValidateA);
	}

	@Test
	public void testConjugate()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.conjugate();

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateB.conjugate();

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testMulQuaternions()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.mul(quatToTestA, quatToTestB);

		quatToValidateA.set(valuesA);
		quatToValidateB.set(valuesB);
		quatToValidateC.mul(quatToValidateA, quatToValidateB);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		assertEquals(quatToValidateC, quatToValidateA);
	}

	@Test
	public void testMulQuaternion()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.mul(quatToTestB);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateC.set(valuesB);
		quatToValidateB.mul(quatToValidateC);

		assertEquals(quatToValidateB, quatToValidateA);
		
	}

	@Test
	public void testInverseQuaternion()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.inverse(quatToTestA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateC.set(valuesA);
		quatToValidateB.inverse(quatToValidateC);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testInverse()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.inverse();

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateB.inverse();

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testNormalizeQuaternion()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.normalize(quatToTestA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateC.set(valuesA);
		quatToValidateB.normalize(quatToValidateC);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testNormalize()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.normalize();

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateB.normalize();

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testNegate()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.negate();

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateB.negate();

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testNegateVector4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.negate(quatToTestB);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateC.set(valuesB);
		quatToValidateB.negate(quatToValidateC);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testInterpolateQuaternion()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.interpolate(quatToTestB, alphaValueA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);
		quatToValidateC.set(valuesB);
		quatToValidateB.interpolate(quatToValidateC, alphaValueA);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testInterpolateQuaternions()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.interpolate(quatToTestA, quatToTestB, alphaValueA);

		quatToValidateA.set(valuesA);
		quatToValidateB.set(valuesB);
		quatToValidateC.interpolate(quatToValidateA, quatToValidateB, alphaValueA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
				  			  yField.getFloat(quatUnderTest),
				  			  zField.getFloat(quatUnderTest),
				  			  wField.getFloat(quatUnderTest));

		assertEquals(quatToValidateC, quatToValidateA);
	}

	@Test
	public void testGetFloats()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);
	
		quatUnderTest.get(tmp);
		quatToValidateA.set(tmp);
	
		quatToValidateB.set(valuesA);
	
		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testSetFloats()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.set(valuesA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateB.set(valuesA);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testGetTuple()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);
		yField.set(quatUnderTest, yValueA);
		zField.set(quatUnderTest, zValueA);
		wField.set(quatUnderTest, wValueA);

		quatUnderTest.get(quatToTestA);

		quatToValidateC.set(valuesA);
		quatToValidateC.get(quatToValidateB);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testSetTuple()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.set(quatToTestA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
							  yField.getFloat(quatUnderTest),
							  zField.getFloat(quatUnderTest),
							  wField.getFloat(quatUnderTest));

		quatToValidateC.set(valuesA);
		quatToValidateB.set(quatToValidateC);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testSetXYZW()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.set(xValueA, yValueA, zValueA, wValueA);

		quatToValidateA.set(xField.getFloat(quatUnderTest),
				  			  yField.getFloat(quatUnderTest),
				  			  zField.getFloat(quatUnderTest),
				  			  wField.getFloat(quatUnderTest));

		quatToValidateB.set(xValueA, yValueA, zValueA, wValueA);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testGetX()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(quatUnderTest, xValueA);

		quatToValidateA.set(quatUnderTest.getX(), 0, 0, 0);

		quatToValidateB.set(xValueA, 0, 0, 0);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testSetX()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.setX(xValueA);

		quatToValidateA.set(xField.getFloat(quatUnderTest), 0, 0, 0);

		quatToValidateB.set(xValueA, 0, 0, 0);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testGetY()
	throws IllegalArgumentException, IllegalAccessException
	{
		yField.set(quatUnderTest, yValueA);

		quatToValidateA.set(0, quatUnderTest.getY(), 0, 0);

		quatToValidateB.set(0, yValueA, 0, 0);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testSetY()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.setY(yValueA);

		quatToValidateA.set(0, yField.getFloat(quatUnderTest), 0, 0);

		quatToValidateB.set(0, yValueA, 0, 0);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testGetZ()
	throws IllegalArgumentException, IllegalAccessException
	{
		zField.set(quatUnderTest, zValueA);

		quatToValidateA.set(0, 0, quatUnderTest.getZ(), 0);

		quatToValidateB.set(0, 0, zValueA, 0);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testSetZ()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.setZ(zValueA);

		quatToValidateA.set(0, 0, zField.getFloat(quatUnderTest), 0);

		quatToValidateB.set(0, 0, zValueA, 0);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testGetW()
	throws IllegalArgumentException, IllegalAccessException
	{
		wField.set(quatUnderTest, wValueA);

		quatToValidateA.set(0, 0, 0, quatUnderTest.getW());

		quatToValidateB.set(0, 0, 0, wValueA);

		assertEquals(quatToValidateB, quatToValidateA);
	}

	@Test
	public void testSetW()
	throws IllegalArgumentException, IllegalAccessException
	{
		quatUnderTest.setW(wValueA);

		quatToValidateA.set(0, 0, 0, wField.getFloat(quatUnderTest));

		quatToValidateB.set(0, 0, 0, wValueA);

		assertEquals(quatToValidateB, quatToValidateA);
	}
}
