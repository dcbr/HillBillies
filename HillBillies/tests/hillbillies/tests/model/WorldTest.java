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
import hillbillies.part2.listener.DefaultTerrainChangeListener;

import java.util.*;

public class WorldTest {

	private World w;
	private Unit u1, u2;
	private Log l;
	private Boulder b;
	private static ArrayDeque<Vector> lastTerrainChanges;
	private static int[][][] terrain;
	private static TerrainChangeListener listener;
	private static Set<Vector> adjacentDirections, neighbouringDirections;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		terrain = new int[5][5][5];
		for(int i=1;i<3;i++)
			for(int j=1;j<3;j++)
				terrain[i][j][1] = 1 + ((i+j)%2);
		terrain[2][2][0] = Terrain.WORKSHOP.getId();
		terrain[3][1][1] = Terrain.ROCK.getId();
		terrain[1][3][1] = Terrain.WOOD.getId();

		lastTerrainChanges = new ArrayDeque<>();
		listener = (x, y, z) -> lastTerrainChanges.addFirst(new Vector(x, y, z));

		adjacentDirections = new HashSet<>(Arrays.asList(
				new Vector(1,0,0), new Vector(0,1,0), new Vector(0,0,1),
				new Vector(-1,0,0), new Vector(0,-1,0), new Vector(0,0,-1)
		));
		neighbouringDirections = new HashSet<>(Arrays.asList(
				new Vector(1,0,0), new Vector(1,0,1), new Vector(1,1,0), new Vector(1,1,1), new Vector(1,0,-1), new Vector(1,-1,0), new Vector(1,-1,-1), new Vector(1,-1,1), new Vector(1,1,-1),
								   new Vector(0,0,1), new Vector(0,1,0), new Vector(0,1,1), new Vector(0,0,-1), new Vector(0,-1,0), new Vector(0,-1,-1), new Vector(0,-1,1), new Vector(0,1,-1),
				new Vector(-1,0,0), new Vector(-1,0,1), new Vector(-1,1,0), new Vector(-1,1,1), new Vector(-1,0,-1), new Vector(-1,-1,0), new Vector(-1,-1,-1), new Vector(-1,-1,1), new Vector(-1,1,-1)
		));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		w = new World(terrain, listener);
		u1 = new Unit(w, "UnitA", new Vector(1,1,0));
		u2 = new Unit(w, "UnitB", new Vector(0,0,0));
		lastTerrainChanges.clear();
		b = new Boulder(w, w.getCube(new Vector(1,0,0)));
		l = new Log(w, w.getCube(new Vector(0,1,0)));
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

		assertEquals(2, w.getNbMaterials());
		assertEquals(1 + ((u1.getFaction()==u2.getFaction())?0:1), w.getNbFactions());
		assertEquals(2, w.getNbUnits());//u1 en u2 zitten al in deze wereld
		
		advanceTimeFor(w, 8d);// Check if collapsing cubes are notified to listener
		assertEquals(6, lastTerrainChanges.size());
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
		int[][][] wrongTerrain = new int[6][6][6];
		wrongTerrain[5] = new int [6][7];
		new World(wrongTerrain, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorIllegal2() throws IllegalArgumentException{
		int[][][] wrongTerrain = new int[6][6][6];
		wrongTerrain[0] = new int[7][6];
		new World(wrongTerrain, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorIllegal3() throws IllegalArgumentException{
		int[][][] wrongTerrain = new int[0][0][0];
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
		assertFalse(w.isValidPosition(null));
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
		new Unit(w, "TestII", new Vector(0,0,0));
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
			new Unit(w, "TestII", new Vector(0,0,0));
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

	@Test(expected = IllegalStateException.class)
	public void spawnUnitIllegal() throws IllegalStateException{
		int[][][] terrain = new int[1][1][1];
		terrain[0][0][0] = Terrain.ROCK.getId();
		new World(terrain, null).spawnUnit(false);// World with only non-passable cubes
	}

	@Test
	public void addUnit() throws Exception {
		Unit t = new Unit(LobbyWorld.lobby, "Test", new Vector(0,0,0));
		w.addUnit(t);
		assertTrue(w.hasAsUnit(t));
		Faction f = t.getFaction();
		assertEquals(1, f.getNbUnits());
		assertTrue(f.hasAsUnit(t));
		for(int i=0;i<2;i++) {
			Unit blub = new Unit(LobbyWorld.lobby, "Blub", new Vector(0, 0, 0));
			w.addUnit(blub);
		}
		t.terminate();
		assertTrue(f.hasAsUnit(new Unit(w, "TestII", new Vector(0,0,0))));
		assertEquals(1, f.getNbUnits());
	}

	@Test
	public void hasAsUnit() throws Exception {
		assertTrue(w.hasAsUnit(u1));
		assertTrue(w.hasAsUnit(u2));
		assertFalse(w.hasAsUnit(new Unit(LobbyWorld.lobby, "Blub", new Vector(0,0,0))));

		Unit t = new Unit(w, "Test", new Vector(1,0,0));
		assertTrue(w.hasAsUnit(new Unit(w, "TestB", new Vector(0,0,0))));
		t.terminate();
		assertTrue(w.hasAsUnit(t));// Units are removed in advancetime
		advanceTimeFor(w, 0.2);
		assertFalse(w.hasAsUnit(t));

		for(Unit u : w.getUnits())
			assertTrue(w.hasAsUnit(u));
	}

	@Test
	public void canHaveAsUnit() throws Exception {
		for(Unit u : w.getUnits())
			assertTrue(w.canHaveAsUnit(u));

		assertFalse(w.canHaveAsUnit(null));
		u1.terminate();
		assertFalse(w.canHaveAsUnit(u1));
		assertFalse(w.canHaveAsUnit(new Unit(LobbyWorld.lobby,"LobbyUnit", new Vector(0,0,0))));
	}

	@Test
	public void hasProperUnits() throws Exception {
		assertTrue(w.hasProperUnits());
		u1.terminate();
		assertFalse(w.hasProperUnits());
		advanceTimeFor(w, 0.2);
		assertTrue(w.hasProperUnits());
	}

	@Test
	public void getNbUnits() throws Exception {
		assertEquals(2, w.getNbUnits());

		Unit t = new Unit(w, "Test", new Vector(1,0,0));
		for(int i=0;i<5;i++)
			new Unit(w, "TestII", new Vector(0,0,0));
		t.terminate();

		assertEquals(8, w.getNbUnits());
		advanceTimeFor(w, 0.2);
		assertEquals(7, w.getNbUnits());
	}

	@Test
	public void getUnits() throws Exception {
		assertTrue(w.getUnits().containsAll(Arrays.asList(u1, u2)));
		assertEquals(2, w.getUnits().size());

		for(int i=0;i<5;i++)
			new Unit(w, "Test", new Vector(0,0,0));

		for(Unit u : w.getUnits())
			assertTrue(w.hasAsUnit(u));
	}

	@Test
	public void getWorkshops() throws Exception {
		assertEquals(1,w.getWorkshops().size());
		assertTrue(w.getWorkshops().contains(w.getCube(new Vector(2,2,0))));
	}

	@Test
	public void isCubePassable() throws Exception {
		for(int x=0;x<terrain.length;x++)
			for(int y=0;y<terrain[x].length;y++)
				for(int z=0;z<terrain[x][y].length;z++)
					assertEquals(Terrain.fromId(terrain[x][y][z]).isPassable(), w.isCubePassable(new Vector(x,y,z)));
	}

	@Test
	public void getSpawnPosition() throws Exception {
		Vector s = w.getSpawnPosition();
		for(Unit u : w.getUnits())
			assertTrue(u.isValidPosition(s));
	}

	@Test(expected = IllegalStateException.class)
	public void getSpawnPositionIllegal() throws IllegalStateException{
		int[][][] terrain = new int[1][1][1];
		terrain[0][0][0] = Terrain.ROCK.getId();
		new World(terrain, null).getSpawnPosition();// World with only non-passable cubes
	}

	@Test
	public void getCube() throws Exception {
		for (int x = 0; x < terrain.length; x++) {
			for (int y = 0; y < terrain[x].length; y++) {
				for (int z = 0; z < terrain[x][y].length; z++) {
					assertEquals(new Vector(x, y, z), w.getCube(new Vector(x, y, z)).getPosition());
					assertEquals(Terrain.fromId(terrain[x][y][z]), w.getCube(new Vector(x, y, z)).getTerrain());
				}
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCubeIllegal() throws IllegalArgumentException{
		w.getCube(w.getMaxPosition());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCubeIllegal2() throws IllegalArgumentException{
		w.getCube(null);
	}

	@Test
	public void getDirectlyAdjacentCubes() throws Exception {
		Vector pos = new Vector(1,1,1);
		Set<Cube> adjCubes = w.getDirectlyAdjacentCubes(pos);
		for(Vector adjDir : adjacentDirections)
			assertTrue(adjCubes.contains(w.getCube(pos.add(adjDir))));

		// Corner position
		pos = new Vector(0,0,0);
		adjCubes = w.getDirectlyAdjacentCubes(pos);
		int i = 0;
		for(Vector adjDir : adjacentDirections) {
			Vector adjPos = pos.add(adjDir);
			if (w.isValidPosition(adjPos)) {
				assertTrue(adjCubes.contains(w.getCube(adjPos)));
				i++;
			}
		}
		assertTrue(i==adjCubes.size());
	}

	@Test
	public void getNeighbouringCubes() throws Exception {
		Vector pos = new Vector(1,1,1);
		Set<Cube> neighbCubes = w.getNeighbouringCubes(pos);
		for(Vector neighbDir : neighbouringDirections)
			assertTrue(neighbCubes.contains(w.getCube(pos.add(neighbDir))));

		// Corner position
		pos = new Vector(0,0,0);
		neighbCubes = w.getNeighbouringCubes(pos);
		int i = 0;
		for(Vector neighbDir : neighbouringDirections) {
			Vector neighbPos = pos.add(neighbDir);
			if (w.isValidPosition(neighbPos)) {
				assertTrue(neighbCubes.contains(w.getCube(neighbPos)));
				i++;
			}
		}
		assertEquals(i, neighbCubes.size());
	}

	@Test(expected = NullPointerException.class)
	public void getDirectlyAdjacentCubesInvalid() throws NullPointerException{
		w.getDirectlyAdjacentCubes(null);
	}

	@Test(expected = NullPointerException.class)
	public void getNeighbouringCubesInvalid() throws NullPointerException{
		w.getNeighbouringCubes(null);
	}

	@Test
	public void getDirectlyAdjacentCubesSatisfying() throws Exception {
		Vector pos = new Vector(1,1,1);
		Set<Vector> adjCubes = new HashSet<>();
		w.getDirectlyAdjacentCubesSatisfying(adjCubes, pos, Cube::isPassable, WorldObject::getPosition);
		for(Vector adjDir : adjacentDirections) {
			Vector adjPos = pos.add(adjDir);
			if (w.isValidPosition(adjPos) && w.getCube(adjPos).isPassable())
				assertTrue(adjCubes.contains(adjPos));
			else
				assertFalse(adjCubes.contains(adjPos));
		}

		// Corner position
		pos = new Vector(0,0,0);
		adjCubes.clear();
		w.getDirectlyAdjacentCubesSatisfying(adjCubes, pos, Cube::containsMaterials, WorldObject::getPosition);
		for(Vector adjDir : adjacentDirections) {
			Vector adjPos = pos.add(adjDir);
			if (w.isValidPosition(adjPos) && w.getCube(adjPos).containsMaterials())
				assertTrue(adjCubes.contains(adjPos));
			else
				assertFalse(adjCubes.contains(adjPos));
		}
	}

	@Test
	public void getNeighbouringCubesSatisfying() throws Exception {
		Vector pos = new Vector(1,1,1);
		Set<Cube> neighbCubes = new HashSet<>();
		w.getNeighbouringCubesSatisfying(neighbCubes, pos, Cube::isPassable, cube -> cube);
		for(Vector neighbDir : neighbouringDirections) {
			Vector neighbPos = pos.add(neighbDir);
			if (w.isValidPosition(neighbPos) && w.getCube(neighbPos).isPassable())
				assertTrue(neighbCubes.contains(w.getCube(neighbPos)));
			else
				assertFalse(neighbCubes.contains(w.getCube(neighbPos)));
		}

		// Corner position
		pos = new Vector(0,0,0);
		neighbCubes.clear();
		w.getNeighbouringCubesSatisfying(neighbCubes, pos, Cube::containsMaterials, cube -> cube);
		int i = 0;
		for(Vector neighbDir : neighbouringDirections) {
			Vector neighbPos = pos.add(neighbDir);
			if (w.isValidPosition(neighbPos) && w.getCube(neighbPos).containsMaterials()) {
				assertTrue(neighbCubes.contains(w.getCube(neighbPos)));
				i++;
			}
		}
		assertTrue(neighbCubes.size()==i);
	}

	@Test(expected = NullPointerException.class)
	public void getDirectlyAdjacentCubesSatisfyingInvalid() throws NullPointerException{
		w.getDirectlyAdjacentCubesSatisfying(null, null, null, null);
	}

	@Test(expected = NullPointerException.class)
	public void getNeighbouringCubesSatisfyingInvalid() throws NullPointerException{
		w.getNeighbouringCubesSatisfying(null, null, null, null);
	}

	@Test
	public void getDirectlyAdjacentCubesPositions() throws Exception {
		Vector pos = new Vector(1,1,1);
		List<Vector> adjCubes = w.getDirectlyAdjacentCubesPositions(pos);
		for(Vector adjDir : adjacentDirections)
			assertTrue(adjCubes.contains(pos.add(adjDir)));

		// Corner position
		pos = new Vector(0,0,0);
		adjCubes = w.getDirectlyAdjacentCubesPositions(pos);
		for(Vector adjDir : adjacentDirections) {
			Vector adjPos = pos.add(adjDir);
			if (w.isValidPosition(adjPos))
				assertTrue(adjCubes.contains(adjPos));
			else
				assertFalse(adjCubes.contains(adjPos));
		}
	}

	@Test
	public void getNeighbouringCubesPositions() throws Exception {
		Vector pos = new Vector(1,1,1);
		List<Vector> neighbCubes = w.getNeighbouringCubesPositions(pos);
		for(Vector neighbDir : neighbouringDirections)
			assertTrue(neighbCubes.contains(pos.add(neighbDir)));

		// Corner position
		pos = new Vector(0,0,0);
		neighbCubes = w.getNeighbouringCubesPositions(pos);
		for(Vector neighbDir : neighbouringDirections) {
			Vector neighbPos = pos.add(neighbDir);
			if (w.isValidPosition(neighbPos))
				assertTrue(neighbCubes.contains(neighbPos));
			else
				assertFalse(neighbCubes.contains(neighbPos));
		}
	}

	@Test(expected = NullPointerException.class)
	public void getDirectlyAdjacentCubesPositionsInvalid() throws NullPointerException{
		w.getDirectlyAdjacentCubesPositions(null);
	}

	@Test(expected = NullPointerException.class)
	public void getNeighbouringCubesPositionsInvalid() throws NullPointerException{
		w.getNeighbouringCubesPositions(null);
	}

	@Test
	public void isAdjacentSolid() throws Exception {
		assertTrue(w.isAdjacentSolid(new Vector(0,0,0)));
		assertTrue(w.isAdjacentSolid(new Vector(2,0,1)));
		assertTrue(w.isAdjacentSolid(new Vector(0,2,1)));
		assertFalse(w.isAdjacentSolid(new Vector(2,3,2)));
		assertTrue(w.isLowerSolid(new Vector(1,3,2)));
		assertTrue(w.isLowerSolid(new Vector(3,1,2)));
	}

	@Test
	public void isLowerSolid() throws Exception {
		assertTrue(w.isLowerSolid(new Vector(0,0,0)));
		assertFalse(w.isLowerSolid(new Vector(2,0,1)));
		assertFalse(w.isLowerSolid(new Vector(0,2,1)));
		assertTrue(w.isLowerSolid(new Vector(2,2,2)));
		assertTrue(w.isLowerSolid(new Vector(1,3,2)));
		assertTrue(w.isLowerSolid(new Vector(3,1,2)));
	}

	@Test(expected = NullPointerException.class)
	public void isAdjacentSolidInvalid() throws NullPointerException{
		w.isAdjacentSolid(null);
	}

	@Test(expected = NullPointerException.class)
	public void isLowerSolidInvalid() throws NullPointerException{
		w.isLowerSolid(null);
	}

	@Test
	public void getUnitsInCube() throws Exception {
		assertTrue(w.getUnitsInCube(w.getCube(new Vector(2,2,2))).isEmpty());
		Unit t = new Unit(w, "Test", new Vector(0,0,0));
		assertTrue(w.getUnitsInCube(w.getCube(new Vector(0,0,0))).containsAll(Arrays.asList(u2, t)));
	}

	@Test(expected = NullPointerException.class)
	public void getUnitsInCubeInvalid() throws NullPointerException{
		w.getUnitsInCube(null);
	}

	@Test
	public void hasAsMaterial() throws Exception {
		assertTrue(w.hasAsMaterial(l));
		assertTrue(w.hasAsMaterial(b));
		World dummy = new World(new int[1][1][1], null);
		assertFalse(w.hasAsMaterial(new Log(dummy, dummy.getCube(new Vector(0,0,0)))));

		Log t = new Log(w, w.getCube(new Vector(2,2,2)));
		t.terminate();
		assertTrue(w.hasAsMaterial(t));// Materials are removed in advanceTime
		advanceTimeFor(w, 0.2);
		assertFalse(w.hasAsMaterial(t));

		for(Material m : w.getMaterials(Material.class, true))
			assertTrue(w.hasAsMaterial(m));
	}

	@Test
	public void canHaveAsMaterial() throws Exception {
		for(Material m : w.getMaterials(Material.class, true))
			assertTrue(w.canHaveAsMaterial(m));

		assertFalse(w.canHaveAsMaterial(null));
	}

	@Test
	public void hasProperMaterials() throws Exception {
		assertTrue(w.hasProperMaterials());
		l.terminate();
		assertFalse(w.hasProperMaterials());
		advanceTimeFor(w, 0.2);
		assertTrue(w.hasProperMaterials());
	}

	@Test
	public void getNbMaterials() throws Exception {
		assertTrue(w.getNbMaterials()>=2);

		int nbMaterials = w.getNbMaterials();
		new Boulder(w, w.getCube(new Vector(2,2,2)));
		assertEquals(nbMaterials+1, w.getNbMaterials());
	}

	@Test
	public void addMaterial() throws Exception {
		// addMaterial can only be called from inside the Material's constructor since the given material should already
		// reference the world.
		Boulder bt = new Boulder(w, w.getCube(new Vector(2,2,2)));
		assertTrue(w.hasAsMaterial(bt));
	}

	@Test
	public void getMaterials() throws Exception {
		assertTrue(w.getMaterials(Material.class, true).containsAll(Arrays.asList(b, l)));
		assertTrue(2 <= w.getMaterials(Material.class, true).size());

		for(Material m : w.getMaterials(Material.class, true))
			assertTrue(w.hasAsMaterial(m));
	}

	@Test
	public void getLogs() throws Exception {
		assertTrue(w.getLogs(true).contains(l));
		assertTrue(1 <= w.getLogs(true).size());

		u2.setCarriedMaterial(l);
		assertTrue(w.getLogs(false).contains(l));
		assertFalse(w.getLogs(true).contains(l));

		for(Log log : w.getLogs(true))
			assertTrue(w.hasAsMaterial(log));
	}

	@Test
	public void getBoulders() throws Exception {
		assertTrue(w.getBoulders(true).contains(b));
		assertTrue(1 <= w.getBoulders(true).size());

		u2.setCarriedMaterial(b);
		assertTrue(w.getBoulders(false).contains(b));
		assertFalse(w.getBoulders(true).contains(b));

		for(Boulder boulder : w.getBoulders(true))
			assertTrue(w.hasAsMaterial(boulder));
	}

}
