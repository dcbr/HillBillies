package hillbillies.tests.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import hillbillies.*;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;

public class WorldTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
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

	}

	@Test
	public void getNbCubesX() throws Exception {

	}

	@Test
	public void getNbCubesY() throws Exception {

	}

	@Test
	public void getNbCubesZ() throws Exception {

	}

	@Test
	public void getMinPosition() throws Exception {

	}

	@Test
	public void getMaxPosition() throws Exception {

	}

	@Test
	public void hasAsFaction() throws Exception {

	}

	@Test
	public void canHaveAsFaction() throws Exception {

	}

	@Test
	public void hasProperFactions() throws Exception {

	}

	@Test
	public void getNbFactions() throws Exception {

	}

	@Test
	public void addFaction() throws Exception {

	}

	@Test
	public void getFactions() throws Exception {

	}

	@Test
	public void spawnUnit() throws Exception {

	}

	@Test
	public void addUnit() throws Exception {

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
