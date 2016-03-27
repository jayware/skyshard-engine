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


public class DefaultVector4fTest
{
	private final double assertEqualsFloatingPointDelta = 0.00001;

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

	private DefaultVector4f vectorUnderTest;

	private Field xField;
	private Field yField;
	private Field zField;
	private Field wField;

	@Mock
	private Vector4f vectorToTestA;

	@Mock
	private Vector4f vectorToTestB;

	private javax.vecmath.Vector4f vectorToValidateA;
	private javax.vecmath.Vector4f vectorToValidateB;
	private javax.vecmath.Vector4f vectorToValidateC;

	private float[] tmp;

	@BeforeTest
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		vectorUnderTest = new DefaultVector4f();

		Class<DefaultVector4f> vectorClass = DefaultVector4f.class;
		xField = vectorClass.getDeclaredField("x");
		xField.setAccessible(true);
		yField = vectorClass.getDeclaredField("y");
		yField.setAccessible(true);
		zField = vectorClass.getDeclaredField("z");
		zField.setAccessible(true);
		wField = vectorClass.getDeclaredField("w");
		wField.setAccessible(true);

		when(vectorToTestA.getX()).thenReturn(xValueA);
		when(vectorToTestA.getY()).thenReturn(yValueA);
		when(vectorToTestA.getZ()).thenReturn(zValueA);
		when(vectorToTestA.getW()).thenReturn(wValueA);

		doAnswer(new Answer<float[]>()
		{
			@Override
			public float[] answer(InvocationOnMock invocation) throws Throwable
			{
				float[] array = (float[]) invocation.getArguments()[0];
				vectorToValidateA.set(array);
				return array;
			}
		}).when(vectorToTestA).set(any(float[].class));

		when(vectorToTestA.length()).thenReturn((float)
				Math.sqrt(xValueA * xValueA + yValueA * yValueA +
						  zValueA * zValueA + wValueA * wValueA));

		when(vectorToTestB.getX()).thenReturn(xValueB);
		when(vectorToTestB.getY()).thenReturn(yValueB);
		when(vectorToTestB.getZ()).thenReturn(zValueB);
		when(vectorToTestB.getW()).thenReturn(wValueB);

		vectorToValidateA = new javax.vecmath.Vector4f();
	    vectorToValidateB = new javax.vecmath.Vector4f();
        vectorToValidateC = new javax.vecmath.Vector4f();
 
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
		vectorUnderTest.setZero();
		vectorToValidateB.set(0f, 0f, 0f, 0f);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		assertEquals(vectorToValidateA, vectorToValidateB);
	}

	@Test
	public void testAddTuple4fTuple4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.add(vectorToTestA, vectorToTestB);

		vectorToValidateA.set(valuesA);
		vectorToValidateB.set(valuesB);
		vectorToValidateC.add(vectorToValidateA, vectorToValidateB);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		assertEquals(vectorToValidateC, vectorToValidateA);
	}

	@Test
	public void testAddTuple4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorUnderTest.add(vectorToTestB);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(valuesA);
		vectorToValidateC.set(valuesB);
		vectorToValidateB.add(vectorToValidateC);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testSubTuple4fTuple4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.sub(vectorToTestA, vectorToTestB);

		vectorToValidateA.set(valuesA);
		vectorToValidateB.set(valuesB);
		vectorToValidateC.sub(vectorToValidateA, vectorToValidateB);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		assertEquals(vectorToValidateC, vectorToValidateA);
	}

	@Test
	public void testSubTuple4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorUnderTest.sub(vectorToTestB);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(valuesA);
		vectorToValidateC.set(valuesB);
		vectorToValidateB.sub(vectorToValidateC);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testScaleTuple()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.scale(scaleValueA, vectorToTestA);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateC.set(valuesA);
		vectorToValidateB.scale(scaleValueA, vectorToValidateC);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testScale()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorUnderTest.scale(scaleValueA);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(valuesA);
		vectorToValidateB.scale(scaleValueA);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testGetFloats()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);
	
		vectorUnderTest.get(tmp);
		vectorToValidateA.set(tmp);
	
		vectorToValidateB.set(valuesA);
	
		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testSetFloats()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.set(valuesA);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(valuesA);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testGetTuple()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorUnderTest.get(vectorToTestA);

		vectorToValidateC.set(valuesA);
		vectorToValidateC.get(vectorToValidateB);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testSetTuple()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.set(vectorToTestA);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateC.set(valuesA);
		vectorToValidateB.set(vectorToValidateC);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testSetXYZW()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.set(xValueA, yValueA, zValueA, wValueA);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
				  			  yField.getFloat(vectorUnderTest),
				  			  zField.getFloat(vectorUnderTest),
				  			  wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(xValueA, yValueA, zValueA, wValueA);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testGetX()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);

		vectorToValidateA.set(vectorUnderTest.getX(), 0, 0, 0);

		vectorToValidateB.set(xValueA, 0, 0, 0);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testSetX()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.setX(xValueA);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest), 0, 0, 0);

		vectorToValidateB.set(xValueA, 0, 0, 0);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testGetY()
	throws IllegalArgumentException, IllegalAccessException
	{
		yField.set(vectorUnderTest, yValueA);

		vectorToValidateA.set(0, vectorUnderTest.getY(), 0, 0);

		vectorToValidateB.set(0, yValueA, 0, 0);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testSetY()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.setY(yValueA);

		vectorToValidateA.set(0, yField.getFloat(vectorUnderTest), 0, 0);

		vectorToValidateB.set(0, yValueA, 0, 0);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testGetZ()
	throws IllegalArgumentException, IllegalAccessException
	{
		zField.set(vectorUnderTest, zValueA);

		vectorToValidateA.set(0, 0, vectorUnderTest.getZ(), 0);

		vectorToValidateB.set(0, 0, zValueA, 0);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testSetZ()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.setZ(zValueA);

		vectorToValidateA.set(0, 0, zField.getFloat(vectorUnderTest), 0);

		vectorToValidateB.set(0, 0, zValueA, 0);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testGetW()
	throws IllegalArgumentException, IllegalAccessException
	{
		wField.set(vectorUnderTest, wValueA);

		vectorToValidateA.set(0, 0, 0, vectorUnderTest.getW());

		vectorToValidateB.set(0, 0, 0, wValueA);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testSetW()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.setW(wValueA);

		vectorToValidateA.set(0, 0, 0, wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(0, 0, 0, wValueA);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testDot()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorUnderTest.dot(vectorToTestB);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(valuesA);
		vectorToValidateC.set(valuesB);
		vectorToValidateB.dot(vectorToValidateC);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testNormalizeVector()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.normalize(vectorToTestA);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateC.set(valuesA);
		vectorToValidateB.normalize(vectorToValidateC);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testNormalize()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorUnderTest.normalize();

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(valuesA);
		vectorToValidateB.normalize();

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testNegate()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorUnderTest.negate();

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateB.set(valuesA);
		vectorToValidateB.negate();

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testNegateVector4f()
	throws IllegalArgumentException, IllegalAccessException
	{
		vectorUnderTest.negate(vectorToTestB);

		vectorToValidateA.set(xField.getFloat(vectorUnderTest),
							  yField.getFloat(vectorUnderTest),
							  zField.getFloat(vectorUnderTest),
							  wField.getFloat(vectorUnderTest));

		vectorToValidateC.set(valuesB);
		vectorToValidateB.negate(vectorToValidateC);

		assertEquals(vectorToValidateB, vectorToValidateA);
	}

	@Test
	public void testLength()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorToValidateA.set(valuesA);

		assertEquals(vectorToValidateA.length(), vectorUnderTest.length(), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testLengthSquared()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueA);
		yField.set(vectorUnderTest, yValueA);
		zField.set(vectorUnderTest, zValueA);
		wField.set(vectorUnderTest, wValueA);

		vectorToValidateA.set(valuesA);

		assertEquals(vectorToValidateA.lengthSquared(), vectorUnderTest.lengthSquared(), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testAngle()
	throws IllegalArgumentException, IllegalAccessException
	{
		xField.set(vectorUnderTest, xValueB);
		yField.set(vectorUnderTest, yValueB);
		zField.set(vectorUnderTest, zValueB);
		wField.set(vectorUnderTest, wValueB);

		vectorToValidateA.set(valuesB);
		vectorToValidateB.set(valuesA);

		assertEquals(vectorToValidateA.angle(vectorToValidateB), vectorUnderTest.angle(vectorToTestA), assertEqualsFloatingPointDelta);
	}

}
