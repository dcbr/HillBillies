package hillbillies.model;


import java.util.*;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;

/**
 * Class representing a Hillbilly world
 * @author Kenneth & Bram
 * @version 1.0
 */
public class World implements IWorld {
/** TO BE ADDED TO CLASS HEADING
 * @invar  The Terrain Matrix of each World must be a valid Terrain Matrix for any
 *         World.
 *       | isValidTerrainMatrix(getTerrainMatrix())
 */
	
	private static final int MAX_UNITS = 100;
	private static final int MAX_FACTIONS = 5;




	/**
	 * Initialize this new World with given Terrain Matrix.
	 *
	 * @param  terrainTypes
	 *         The Terrain Matrix for this new World.
	 * @effect The Terrain Matrix of this new World is set to
	 *         the given Terrain Matrix.
	 *       | this.setTerrainMatrix(terrainTypes)
	 */
	public World(int[][][] terrainTypes)
			throws IllegalArgumentException {
		this.setTerrainMatrix(terrainTypes);
	}
	

	
	/**
	 * Check whether the given Terrain Matrix is a valid Terrain Matrix for
	 * any World.
	 *  
	 * @param  terrainTypes
	 *         The Terrain Matrix to check.
	 * @return 
	 *       | result == 
	*/
	public static boolean isValidTerrainMatrix(int[][][] terrainTypes) {
		//TODO
		return false;
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
		//TODO: probably move this to WorldObject?
		return position.isInBetween(this.getMinPosition(), this.getMaxPosition());
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
		Map<Vector, Cube> CubeMap = new HashMap<Vector , Cube>();
		setNbCubesX(terrainMatrix.length);
		setNbCubesY(terrainMatrix[0].length);
		setNbCubesZ(terrainMatrix[0][0].length);
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
					CubeMap.put(position, new Cube(this,position,Terrain.fromId(terrainMatrix[x][y][z])));
				}
			}
		}
		
		
	}
	

	private int NbCubesX;
	private int NbCubesY;
	private int NbCubesZ;
	
	private void setNbCubesX(int nbCubesX){
		this.NbCubesX = nbCubesX;
	}
	public int getNbCubesX(){
		return this.NbCubesX;
	}
	
	private void setNbCubesY(int nbCubesY){
		this.NbCubesY = nbCubesY;
	}
	public int getNbCubesY(){
		return this.NbCubesY;
	}
	
	private void setNbCubesZ(int nbCubesZ){
		this.NbCubesZ = nbCubesZ;
	}
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
			if(result==null || result.getNbUnits()<f.getNbUnits())
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
		return (unit != null) && (Unit.isValidWorld(this));
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
	 * its units, and the given unit does not
	 * reference any world.
	 * | this.hasAsUnit(unit) &&
	 * | (unit.getWorld() == null)
	 * @post This world no longer has the given unit as
	 * one of its units.
	 * | ! new.hasAsUnit(unit)
	 */
	@Raw
	public void removeUnit(Unit unit) {
		assert this.hasAsUnit(unit) && (unit.getWorld() == null);
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
}
