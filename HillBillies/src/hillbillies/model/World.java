package hillbillies.model;


import static hillbillies.utils.Utils.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.activities.AdjacentMove;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import hillbillies.utils.Vector;

/**
 * Class representing a Hillbilly world
 * @author Kenneth & Bram
 * @version 1.0
 *
 * @invar Each world must have proper materials.
 * | hasProperMaterials()
 */
public class World implements IWorld {
/** TO BE ADDED TO CLASS HEADING
 * @invar  The Terrain Matrix of each World must be a valid Terrain Matrix for any
 *         World.
 *       | isValidTerrainMatrix(getTerrainMatrix())
 */
	/**
	 * Constant reflecting the maximum units in a world.    
	 */
	private static final int MAX_UNITS = 100;
	/**
	 * Constant reflecting the maximum factions in a world.    
	 */
	private static final int MAX_FACTIONS = 5;
	
	/**
	 * A list of vectors reflecting the directly adjacent directions of a cube.    
	 */
	private static final List<Vector> DIRECTLY_ADJACENT_DIRECTIONS;
	/**
	 * A list of vectors reflecting the neighboring adjacent directions of a cube.    
	 */
	private static final List<Vector> NEIGHBOURING_DIRECTIONS;

	/**
	 * Constant reflecting number of adjacent directions.    
	 */
	private static final int NB_DIRECTLY_ADJACENT_DIRECTIONS = 6;
	/**
	 * Constant reflecting number of neighboring directions.    
	 */
	private static final int NB_NEIGHBOURING_DIRECTIONS = 26;

	/**
	 * TODO
	 */
	private TerrainChangeListener terrainChangeListener;

	/**
	 * Static initializer to set-up DIRECTLY_ADJACANT_ and NEIGHBOURING_ DIRECTIONS
	 */
	static {
		List<Vector> adjacentDirections = new ArrayList<>(NB_DIRECTLY_ADJACENT_DIRECTIONS);
		for(int i=0;i<NB_DIRECTLY_ADJACENT_DIRECTIONS;i++) {
			double sign = ((i + 1) % 2) * 2 - 1;// i odd -> -1 ; i even -> 1
			int dx = ((i + 1) % 3) % 2;// 0 -> 1 ; 1 -> 0 ; 2 -> 0 ; 3 -> 1 ; 4 -> 0 ; 5 -> 0
			int dy = (i % 3) % 2;// 0 -> 0 ; 1 -> 1 ; 2 -> 0 ; 3 -> 0 ; 4 -> 1 ; 5 -> 0
			int dz = ((i + 2) % 3) % 2;// 0 -> 0 ; 1 -> 0 ; 2 -> 1 ; 3 -> 0 ; 4 -> 0 ; 5 -> 1
			adjacentDirections.add(new Vector(dx, dy, dz).multiply(sign));
		}
		List<Vector> neighbouringDirections = new ArrayList<>(NB_NEIGHBOURING_DIRECTIONS);
		for(int x=-1;x<=1;x++){
			for(int y=-1;y<=1;y++){
				for(int z=-1;z<=1;z++){
					if(x==0 && y==0 && z==0) continue;
					neighbouringDirections.add(new Vector(x,y,z));
				}
			}
		}
		DIRECTLY_ADJACENT_DIRECTIONS = Collections.unmodifiableList(adjacentDirections);
		NEIGHBOURING_DIRECTIONS = Collections.unmodifiableList(neighbouringDirections);
	}

	/**
	 * Initialize this new World with given Terrain Matrix.
	 *
	 * @param  terrainTypes
	 *         The Terrain Matrix for this new World.
	 * @effect The Terrain Matrix of this new World is set to
	 *         the given Terrain Matrix.
	 *       | this.setTerrainMatrix(terrainTypes)
	 * @post This new world has no materials yet.
	 * | new.getNbMaterials() == 0
	 */
	public World(int[][][] terrainTypes, TerrainChangeListener terrainChangeListener)
			throws IllegalArgumentException {
		this.terrainChangeListener = terrainChangeListener;
		setNbCubesX(terrainTypes.length);
		setNbCubesY(terrainTypes[0].length);
		setNbCubesZ(terrainTypes[0][0].length);
		connectedToBorder = new ConnectedToBorder(this.getNbCubesX(),this.getNbCubesY(),this.getNbCubesZ());
		this.setTerrainMatrix(terrainTypes);
	}

	/**
	 * Check whether the given position is a valid position for
	 * any WorldObject.
	 *
	 * @param position The position to check.
	 * @return True when each coordinate of position is within the predefined bounds of MIN_POSITION and getMaxPosition()
	 * | result == position.isInBetween(MIN_POSITION, getMaxPosition())
	 */
	@Override
	public boolean isValidPosition(Vector position){
		return position.isInBetweenStrict(this.getMinPosition(), this.getMaxPosition());
	}
	
	/**
	 * Set the Terrain Matrix of this World to the given Terrain Matrix.
	 * 
	 * @param  terrainMatrix
	 *         The new Terrain Matrix for this World.
	 * @post   The Terrain Matrix of this new World is equal to
	 *         the given Terrain Matrix.
	 *       | new.getTerrainMatrix() == terrainTypes
	 * @throws IllegalArgumentException
	 *         The given Terrain Matrix is not a valid Terrain Matrix for any
	 *         World.
	 *       | ! isValidTerrainMatrix(getTerrainMatrix())
	 */
	@Raw
	public void setTerrainMatrix(int[][][] terrainMatrix)
			throws IllegalArgumentException {
		//Map<Vector, Cube> CubeMap = new HashMap<Vector , Cube>();
		for(int x = 0; x< getNbCubesX(); x++){
			for(int y = 0; y< getNbCubesY(); y++){
				if (terrainMatrix[x].length != getNbCubesY()){
					throw new IllegalArgumentException();
				}
				for(int z = 0; z< getNbCubesZ(); z++){
					if (terrainMatrix[x][y].length != getNbCubesZ()){
						throw new IllegalArgumentException();
					}
					Vector position = new Vector(x,y,z);
					CubeMap.put(position, new Cube(this, position, Terrain.fromId(terrainMatrix[x][y][z]), this::onTerrainChange));
				}
			}
		}
		
		
	}
	
	/**
	 * Constant reflecting number of cubes in the x-direction.    
	 */
	private int NbCubesX;
	/**
	 * Constant reflecting number of cubes in the x-direction.    
	 */
	private int NbCubesY;
	/**
	 * Constant reflecting number of cubes in the x-direction.    
	 */
	private int NbCubesZ;
	
	/**
	 * Set NbCubesX of this world to the given number of cubes.
	 *
	 * @param nbCubesX
	 * The new numbers of cubes in x-direction of this world.
	 * @post NbCubesX of this world is equal to
	 * the given number.
	 * | new.getNbCubesX() == nbCubesX
	 */
	private void setNbCubesX(int nbCubesX){
		this.NbCubesX = nbCubesX;
	}
	/**
	 * Return the number of x-cubes of this world.
	 */
	public int getNbCubesX(){
		return this.NbCubesX;
	}
	
	/**
	 * Set NbCubesY of this world to the given number of cubes.
	 *
	 * @param nbCubesY
	 * The new numbers of cubes in y-direction of this world.
	 * @post NbCubesY of this world is equal to
	 * the given number.
	 * | new.getNbCubesY() == nbCubesY
	 */
	private void setNbCubesY(int nbCubesY){
		this.NbCubesY = nbCubesY;
	}
	/**
	 * Return the number of y-cubes of this world.
	 */
	public int getNbCubesY(){
		return this.NbCubesY;
	}
	
	/**
	 * Set NbCubesZ of this world to the given number of cubes.
	 *
	 * @param nbCubesZ
	 * The new numbers of cubes in z-direction of this world.
	 * @post NbCubesZ of this world is equal to
	 * the given number.
	 * | new.getNbCubesZ() == nbCubesZ
	 */
	private void setNbCubesZ(int nbCubesZ){
		this.NbCubesZ = nbCubesZ;
	}
	/**
	 * Return the number of z-cubes of this world.
	 */
	public int getNbCubesZ(){
		return this.NbCubesZ;
	}

	/**
	 * Get the minimum position in this world.
	 */
	@Override
	public Vector getMinPosition(){
		return new Vector(Cube.CUBE_SIDE_LENGTH * 0, Cube.CUBE_SIDE_LENGTH * 0, Cube.CUBE_SIDE_LENGTH * 0);
	}
	/**
	 * Get the maximum position in this world.
     */
	@Override
	public Vector getMaxPosition(){
		return new Vector(Cube.CUBE_SIDE_LENGTH * getNbCubesX(), Cube.CUBE_SIDE_LENGTH * getNbCubesY(), Cube.CUBE_SIDE_LENGTH * getNbCubesZ());
	}
	
	public Unit spawnUnit(boolean enableDefaultBehavior){
		// addUnit is called inside Unit's constructor
		Unit unit = new Unit(this);// TODO: make constructor which chooses random initial properties
		if(enableDefaultBehavior)
			unit.startDefaultBehaviour();
		return unit;
	}

	/**
	 * Add the given unit to the set of units of this world.
	 *
	 * @param unit
	 * The unit to be added.
	 * @pre The given unit is effective and already references
	 * this world.
	 * | (unit != null) && (unit.getWorld() == this)
	 * @post This world has the given unit as one of its units.
	 * | new.hasAsUnit(unit)
	 */
	@Override
	public void addUnit(Unit unit){
		assert canHaveAsUnit(unit);
		// Bind unit to this world
		unit.setWorld(this);
		units.add(unit);

		Faction f;
		if(this.factions.size()<MAX_FACTIONS) {
			f = new Faction();
			this.factions.add(f);
		}else {
			f = getFactionWithLeastUnits();
		}
		// Bind unit to its faction
		f.addUnit(unit);
		unit.setFaction(f);
	}

	/** TO BE ADDED TO THE CLASS INVARIANTS
	 * @invar Each world must have proper factions.
	 * | hasProperFactions()
	 */

	/**
	 * Check whether this world has the given faction as one of its
	 * factions.
	 *
	 * @param faction
	 * The faction to check.
	 */
	@Basic
	@Raw
	public boolean hasAsFaction(@Raw Faction faction) {
		return factions.contains(faction);
	}
	/**
	 * Check whether this world can have the given faction
	 * as one of its factions.
	 *
	 * @param faction
	 * The faction to check.
	 * @return True if and only if the given faction is effective
	 * and that faction is a valid faction for a world.
	 * | result == (faction != null)
	 */
	@Raw
	public boolean canHaveAsFaction(Faction faction) {
		return (faction != null);
	}
	/**
	 * Check whether this world has proper factions attached to it.
	 *
	 * @return True if and only if this world can have each of the
	 * factions attached to it as one of its factions,
	 * and if each of these factions references this world as
	 * the world to which they are attached.
	 * | for each faction in Faction:
	 * | if (hasAsFaction(faction))
	 * | then canHaveAsFaction(faction)
	 */
	public boolean hasProperFactions() {
		for (Faction faction: factions) {
			if (!canHaveAsFaction(faction))
			    return false;
		}
		return true;
	}
	/**
	 * Return the number of factions associated with this world.
	 *
	 * @return The total number of factions collected in this world.
	 * | result ==
	 * | card({faction:Faction | hasAsFaction({faction)})
	 */
	public int getNbFactions() {
		return factions.size();
	}
	/**
	 * Add the given faction to the set of factions of this world.
	 *
	 * @param faction
	 * The faction to be added.
	 * @pre The given faction is effective and already references
	 * this world.
	 * | (faction != null) && (faction.getWorld() == this)
	 * @post This world has the given faction as one of its factions.
	 * | new.hasAsFaction(faction)
	 */
	public void addFaction(Faction faction) {
		assert canHaveAsFaction(faction);
		factions.add(faction);
	}
	/**
	 * Remove the given faction from the set of factions of this world.
	 *
	 * @param faction
	 * The faction to be removed.
	 * @pre This world has the given faction as one of
	 * its factions.
	 * | this.hasAsFaction(faction)
	 * @post This world no longer has the given faction as
	 * one of its factions.
	 * | ! new.hasAsFaction(faction)
	 */
	@Raw
	public void removeFaction(Faction faction) {
		assert this.hasAsFaction(faction);
		factions.remove(faction);
	}
	/**
	 * Variable referencing a set collecting all the factions
	 * of this world.
	 *
	 * @invar The referenced set is effective.
	 * | factions != null
	 * @invar Each faction registered in the referenced list is
	 * effective.
	 * | for each faction in factions:
	 * | ( (faction != null) )
	 */
	private final Set <Faction> factions = new HashSet<>(MAX_FACTIONS);

	private Faction getFactionWithLeastUnits(){
		Faction result = null;
		for(Faction f : factions){
			if(result==null || result.getNbUnits()>f.getNbUnits())
				result = f;
		}
		return result;
	}

	/** TO BE ADDED TO THE CLASS INVARIANTS
	 * @invar Each world must have proper units.
	 * | hasProperUnits()
	 */

	/**
	 * Check whether this world has the given unit as one of its
	 * units.
	 *
	 * @param unit
	 * The unit to check.
	 */
	@Basic
	@Raw
	public boolean hasAsUnit(@Raw Unit unit) {
		return units.contains(unit);
	}
	/**
	 * Check whether this world can have the given unit
	 * as one of its units.
	 *
	 * @param unit
	 * The unit to check.
	 * @return True if and only if the given unit is effective
	 * and that unit is a valid unit for a world.
	 * | result ==
	 * | (unit != null) &&
	 * | Unit.isValidWorld(this)
	 */
	@Raw
	public boolean canHaveAsUnit(Unit unit) {
		return (unit != null) && (Unit.isValidWorld(this)) && (this.getNbUnits() < MAX_UNITS);
	}
	/**
	 * Check whether this world has proper units attached to it.
	 *
	 * @return True if and only if this world can have each of the
	 * units attached to it as one of its units,
	 * and if each of these units references this world as
	 * the world to which they are attached.
	 * | for each unit in Unit:
	 * | if (hasAsUnit(unit))
	 * | then canHaveAsUnit(unit) &&
	 * | (unit.getWorld() == this)
	 */
	public boolean hasProperUnits() {
		for (Unit unit: units) {
			if (!canHaveAsUnit(unit))
			    return false;
			if (unit.getWorld() != this)
			    return false;
		}
		return true;
	}
	/**
	 * Return the number of units associated with this world.
	 *
	 * @return The total number of units collected in this world.
	 * | result ==
	 * | card({unit:Unit | hasAsUnit({unit)})
	 */
	public int getNbUnits() {
		return units.size();
	}

	/**
	 * Remove the given unit from the set of units of this world.
	 *
	 * @param unit
	 * The unit to be removed.
	 * @pre This world has the given unit as one of
	 * its units. And the given unit is terminated.
	 * | this.hasAsUnit(unit) &&
	 * | unit.isTerminated()
	 * @post This world no longer has the given unit as
	 * one of its units.
	 * | ! new.hasAsUnit(unit)
	 */
	@Raw
	public void removeUnit(Unit unit) {
		assert this.hasAsUnit(unit) && unit.isTerminated();
		units.remove(unit);
	}
	/**
	 * Variable referencing a set collecting all the units
	 * of this world.
	 *
	 * @invar The referenced set is effective.
	 * | units != null
	 * @invar Each unit registered in the referenced list is
	 * effective and not yet terminated.
	 * | for each unit in units:
	 * | ( (unit != null) &&
	 * | (! unit.isTerminated()) )
	 */
	private final Set<Unit> units = new HashSet <>(MAX_UNITS);

	public Set<Unit> getUnits(){
		return new HashSet<>(units);
	}

	public Set<Faction> getFactions(){
		return new HashSet<>(factions);
	}
	
	private Map<Vector, Cube> CubeMap = new HashMap<Vector , Cube>();
	
	/*
	 * (non-Javadoc)
	 * @see hillbillies.model.IWorld#isCubePassable(hillbillies.utils.Vector)
	 */
	@Override
	public boolean isCubePassable(Vector cubeCoordinates){
		return getCube(cubeCoordinates).isPassable();
	}
	
	@Override
	public Vector getSpawnPosition(){
		boolean isPassable = false;
		int x = 0;
		int y = 0;
		int z = 0;
		Vector position = new Vector(x,y,z);
		while (!isPassable){
			x = (int)(randDouble(this.getMinPosition().X(), this.getMaxPosition().X()));
			y = (int)(randDouble(this.getMinPosition().Y(), this.getMaxPosition().Y()));
			z = (int)(randDouble(this.getMinPosition().Z(), this.getMaxPosition().Z()));
			position = (new Vector(x,y,z)).getCubeCoordinates();
			isPassable = isCubePassable(position);
		}
		while(!CorrectSpawnPosition(position)){
			z -=1;
			position = new Vector(x,y,z);
		}
		return position;	
	}
	
	
	protected boolean CorrectSpawnPosition(Vector position) {// TODO: waarom dit niet vervangen door unit.isValidPosition?
		if(this.isValidPosition(position) && this.isCubePassable(position) && (position.cubeZ() ==0 || !this.isCubePassable(new Vector(position.X(),position.Y(),position.Z()-1))))
			return true;
		return false;
	}

	/**
	 * Get the Cube at the corresponding position.
	 * @param cubeCoordinates The position of the cube
	 * @return The Cube associated with this position
	 * @throws IllegalArgumentException
	 * 			When the given position is not a valid position in this World.
	 * 			| !isValidPosition(cubeCoordinates)
     */
	public Cube getCube(Vector cubeCoordinates) throws IllegalArgumentException{
		if(!isValidPosition(cubeCoordinates))
			throw new IllegalArgumentException("The given coordinates do not reference a valid position.");
		return this.CubeMap.get(cubeCoordinates);
	}

	@Override
	public Set<Cube> getDirectlyAdjacentCubes(Vector cubeCoordinates){
		Set<Cube> result = new HashSet<>(NB_DIRECTLY_ADJACENT_DIRECTIONS);
		getDirectlyAdjacentCubesSatisfying(result, cubeCoordinates, cube -> true, cube -> cube);
		return result;
	}

	public Set<Cube> getNeighbouringCubes(Vector cubeCoordinates){
		return getNeighbouringCubesSatisfying(cubeCoordinates, cube -> true, cube -> cube);
	}

	public Set<Cube> getDirectlyAdjacentCubes(Cube cube){
		return getDirectlyAdjacentCubes(cube.getPosition());
	}

	public Set<Cube> getNeighbouringCubes(Cube cube){
		return getNeighbouringCubes(cube.getPosition());
	}

	/**
	 * Fill the given collection with directly adjacent cubes, of the Cube with position cubeCoordinates,
	 * which satisfy the given condition. The resulting cubes are mapped to a custom type using the given
	 * mapper. These mapped cubes are then added to the given collection.
	 * @param cubeCoordinates The CUBE-coordinates of the Cube. The method will only return the directly
	 *                        adjacent cubes relative to this Cube.
	 * @param condition The condition imposed on the directly adjacent cubes. Only directly adjacent
	 *                  cubes satisfying this condition will be added to the resulting collection.
	 * @param mapper The mapper used to map the resulting adjacent cubes to the custom Type of the given collection
	 * @param <T> The type of the resulting collection after mapping it.
     * @post The given collection contains valid directly adjacent cubes satisfying condition.
	 * 			| foreach(new T element in collection)
	 * 			|	exists(Cube c | isValidPosition(c.getPosition()) && condition.test(c) &&
	 * 			|		exists(Vector adjDirection | DIRECTLY_ADJACENT_DIRECTIONS.contains(adjDirection) &&
	 * 			|			cubeCoordinates.add(adjDirection).equals(c.getPosition())
	 * 			|		)
	 * 			|	)
     */
	@Override
	public <T> void getDirectlyAdjacentCubesSatisfying(Collection<T> collection, Vector cubeCoordinates, Predicate<Cube> condition, Function<Cube, T> mapper){
		for(Vector adjacentDirection : DIRECTLY_ADJACENT_DIRECTIONS) {
			Vector adjacentPos = cubeCoordinates.add(adjacentDirection);
			if (isValidPosition(adjacentPos) && condition.test(this.getCube(adjacentPos)))
				collection.add(mapper.apply(this.getCube(adjacentPos)));
		}
	}

	/**
	 * Return the set of neighbouring cubes, of the Cube with position cubeCoordinates, which
	 * satisfy the given condition. The resulting set is mapped to a custom type using the given
	 * mapper.
	 * @param cubeCoordinates The CUBE-coordinates of the Cube. The method will return the neighbouring
	 *                        cubes relative to this Cube.
	 * @param condition The condition imposed on the neighbouring cubes. Only neighbouring cubes
	 *                  satisfying this condition will be added to the resulting Set.
	 * @param mapper The mapper used to map the resulting neighbouring cubes set to a set of custom Type
	 * @param <T> The type of the resulting Set after mapping it.
	 * @return The mapped set of valid neighbouring cubes satisfying condition.
	 * 			| foreach(T element in result)
	 * 			|	exists(Cube c | isValidPosition(c.getPosition()) && condition.test(c) &&
	 * 			|		exists(Vector neighbouringDirection | NEIGHBOURING_DIRECTIONS.contains(neighbouringDirection) &&
	 * 			|			cubeCoordinates.add(neighbouringDirection).equals(c.getPosition())
	 * 			|		)
	 * 			|	)
	 */
	public <T> Set<T> getNeighbouringCubesSatisfying(Vector cubeCoordinates, Predicate<Cube> condition, Function<Cube, T> mapper){
		Set<T> neighbouringCubes = new HashSet<>(NB_NEIGHBOURING_DIRECTIONS);
		for(Vector neighbouringDirection : NEIGHBOURING_DIRECTIONS) {
			Vector neighbouringPos = cubeCoordinates.add(neighbouringDirection);
			if (isValidPosition(neighbouringPos) && condition.test(this.getCube(neighbouringPos)))
				neighbouringCubes.add(mapper.apply(this.getCube(neighbouringPos)));
		}
		return neighbouringCubes;
	}

	@Override
	public List<Vector> getDirectlyAdjacentCubesPositions(Vector cubeCoordinates){// TODO: update this so it supports more general method
		List<Vector> adjacentCubes = new ArrayList<>(NB_DIRECTLY_ADJACENT_DIRECTIONS);
		for(Vector adjacentDirection : DIRECTLY_ADJACENT_DIRECTIONS) {
			Vector adjacentPos = cubeCoordinates.add(adjacentDirection);
			if (isValidPosition(adjacentPos))
				adjacentCubes.add(adjacentPos);
		}
		return adjacentCubes;
	}

	public List<Vector> getNeighbouringCubesPositions(Vector cubeCoordinates){
		List<Vector> neighbouringCubes = new ArrayList<>(NB_NEIGHBOURING_DIRECTIONS);
		for(Vector neighbouringDirection : NEIGHBOURING_DIRECTIONS) {
			Vector neighbouringPos = cubeCoordinates.add(neighbouringDirection);
			if (isValidPosition(neighbouringPos))
				neighbouringCubes.add(neighbouringPos);
		}
		return neighbouringCubes;
	}

	public List<Vector> getDirectlyAdjacentCubesPositions(Cube cube){
		return getDirectlyAdjacentCubesPositions(cube.getPosition());
	}

	public void advanceTime(double dt){
		unitsByCubePosition.clear();
		for(Unit unit : units){
			unit.advanceTime(dt);
			if(!unitsByCubePosition.containsKey(unit.getPosition().getCubeCoordinates()))
				unitsByCubePosition.put(unit.getPosition().getCubeCoordinates(), new HashSet<>());
			unitsByCubePosition.get(unit.getPosition().getCubeCoordinates()).add(unit);
		}
		for(Material m : materials){
			m.advanceTime(dt);
		}
		//COLLAPSING CUBES
		Iterator<Vector> cubeIterator = CollapsingCubes.keySet().iterator();
		while(cubeIterator.hasNext()){
			Vector cube = cubeIterator.next();
			double time = CollapsingCubes.get(cube);
			if (time >= 4d){
				collapse(cube);
				cubeIterator.remove();
				CollapsingCubes.remove(cube);
			}
			else
				CollapsingCubes.replace(cube, time+dt);

		}
	}

	public void collapse(Vector coordinate) {
		Vector CubeCoor = coordinate.getCubeCoordinates();
		Cube cube  = getCube(CubeCoor);
		Terrain cubeTerrain = cube.getTerrain();
		if (cubeTerrain == Terrain.ROCK){
			if (randInt(0, 99) < 25)
				//cubeTerrain = Terrain.AIR;
				new Boulder(this,cube);
		}
		else if (cubeTerrain == Terrain.WOOD){
			if (randInt(0, 99) < 25)
				//cubeTerrain = Terrain.AIR;
				new Log(this,cube);
		}
		cube.setTerrain(Terrain.AIR);


	}


	private final Map<Vector, Set<Unit>> unitsByCubePosition = new HashMap<>();
	
	@Override
	public Set<Unit> getUnitsInCube(Cube cube){
		return unitsByCubePosition.getOrDefault(cube.getPosition(), new HashSet<>());
	}

	public final ConnectedToBorder connectedToBorder;

	public void onTerrainChange(Terrain oldTerrain, Cube cube){
		int x = (int)cube.getPosition().X();
		int y = (int)cube.getPosition().Y();
		int z = (int)cube.getPosition().Z();
		if(oldTerrain!=null) {
			terrainChangeListener.notifyTerrainChanged(x, y, z);
			if (cube.isPassable() && !oldTerrain.isPassable()){
				List<int[]> changingCubes = connectedToBorder.changeSolidToPassable(x, y, z);
				for (int[] coord : changingCubes){
					Vector coordi = new Vector(coord[0], coord[1], coord[2]);
					if(!CollapsingCubes.containsKey(coordi))
							CollapsingCubes.put(coordi, 0d);
				}
			}
			else if (!cube.isPassable() && oldTerrain.isPassable())
				connectedToBorder.changePassableToSolid(x, y, z);
			for(Unit unit : units){
				unit.notifyTerrainChange(oldTerrain, cube);
			}
		}else if(cube.isPassable()){
			connectedToBorder.changeSolidToPassable(x, y, z);
		}
	}
	
	/**
	 * Check whether this world has the given material as one of its
	 * materials.
	 *
	 * @param material
	 * The material to check.
	 */
	@Basic
	@Raw
	public boolean hasAsMaterial(@Raw Material material) {
		return materials.contains(material);
	}
	/**
	 * Check whether this world can have the given material
	 * as one of its materials.
	 *
	 * @param material
	 * The material to check.
	 * @return True if and only if the given material is effective.
	 * | result ==
	 * | (material != null)
	 */
	@Raw
	public boolean canHaveAsMaterial(Material material) {
		return (material != null);
	}
	/**
	 * Check whether this world has proper materials attached to it.
	 *
	 * @return True if and only if this world can have each of the
	 * materials attached to it as one of its materials,
	 * and if each of these materials references this world as
	 * the world to which they are attached.
	 * | for each material in Material:
	 * | if (hasAsMaterial(material))
	 * | then canHaveAsMaterial(material) &&
	 * | (material.getWorld() == this)
	 */
	public boolean hasProperMaterials() {
		for (Material material: materials) {
			if (!canHaveAsMaterial(material))
			    return false;
			if (material.getWorld() != this)
			    return false;
		}
		return true;
	}
	/**
	 * Return the number of materials associated with this world.
	 *
	 * @return The total number of materials collected in this world.
	 * | result ==
	 * | card({material:Material | hasAsMaterial({material)})
	 */
	public int getNbMaterials() {
		return materials.size();
	}
	/**
	 * Add the given material to the set of materials of this world.
	 *
	 * @param material
	 * The material to be added.
	 * @pre The given material is effective and already references
	 * this world.
	 * | (material != null) && (material.getWorld() == this)
	 * @post This world has the given material as one of its materials.
	 * | new.hasAsMaterial(material)
	 */
	public void addMaterial(@Raw Material material) {
		assert(material != null) && (material.getWorld() == this);
		materials.add(material);
	}
	/**
	 * Remove the given material from the set of materials of this world.
	 *
	 * @param material
	 * The material to be removed.
	 * @pre This world has the given material as one of
	 * its materials, and the given material is terminated.
	 * | this.hasAsMaterial(material) &&
	 * | (material.isTerminated())
	 * @post This world no longer has the given material as
	 * one of its materials.
	 * | ! new.hasAsMaterial(material)
	 */
	@Raw
	public void removeMaterial(Material material) {
		assert this.hasAsMaterial(material) && (material.isTerminated());
		materials.remove(material);
	}
	/**
	 * Variable referencing a set collecting all the materials
	 * of this world.
	 *
	 * @invar The referenced set is effective.
	 * | materials != null
	 * @invar Each material registered in the referenced list is
	 * effective and not yet terminated.
	 * | for each material in materials:
	 * | ( (material != null) &&
	 * | (! material.isTerminated()) )
	 */
	private final Set<Material> materials = new HashSet<>();

	/**
	 * Get all materials of the given type in this world. If inCube
	 * is set to true, only materials with an owner of type Cube
	 * will be returned.
	 * @param type The type of Material to get. This type must extend
	 *             Material.
	 * @param inCube Boolean indicating whether only materials with
	 *               an owner of type Cube should be returned
	 * @param <T> The type of Material to get. This type must extend
	 *            Material.
     * @return A Set<T> containing all materials of given type in this
	 * 			world. If inCube is true, only materials with an owner
	 * 		 	of type Cube will be present in the Set.
	 * 		 | foreach(T material in result : if(inCube) T.getOwner instanceof Cube)
     */
	public <T extends Material> Set<T> getMaterials(Class<T> type, boolean inCube){
		Set<T> result = new HashSet<>();
		for(Material m : materials){
			if(type.isInstance(m) && (!inCube || m.getOwner() instanceof Cube || m.getOwner() == null))
				result.add((T)m);
		}
		return result;
	}

	/**
	 * Get all Logs in this world. If inCube is true, only Logs with
	 * an owner of type Cube will be returned.
	 * @param inCube Boolean indicating whether only Logs with an owner
	 *               of type Cube should be returned
	 * @return A Set<Log> containing all Logs in this world. If inCube
	 * 			is true, only Logs with an owner of type Cube will be
	 * 			present in the Set.
	 * @effect getMaterials(Log.class, inCube)
     */
	public Set<Log> getLogs(boolean inCube){
		return getMaterials(Log.class, inCube);
	}

	/**
	 * Get all Boulders in this world. If inCube is true, only Boulders
	 * with an owner of type Cube will be returned.
	 * @param inCube Boolean indicating whether only Boulders with
	 *               an owner of type Cube should be returned
	 * @return A Set<Boulder> containing all Boulders in this world.
	 * 			If inCube is true, only Logs with an owner of type
	 * 			Cube will be present in the Set.
	 * @effect getMaterials(Boulder.class, inCube)
	 */
	public Set<Boulder> getBoulders(boolean inCube){
		return getMaterials(Boulder.class, inCube);
	}

	public void checkWorld(){
		for(int x = 0; x < this.getNbCubesX(); x++){
			for(int y = 0; y < this.getNbCubesX(); y++){
				for(int z = 0; z < this.getNbCubesX(); z++){
					if( !connectedToBorder.isSolidConnectedToBorder(x, y, z))
						CollapsingCubes.put(new Vector(x,y,z), 0d);

				}
			}
		}
	}

	private Map<Vector, Double> CollapsingCubes = new HashMap<Vector , Double>();

}
