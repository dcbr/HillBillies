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
	private static Material testMaterial, testLog, testBoulder;
	private static Vector nullPosition = new Vector(0,0,0);
	private static double dt = 0.2;
    @Before
	public void setUp() throws Exception {
    	testUnit = new Unit(airWorld,"TestUnit", nullPosition);
    	testMaterial = new Material(airWorld, airWorld.getCube(nullPosition));
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
	public void testMaterialConstructor() {
		assertTrue(new Material(airWorld,testUnit).getOwner() == testUnit);
		assertTrue(new Material(airWorld,airWorld.getCube(nullPosition)).getOwner() == airWorld.getCube(nullPosition));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMaterialConstructor() throws IllegalArgumentException{
		new Material(otherWorld,airWorld.getCube(nullPosition));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testInvalidMaterialConstructor2() throws IllegalArgumentException{
		while(testUnit.getNbOwnedMaterials()!=testUnit.getMaxNbOwnedMaterials())
			new Material(airWorld,testUnit);
		new Material(airWorld,testUnit);
	}
	
	
	@Test
	public void testAdvanceTime() {
		Material material = new Material(airWorld, airWorld.getCube(new Vector(0,0,2)));
		material.advanceTime(0.001);
		while(material.getOwner() == null)
			material.advanceTime(dt);
		assertTrue(material.getPosition().getCubeCoordinates().equals(nullPosition));
	}

	@Test
	public void testSetOwner() {
		testMaterial.setOwner(airWorld.getCube(new Vector(0,0,2)));
		assertEquals(testMaterial.getOwner(), airWorld.getCube(new Vector(0,0,2)));
		testMaterial.setOwner(null);
		assertTrue(testMaterial.getOwner() == null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidOwner() {
		testMaterial.setOwner(new Unit(otherWorld));
	}

	@Test
	public void testGetPosition() {
		assertTrue(testMaterial.getPosition().equals(testMaterial.getPosition()));
		testMaterial.setOwner(testUnit);
		testUnit.moveToTarget(new Vector(10,10,0));
		while(testUnit.isMoving()){
			assertTrue(testMaterial.getPosition().equals(testUnit.getPosition()));
			advanceTimeFor(airWorld, 0.2);
		}
	}

	@Test
	public void testGetWorld() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOwner() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWeight() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidOwner() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanHaveAsWeight() {
		fail("Not yet implemented");
	}

	@Test
	public void testTerminate() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsTerminated() {
		fail("Not yet implemented");
	}

}
