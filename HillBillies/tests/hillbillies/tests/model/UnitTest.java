package hillbillies.tests.model;

import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.utils.Vector;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Unit Test Class
 * @author Kenneth & Bram
 * @version 1.0
 */
public class UnitTest {

	private static Unit unit, customUnit, testUnit, unitx, unity, unitz;
	private static World AirWorld;
	private final static Set<TerrainChangeListener> listeners = new HashSet<>();
	private static TerrainChangeListener modelListener = new TerrainChangeListener() {

		@Override
		public void notifyTerrainChanged(int x, int y, int z) {
			for (TerrainChangeListener listener : new HashSet<>(listeners)) {
				listener.notifyTerrainChanged(x, y, z);
			}
		}
	};

    @BeforeClass
    public static void setUpClass() {
    	int[][][] types = new int[50][50][2];
    	for (int x = 0; x < types.length; x++) {
			for (int y = 0; y < types[x].length; y++) {
				for (int z = 0; z < types[x][y].length; z++) {
					types[x][y][z] = 0;// A test world that only have air as type
				}
			}
    	}
    	AirWorld = new World(types, modelListener); 
        unit = new Unit(AirWorld, "Unit", new Vector(25,25,0));
        customUnit = new Unit(AirWorld, "Custom", new Vector(20,20,0), 50, 50, 50, 100, 100, 100);
    }

    @Before
    public void setUp() throws Exception {
    	unitx = new Unit(AirWorld,"Unitx", new Vector(23,23,0));
    	unity = new Unit(AirWorld,"Unity", new Vector(21,23,0));
    	unitz = new Unit(AirWorld,"Unitz", new Vector(22,22,0));
        testUnit = new Unit(AirWorld,"TestUnit", new Vector(0,0,0));
    }
    //TESTING CONSTRUCTORS
    @Test
    public void testConstructor1(){
        assertEquals(unit.getName(),"Unit");
        assertTrue(unit.getPosition().equals(new Vector(25.5,25.5,0.5)));
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
        assertTrue(customUnit.getPosition().equals(new Vector(20.5,20.5,0.5)));
        assertEquals(customUnit.getAgility(), 50);
        assertEquals(customUnit.getStrength(), 50);
        assertEquals(customUnit.getToughness(), 50);
        assertEquals(customUnit.getWeight(), 100);
        assertEquals(customUnit.getStamina(), 100);
        assertEquals(customUnit.getHitpoints(), 100);
        assertEquals(customUnit.getOrientation(), Unit.INITIAL_ORIENTATION, Vector.EQUALS_PRECISION);
    }
    @Test
    public void testConstructorRandom1(){
    	Unit Random;
    	for (int i=0; i<50; i++){
    		Random = new Unit(AirWorld);
    		assertTrue(Unit.isValidName(Random.getName()) && 
    				Unit.isValidAgility(Random.getAgility())&&
    		Unit.isValidInitialAgility(Random.getAgility())&&
    		Unit.isValidHitpoints(Random.getHitpoints(), Random.getWeight(), Random.getToughness())&&
    		Unit.isValidInitialHitpoints(Random.getHitpoints(), Random.getWeight(), Random.getToughness())&&
    		Unit.isValidInitialStamina(Random.getStamina(), Random.getWeight(), Random.getToughness())&&
    		Unit.isValidStamina(Random.getStamina(), Random.getWeight(), Random.getToughness())&&
    		Unit.isValidInitialStrength(Random.getStrength())&&
    		Unit.isValidStrength(Random.getStrength())&&
    		Unit.isValidInitialToughness(Random.getToughness())&&
    		Unit.isValidToughness(Random.getToughness())&&
    		Unit.isValidInitialWeight(Random.getWeight(), Random.getStrength(), Random.getAgility())&&
    		Unit.isValidWeight(Random.getWeight(), Random.getStrength(), Random.getAgility())&&
    		Unit.isValidOrientation(Random.getOrientation()));
    	}
    }
    @Test
    public void testConstructorRandom2(){
    	Unit Random;
    	Set<Long> ids = new HashSet<>();
    	for (int i=0; i<50; i++){
    		Random = new Unit(AirWorld);
    		assertTrue(Unit.isValidName(Random.getName()) && 
    				Unit.isValidAgility(Random.getAgility())&&
    		Unit.isValidInitialAgility(Random.getAgility())&&
    		Unit.isValidHitpoints(Random.getHitpoints(), Random.getWeight(), Random.getToughness())&&
    		Unit.isValidInitialHitpoints(Random.getHitpoints(), Random.getWeight(), Random.getToughness())&&
    		Unit.isValidInitialStamina(Random.getStamina(), Random.getWeight(), Random.getToughness())&&
    		Unit.isValidStamina(Random.getStamina(), Random.getWeight(), Random.getToughness())&&
    		Unit.isValidInitialStrength(Random.getStrength())&&
    		Unit.isValidStrength(Random.getStrength())&&
    		Unit.isValidInitialToughness(Random.getToughness())&&
    		Unit.isValidToughness(Random.getToughness())&&
    		Unit.isValidInitialWeight(Random.getWeight(), Random.getStrength(), Random.getAgility())&&
    		Unit.isValidWeight(Random.getWeight(), Random.getStrength(), Random.getAgility())&&
    		Unit.isValidOrientation(Random.getOrientation())&&
    		Random.isValidFaction(Random.getFaction()));
    		assertFalse(ids.contains(Random.getId()));
    		ids.add(Random.getId());
    	}
    }
    @Test
    public void testConstructor3(){
    	Unit testUnit3 = new Unit(AirWorld, "TestUnit II", new Vector(0,0,0),50, 50, 50, 100 );
        assertEquals(testUnit3.getName(),"TestUnit II");
        assertTrue(testUnit3.getPosition().equals(new Vector(0.5,0.5,0.5)));
        assertEquals(testUnit3.getAgility(), 50);
        assertEquals(testUnit3.getStrength(), 50);
        assertEquals(testUnit3.getToughness(), 50);
        assertEquals(testUnit3.getWeight(), 100);
        assertEquals(testUnit3.getStamina(), Unit.getMaxStamina(testUnit3.getWeight(), testUnit3.getToughness()));
        assertEquals(testUnit3.getHitpoints(), Unit.getMaxHitpoints(testUnit3.getWeight(), testUnit3.getToughness()));
        assertEquals(testUnit3.getOrientation(), Unit.INITIAL_ORIENTATION, Vector.EQUALS_PRECISION);
    }
    
    //TESTING NAMES
    @Test
    public void TestSetValidNames(){
    	String validNames[] = new String[] { "O'Hara", "A '\"", "BLuB", "B ", "J Trump", "K III"};
    	for (String name : validNames){
    		testUnit.setName(name);
    		assertEquals(name, testUnit.getName());
    	}
    }
    @Test/*(expected = IllegalArgumentException.class)*/
    public void testSetInvalidNames() throws IllegalArgumentException {
    	String invalidNames[] = new String[] {"no caps", "B", " Blub", "'ABC", "K3"};
    	for(String name:invalidNames){
    		try{
    			testUnit.setName(name);
    		}catch (IllegalArgumentException e){
    			//OK
    		}
    		assertEquals("TestUnit", testUnit.getName());
    	}
    }
    
    //TESTING POSITION
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidPosition() throws IllegalArgumentException {
        Unit n = new Unit(AirWorld, "Name", new Vector(-10,20,30));
    }
    
//    @Test
//    public void testIsValidPosition(){
//        assertFalse(Unit.validatePosition(new Vector(-1,0,0)));
//        assertFalse(Unit.isValidPosition(new Vector(0,-0.0001,0)));
//        assertFalse(Unit.isValidPosition(new Vector(0,0,-1)));
//        assertFalse(Unit.isValidPosition(new Vector(51,0,0)));
//        assertFalse(Unit.isValidPosition(new Vector(0,50.0001,0)));
//        assertFalse(Unit.isValidPosition(new Vector(0,0,51)));
//        assertTrue(Unit.isValidPosition(new Vector(1,1,1)));
//        assertTrue(Unit.isValidPosition(new Vector(0,0,0)));
//    }
// TODO: dit is voor WorldObject   
     
    @Test
    public void testConstructorInvalidProperties() {
        Unit n = new Unit(AirWorld, "New", new Vector(20,20,0), 500, 50, 50, 100, 50, 50);// Invalid strength
        assertEquals(n.getStrength(), Unit.INITIAL_MIN_STRENGTH);
        n = new Unit(AirWorld, "New", new Vector(20,20,0), 50, 500, 50, 100, 50, 50);// Invalid agility
        assertEquals(n.getAgility(), Unit.INITIAL_MIN_AGILITY);
        n = new Unit(AirWorld, "New", new Vector(20,20,0), 50, 50, 500, 100, 50, 50);// Invalid toughness
        assertEquals(n.getToughness(), Unit.INITIAL_MIN_TOUGHNESS);
        n = new Unit(AirWorld, "New", new Vector(20,20,0), 50, 50, 50, -100, 50, 50);// Invalid weight
        assertEquals(n.getWeight(), Unit.getInitialMinWeight(50,50));
    }
    
    //TESTING PROPERTIES
    @Test
    public void testIsValidStrength(){
        assertFalse(Unit.isValidStrength(-1));// strength < MIN_STRENGTH
        assertFalse(Unit.isValidStrength(0));// strength < MIN_STRENGTH
        assertTrue(Unit.isValidStrength(1));
        assertFalse(Unit.isValidStrength(201));// strength > MAX_STRENGTH
        assertTrue(Unit.isValidStrength(150));
        assertTrue(Unit.isValidStrength(Unit.MAX_STRENGTH));
        assertTrue(Unit.isValidStrength(Unit.MIN_STRENGTH)); 
        assertFalse(Unit.isValidStrength(Unit.MAX_STRENGTH+1));
        assertFalse(Unit.isValidStrength(Unit.MIN_STRENGTH-1));
    }
    @Test
    public void testIsValidInitialStrength(){
        assertFalse(Unit.isValidInitialStrength(-1));// strength < MIN_STRENGTH
        assertFalse(Unit.isValidInitialStrength(Unit.INITIAL_MIN_STRENGTH-1));// strength < MIN_STRENGTH
        assertFalse(Unit.isValidInitialStrength(1));// strength < INITIAL_MIN_STRENGTH
        assertTrue(Unit.isValidInitialStrength(25));
        assertFalse(Unit.isValidInitialStrength(Unit.INITIAL_MAX_STRENGTH+1));// strength > INITIAL_MAX_STRENGTH
        assertTrue(Unit.isValidInitialStrength(Unit.INITIAL_MAX_STRENGTH));
        assertTrue(Unit.isValidInitialStrength(Unit.INITIAL_MIN_STRENGTH));
    }

    @Test
    public void testIsValidAgility(){
        assertFalse(Unit.isValidAgility(-1));
        assertFalse(Unit.isValidAgility(Unit.MIN_AGILITY - 1));// Unit.MIN_AGILITY - 1
        assertFalse(Unit.isValidAgility(Unit.MAX_AGILITY + 1));// Unit.MAX_AGILITY + 1
        assertTrue(Unit.isValidAgility(150));
        assertTrue(Unit.isValidAgility(Unit.MAX_AGILITY));
        assertTrue(Unit.isValidAgility(Unit.MIN_AGILITY));
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
        assertFalse(Unit.isValidToughness(Unit.MAX_TOUGHNESS+1));// toughness > MAX_TOUGHNESS
        assertTrue(Unit.isValidToughness(1));
        assertFalse(Unit.isValidToughness(Unit.MIN_TOUGHNESS-1));// toughness < MIN_TOUGHNESS
        assertTrue(Unit.isValidToughness(150));
        assertTrue(Unit.isValidToughness(Unit.MAX_TOUGHNESS));
        assertTrue(Unit.isValidToughness(Unit.MIN_TOUGHNESS));
    }
    @Test
    public void testIsValidInitialToughness(){
        assertFalse(Unit.isValidInitialToughness(-1));// toughness < MIN_TOUGHNESS
        assertFalse(Unit.isValidInitialToughness(Unit.INITIAL_MIN_TOUGHNESS-1));// toughness < MIN_TOUGHNESS
        assertFalse(Unit.isValidInitialToughness(1));// toughness < INITIAL_MIN_TOUGHNESS
        assertTrue(Unit.isValidInitialToughness(25));
        assertFalse(Unit.isValidInitialToughness(Unit.INITIAL_MAX_TOUGHNESS+1));// toughness > INITIAL_MAX_TOUGHNESS
        assertTrue(Unit.isValidInitialToughness(Unit.INITIAL_MAX_TOUGHNESS));
        assertTrue(Unit.isValidInitialToughness(Unit.INITIAL_MIN_TOUGHNESS));
    }

    @Test
    public void testIsValidWeight(){
        assertFalse(Unit.isValidWeight(-1,1,1));// weight < getMinWeight(1,1)
        assertFalse(Unit.isValidWeight(Unit.MIN_WEIGHT-1,1,1));// weight < getMinWeight(1,1)
        assertTrue(Unit.isValidWeight(1,1,1));
        assertFalse(Unit.isValidWeight(Unit.MAX_WEIGHT+1,1,1));// weight > MAX_WEIGHT
        assertTrue(Unit.isValidWeight(150,1,1));
        assertFalse(Unit.isValidWeight(50,100,100));// weight < getMinWeight(100,100)
        assertTrue(Unit.isValidWeight(Unit.MIN_WEIGHT, 1,1));
        assertTrue(Unit.isValidWeight(Unit.MAX_WEIGHT,1,1));
        assertTrue(Unit.isValidWeight(Unit.getMinWeight(1, 1),1,1));
        assertFalse(Unit.isValidWeight(Unit.getMinWeight(1, 1)-1,1,1));
    }
    @Test
    public void testIsValidInitialWeight(){
        assertFalse(Unit.isValidInitialWeight(-1,1,1));// weight < getMinWeight(1,1)
        assertFalse(Unit.isValidInitialWeight(Unit.INITIAL_MIN_WEIGHT-1,1,1));// weight < getMinWeight(1,1)
        assertFalse(Unit.isValidInitialWeight(1,1,1));// weight < getInitialMinWeight(1,1)
        assertTrue(Unit.isValidInitialWeight(25,1,1));
        assertFalse(Unit.isValidInitialWeight(Unit.INITIAL_MAX_WEIGHT+1,1,1));// weight > INITIAL_MAX_WEIGHT
        assertTrue(Unit.isValidInitialWeight(50,1,1));
        assertFalse(Unit.isValidInitialWeight(50,100,100));// weight < getMinWeight(100,100)
        assertTrue(Unit.isValidInitialWeight(Unit.INITIAL_MIN_WEIGHT, 1,1));
        assertTrue(Unit.isValidInitialWeight(Unit.INITIAL_MAX_WEIGHT,1,1));
        assertTrue(Unit.isValidInitialWeight(Unit.getInitialMinWeight(1, 1),1,1));
        assertFalse(Unit.isValidInitialWeight(Unit.getInitialMinWeight(1, 1)-1,1,1));
    }

    @Test
    public void testIsValidStamina(){
        assertFalse(Unit.isValidStamina(-1,1,1));// stamina < MIN_STAMINA
        assertTrue(Unit.isValidStamina(0,100,100));
        assertFalse(Unit.isValidStamina(801,200,200));// stamina > getMaxStamina(200,200)
        assertTrue(Unit.isValidStamina(560,200,200));
        assertFalse(Unit.isValidStamina(100,25,25));
        assertTrue(Unit.isValidStamina(Unit.getMaxStamina(200, 200),200,200));
        assertTrue(Unit.isValidStamina(Unit.MIN_STAMINA,200,200));
        assertFalse(Unit.isValidStamina(Unit.getMaxStamina(200, 200)+1,200,200));
        assertFalse(Unit.isValidStamina(Unit.MIN_STAMINA-1,200,200));
    }
    @Test
    public void testIsValidInitialStamina(){
        assertFalse(Unit.isValidInitialStamina(-1,1,1));// stamina < MIN_STAMINA
        assertFalse(Unit.isValidInitialStamina(Unit.INITIAL_MIN_STAMINA-1,100,100));// stamina < INITIAL_MIN_STAMINA
        assertTrue(Unit.isValidInitialStamina(1,100,100));
        assertFalse(Unit.isValidInitialStamina(Unit.getMaxStamina(200,200)+1,200,200));// stamina > getMaxStamina(200,200)
        assertTrue(Unit.isValidInitialStamina(Unit.INITIAL_MIN_STAMINA,100,100));
        assertTrue(Unit.isValidInitialStamina(Unit.getMaxStamina(200,200),200,200));
    }

    @Test
    public void testIsValidHitpoints(){
        assertFalse(Unit.isValidHitpoints(-1,1,1));// hitpoints < MIN_HITPOINTS
        assertTrue(Unit.isValidHitpoints(0,1,1));
        assertFalse(Unit.isValidHitpoints(801,200,200));// hitpoints > getMaxHitpoints(200,200)
        assertTrue(Unit.isValidHitpoints(560,200,200));
        assertFalse(Unit.isValidHitpoints(100,25,25));
        assertTrue(Unit.isValidHitpoints(Unit.getMaxHitpoints(200, 200),200,200));
        assertTrue(Unit.isValidHitpoints(Unit.MIN_HITPOINTS,200,200));
        assertFalse(Unit.isValidHitpoints(Unit.getMaxHitpoints(200, 200)+1,200,200));
        assertFalse(Unit.isValidHitpoints(Unit.MIN_HITPOINTS-1,200,200));
    }
    @Test
    public void testIsValidInitialHitpoints(){
        assertFalse(Unit.isValidInitialHitpoints(-1,1,1));// stamina < MIN_STAMINA
        assertFalse(Unit.isValidInitialHitpoints(Unit.INITIAL_MIN_HITPOINTS-1,100,100));// stamina < INITIAL_MIN_STAMINA
        assertTrue(Unit.isValidInitialHitpoints(1,100,100));
        assertFalse(Unit.isValidInitialHitpoints(Unit.getMaxHitpoints(200,200)+1,200,200));// stamina > getMaxStamina(200,200)
        assertTrue(Unit.isValidInitialHitpoints(Unit.INITIAL_MIN_HITPOINTS,100,100));
        assertTrue(Unit.isValidInitialHitpoints(Unit.getMaxHitpoints(200,200),200,200));
    }
    
    @Test
    public void testIsValidOrientation(){
    	assertFalse(Unit.isValidOrientation((float) -0.001));
    	assertFalse(Unit.isValidOrientation((float)(5*Math.PI)));
    	assertTrue(Unit.isValidOrientation((float)(0)));
    	assertTrue(Unit.isValidOrientation(Unit.MAX_ORIENTATION));
    	assertTrue(Unit.isValidOrientation(Unit.MIN_ORIENTATION));
    	assertFalse(Unit.isValidOrientation(Unit.MAX_ORIENTATION+1));
    	assertFalse(Unit.isValidOrientation(Unit.MIN_ORIENTATION-1));
    	assertTrue(Unit.isValidOrientation(Unit.INITIAL_ORIENTATION));
    }
    
    @Test
    public void testSetFaction(){
    	Set<Faction> factions = AirWorld.getFactions();
    	Iterator<Faction> iterator = factions.iterator();
    	while(iterator.hasNext()){
    		Faction f = iterator.next();
    		testUnit.setFaction(f);
    		assertTrue(testUnit.isValidFaction(testUnit.getFaction()));
    		assertEquals(f, testUnit.getFaction());
    	}
    	assertFalse(testUnit.isValidFaction(new Faction()));
    	
    }
    
    @Test
    public void testSetAgility(){
    	testUnit.setAgility(Unit.MIN_AGILITY-1);
    	assertFalse(Unit.MIN_AGILITY-1 == testUnit.getAgility());
    	int weight = testUnit.getWeight();
    	testUnit.setStrength(Unit.MAX_STRENGTH);
    	testUnit.setAgility(Unit.MAX_AGILITY);
    	assertTrue(testUnit.getAgility() == Unit.MAX_AGILITY);
    	assertFalse(testUnit.getWeight() == weight);
    }
    
    @Test
    public void testSetHitpoints(){
    	testUnit.setHitpoints(Unit.MIN_HITPOINTS);
    	assertTrue(Unit.MIN_HITPOINTS == testUnit.getHitpoints());
    	assertFalse(Unit.MIN_HITPOINTS+1 == testUnit.getHitpoints());
    	testUnit.setHitpoints(Unit.getMaxHitpoints(testUnit.getWeight(), testUnit.getToughness()));
    	int hit = testUnit.getHitpoints();
    	testUnit.removeHitpoints(0);
    	assertEquals(hit,testUnit.getHitpoints());
    	testUnit.setHitpoints(hit);
    	testUnit.removeHitpoints(hit*2);
    	assertEquals(testUnit.getHitpoints(), Unit.MIN_HITPOINTS);
    	testUnit.setHitpoints(hit);
    	testUnit.removeHitpoints(1);
    	assertEquals(testUnit.getHitpoints(), hit-1);
    	
    }
    @Test
    public void testSetInitialHitpoints(){
    	testUnit.setInitialHitpoints(Unit.INITIAL_MIN_HITPOINTS);
    	assertEquals(Unit.INITIAL_MIN_HITPOINTS,testUnit.getHitpoints());
    	assertFalse(Unit.INITIAL_MIN_HITPOINTS+1 == testUnit.getHitpoints());
    }
    
    @Test
    public void testSetOrientation(){
    	testUnit.setOrientation(Unit.MAX_ORIENTATION);
    	assertTrue(testUnit.getOrientation() == Unit.MIN_ORIENTATION);
    	testUnit.setOrientation(3*(float)Math.PI);
    	assertTrue(testUnit.getOrientation() == (3*(float)Math.PI)%Unit.MAX_ORIENTATION);	
    }
    @Test
    public void testSetStamina(){
    	testUnit.setStamina(Unit.MIN_STAMINA);
    	assertTrue(Unit.MIN_STAMINA == testUnit.getStamina());
    	assertFalse(Unit.MIN_STAMINA+1 == testUnit.getStamina());
    }
    @Test
    public void testSetInitialStamina(){
    	testUnit.setInitialStamina(Unit.INITIAL_MIN_STAMINA);
    	assertTrue(Unit.INITIAL_MIN_STAMINA == testUnit.getStamina());
    	assertFalse(Unit.INITIAL_MIN_STAMINA+1 == testUnit.getStamina());
    }
    @Test
    public void testSetStrength(){
    	testUnit.setStrength(Unit.MIN_STRENGTH-1);
    	assertFalse(Unit.MIN_STRENGTH-1 == testUnit.getStrength());
    	int weight = testUnit.getWeight();
    	testUnit.setAgility(Unit.MAX_AGILITY);
    	testUnit.setStrength(Unit.MAX_STRENGTH);
    	assertTrue(testUnit.getStrength() == Unit.MAX_STRENGTH);
    	assertFalse(testUnit.getWeight() == weight);    	
    }
    @Test
    public void testSetToughness(){
    	testUnit.setToughness(Unit.MIN_TOUGHNESS-1);
    	assertFalse(Unit.MIN_TOUGHNESS-1 == testUnit.getToughness());
    	testUnit.setToughness(Unit.MAX_TOUGHNESS);
    	assertTrue(Unit.MAX_TOUGHNESS == testUnit.getToughness());
    	testUnit.setToughness(Unit.MAX_TOUGHNESS+1);
    	assertTrue(Unit.MAX_TOUGHNESS == testUnit.getToughness());    	
    }
    @Test
    public void testSetWeight(){
    	testUnit.setWeight(Unit.getMinWeight(testUnit.getStrength()-1, testUnit.getAgility()));
    	assertFalse(Unit.getMinWeight(testUnit.getStrength()-1, testUnit.getAgility()) == testUnit.getWeight());
    	testUnit.setWeight(Unit.MAX_WEIGHT+1);
    	assertTrue(testUnit.getWeight()== Unit.INITIAL_MIN_WEIGHT);
    	testUnit.setWeight(Unit.MAX_WEIGHT);
    	assertTrue(testUnit.getWeight()== Unit.MAX_WEIGHT);
    	int weight = testUnit.getWeight();
    	Log log = new Log(AirWorld,testUnit);
    	assertTrue(testUnit.getWeight()> weight);
    }
    
    //TESTING 
/*    @Test
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
        unitx.startDefaultBehaviour();
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

    @Test
    public void testGetIntervalTicks(){
        assertEquals(2, Unit.getIntervalTicks(12.0258, 0.1824, 0.1));// From getIntervalTicksExample.txt
        assertEquals(0, Unit.getIntervalTicks(12.2082, 0.0523, 0.1));// From getIntervalTicksExample.txt

        assertEquals(2, Unit.getIntervalTicks(19.965, 1.86, 1d));// From restingLogicExample.txt
        assertEquals(1, Unit.getIntervalTicks(23.562, 0.154, 0.2));// From restingLogicExample.txt
        assertEquals(1, Unit.getIntervalTicks(19.44, 0.56, 1d));// From restingLogicExample.txt
        assertEquals(1, Unit.getIntervalTicks(23.562, 0.038, 0.2));// From restingLogicExample.txt
    }

    @Test
    public void testDefaultBehaviour(){
        assertFalse(unitx.isDefaultActive());
        unitx.startDefaultBehaviour();
        assertTrue(unitx.isDefaultActive());
        unitx.stopDefaultBehaviour();
        assertFalse(unitx.isDefaultActive());
        assertTrue(unitx.getCurrentActivity()==Activity.NONE);
    }

    @Test
    public void testFighting(){
        Vector oldDefenderPosition = unitz.getPosition();
        int oldDefenderHp = unitz.getHitpoints();
        assertFalse(unitx.isAttacking());
        unitx.startDefaultBehaviour();
        unitx.attack(unitz);
        assertFalse(unitx.isDefaultActive());
        assertTrue(unitx.isAttacking());
        assertEquals(Math.atan2(unitz.getPosition().Y()-unitx.getPosition().Y(),unitz.getPosition().X()-unitx.getPosition().X()), unitx.getOrientation(), Vector.EQUALS_PRECISION);
        assertEquals(Math.atan2(unitx.getPosition().Y()-unitz.getPosition().Y(),unitx.getPosition().X()-unitz.getPosition().X()), unitz.getOrientation(), Vector.EQUALS_PRECISION);
        assertTrue((!unitz.getPosition().equals(oldDefenderPosition) && unitz.getHitpoints()==oldDefenderHp) || // Unit dodged the attack
                (unitz.getPosition().equals(oldDefenderPosition) && unitz.getHitpoints()==oldDefenderHp) || // Unit blocked the attack
                (unitz.getPosition().equals(oldDefenderPosition) && unitz.getHitpoints()<oldDefenderHp) // Unit got damage
        );
        // Defending is instantaneous:
        assertTrue(unitz.isAbleToWork() && unitz.isAbleToMove() && unitz.isAbleToRest());
        // Attacking lasts 1s:
        assertFalse(unitx.isAbleToWork() || unitx.isAbleToMove() || unitx.isAbleToRest());
        for(int i=0;i<5;i++)
            unitx.advanceTime(0.2);
        assertTrue(unitx.isAbleToWork() && unitx.isAbleToMove() && unitx.isAbleToRest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalAttack() throws IllegalArgumentException{
        unitx.attack(unity);
    }

    @Test
    public void testMove(){
        // MoveToAdjacent
        Vector direction = new Vector(1,0,0);
        Vector target = unitx.getPosition().add(direction);
        unitx.startDefaultBehaviour();
        unitx.moveToAdjacent(direction);
        assertTrue(unitx.isMoving());
        assertFalse(unitx.isDefaultActive());
        assertTrue(unitx.getNextPosition().equals(target));
        while(!unitx.getPosition().equals(target))
            unitx.advanceTime(0.2);
        assertTrue(unitx.getPosition().equals(target));

        // MoveToTarget
        target = new Vector(20,20,1);
        unitx.startDefaultBehaviour();
        unitx.moveToTarget(target);
        assertTrue(unitx.isMoving());
        assertFalse(unitx.isDefaultActive());
        while(!unitx.getPosition().equals(target))
            unitx.advanceTime(0.2);
        assertTrue(unitx.getPosition().equals(target));
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalMove() throws IllegalStateException{
        unitx.rest();
        unitx.moveToAdjacent(new Vector(1,0,0));// Unit is in initial rest mode => not able to move
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMove2() throws IllegalArgumentException{
        unitx.moveToAdjacent(new Vector(0,0,-1));
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalMove3() throws IllegalStateException{
        unitx.rest();
        unitx.moveToTarget(new Vector(1,1,1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMove4() throws IllegalArgumentException{
        unitx.moveToTarget(new Vector(0,0,-1));
    }

    @Test
    public void testSprinting(){
        unitx.moveToTarget(new Vector(20,20,1));
        unitx.sprint();
        assertTrue(unitx.isSprinting());
        unitx.advanceTime(0.2);
        unitx.stopSprint();
        assertFalse(unitx.isSprinting());
    }

    @Test(expected = IllegalStateException.class)
    public void testSprintingZeroStamina() throws IllegalStateException{
        unitx.moveToTarget(new Vector(20,20,1));
        unitx.sprint();
        while(unitx.isSprinting())
            unitx.advanceTime(0.2);
        unitx.sprint();// Unit is exhausted => unable to sprint
    }

    @Test
    public void testRest(){
        unitx.startDefaultBehaviour();
        unitx.rest();
        assertTrue(unitx.getCurrentActivity()==Activity.REST);
        assertFalse(unitx.isDefaultActive());
        int maxHp = Unit.getMaxHitpoints(unitx.getWeight(), unitx.getToughness());
        int maxSt = Unit.getMaxStamina(unitx.getWeight(), unitx.getToughness());
        while(unitx.getHitpoints()<maxHp || unitx.getStamina()<maxSt)
            unitx.advanceTime(0.2);
        assertTrue(unitx.getCurrentActivity()==Activity.NONE);
        // Test sleep timer
        for(int i=0;i<=5*60*3;i++)
            unity.advanceTime(0.2);
        assertTrue(unity.getCurrentActivity()==Activity.REST);
    }

    @Test(expected = IllegalStateException.class)
    public void testRestIllegal() throws IllegalStateException{
        unitx.work();
        unitx.rest();// Unit is still working => unable to rest
    }

    @Test
    public void testWork(){
        unitx.startDefaultBehaviour();
        unitx.work();
        assertTrue(unitx.getCurrentActivity()==Activity.WORK);
        assertFalse(unitx.isDefaultActive());
        float duration = unitx.getWorkDuration();
        double time = 0;
        while(time<=duration){
            time += 0.2;
            unitx.advanceTime(0.2);
        }
        assertTrue(unitx.getCurrentActivity()==Activity.NONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testWorkIllegal() throws IllegalStateException{
        unitx.rest();
        unitx.work();// Unit is in initial rest mode => unable to work
    }*/

}