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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.vecmath.Quat4f;
import java.lang.reflect.Field;

import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


public class DefaultAxisAngle4fTest
{
	private final double assertEqualsFloatingPointDelta = 0.00001;

	private final float xValueA = 0.73f;
	private final float yValueA = 46.2f;
	private final float zValueA = 48.8f;
	private final float aValueA = 13.37f;

	private final float xValueB = 1.12f;
	private final float yValueB = -3.14f;
	private final float zValueB = 24.88f;
	private final float wValueB = -5.01f;

	private final float angleValueA = 42;

	private final float[] valuesA = {xValueA, yValueA, zValueA, aValueA};
	private final float[] valuesB = {xValueB, yValueB, zValueB, wValueB};

	private DefaultAxisAngle4f axisAngleUnderTest;

	private Field xField;
	private Field yField;
	private Field zField;
	private Field aField;

	@Mock
	private AxisAngle4f axisAngleToTestA;

	@Mock
	private Vector3f vector3fToTestA;

	@Mock
	private Quaternion4f quat4fToTestA;

	private javax.vecmath.AxisAngle4f axisAngleToValidateA;
	private javax.vecmath.AxisAngle4f axisAngleToValidateB;

	private float[] tmp;

	@BeforeTest
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		axisAngleUnderTest = new DefaultAxisAngle4f();

		Class<DefaultAxisAngle4f> vectorClass = DefaultAxisAngle4f.class;
		xField = vectorClass.getDeclaredField("x");
		xField.setAccessible(true);
		yField = vectorClass.getDeclaredField("y");
		yField.setAccessible(true);
		zField = vectorClass.getDeclaredField("z");
		zField.setAccessible(true);
		aField = vectorClass.getDeclaredField("angle");
		aField.setAccessible(true);

		axisAngleToValidateA = new javax.vecmath.AxisAngle4f();
		axisAngleToValidateB = new javax.vecmath.AxisAngle4f();

		when(axisAngleToTestA.getX()).thenReturn(xValueA);
		when(axisAngleToTestA.getY()).thenReturn(yValueA);
		when(axisAngleToTestA.getZ()).thenReturn(zValueA);
		when(axisAngleToTestA.getAngle()).thenReturn(aValueA);

		when(vector3fToTestA.getX()).thenReturn(xValueB);
		when(vector3fToTestA.getY()).thenReturn(yValueB);
		when(vector3fToTestA.getZ()).thenReturn(zValueB);

		when(quat4fToTestA.getX()).thenReturn(xValueB);
		when(quat4fToTestA.getY()).thenReturn(yValueB);
		when(quat4fToTestA.getZ()).thenReturn(zValueB);
		when(quat4fToTestA.getW()).thenReturn(wValueB);

		tmp = new float[4];
	}

	@AfterTest
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testSetXYZA()
	throws IllegalArgumentException, IllegalAccessException
	{
		axisAngleUnderTest.set(xValueA, yValueA, zValueA, aValueA);

		putXYZAValuesOfAxisAngleUnderTestInto(axisAngleToValidateA);
		axisAngleToValidateB.set(valuesA);

		assertEquals(axisAngleToValidateB, axisAngleToValidateA);
	}

	@Test
	public void testSetFloats()
	{
		axisAngleUnderTest.set(valuesA);

		putXYZAValuesOfAxisAngleUnderTestInto(axisAngleToValidateA);
		axisAngleToValidateB.set(valuesA);

		assertEquals(axisAngleToValidateB, axisAngleToValidateA);
	}

	@Test
	public void testSetAxisAngle()
	{
		axisAngleUnderTest.set(axisAngleToTestA);

		putXYZAValuesOfAxisAngleUnderTestInto(axisAngleToValidateA);
		axisAngleToValidateB.set(valuesA);

		assertEquals(axisAngleToValidateB, axisAngleToValidateA);
	}

	@Test
	public void testSetVector3fFloat()
	{
		axisAngleUnderTest.set(vector3fToTestA, angleValueA);

		putXYZAValuesOfAxisAngleUnderTestInto(axisAngleToValidateA);
		axisAngleToValidateB.set(xValueB, yValueB, zValueB, angleValueA);

		assertEquals(axisAngleToValidateB, axisAngleToValidateA);
	}

	@Test
	public void testGetArray()
	{
		putXYZAIntoAxisAngleUnderTest(valuesA);

		axisAngleUnderTest.get(tmp);

		axisAngleToValidateA.set(tmp);
		axisAngleToValidateB.set(valuesA);

		assertEquals(axisAngleToValidateB, axisAngleToValidateA);
	}

	@Test
	public void testSetQuaternion4f()
	{
		axisAngleUnderTest.set(quat4fToTestA);

		putXYZAValuesOfAxisAngleUnderTestInto(axisAngleToValidateA);

		axisAngleToValidateB.set(new Quat4f(xValueB, yValueB, zValueB, wValueB));

		assertEquals(axisAngleToValidateB, axisAngleToValidateA);
	}

	@Test
	public void testSetMatrix4f()
	{
	}

	@Test
	public void testGetAngle()
	{
		putXYZAIntoAxisAngleUnderTest(valuesA);
		assertEquals(valuesA[3], axisAngleUnderTest.getAngle(), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testSetAngle()
	throws IllegalArgumentException, IllegalAccessException
	{
		axisAngleUnderTest.setAngle(angleValueA);
		assertEquals(angleValueA, aField.getFloat(axisAngleUnderTest), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testGetX()
	{
		putXYZAIntoAxisAngleUnderTest(valuesA);
		assertEquals(valuesA[0], axisAngleUnderTest.getX(), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testSetX()
	throws IllegalArgumentException, IllegalAccessException
	{
		axisAngleUnderTest.setX(xValueA);
		assertEquals(xValueA, xField.getFloat(axisAngleUnderTest), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testGetY()
	throws IllegalArgumentException, IllegalAccessException
	{
		putXYZAIntoAxisAngleUnderTest(valuesA);
		assertEquals(valuesA[1], axisAngleUnderTest.getY(), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testSetY()
	throws IllegalArgumentException, IllegalAccessException
	{
		axisAngleUnderTest.setY(yValueA);
		assertEquals(yValueA, yField.getFloat(axisAngleUnderTest), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testGetZ()
	throws IllegalArgumentException, IllegalAccessException
	{
		putXYZAIntoAxisAngleUnderTest(valuesA);
		assertEquals(valuesA[2], axisAngleUnderTest.getZ(), assertEqualsFloatingPointDelta);
	}

	@Test
	public void testSetZ()
	throws IllegalArgumentException, IllegalAccessException
	{
		axisAngleUnderTest.setZ(zValueA);
		assertEquals(zValueA, zField.getFloat(axisAngleUnderTest), assertEqualsFloatingPointDelta);
	}

	private void putXYZAIntoAxisAngleUnderTest(float[] array)
	{
		try
		{
			xField.setFloat(axisAngleUnderTest, array[0]);
			yField.setFloat(axisAngleUnderTest, array[1]);
			zField.setFloat(axisAngleUnderTest, array[2]);
			aField.setFloat(axisAngleUnderTest, array[3]);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	private void putXYZAValuesOfAxisAngleUnderTestInto(javax.vecmath.AxisAngle4f axisAngle)
	{
		try
		{
			axisAngle.set(xField.getFloat(axisAngleUnderTest),
					  	  yField.getFloat(axisAngleUnderTest),
				 		  zField.getFloat(axisAngleUnderTest),
					 	  aField.getFloat(axisAngleUnderTest));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}
}
