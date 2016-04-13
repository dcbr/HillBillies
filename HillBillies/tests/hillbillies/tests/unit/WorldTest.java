package hillbillies.tests.unit;

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

}
