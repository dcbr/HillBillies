package hillbillies.tests.unit;

import hillbillies.utils.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static hillbillies.utils.Utils.randInt;
import static org.junit.Assert.*;

/**
 * Test class for the Utils package
 * @author Kenneth & Bram
 * @version 1.0
 */
public class UtilsTest {

    public static Vector origin, unitX, unitY, unitZ, unitSum, zeroD, oneD, twoD, threeD, fourD, neg;
    public static final double EQUALS_PRECISION = Vector.EQUALS_PRECISION;

    @BeforeClass
    public static void setUp() throws Exception {
        origin = new Vector();
        unitX = new Vector(1,0,0);
        unitY = new Vector(0,1,0);
        unitZ = new Vector(0,0,1);
        unitSum = new Vector(1,1,1);
        zeroD = new Vector(new double[]{});
        oneD = new Vector(5.4321);
        twoD = new Vector(5,4.321);
        threeD = new Vector(5,4,3.21);
        fourD = new Vector(5,4,3,2.1);
        neg = new Vector(-1,-2,-3.45);
    }

    @Test
    public void testConstructor() throws Exception {
        double[] coordinates = new double[]{1,2,3};
        Vector v = new Vector(coordinates);
        assertEquals(coordinates.length,v.dimension());
        assertTrue(Arrays.equals(coordinates, v.asArray()));
        assertTrue(Arrays.equals(new double[]{0,0,0},origin.asArray()));
    }
    @Test(expected = NullPointerException.class)
    public void testConstructorNull() throws NullPointerException {
        new Vector(null);
    }

    @Test
    public void testAdd() throws Exception {
        // Test immutability (returned Vector is not the same as original Vector)
        assertFalse(origin.add(origin)==origin);
        // Basic cases
        assertTrue(origin.add(origin).equals(origin));
        assertTrue(unitX.equals(unitX.add(origin)));
        assertTrue(new Vector(1,1,1).equals(unitX.add(unitY.add(unitZ))));
        assertTrue(origin.equals(unitX.add(unitX.multiply(-1))));
        // Different dimension
        assertTrue(new Vector(10.4321).equals(oneD.add(twoD)));
        // Zero dimensions
        assertTrue(zeroD.equals(zeroD.add(fourD)));
        // TODO: implement constant addition
    }
    @Test(expected = NullPointerException.class)
    public void testAddNull() throws NullPointerException {
        origin.add(null);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddInvalid() throws IndexOutOfBoundsException {
        twoD.add(oneD);
    }

    @Test
    public void testDifference() throws Exception {
        // Test immutability (returned Vector is not the same as original Vector)
        assertFalse(origin.difference(origin)==origin);
        // Basic cases
        assertTrue(threeD.difference(threeD).equals(origin));
        assertTrue(unitX.equals(unitX.difference(origin)));
        assertTrue(new Vector(5,3,2.21).equals(threeD.difference(unitY.add(unitZ))));
        assertTrue(new Vector(6,6,6.66).equals(threeD.difference(neg)));
        // Different dimension
        assertTrue(new Vector(0.4321).equals(oneD.difference(twoD)));
        // Zero dimensions
        assertTrue(zeroD.equals(zeroD.difference(fourD)));
    }
    @Test(expected = NullPointerException.class)
    public void testDifferenceNull() throws NullPointerException {
        origin.difference(null);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testDifferenceInvalid() throws IndexOutOfBoundsException {
        twoD.difference(oneD);
    }

    @Test
    public void testMultiply() throws Exception {
        // Test immutability (returned Vector is not the same as original Vector)
        assertFalse(threeD.multiply(1)==threeD);
        // Basic cases
        assertTrue(threeD.multiply(1).equals(threeD));
        assertTrue(threeD.multiply(0).equals(origin));
        assertTrue(new Vector(10,8,6.42).equals(threeD.multiply(2)));
        // Different dimension
        assertTrue(new Vector(0.4321).equals(oneD.difference(twoD)));
        // Zero dimensions
        assertTrue(zeroD.equals(zeroD.multiply(5)));
    }

    @Test
    public void testLength() throws Exception {
        // Basic cases
        assertEquals(1, unitX.length(), EQUALS_PRECISION);
        assertEquals(1, unitY.length(), EQUALS_PRECISION);
        assertEquals(1, unitZ.length(), EQUALS_PRECISION);
        assertEquals(5.4321, oneD.length(), EQUALS_PRECISION);
        assertEquals(Math.sqrt(25+16+9+Math.pow(2.1,2)), fourD.length(), EQUALS_PRECISION);
        assertEquals(Math.sqrt(1+4+Math.pow(3.45,2)), neg.length(), EQUALS_PRECISION);
        // Zero dimensions
        assertEquals(0, zeroD.length(), EQUALS_PRECISION);
    }

    @Test
    public void testIsInBetween() throws Exception {
        // Unit vectors in between unit cube:
        assertTrue(unitX.isInBetween(origin, unitSum));
        assertTrue(unitY.isInBetween(origin, unitSum));
        assertTrue(unitZ.isInBetween(origin, unitSum));
        // Origin is in between unit cube:
        assertTrue(origin.isInBetween(origin, unitSum));
        // Basic cases
        assertFalse(threeD.isInBetween(origin, unitSum));
        assertFalse(neg.isInBetween(origin, unitSum));
        assertTrue(neg.isInBetween(neg.difference(unitSum),neg.add(unitSum)));
        // One dimension
        assertTrue(oneD.isInBetween(new Vector(5), new Vector(6)));
        // Different dimensions
        assertFalse(twoD.isInBetween(threeD, fourD));
        assertTrue(twoD.isInBetween(threeD, unitSum.multiply(5)));
        // Zero dimensions
        assertTrue(zeroD.isInBetween(fourD,twoD));
    }
    @Test(expected = NullPointerException.class)
    public void testIsInBetweenNullMin() throws NullPointerException {
        threeD.isInBetween(null, fourD);
    }
    @Test(expected = NullPointerException.class)
    public void testIsInBetweenNullMax() throws NullPointerException {
        threeD.isInBetween(fourD, null);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsInBetweenInvalidMin() throws IndexOutOfBoundsException {
        threeD.isInBetween(twoD, fourD);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsInBetweenInvalidMax() throws IndexOutOfBoundsException {
        threeD.isInBetween(fourD, twoD);
    }

    @Test
    public void testClone() throws Exception {
        assertFalse(fourD.clone()==fourD);
        assertTrue(fourD.clone().equals(fourD));
    }

    @Test
    public void testEquals() throws Exception {
        // Wrong dimensions
        assertFalse(fourD.equals(threeD));
        assertFalse(oneD.equals(twoD));
        // Basic cases
        assertFalse(unitX.equals(unitY));
        assertTrue(fourD.equals(fourD));
        assertTrue(unitX.equals(new Vector(1,0,0)));
        // Zero dimensions
        assertTrue(zeroD.equals(zeroD));
    }
    @Test(expected = NullPointerException.class)
    public void testEqualsNull() throws NullPointerException {
        twoD.equals(null);
    }

    @Test
    public void testDimension() throws Exception {
        assertEquals(0, zeroD.dimension());
        assertEquals(1, oneD.dimension());
        assertEquals(2, twoD.dimension());
        assertEquals(3, threeD.dimension());
        assertEquals(4, fourD.dimension());
    }

    @Test
    public void testAsArray() throws Exception {
        double[] coordinates = new double[]{1,2,3};
        Vector v = new Vector(coordinates);
        assertTrue(Arrays.equals(coordinates, v.asArray()));
        assertTrue(Arrays.equals(new double[]{0,0,0},origin.asArray()));
        assertTrue(Arrays.equals(new double[]{},zeroD.asArray()));
    }

    @Test
    public void testGet() throws Exception {
        assertEquals(5.4321,oneD.get(0),EQUALS_PRECISION);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutBounds() throws IndexOutOfBoundsException {
        zeroD.get(0);
    }

    @Test
    public void testX() throws Exception {
        assertEquals(1,unitX.X(),EQUALS_PRECISION);
        assertEquals(fourD.get(Vector.X_INDEX), fourD.X(),EQUALS_PRECISION);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testXOutBounds() throws IndexOutOfBoundsException {
        zeroD.X();
    }

    @Test
    public void testY() throws Exception {
        assertEquals(1,unitY.Y(),EQUALS_PRECISION);
        assertEquals(fourD.get(Vector.Y_INDEX), fourD.Y(),EQUALS_PRECISION);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testYOutBounds() throws IndexOutOfBoundsException {
        zeroD.Y();
    }

    @Test
    public void testZ() throws Exception {
        assertEquals(1,unitZ.Z(),EQUALS_PRECISION);
        assertEquals(fourD.get(Vector.Z_INDEX), fourD.Z(),EQUALS_PRECISION);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testZOutBounds() throws IndexOutOfBoundsException {
        twoD.Z();
    }

    @Test
    public void testGetCube() throws Exception {
        // Unit vectors and origin lie in unit cube
        assertEquals(1, unitX.getCube(Vector.X_INDEX));
        assertEquals(0, unitX.getCube(Vector.Y_INDEX));
        assertEquals(0, unitX.getCube(Vector.Z_INDEX));
        assertEquals(0, origin.getCube(Vector.X_INDEX));
        assertEquals(0, origin.getCube(Vector.Y_INDEX));
        assertEquals(0, origin.getCube(Vector.Z_INDEX));
        // Basic case
        assertEquals(5, oneD.getCube(0));
        assertEquals(4, twoD.getCube(1));
        assertEquals(3, threeD.getCube(2));
        assertEquals(2, fourD.getCube(3));
        assertEquals(-1, neg.getCube(0));
        assertEquals(-2, neg.getCube(1));
        assertEquals(-4, neg.getCube(2));
        // Test extra return condition
        assertTrue(oneD.getCube(0)*Vector.CUBE_SIDE_LENGTH<=oneD.get(0));
        assertTrue((oneD.getCube(0)+1)*Vector.CUBE_SIDE_LENGTH>=oneD.get(0));
        assertTrue(neg.getCube(2)*Vector.CUBE_SIDE_LENGTH<=neg.get(2));
        assertTrue((neg.getCube(2)+1)*Vector.CUBE_SIDE_LENGTH>=neg.get(2));
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetCubeOutBounds() throws IndexOutOfBoundsException {
        oneD.getCube(5);
    }

    @Test
    public void testCubeX() throws Exception {
        assertEquals(1,unitX.cubeX());
        assertEquals(5,oneD.cubeX());
        assertEquals(fourD.getCube(Vector.X_INDEX), fourD.cubeX());
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testCubeXOutBounds() throws IndexOutOfBoundsException {
        zeroD.cubeX();
    }

    @Test
    public void testCubeY() throws Exception {
        assertEquals(1,unitY.cubeY());
        assertEquals(4,twoD.cubeY());
        assertEquals(fourD.getCube(Vector.Y_INDEX), fourD.cubeY());
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testCubeYOutBounds() throws IndexOutOfBoundsException {
        oneD.cubeY();
    }

    @Test
    public void testCubeZ() throws Exception {
        assertEquals(1,unitZ.cubeZ());
        assertEquals(3,threeD.cubeZ());
        assertEquals(fourD.getCube(Vector.Z_INDEX), fourD.cubeZ());
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testCubeZOutBounds() throws IndexOutOfBoundsException {
        twoD.cubeZ();
    }

    @Test
    public void testCubeCoordinates() throws Exception {
        // TODO: implement tests
    }

    @Test
    public void testGetCubeCoordinates() throws Exception {
        assertTrue(origin.getCubeCenterCoordinates().equals(unitSum.multiply(0.5)));
        assertTrue(fourD.getCubeCenterCoordinates().equals(new Vector(5.5,4.5,3.5,2.5)));
        assertTrue(neg.getCubeCenterCoordinates().equals(new Vector(-0.5,-1.5,-3.5)));
    }

    @Test
    public void testRandInt() throws Exception {
        assertTrue(randInt(5,10)>=5);
        assertTrue(randInt(5,10)<=10);
        assertEquals(5, randInt(5,5));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRandIntIllegal() throws IllegalArgumentException {
        randInt(6,5);
    }
}