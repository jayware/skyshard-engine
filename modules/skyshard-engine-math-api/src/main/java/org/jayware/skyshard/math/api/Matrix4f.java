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
 * A single precision floating point 4 by 4 matrix.
 * <br><br>
 * The elements of a matrix are indexed (italic) as below:
 * <table border="1" >
 * 	<tr><td><b></b></td><td><b>0</b></td><td><b>1</b></td><td><b>2</b></td><td><b>3</b></td></tr>
 * 	<tr><td><b>0</b></td><td><i>0</i></i></td><td>1</i></td><td><i>2</i></td><td><i>3</i></td></tr>
 * 	<tr><td><b>1</b></td><td><i>4</i></td><td><i>5</i></td><td><i>6</i></td><td><i>7</i></td></tr>
 * 	<tr><td><b>2</b></td><td><i>8</i></td><td><i>9</i></td><td><i>10</i></td><td><i>11</i></td></tr>
 * 	<tr><td><b>3</b></td><td><i>12</i></td><td><i>13</i></td><td>14</i></td><td><i>15</i></td></tr>
 * </table><br>
 * If the row and column (bold, above) number is passed,
 * the index is calculated with: index = 4 * row + column
 * 
 * @author Elmar Schug
 * @since April 7, 2013
 */
public interface Matrix4f
{
    /**
	 * Retrieves the value at the specified index of this matrix.
	 * 
	 * @param index the index of the element to be retrieved.
	 * 
	 * @return the value at the indexed element.
	 */
	public float getElement(int index);

    /**
	 * Sets the specified element of this matrix4f to the value provided.
	 * 
	 * @param index the index number to be modified.
	 * 
	 * @param value the new value.
	 */
	public void setElement(int index, float value);

	/**
	 * Retrieves the value at the specified row and column of this matrix.
	 * 
	 * @param row the row number to be retrieved (zero indexed).
	 * 
	 * @param column the column number to be retrieved (zero indexed).
	 * 
	 * @return the value at the indexed element.
	 */
	public float getElement(int row, int column);

	/**
     * Sets the specified element of this matrix4f to the value provided.
     * 
     * @param row the row number to be modified (zero indexed).
     * 
     * @param column the column number to be modified (zero indexed).
     * 
     * @param value the new value.
     */
	public void setElement(int row, int column, float value);

    /**
	 * Copies all elements of this matrix into the given float array.
	 * 
	 * @param array a float array to store all elements.
	 */
	public float[] getElements(float[] array);

	/**
	 * Sets this Matrix4f to identity.
	 */
	public void setIdentity();

	/**
	 *  Sets this matrix to all zeros.
	 */
	public void setZero();

	/**
     * Adds a scalar to each component of this matrix.
     *  
     * @param scalar the scalar adder
     */
    public void add(float scalar);

    /**
     * Adds a scalar to each component of the matrix m and places
     * the result into this. Matrix m is not modified.
     * 
     * @param scalar the scalar adder.
     * @param m the original matrix values.
     */  
    public void add(float scalar, Matrix4f m);

    /**
     * Sets the value of this matrix to the matrix sum of matrices m1 and m2.
     * 
     * @param m1 the first matrix
     * 
     * @param m2 the second matrix
     */
    public void add(Matrix4f m1, Matrix4f m2);

    /**  
     * Sets the value of this matrix to the sum of itself and matrix m.
     * 
     * @param m the other matrix
     */
    public void add(Matrix4f m);

    /**
     * Performs an element-by-element subtraction of matrix m2 from
     * matrix m1 and places the result into matrix this (this =
     * m1 - m2).
     * 
     * @param m1 the first matrix.
     * 
     * @param m2 the second matrix.
     */
    public void sub(Matrix4f m1, Matrix4f m2);

    /**
     * Sets this matrix to the matrix difference of itself and 
     * matrix m (this = this - m1).
     * 
     * @param m the other matrix.
     */
    public void sub(Matrix4f m);

    /**
     * Sets the value of this matrix to its transpose in place.
     */
    public void transpose();

    /**
     * Sets the value of this matrix to the transpose of the argument matrix.
     * 
     * @param m the matrix to be transposed
     */
    public void transpose(Matrix4f m);

    /**
	 * Inverts this matrix in place.
	 */
	public void invert();

	/**
     * Sets the value of this matrix to the matrix inverse
     * of the passed (user declared) matrix m.
     * 
     * @param m the matrix to be inverted.
     */
    public void invert(Matrix4f m);

    /**
     * Computes the determinate of this matrix.
     * 
     * @return the determinate of the matrix.
     */
    public float determinant();

    /**
     * Sets the rotational component (upper 3x3) of this matrix to the
     * matrix equivalent values of the quaternion argument; the other
     * elements of this matrix are unchanged; a singular value
     * decomposition is performed on this object's upper 3x3 matrix to
     * factor out the scale, then this object's upper 3x3 matrix components
     * are replaced by the matrix equivalent of the quaternion,  
     * and then the scale is reapplied to the rotational components.
     * 
     * @param q the quaternion that specifies the rotation
     */  
    public void setRotation(Quaternion4f q); 

    /**
     * Modifies the translational components of this matrix to the given
     * x, y and z values. the other values of this matrix are not modified.
     * 
     * @param x the x component.
     * @param y the y component.
     * @param z the z component.
     */
    public void setTranslation(float x, float y, float z);

    /**
     * Modifies the translational components of this matrix to the values
     * of the Vector3f argument; the other values of this matrix are not
     * modified.
     * 
     * @param vec the translational component.
     */  
    public void setTranslation(Vector3f vec);

    /**
     * Modifies the translational components of this matrix to the values
     * of the Vector4f (xyz) argument; the other values of this matrix are not
     * modified.
     * 
     * @param vec the translational component.
     */  
    public void setTranslation(Vector4f vec);

    /**
     * Sets the value of this matrix to a counter clockwise rotation 
     * about the x axis.
     * 
     * @param angle the angle to rotate about the X axis in radians.
     */
    public void rotX(float angle);

    /**
     * Sets the value of this matrix to a counter clockwise rotation 
     * about the y axis.
     * 
     * @param angle the angle to rotate about the Y axis in radians.
     */
    public void rotY(float angle);

    /**
     * Sets the value of this matrix to a counter clockwise rotation 
     * about the z axis.
     * 
     * @param angle the angle to rotate about the Z axis in radians.
     */
    public void rotZ(float angle);

    /**
     * Multiplies each element of this matrix by a scalar.
     * 
     * @param scalar the scalar multiplier.
     */
    public void mul(float scalar);

    /**   
     * Multiplies each element of matrix m by a scalar and places
     * the result into this.  Matrix m is not modified.
     * 
     * @param scalar the scalar multiplier.
     *  
     * @param m  the original matrix. 
     */   
    public void mul(float scalar, Matrix4f m);

    /**
     * Sets the value of this matrix to the result of multiplying itself
     * with matrix m.
     * 
     * @param m the other matrix.
     */
    public void mul(Matrix4f m);

    /**
     * Sets the value of this matrix to the result of multiplying
     * the two argument matrices together.
     * 
     * @param m1 the first matrix.
     * 
     * @param m2 the second matrix.
     */
    public void mul(Matrix4f m1, Matrix4f m2);

    /**
     * Negates the value of this matrix: this = -this.
     */  
    public void negate();

    /**
     *  Sets the value of this matrix equal to the negation of
     *  of the Matrix4f parameter.
     *  
     *  @param m1 the source matrix.
     */  
    public void negate(Matrix4f m1);

    /**
     * Sets the value of this matrix from the rotation expressed by the
     * {@link Quaternion4f} and the translation given by the {@link Vector4f}.
     *
     * @param rotation the rotation expressed as a {@link Quaternion4f}.
     * @param translation the translation as a {@link Vector4f}.
     */
    public void set(Quaternion4f rotation, Vector4f translation);

    /**
     * Sets the value of this matrix from the rotation expressed by the
     * {@link Quaternion4f}, the translation given by the {@link Vector4f},
     * and the specified scale.
     *
     * @param rotation the rotation expressed as a {@link Quaternion4f}.
     * @param translation the translation as a {@link Vector4f}.
     * @param scale the scale value.
     */
    public void set(Quaternion4f rotation, Vector4f translation, float scale);
}
