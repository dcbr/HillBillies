package hillbillies.tests.unit;

import hillbillies.model.Activity;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Test Class
 * @author Kenneth & Bram
 * @version 1.0
 */
public class UnitTest {

	private static Unit unit, customUnit, unitx, unity, unitz;

    @BeforeClass
    public void setUpClass() {
        unit = new Unit("Unit", new Vector(25,25,25));
        customUnit = new Unit("Custom", new Vector(20,20,20), 50, 50, 50, 100, 100, 100);
    }

    @Before
    public void setUp() throws Exception {
    	unitx = new Unit("Unitx", new Vector(23,23,0));
    	unity = new Unit("Unity", new Vector(21,23,0));
    	unitz = new Unit("Unitz", new Vector(22,22,0));
    }

    @Test
    public void testConstructor1(){
        assertEquals(unit.getName(),"Unit");
        assertTrue(unit.getPosition().equals(new Vector(25,25,25)));
        assertEquals(unit.getAgility(), Unit.INITIAL_MIN_AGILITY);
        assertEquals(unit.getStrength(), Unit.INITIAL_MIN_STRENGTH);
        assertEquals(unit.getToughness(), Unit.INITIAL_MIN_TOUGHNESS);
        assertEquals(unit.getWeight(), Unit.INITIAL_MIN_WEIGHT);
        assertEquals(unit.getStamina(), Unit.INITIAL_MIN_STAMINA);
        assertEquals(unit.getHitpoints(), Unit.INITIAL_MIN_HITPOINTS);
    }
    @Test
    public void testConstructor2(){
        assertEquals(customUnit.getName(),"Custom");
        assertTrue(customUnit.getPosition().equals(new Vector(20,20,20)));
        assertEquals(customUnit.getAgility(), 50);
        assertEquals(customUnit.getStrength(), 50);
        assertEquals(customUnit.getToughness(), 50);
        assertEquals(customUnit.getWeight(), 100);
        assertEquals(customUnit.getStamina(), 100);
        assertEquals(customUnit.getHitpoints(), 100);
        assertEquals(customUnit.getOrientation(), Unit.INITIAL_ORIENTATION, Vector.EQUALS_PRECISION);
        assertTrue(customUnit.getCurrentActivity()==Activity.NONE);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidName() throws IllegalArgumentException {
        Unit n = new Unit("no caps", new Vector(20,20,20));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidPosition() throws IllegalArgumentException {
        Unit n = new Unit("Name", new Vector(-10,20,30));
    }
    @Test
    public void testConstructorInvalidPoperties() {
        Unit n = new Unit("New", new Vector(20,20,20), 500, 50, 50, 100, 100, 100);// Invalid strength
        assertEquals(n.getStrength(), Unit.INITIAL_MIN_STRENGTH);
        n = new Unit("New", new Vector(20,20,20), 50, 500, 50, 100, 100, 100);// Invalid agility
        assertEquals(n.getAgility(), Unit.INITIAL_MIN_AGILITY);
        n = new Unit("New", new Vector(20,20,20), 50, 50, 500, 100, 100, 100);// Invalid toughness
        assertEquals(n.getToughness(), Unit.INITIAL_MIN_TOUGHNESS);
        n = new Unit("New", new Vector(20,20,20), 50, 50, 50, -100, 100, 100);// Invalid weight
        assertEquals(n.getWeight(), Unit.INITIAL_MIN_WEIGHT);
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
        assertFalse(Unit.isValidPosition(new Vector(-1,0,0)));
        assertFalse(Unit.isValidPosition(new Vector(0,-0.0001,0)));
        assertFalse(Unit.isValidPosition(new Vector(0,0,-1)));
        assertFalse(Unit.isValidPosition(new Vector(51,0,0)));
        assertFalse(Unit.isValidPosition(new Vector(0,50.0001,0)));
        assertFalse(Unit.isValidPosition(new Vector(0,0,51)));
        assertTrue(Unit.isValidPosition(new Vector(1,1,1)));
        assertTrue(Unit.isValidPosition(new Vector(0,0,0)));
    }

    @Test
    public void testIsValidStrength(){
        assertFalse(Unit.isValidStrength(-1));// strength < MIN_STRENGTH
        assertFalse(Unit.isValidStrength(0));// strength < MIN_STRENGTH
        assertTrue(Unit.isValidStrength(1));
        assertFalse(Unit.isValidStrength(201));// strength > MAX_STRENGTH
        assertTrue(Unit.isValidStrength(150));
    }
    @Test
    public void testIsValidInitialStrength(){
        assertFalse(Unit.isValidInitialStrength(-1));// strength < MIN_STRENGTH
        assertFalse(Unit.isValidInitialStrength(0));// strength < MIN_STRENGTH
        assertFalse(Unit.isValidInitialStrength(1));// strength < INITIAL_MIN_STRENGTH
        assertTrue(Unit.isValidInitialStrength(25));
        assertFalse(Unit.isValidInitialStrength(101));// strength > INITIAL_MAX_STRENGTH
        assertTrue(Unit.isValidInitialStrength(50));
    }

    @Test
    public void testIsValidAgility(){
        assertFalse(Unit.isValidAgility(-1));
        assertFalse(Unit.isValidAgility(0));// Unit.MIN_AGILITY - 1
        assertFalse(Unit.isValidAgility(201));// Unit.MAX_AGILITY - 1
        assertTrue(Unit.isValidAgility(150));
    }
    @Test
    public void testIsValidInitialAgility(){
        assertFalse(Unit.isValidInitialAgility(Unit.INITIAL_MIN_AGILITY-1));
        assertTrue(Unit.isValidInitialAgility(Unit.INITIAL_MIN_AGILITY));
        assertTrue(Unit.isValidInitialAgility(Unit.INITIAL_MAX_AGILITY));
        assertFalse(Unit.isValidInitialAgility(Unit.INITIAL_MAX_AGILITY+1));
    }

    @Test
    public void testIsValidToughness(){
        assertFalse(Unit.isValidToughness(-1));// toughness < MIN_TOUGHNESS
        assertFalse(Unit.isValidToughness(0));// toughness < MIN_TOUGHNESS
        assertTrue(Unit.isValidToughness(1));
        assertFalse(Unit.isValidToughness(201));// toughness > MAX_TOUGHNESS
        assertTrue(Unit.isValidToughness(150));
    }
    @Test
    public void testIsValidInitialToughness(){
        assertFalse(Unit.isValidInitialToughness(-1));// toughness < MIN_TOUGHNESS
        assertFalse(Unit.isValidInitialToughness(0));// toughness < MIN_TOUGHNESS
        assertFalse(Unit.isValidInitialToughness(1));// toughness < INITIAL_MIN_TOUGHNESS
        assertTrue(Unit.isValidInitialToughness(25));
        assertFalse(Unit.isValidInitialToughness(101));// toughness > INITIAL_MAX_TOUGHNESS
        assertTrue(Unit.isValidInitialToughness(50));
    }

    @Test
    public void testIsValidWeight(){
        assertFalse(Unit.isValidWeight(-1,1,1));// weight < getMinWeight(1,1)
        assertFalse(Unit.isValidWeight(0,1,1));// weight < getMinWeight(1,1)
        assertTrue(Unit.isValidWeight(1,1,1));
        assertFalse(Unit.isValidWeight(201,1,1));// weight > MAX_WEIGHT
        assertTrue(Unit.isValidWeight(150,1,1));
        assertFalse(Unit.isValidWeight(50,100,100));// weight < getMinWeight(100,100)
    }
    @Test
    public void testIsValidInitialWeight(){
        assertFalse(Unit.isValidInitialWeight(-1,1,1));// weight < getMinWeight(1,1)
        assertFalse(Unit.isValidInitialWeight(0,1,1));// weight < getMinWeight(1,1)
        assertFalse(Unit.isValidInitialWeight(1,1,1));// weight < getInitialMinWeight(1,1)
        assertTrue(Unit.isValidInitialWeight(25,1,1));
        assertFalse(Unit.isValidInitialWeight(101,1,1));// weight > INITIAL_MAX_WEIGHT
        assertTrue(Unit.isValidInitialWeight(50,1,1));
        assertFalse(Unit.isValidInitialWeight(50,100,100));// weight < getMinWeight(100,100)
    }

    @Test
    public void testIsValidStamina(){
        assertFalse(Unit.isValidStamina(-1,1,1));// stamina < MIN_STAMINA
        assertTrue(Unit.isValidStamina(0,100,100));
        assertFalse(Unit.isValidStamina(801,200,200));// stamina > getMaxStamina(200,200)
        assertTrue(Unit.isValidStamina(560,200,200));
        assertFalse(Unit.isValidStamina(100,25,25));
    }
    @Test
    public void testIsValidInitialStamina(){
        assertFalse(Unit.isValidInitialStamina(-1,1,1));// stamina < MIN_STAMINA
        assertFalse(Unit.isValidInitialStamina(0,100,100));// stamina < INITIAL_MIN_STAMINA
        assertTrue(Unit.isValidInitialStamina(1,100,100));
        assertFalse(Unit.isValidInitialStamina(801,200,200));// stamina > getMaxStamina(200,200)
        assertTrue(Unit.isValidInitialStamina(560,200,200));
        assertFalse(Unit.isValidInitialStamina(100,25,25));
    }

    @Test
    public void testIsValidHitpoints(){
        assertFalse(Unit.isValidHitpoints(-1,1,1));// hitpoints < MIN_HITPOINTS
        assertTrue(Unit.isValidHitpoints(0,1,1));
        assertFalse(Unit.isValidHitpoints(801,200,200));// hitpoints > getMaxHitpoints(200,200)
        assertTrue(Unit.isValidHitpoints(560,200,200));
        assertFalse(Unit.isValidHitpoints(100,25,25));
    }
    @Test
    public void testIsValidInitialHitpoints(){
        assertFalse(Unit.isValidInitialHitpoints(-1,1,1));// hitpoints < MIN_HITPOINTS
        assertFalse(Unit.isValidInitialHitpoints(0,1,1));// hitpoints < INITIAL_MIN_HITPOINTS
        assertTrue(Unit.isValidInitialHitpoints(1,1,1));
        assertFalse(Unit.isValidInitialHitpoints(801,200,200));// hitpoints > getMaxHitpoints(200,200)
        assertTrue(Unit.isValidInitialHitpoints(560,200,200));
        assertFalse(Unit.isValidInitialHitpoints(100,25,25));
    }
    
    @Test
    public void testIsValidOrientation(){
    	assertFalse(Unit.isValidOrientation((float) -0.001));
    	assertFalse(Unit.isValidOrientation((float)(5*Math.PI)));
    	assertTrue(Unit.isValidOrientation((float)(0)));
    	assertTrue(Unit.isValidOrientation(Unit.MAX_ORIENTATION));
    }

    @Test
    public void testIsAbleToMove(){
        assertTrue(unity.isAbleToMove());
        unity.work();
        assertFalse(unity.isAbleToMove());// Unit is working
        unitx.attack(unitz);
        assertFalse(unitx.isAbleToMove());// Unit is attacking
        unitz.rest();
        assertFalse(unitz.isAbleToMove());// Unit is in initial rest mode
        while(unitz.isInitialRestMode())
            unitz.advanceTime(0.2d);
        assertTrue(unitz.isAbleToMove());// At least one hitpoint recovered => able to move
    }

    @Test
    public void testIsAbleToRest(){
        assertTrue(unity.isAbleToRest());
        unitx.attack(unitz);
        assertFalse(unitx.isAbleToRest());// Unit is attacking
    }

    @Test
    public void testIsAbleToSprint(){
        assertFalse(unity.isAbleToSprint());// Unit is not moving
        unity.moveToAdjacent(new Vector(1,0,0));
        assertTrue(unity.isAbleToSprint());
        unity.sprint();
        while(unity.isSprinting())
            unity.advanceTime(0.2d);
        assertFalse(unity.isAbleToSprint());// Unit has not enough stamina
    }

    @Test
    public void testIsAbleToWork(){
        assertTrue(unity.isAbleToWork());
        unitx.attack(unitz);
        assertFalse(unitx.isAbleToWork());// Unit is attacking
        unitz.rest();
        assertFalse(unitz.isAbleToWork());// Unit is in initial rest mode
        while(unitz.isInitialRestMode())
            unitz.advanceTime(0.2d);
        assertTrue(unitz.isAbleToWork());// At least one hitpoint recovered => able to work
    }

    @Test
    public void testIsAttacking(){
        assertFalse(unitx.isAttacking());
        unitx.attack(unitz);
        assertTrue(unitx.isAttacking());
    }

    @Test
    public void testIsDefaultActive(){
        assertFalse(unitx.isDefaultActive());
        unitx.setDefaultBehaviour();
        assertTrue(unitx.isDefaultActive());
    }

    @Test
    public void testIsInitialRestMode(){
        assertFalse(unitx.isInitialRestMode());// Unit is not resting
        unitx.rest();
        assertTrue(unitx.isInitialRestMode());
        int hp = unitx.getHitpoints();
        while(unitx.getHitpoints()==hp)
            unitx.advanceTime(0.2d);
        assertFalse(unitx.isInitialRestMode());// At least one hitpoint recovered => not in initial rest mode
    }

    @Test
    public void testIsMoving(){
        assertFalse(unitx.isMoving());
        unitx.moveToAdjacent(new Vector(1,1,0));
        assertTrue(unitx.isMoving());
    }

    @Test
    public void testIsSprinting(){
        assertFalse(unitx.isSprinting());
        unitx.moveToAdjacent(new Vector(1,1,0));
        unitx.sprint();
        assertTrue(unitx.isSprinting());
    }

    @Test
    public void testIsValidAttack(){
    	assertTrue(unitx.isValidAttack(unitz));
    	assertFalse(unitx.isValidAttack(unitx));// Unit cannot attack himself
    	assertFalse(unitx.isValidAttack(unity));// Unity is too far to attack
        unitz.rest();
        assertFalse(unitz.isValidAttack(unitx));// Unit is still in initial rest mode
        unitx.attack(unitz);
        assertFalse(unitx.isValidAttack(unitz));// Unit is still attacking
        unity.terminate();
        assertFalse(unitz.isValidAttack(unity));// Unity has no hitpoints
    }

}