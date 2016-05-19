package hillbillies.tests.model;

import static hillbillies.tests.util.TestHelper.advanceTimeFor;
import static org.junit.Assert.*;

import hillbillies.model.*;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.utils.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import hillbillies.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;

import java.util.ArrayDeque;
import java.util.Arrays;

public class WorldTest {

	private World w;
	private Unit u1, u2;
	private static ArrayDeque<Vector> lastTerrainChanges;
	private static int[][][] terrain;
	private static TerrainChangeListener listener;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		terrain = new int[5][5][5];
		for(int i=1;i<3;i++)
			for(int j=1;j<3;j++)
				terrain[i][j][1] = 1 + ((i+j)%2);
		terrain[2][2][0] = Terrain.WORKSHOP.getId();
		terrain[0][1][1] = Terrain.ROCK.getId();

		listener = (x, y, z) -> lastTerrainChanges.addFirst(new Vector(x, y, z));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		w = new World(terrain, listener);
		u1 = new Unit(w, "Unit1", new Vector(1,1,1));
		u2 = new Unit(w, "Unit2", new Vector(0,0,0));
		lastTerrainChanges.clear();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor(){
		for(int i=0;i<terrain.length;i++)
			for(int j=0;j<terrain[i].length;j++)
				for(int k=0;k<terrain[i][j].length;k++)
					assertEquals(
							Terrain.fromId(terrain[i][j][k]),
							w.getCube(new Vector(i,j,k).multiply(Cube.CUBE_SIDE_LENGTH)).getTerrain()
					);

		assertEquals(terrain.length, w.getNbCubesX());
		assertEquals(terrain[0].length, w.getNbCubesY());
		assertEquals(terrain[0][0].length, w.getNbCubesZ());

		assertEquals(0, w.getNbMaterials());
		assertEquals(0, w.getNbFactions());
		assertEquals(0, w.getNbUnits());

		advanceTimeFor(w, 5d);// Check if collapsing cubes are notified to listener
		assertEquals(9, lastTerrainChanges.size());
		while(!lastTerrainChanges.isEmpty()){
			Vector collapsedCube = lastTerrainChanges.removeLast();
			assertTrue(collapsedCube.cubeX()>=1);
			assertTrue(collapsedCube.cubeX()<=3);
			assertTrue(collapsedCube.cubeY()>=1);
			assertTrue(collapsedCube.cubeY()<=3);
			assertEquals(1, collapsedCube.cubeZ());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorIllegal() throws IllegalArgumentException{
		int[][][] wrongTerrain = new int[6][6][7];
		new World(wrongTerrain, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorIllegal2() throws IllegalArgumentException{
		int[][][] wrongTerrain = new int[6][7][7];
		new World(wrongTerrain, null);
	}

	@Test
	public void worldTest() {
		int nbX = 10;
		int nbY = 10;
		int nbZ = 10;
		int[][][] terrainMatrix = new int[nbX][nbY][nbZ];
		for (int i = 0; i<5; i++){
			terrainMatrix[i][1][1] = 1;
			terrainMatrix[i][2][1] = 2;
		}
		World world = new World(terrainMatrix, new DefaultTerrainChangeListener());
		assertEquals(nbX, world.getNbCubesX());
		assertEquals(nbY, world.getNbCubesY());
		assertEquals(nbZ, world.getNbCubesZ());
	}

	@Test
	public void isValidPosition() throws Exception {
		assertTrue(w.isValidPosition(new Vector(2,2,2)));
		assertFalse(w.isValidPosition(w.getMaxPosition()));
		assertTrue(w.isValidPosition(w.getMinPosition()));
		assertFalse(w.isValidPosition(w.getMinPosition().add(-0.01d)));
	}

	@Test
	public void getNbCubesX() throws Exception {
		assertEquals(terrain.length, w.getNbCubesX());
	}

	@Test
	public void getNbCubesY() throws Exception {
		assertEquals(terrain[0].length, w.getNbCubesY());
	}

	@Test
	public void getNbCubesZ() throws Exception {
		assertEquals(terrain[0][0].length, w.getNbCubesZ());
	}

	@Test
	public void getMinPosition() throws Exception {
		assertEquals(new Vector(0,0,0), w.getMinPosition());
	}

	@Test
	public void getMaxPosition() throws Exception {
		assertEquals(
				new Vector(w.getNbCubesX(), w.getNbCubesY(), w.getNbCubesZ()).multiply(Cube.CUBE_SIDE_LENGTH),
				w.getMaxPosition()
		);
	}

	@Test
	public void hasAsFaction() throws Exception {
		assertTrue(w.hasAsFaction(u1.getFaction()));
		assertTrue(w.hasAsFaction(u2.getFaction()));
		assertFalse(w.hasAsFaction(new Faction()));

		Unit t = new Unit(w, "Test", new Vector(1,0,0));
		new Unit(w, "Test2", new Vector(0,0,0));
		t.terminate();

		for(Faction f : w.getFactions())
			assertTrue(w.hasAsFaction(f));
	}

	@Test
	public void canHaveAsFaction() throws Exception {
		for(Faction f : w.getFactions())
			assertTrue(w.canHaveAsFaction(f));

		assertFalse(w.canHaveAsFaction(null));
	}

	@Test
	public void hasProperFactions() throws Exception {
		assertTrue(w.hasProperFactions());
	}

	@Test
	public void getNbFactions() throws Exception {
		assertEquals(2, w.getNbFactions());

		Unit t = new Unit(w, "Test", new Vector(1,0,0));
		for(int i=0;i<5;i++)
			new Unit(w, "Test2", new Vector(0,0,0));
		t.terminate();

		assertEquals(5, w.getNbFactions());
	}

	@Test
	public void getFactions() throws Exception {
		assertTrue(w.getFactions().containsAll(Arrays.asList(u1.getFaction(), u2.getFaction())));
		assertEquals(2, w.getFactions().size());

		for(int i=0;i<5;i++)
			new Unit(w, "Test", new Vector(0,0,0));

		for(Faction f : w.getFactions())
			assertTrue(w.hasAsFaction(f));
	}

	@Test
	public void spawnUnit() throws Exception {
		Unit t = w.spawnUnit(false);
		assertEquals(w, t.getWorld());
		assertTrue(w.hasAsUnit(t));
		assertFalse(t.isDefaultActive());

		t = w.spawnUnit(true);
		assertEquals(w, t.getWorld());
		assertTrue(w.hasAsUnit(t));
		assertTrue(t.isDefaultActive());
	}

	@Test(expected = IllegalArgumentException.class)
	public void spawnUnitIllegal() throws IllegalArgumentException{
		int[][][] terrain = new int[0][0][0];
		new World(terrain, null).spawnUnit(false);// World without cubes
	}

	@Test(expected = IllegalArgumentException.class)
	public void spawnUnitIllegal2() throws IllegalArgumentException{
		int[][][] terrain = new int[1][1][1];
		terrain[0][0][0] = Terrain.ROCK.getId();
		new World(terrain, null).spawnUnit(false);// World with only non-passable cubes
	}

	@Test
	public void addUnit() throws Exception {
		// addUnit can only be called from inside the Unit's constructor since the given unit should already
		// reference the world.
	}

	@Test
	public void hasAsUnit() throws Exception {

	}

	@Test
	public void canHaveAsUnit() throws Exception {

	}

	@Test
	public void hasProperUnits() throws Exception {

	}

	@Test
	public void getNbUnits() throws Exception {

	}

	@Test
	public void removeUnit() throws Exception {

	}

	@Test
	public void getUnits() throws Exception {

	}

	@Test
	public void getWorkshops() throws Exception {

	}

	@Test
	public void isCubePassable() throws Exception {

	}

	@Test
	public void getSpawnPosition() throws Exception {

	}

	@Test
	public void getCube() throws Exception {

	}

	@Test
	public void getDirectlyAdjacentCubes() throws Exception {

	}

	@Test
	public void getNeighbouringCubes() throws Exception {

	}

	@Test
	public void getDirectlyAdjacentCubesSatisfying() throws Exception {

	}

	@Test
	public void getNeighbouringCubesSatisfying() throws Exception {

	}

	@Test
	public void getDirectlyAdjacentCubesPositions() throws Exception {

	}

	@Test
	public void getNeighbouringCubesPositions() throws Exception {

	}

	@Test
	public void isAdjacentSolid() throws Exception {

	}

	@Test
	public void isLowerSolid() throws Exception {

	}

	@Test
	public void advanceTime() throws Exception {

	}

	@Test
	public void getUnitsInCube() throws Exception {

	}

	@Test
	public void hasAsMaterial() throws Exception {

	}

	@Test
	public void canHaveAsMaterial() throws Exception {

	}

	@Test
	public void hasProperMaterials() throws Exception {

	}

	@Test
	public void getNbMaterials() throws Exception {

	}

	@Test
	public void addMaterial() throws Exception {

	}

	@Test
	public void removeMaterial() throws Exception {

	}

	@Test
	public void getMaterials() throws Exception {

	}

	@Test
	public void getLogs() throws Exception {

	}

	@Test
	public void getBoulders() throws Exception {

	}

}
