package hillbillies.tests.model;

import static org.junit.Assert.*;

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

	private static World airWorld;
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
    	for (int x = 0; x < types.length; x++) {
			for (int y = 0; y < types[x].length; y++) {
				for (int z = 0; z < types[x][y].length; z++) {
					types[x][y][z] = 0;// A test world that only have air as type
				}
			}
    	}
    	airWorld = new World(types, modelListener);
    }

	private static Unit testUnit;
	private static Material testMaterial, testLog, testBoulder;
	private static Vector nullPosition = new Vector(0,0,0);
	private static double dt = 0.2;
    @Before
	public void setUp() throws Exception {
    	testUnit = new Unit(airWorld,"TestUnit", nullPosition);
    	testMaterial = new Material(airWorld, testUnit);
    	testLog = new Log(airWorld, airWorld.getCube(nullPosition));
    	testBoulder = new Boulder(airWorld, airWorld.getCube(nullPosition));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMaterialConstructor() {
		
	}

	@Test
	public void testAdvanceTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetOwner() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPosition() {
		fail("Not yet implemented");
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
