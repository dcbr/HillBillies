package hillbillies.utils;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import hillbillies.model.Unit;

/**
 * Utility class representing a Vector
 * @author Kenneth & Bram
 * @version 1.0
 */
public class Vector {

    public static final double EQUALS_PRECISION = 1e-7;
    public static final double CUBE_SIDE_LENGTH = Unit.CUBE_SIDE_LENGTH;

    public static final int X_INDEX = 0;
    public static final int Y_INDEX = 1;
    public static final int Z_INDEX = 2;

    private double[] vectorList;

    /**
     * Initialize a new immutable N-dimensional vector with given coordinates.
     * @param vectorList Double array containing the coordinates of this new vector.
     * @post    The dimension of this new vector equals the length of vectorList
     *          | this.dimension() == vectorList.length
     * @post    The new vector's coordinates are set to the corresponding elements of vectorList
     *          | for(int i=0;i<vectorList.length;i++)
     *          |   this.get(i) == vectorList[i]
     */
    public Vector(double... vectorList){
        this.vectorList = vectorList.clone();
    }

    /**
     * Initialize a new immutable 3-dimensional vector. This vector represents the origin.
     * @effect  Initialize a new Vector with given coordinates: {0,0,0}
     *          | this(0,0,0)
     */
    public Vector(){
        this(0,0,0);
    }

    /**
     * Add this vector to other vector. The result is a new immutable Vector instance.
     * @param other The vector to add
     * @return A new immutable Vector which is the addition of this Vector and the other Vector.
     *          | for(int i = 0; i < this.dimension(); i++)
     *          |   result.get(i) == this.get(i) + other.get(i)
     * @throws  NullPointerException
     *          When other references null
     *          | other == null
     * @throws  IndexOutOfBoundsException
     *          When the dimension of other is less than the dimension of this vector.
     *          | other.dimension() < this.dimension()
     * @note    When the dimension of this vector is less than the dimension of other vector,
     *          no IndexOutOfBoundsException will be thrown.
     *          The result will be the sum of the first 'this.dimension()' coordinates of this
     *          and other. The remaining coordinates of other do not influence the result.
     */
    public Vector add(Vector other) throws NullPointerException, IndexOutOfBoundsException {// TODO: rekening houden met overflow? Miss ExtMath gebruiken?
        Vector result = this.clone();
        for(int i=0;i<vectorList.length;i++){
            result.vectorList[i] += other.vectorList[i];
        }
        return result;
    }

    /**
     * Subtract other vector from this vector. The result is a new immutable Vector instance.
     * @param other The vector to subtract
     * @return A new immutable Vector which is the subtraction of other Vector from this Vector.
     *          | for(int i = 0; i < this.dimension(); i++)
     *          |   result.get(i) == this.get(i) - other.get(i)
     * @throws  NullPointerException
     *          When other references null
     *          | other == null
     * @throws  IndexOutOfBoundsException
     *          When the dimension of other is less than the dimension of this vector.
     *          | other.dimension() < this.dimension()
     * @note    When the dimension of this vector is less than the dimension of other vector,
     *          no IndexOutOfBoundsException will be thrown.
     *          The result will be the subtraction of the first 'this.dimension()' coordinates of this
     *          and other. The remaining coordinates of other do not influence the result.
     */
    public Vector difference(Vector other) throws NullPointerException, IndexOutOfBoundsException {
        Vector result = this.clone();
        for(int i=0;i<vectorList.length;i++){
            result.vectorList[i] -= other.vectorList[i];
        }
        return result;
    }

    /**
     * Multiply this vector with the given factor. The result is a new immutable Vector instance.
     * @param factor The factor to multiply with
     * @return A new immutable Vector which is the multiplication of this Vector with the given factor.
     *          | for(int i = 0; i < this.dimension(); i++)
     *          |   result.get(i) == this.get(i) * factor
     */
    public Vector multiply(double factor){
        Vector result = this.clone();
        for(int i=0;i<vectorList.length;i++){
            result.vectorList[i] *= factor;
        }
        return result;
    }

    /**
     * Calculate the Euclidean norm of this Vector.
     * @return The Euclidean norm of this Vector
     *          | result == sqrt(sum(this.get(i)^2,i=0..this.dimension()-1))
     */
    public double length(){
        double lengthSq = 0d;
        for(double coordinate : vectorList)
            lengthSq += Math.pow(coordinate,2);
        return Math.sqrt(lengthSq);
    }

    /**
     * Check whether this Vector's coordinates lie in between the coordinates of minPosition and maxPosition.
     * @param minPosition The minimum position
     * @param maxPosition The maximum position
     * @return  True when this Vector lies in between minPosition and maxPosition
     *          | if    ( for(int i=0;i < this.dimension();i++)
     *          |           this.get(i)>=minPosition.get(i) && this.get(i)<=maxPosition.get(i)
     *          |       )
     *          |       result == true
     *          | else  result == false
     * @throws  NullPointerException
     *          When minPosition or maxPosition reference null
     *          | minPosition == null || maxPosition == null
     * @throws  IndexOutOfBoundsException
     *          When the dimension of minPosition or maxPosition is less than the dimension of this vector.
     *          | minPosition.dimension() < this.dimension() || maxPosition.dimension() < this.dimension()
     * @note    When the dimension of this vector is less than the dimension of the other vectors,
     *          no IndexOutOfBoundsException will be thrown.
     *          The result will only be checked for the first 'this.dimension()' coordinates of this,
     *          minPosition and maxPosition. The remaining coordinates of minPosition or maxPosition
     *          do not influence the result.
     */
    public boolean isInBetween(Vector minPosition, Vector maxPosition) throws NullPointerException, IndexOutOfBoundsException{
        for(int i=0;i<vectorList.length;i++){
            if(vectorList[i]<minPosition.vectorList[i] || vectorList[i]>maxPosition.vectorList[i])
                return false;
        }
        return true;
    }

    /**
     * @return A clone of this Vector. The result is a new immutable Vector instance with the same coordinates
     *          as this Vector.
     *          | result != this &&
     *          | result.equals(this) == true
     */
    public Vector clone() {
        try {
            return (Vector)super.clone();
        }catch(CloneNotSupportedException e){
            return new Vector(this.vectorList);
        }
    }

    /**
     * Check whether two Vectors equal each other.
     * @param other The Vector to check against
     * @return  False when the dimensions of both Vectors do not match
     *          | if(this.dimension()!=other.dimension())
     *          |   result == false
     *          True when the absolute difference of each coordinate of this Vector and other Vector is less
     *          than EQUALS_PRECISION.
     *          | if    ( for(int i=0;i < this.dimension();i++)
     *          |           Math.abs(this.get(i)-other.get(i))<=EQUALS_PRECISION
     *          |       )
     *          |       result == true
     *          | else  result == false
     * @throws  NullPointerException
     *          When other references null
     *          | other == null
     */
    public boolean equals(Vector other) throws NullPointerException {
        if(this.dimension()!=other.dimension())
            return false;
        for(int i=0;i<vectorList.length;i++){
            if(Math.abs(vectorList[i]-other.vectorList[i])>EQUALS_PRECISION)
                return false;
        }
        return true;
    }

    /**
     * @return The dimension of this Vector.
     */
    @Immutable
    public int dimension(){
        return this.vectorList.length;
    }

    /**
     * @return The Vector's coordinates as a double[] array
     */
    @Immutable
    @Basic
    public double[] asArray(){
        return this.vectorList.clone();
    }

    /**
     * Return the coordinate at position index.
     * @param index The index of the coordinate to retrieve
     * @return The coordinate at position index
     * @throws IndexOutOfBoundsException
     *          When the given index is greater than or equal to this Vector's dimension.
     *          | index >= this.dimension()
     */
    @Immutable
    @Basic // TODO: is this really a basic inspector?
    public double get(int index) throws IndexOutOfBoundsException {
        return this.vectorList[index];
    }

    /**
     * Return the X-coordinate of this Vector, this is the coordinate at position X_INDEX.
     * @return The coordinate at position X_INDEX
     * @effect  Retrieve the coordinate at position X_INDEX
     *          | this.get(X_INDEX)
     * @throws IndexOutOfBoundsException
     *          When this Vector's dimension equals X_INDEX
     *          | this.dimension() == X_INDEX
     */
    @Immutable
    public double X() throws IndexOutOfBoundsException {
        return this.get(X_INDEX);
    }
    /**
     * Return the Y-coordinate of this Vector, this is the coordinate at position Y_INDEX.
     * @return The coordinate at position Y_INDEX
     * @effect  Retrieve the coordinate at position Y_INDEX
     *          | this.get(Y_INDEX)
     * @throws IndexOutOfBoundsException
     *          When this Vector's dimension is less than or equal to Y_INDEX
     *          | this.dimension() <= Y_INDEX
     */
    @Immutable
    public double Y() throws IndexOutOfBoundsException {
        return this.get(Y_INDEX);
    }
    /**
     * Return the Z-coordinate of this Vector, this is the coordinate at position Z_INDEX.
     * @return The coordinate at position Z_INDEX
     * @effect  Retrieve the coordinate at position Z_INDEX
     *          | this.get(Z_INDEX)
     * @throws IndexOutOfBoundsException
     *          When this Vector's dimension is less than or equal to Z_INDEX
     *          | this.dimension() <= Z_INDEX
     */
    @Immutable
    public double Z() throws IndexOutOfBoundsException {
        return this.get(Z_INDEX);
    }

    /**
     * Retrieve the index of the cube (where this Vector lies in) along the 'index'-axis
     * @param index The index of the axis
     * @return The index of the cube (where this Vector lies in) along the 'index'-axis.
     *          | result == Math.floor(this.get(index)/CUBE_SIDE_LENGTH)
     *          The coordinate of this Vector along the 'index'-axis lies in between the boundaries
     *          of the returned cube.
     *          | result * CUBE_SIDE_LENGTH <= this.get(index) <= (result + 1) * CUBE_SIDE_LENGTH
     * @throws IndexOutOfBoundsException
     *          When this Vector's dimension is less than or equals index.
     *          | this.dimension() <= index
     */
    public double getCube(int index) throws IndexOutOfBoundsException {
        return Math.floor(this.get(index)/CUBE_SIDE_LENGTH);
    }
    /**
     * @return The index of the cube (this Vector lies in) along the X-axis.
     * @effect  Retrieve the index of the cube (this Vector lies in) along the 'X_INDEX'-axis
     *          | this.getCube(X_INDEX)
     * @throws IndexOutOfBoundsException
     *          When this Vector's dimension equals X_INDEX
     *          | this.dimension() == X_INDEX
     */
    @Immutable
    public double cubeX() throws IndexOutOfBoundsException {
        return this.getCube(X_INDEX);
    }
    /**
     * @return The index of the cube (this Vector lies in) along the Y-axis.
     * @effect  Retrieve the index of the cube (this Vector lies in) along the 'Y_INDEX'-axis
     *          | this.getCube(Y_INDEX)
     * @throws IndexOutOfBoundsException
     *          When this Vector's dimension is less than or equal to Y_INDEX
     *          | this.dimension() <= Y_INDEX
     */
    @Immutable
    public double cubeY(){
        return this.getCube(Y_INDEX);
    }
    /**
     * @return The index of the cube (this Vector lies in) along the Z-axis.
     * @effect  Retrieve the index of the cube (this Vector lies in) along the 'Z_INDEX'-axis
     *          | this.getCube(Z_INDEX)
     * @throws IndexOutOfBoundsException
     *          When this Vector's dimension is less than or equal to Z_INDEX
     *          | this.dimension() <= Z_INDEX
     */
    @Immutable
    public double cubeZ(){
        return this.getCube(Z_INDEX);
    }

    /**
     * Returns the coordinates of the center of the cube this Vector lies in.
     * @return A new Vector instance containing the coordinates of the center of the cube this Vector lies in.
     *          | result == new Vector(cubeX()+CUBE_SIDE_LENGTH/2, cubeY()+CUBE_SIDE_LENGTH/2, cubeZ()+CUBE_SIDE_LENGTH/2)
     *          |                       .multiply(CUBE_SIDE_LENGTH)
     */
    @Immutable
    public Vector getCubeCoordinates(){
        return new Vector(
                this.cubeX()+CUBE_SIDE_LENGTH/2,
                this.cubeY()+CUBE_SIDE_LENGTH/2,
                this.cubeZ()+CUBE_SIDE_LENGTH/2
        ).multiply(CUBE_SIDE_LENGTH);
    }
}
