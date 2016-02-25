package hillbillies.tests.unit;

import hillbillies.model.Unit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Test Class
 * @author Kenneth & Bram
 * @version 1.0
 */
public class UnitTest {

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void testIsValidName() {
        assertTrue(Unit.isValidName("Blub"));
        assertFalse(Unit.isValidName("blub"));
        assertFalse(Unit.isValidName("B"));
        assertTrue(Unit.isValidName("A '\""));
        assertFalse(Unit.isValidName("B.' 5"));
        assertFalse(Unit.isValidName(" blub"));
        assertFalse(Unit.isValidName("'ABC"));
    }

    @Test
    public void testIsValidPosition(){
        assertFalse(Unit.isValidPosition(new double[]{-1,0,0}));
        assertFalse(Unit.isValidPosition(new double[]{0,-0.0001,0}));
        assertFalse(Unit.isValidPosition(new double[]{0,0,-1}));
        assertFalse(Unit.isValidPosition(new double[]{51,0,0}));
        assertFalse(Unit.isValidPosition(new double[]{0,50.0001,0}));
        assertFalse(Unit.isValidPosition(new double[]{0,0,51}));
        assertTrue(Unit.isValidPosition(new double[]{1,1,1}));
    }

    @Test
    public void testIsValidStrength(){
        assertFalse(Unit.isValidStrength(-1));
        assertFalse(Unit.isValidStrength(0));
        assertFalse(Unit.isValidStrength(201));
        assertTrue(Unit.isValidStrength(150));
    }

    @Test
    public void testIsValidAgility(){
        assertFalse(Unit.isValidAgility(-1));
        assertFalse(Unit.isValidAgility(0));
        assertFalse(Unit.isValidAgility(201));
        assertTrue(Unit.isValidAgility(150));
    }

    @Test
    public void testIsValidToughness(){
        assertFalse(Unit.isValidToughness(-1));
        assertFalse(Unit.isValidToughness(0));
        assertFalse(Unit.isValidToughness(201));
        assertTrue(Unit.isValidToughness(150));
    }

    @Test
    public void testIsValidWeight(){
        assertFalse(Unit.isValidWeight(-1,1,1));
        assertFalse(Unit.isValidWeight(0,1,1));
        assertFalse(Unit.isValidWeight(201,1,1));
        assertTrue(Unit.isValidWeight(150,1,1));
        assertFalse(Unit.isValidWeight(50,100,100));
    }

    @Test
    public void testIsValidStamina(){
        assertFalse(Unit.isValidStamina(-1,1,1));
        assertFalse(Unit.isValidStamina(0,1,1));
        assertFalse(Unit.isValidStamina(801,200,200));
        assertTrue(Unit.isValidStamina(560,200,200));
        assertFalse(Unit.isValidStamina(100,25,25));
    }

    @Test
    public void testIsValidHitpoints(){
        assertFalse(Unit.isValidHitpoints(-1,1,1));
        assertFalse(Unit.isValidHitpoints(0,1,1));
        assertFalse(Unit.isValidHitpoints(801,200,200));
        assertTrue(Unit.isValidHitpoints(560,200,200));
        assertFalse(Unit.isValidHitpoints(100,25,25));
    }
    
    @Test
    public void testIsValidOrientation(){
    	assertFalse(Unit.isValidOrientation((float) -0.001));
    	assertFalse(Unit.isValidOrientation((float)(5*Math.PI)));
    	assertTrue(Unit.isValidOrientation((float)(0)));
    	assertFalse(Unit.isValidOrientation(Unit.MAX_ORIENTATION));
    	
    }
}