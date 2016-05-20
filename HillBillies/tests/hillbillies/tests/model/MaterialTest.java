package hillbillies.tests.model;

import static org.junit.Assert.*;
import static hillbillies.tests.util.TestHelper.advanceTimeFor;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Boulder;
import hillbillies.model.Log;
import hillbillies.model.Material;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.utils.Vector;

public class MaterialTest {

	private static World airWorld, otherWorld;;
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
    	int[][][] types = new int[25][25][3];
    	airWorld = new World(types, modelListener);
        otherWorld = new World(new int[5][5][5],null);
    }


	private static Unit testUnit;
	private static Material testBoulder, testLog;
	private static Vector nullPosition = new Vector(0,0,0);
	private static double dt = 0.2;
    @Before
	public void setUp() throws Exception {
    	testUnit = new Unit(airWorld,"TestUnit", nullPosition);
    	testLog = new Log(airWorld, airWorld.getCube(nullPosition));
    	testBoulder = new Boulder(airWorld, airWorld.getCube(nullPosition));
	}

	@After
	public void tearDown() throws Exception {
		for(Material m:airWorld.getMaterials(Material.class, false))
			m.terminate();
		advanceTimeFor(airWorld, 0.2);
		for(Material m:otherWorld.getMaterials(Material.class, false))
			m.terminate();
		advanceTimeFor(otherWorld, 0.2);
	}

	@Test
	public void testBoulderConstructor() {
		assertTrue(new Boulder(airWorld,testUnit).getOwner() == testUnit);
		assertTrue(new Boulder(airWorld,airWorld.getCube(nullPosition)).getOwner() == airWorld.getCube(nullPosition));
	}
	@Test
	public void testLogConstructor() {
		assertTrue(new Log(airWorld,testUnit).getOwner() == testUnit);
		assertTrue(new Log(airWorld,airWorld.getCube(nullPosition)).getOwner() == airWorld.getCube(nullPosition));
	}
	
	@Test(expected = IllegalArgumentException.class)//different world
	public void testInvalidBoulderConstructor() throws IllegalArgumentException{
		new Boulder(otherWorld,airWorld.getCube(nullPosition));
	}
	@Test(expected = IllegalArgumentException.class)//different world
	public void testInvalidLogConstructor() throws IllegalArgumentException{
		new Log(otherWorld,airWorld.getCube(nullPosition));
	}
	
	@Test(expected = IllegalStateException.class)//nb >max
	public void testInvalidBoulederConstructor2() throws IllegalArgumentException{
		while(testUnit.getNbOwnedMaterials()!=testUnit.getMaxNbOwnedMaterials())
			new Boulder(airWorld,testUnit);
		new Boulder(airWorld,testUnit);
	}
	@Test(expected = IllegalStateException.class)//nb >max
	public void testInvalidLogConstructor2() throws IllegalArgumentException{
		while(testUnit.getNbOwnedMaterials()!=testUnit.getMaxNbOwnedMaterials())
			new Log(airWorld,testUnit);
		new Log(airWorld,testUnit);
	}
	
	@Test(expected = NullPointerException.class)//null
	public void testInvalidBoulderConstructor3() throws NullPointerException{
		new Boulder(null, null);
	}
	@Test(expected = NullPointerException.class)//null
	public void testInvalidLogConstructor3() throws NullPointerException{
		new Boulder(null, null);
	}
	
	@Test
	public void testAdvanceTime() {
		Boulder boulder = new Boulder(airWorld, airWorld.getCube(new Vector(0,0,2)));
		boulder.advanceTime(0.001);
		while(boulder.getOwner() == null)
			boulder.advanceTime(dt);
		assertTrue(boulder.getPosition().getCubeCoordinates().equals(nullPosition));
		Log log = new Log(airWorld, airWorld.getCube(new Vector(0,0,2)));
		log.advanceTime(0.001);
		while(log.getOwner() == null)
			log.advanceTime(dt);
		assertTrue(log.getPosition().getCubeCoordinates().equals(nullPosition));
	}

	@Test
	public void testSetOwner() {
		testBoulder.setOwner(airWorld.getCube(new Vector(0,0,2)));
		assertEquals(testBoulder.getOwner(), airWorld.getCube(new Vector(0,0,2)));
		testBoulder.setOwner(null);
		assertTrue(testBoulder.getOwner() == null);
		testLog.setOwner(airWorld.getCube(new Vector(0,0,2)));
		assertEquals(testLog.getOwner(), airWorld.getCube(new Vector(0,0,2)));
		testLog.setOwner(null);
		assertTrue(testLog.getOwner() == null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBoulderSetInvalidOwner() {
		testBoulder.setOwner(new Unit(otherWorld));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testLogSetInvalidOwner() {
		testLog.setOwner(new Unit(otherWorld));
	}

	@Test
	public void testGetPositionBoulder() {
		assertTrue(testBoulder.getPosition().equals(nullPosition.getCubeCenterCoordinates()));
		testBoulder.setOwner(testUnit);
		testUnit.moveToTarget(new Vector(10,10,0));
		while(testUnit.isMoving()){
			assertTrue(testBoulder.getPosition().equals(testUnit.getPosition()));
			advanceTimeFor(airWorld, 0.2);
		}
	}
	@Test
	public void testGetPositionLog() {	
		assertTrue(testLog.getPosition().equals(nullPosition.getCubeCenterCoordinates()));
		testLog.setOwner(testUnit);
		testUnit.moveToTarget(nullPosition);
		while(testUnit.isMoving()){
			assertTrue(testLog.getPosition().equals(testUnit.getPosition()));
			advanceTimeFor(airWorld, 0.2);
		}
	}

	@Test
	public void testGetWorldBoulder() {
		assertTrue((new Boulder(airWorld, testUnit)).getWorld() == airWorld);
		assertTrue((new Boulder(airWorld,airWorld.getCube(nullPosition))).getWorld() == airWorld);	
	}
	@Test
	public void testGetWorldLog() {
		assertTrue((new Log(airWorld, testUnit)).getWorld() == airWorld);
		assertTrue((new Log(airWorld,airWorld.getCube(nullPosition))).getWorld() == airWorld);	
	}

	@Test
	public void testGetOwnerBoulder() {		
		assertTrue((new Boulder(airWorld, testUnit)).getOwner() == testUnit);
		assertTrue((new Boulder(airWorld,airWorld.getCube(nullPosition))).getOwner() == airWorld.getCube(nullPosition));
		Boulder b = new Boulder(airWorld, airWorld.getCube(new Vector (0,0,2)));
		b.advanceTime(0.01);
		assertTrue(b.getOwner() == null);
	}
	public void testGetOwnerLog() {		
		assertTrue((new Log(airWorld, testUnit)).getOwner() == testUnit);
		assertTrue((new Log(airWorld,airWorld.getCube(nullPosition))).getOwner() == airWorld.getCube(nullPosition));
		Log l = new Log(airWorld, airWorld.getCube(new Vector (0,0,2)));
		l.advanceTime(0.01);
		assertTrue(l.getOwner() == null);
	}

	@Test
	public void testGetWeight() {
		for(int i = 0; i<30; i++){
			assertTrue(Boulder.canHaveAsWeight(new Boulder(airWorld,airWorld.getCube(nullPosition)).getWeight()));
		}
		int boulderWeight= testBoulder.getWeight();
		boulderWeight += 1;
		assertFalse(boulderWeight == testBoulder.getWeight());
		for(int i = 0; i<30; i++){
			assertTrue(Log.canHaveAsWeight(new Log(airWorld,airWorld.getCube(nullPosition)).getWeight()));
		}
		int logWeight = testBoulder.getWeight();
		logWeight += 1;
		assertFalse(logWeight == testBoulder.getWeight());
	}

	@Test
	public void testIsValidOwner() {
		assertFalse(testBoulder.isValidOwner(otherWorld.getCube(nullPosition)));
		assertTrue(testBoulder.isValidOwner(null));
		testUnit.terminate();
		assertFalse(testBoulder.isValidOwner(testUnit));
		
		assertFalse(testLog.isValidOwner(otherWorld.getCube(nullPosition)));
		assertTrue(testLog.isValidOwner(null));
		testUnit.terminate();
		assertFalse(testLog.isValidOwner(testUnit));
	}

	@Test
	public void testCanHaveAsWeight() {
		assertTrue(Boulder.canHaveAsWeight(Boulder.MAX_WEIGHT));
		assertTrue(Boulder.canHaveAsWeight(Boulder.MIN_WEIGHT));
		assertFalse(Boulder.canHaveAsWeight(Boulder.MAX_WEIGHT+1));
		assertFalse(Boulder.canHaveAsWeight(Boulder.MIN_WEIGHT-1));
		
		assertTrue(Log.canHaveAsWeight(Log.MAX_WEIGHT));
		assertTrue(Log.canHaveAsWeight(Log.MIN_WEIGHT));
		assertFalse(Log.canHaveAsWeight(Log.MAX_WEIGHT+1));
		assertFalse(Log.canHaveAsWeight(Log.MIN_WEIGHT-1));
	}
	
	@Test
	public void testTerminateBoulder() {
		int nb = airWorld.getCube(nullPosition).getNbOwnedMaterials();
		testBoulder.terminate();
		assertTrue(testBoulder.isTerminated());
		assertTrue(nb > airWorld.getCube(nullPosition).getNbOwnedMaterials());
		assertTrue(testBoulder.getOwner() == null);
		testBoulder.advanceTime(dt);
	}
	public void testTerminateLog() {
		int nb = airWorld.getCube(nullPosition).getNbOwnedMaterials();
		testLog.terminate();
		assertTrue(testLog.isTerminated());
		assertTrue(nb > airWorld.getCube(nullPosition).getNbOwnedMaterials());
		assertTrue(testLog.getOwner() == null);
		testLog.advanceTime(dt);
	}

	@Test
	public void testIsTerminated() {
		assertFalse(testBoulder.isTerminated());
		testBoulder.terminate();
		assertTrue(testBoulder.isTerminated());
		
		assertFalse(testLog.isTerminated());
		testLog.terminate();
		assertTrue(testLog.isTerminated());
	}

}
