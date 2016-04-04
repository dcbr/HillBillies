package hillbillies.part2.facade;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.Faction;
import hillbillies.model.LobbyWorld;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.utils.Vector;
import ogp.framework.util.ModelException;

import java.util.Set;

/**
 * Implementation of the Facade class
 * @author Kenneth & Bram
 * @version 1.0
 *
 */
public class Facade implements IFacade {
    /**
     * Create a new world of the given size and with the given terrain. To keep
     * the GUI display up to date, the method in the given listener must be
     * called whenever the terrain type of a cube in the world changes.
     *
     * @param terrainTypes  A three-dimensional array (structured as [x][y][z]) with the
     *                      types of the terrain, encoded as integers. The terrain always
     *                      has the shape of a box (i.e., the array terrainTypes[0] has
     *                      the same length as terrainTypes[1] etc.). The integer types
     *                      are as follows:
     *                      <ul>
     *                      <li>0: air</li>
     *                      <li>1: rock</li>
     *                      <li>2: tree</li>
     *                      <li>3: workshop</li>
     *                      </ul>
     * @param modelListener An object with a single method,
     *                      {@link TerrainChangeListener#notifyTerrainChanged(int, int, int)}
     *                      . This method must be called by your implementation whenever
     *                      the terrain type of a cube changes (e.g., as a consequence of
     *                      cave-ins), so that the GUI will correctly update the display.
     *                      The coordinate of the changed cube must be given in the form
     *                      of the parameters x, y and z. You do not need to call this
     *                      method during the construction of your world.
     * @return
     * @throws ModelException
     */
    @Override
    public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
        return null;
    }

    /**
     * Return the number of cubes in the world in the x-direction.
     *
     * @param world The world for which to retrieve the number of cubes.
     * @return The number of cubes in the x-direction.
     * @throws ModelException
     */
    @Override
    public int getNbCubesX(World world) throws ModelException {
        return 0;
    }

    /**
     * Return the number of cubes in the world in the y-direction.
     *
     * @param world The world for which to retrieve the number of cubes.
     * @return The number of cubes in the y-direction.
     * @throws ModelException
     */
    @Override
    public int getNbCubesY(World world) throws ModelException {
        return 0;
    }

    /**
     * Return the number of cubes in the world in the z-direction.
     *
     * @param world The world for which to retrieve the number of cubes.
     * @return The number of cubes in the z-direction.
     * @throws ModelException
     */
    @Override
    public int getNbCubesZ(World world) throws ModelException {
        return 0;
    }

    /**
     * Advance the state of the given world by the given time period.
     *
     * @param world The world for which to advance the time
     * @param dt    The time period, in seconds, by which to advance the world's
     *              state.
     * @throws ModelException A precondition was violated or an exception was thrown.
     * @note This method replaces the {@link #advanceTime(Unit, double)} method
     * from part 1.
     */
    @Override
    public void advanceTime(World world, double dt) throws ModelException {

    }

    /**
     * Return the terrain type of the cube at the given coordinates.
     *
     * @param world The world from which to retrieve the type.
     * @param x     The x-coordinate of the cube
     * @param y     The y-coordinate of the cube
     * @param z     The z-coordinate of the cube
     * @return The terrain type of the given cube, encoded as an integer
     * according to the values in
     * {@link #createWorld(int[][][], TerrainChangeListener)}.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getCubeType(World world, int x, int y, int z) throws ModelException {
        return 0;
    }

    /**
     * Set the terrain type of the cube at the given coordinates the given
     * value.
     *
     * @param world The world in which to set the type.
     * @param x     The x-coordinate of the cube
     * @param y     The y-coordinate of the cube
     * @param z     The z-coordinate of the cube
     * @param value The new value of the terrain type of the cube, encoded as an
     *              integer according to the values in
     *              {@link #createWorld(int[][][], TerrainChangeListener)}.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {

    }

    /**
     * Return whether the cube at the given coordinates is solid and connected
     * to the border of the world.
     *
     * @param world The world to which the cube belongs
     * @param x     The x-coordinate of the cube
     * @param y     The y-coordinate of the cube
     * @param z     The z-coordinate of the cube
     * @return true if the given cube is solid and connected to the border of
     * the world; false otherwise.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
        return false;
    }

    /**
     * Spawn a new unit in the world, according to the rules in the assignment
     * (section 1.1.2).
     *
     * @param world                 The world in which to spawn a new unit
     * @param enableDefaultBehavior Whether the unit should act according to the default behaviour
     *                              or not.
     * @return The newly spawned unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
        return null;
    }

    /**
     * Adds the given unit to the given world.
     *
     * @param unit
     * @param world
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void addUnit(Unit unit, World world) throws ModelException {

    }

    /**
     * Return all units that are currently part of the world.
     *
     * @param world The world from which to retrieve units
     * @return A set containing all units from the world.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Unit> getUnits(World world) throws ModelException {
        return null;
    }

    /**
     * Return whether the given unit is currently carrying a log.
     *
     * @param unit The unit
     * @return true if the unit is currently carrying a log; false otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isCarryingLog(Unit unit) throws ModelException {
        return false;
    }

    /**
     * Return whether the given unit is currently carrying a boulder.
     *
     * @param unit The unit
     * @return true if the unit is currently carrying a boulder; false otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isCarryingBoulder(Unit unit) throws ModelException {
        return false;
    }

    /**
     * Return whether the given unit is currently alive.
     *
     * @param unit The unit
     * @return true if the unit is currently alive; false if it's dead
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isAlive(Unit unit) throws ModelException {
        return false;
    }

    /**
     * Return the current number of experience points of the given unit.
     *
     * @param unit
     * @return The number of experience points.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getExperiencePoints(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Make the given unit start working on the given target cube.
     *
     * @param unit The unit that should start working.
     * @param x    The x-coordinate of the target cube
     * @param y    The y-coordinate of the target cube
     * @param z    The z-coordinate of the target cube
     * @throws ModelException A precondition was violated or an exception was thrown.
     * @note This method replaces the {@link #work(Unit)} method from part 1.
     */
    @Override
    public void workAt(Unit unit, int x, int y, int z) throws ModelException {

    }

    /**
     * Return the current faction of the given unit.
     *
     * @param unit The unit of which to retrieve the faction.
     * @return The current faction of the unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Faction getFaction(Unit unit) throws ModelException {
        return null;
    }

    /**
     * Return the units that are part of the given faction.
     *
     * @param faction The faction of which to retrieve the members.
     * @return The set of units that belong to the faction.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
        return null;
    }

    /**
     * Return all the active factions of the given world.
     *
     * @param world@return A set of all active (i.e., non-empty) factions in the world.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Faction> getActiveFactions(World world) throws ModelException {
        return null;
    }

    /**
     * Get the precise coordinates of the given boulder.
     *
     * @param boulder The boulder for which to return the position.
     * @return The coordinate of the center of the boulder, as an array with 3
     * doubles {x, y, z}.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public double[] getPosition(Boulder boulder) throws ModelException {
        return new double[0];
    }

    /**
     * Return all boulders that are part of the given world.
     *
     * @param world The world from which to retrieve the boulders.
     * @return A set containing all boulders present in the given world (i.e.,
     * not picked up, consumed, destroyed, ...).
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Boulder> getBoulders(World world) throws ModelException {
        return null;
    }

    /**
     * Get the precise coordinate of the given log.
     *
     * @param log The log for which to return the position.
     * @return The coordinate of the center of the log, as an array with 3
     * doubles {x, y, z}.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public double[] getPosition(Log log) throws ModelException {
        return new double[0];
    }

    /**
     * Return all logs that are part of the given world.
     *
     * @param world The world from which to retrieve the logs.
     * @return A set containing all logs present in the given world (i.e., not
     * picked up, consumed, destroyed, ...).
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Log> getLogs(World world) throws ModelException {
        return null;
    }

    /**
     * Create a new unit with the given attributes.
     *
     * @param name                  The name of the unit.
     * @param initialPosition       The initial position of the unit, as an array with 3 elements
     *                              {x, y, z}.
     * @param weight                The initial weight of the unit
     * @param agility               The initial agility of the unit
     * @param strength              The initial strength of the unit
     * @param toughness             The initial toughness of the unit
     * @param enableDefaultBehavior Whether the default behavior of the unit is enabled
     * @return The generated unit
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness, boolean enableDefaultBehavior) throws ModelException {
        Unit unit = new Unit(LobbyWorld.lobby, name, new Vector(initialPosition), strength, agility, toughness, weight);
        if(enableDefaultBehavior)
            unit.startDefaultBehaviour();
        return unit;
    }

    /**
     * Get the precise coordinate of the given unit.
     *
     * @param unit The unit for which to return the position.
     * @return The coordinate of the center of the unit, as an array with 3
     * doubles {x, y, z}.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public double[] getPosition(Unit unit) throws ModelException {
        return new double[0];
    }

    /**
     * Get the coordinate of the cube occupied by the given unit.
     *
     * @param unit The unit for which to return the cube coordinate.
     * @return The coordinate of the cube in which the center of the unit lies,
     * as an array with 3 integers {x, y, z}.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int[] getCubeCoordinate(Unit unit) throws ModelException {
        return new int[0];
    }

    /**
     * Get the current name of the given unit.
     *
     * @param unit The unit for which to return the name.
     * @return The current name of the unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public String getName(Unit unit) throws ModelException {
        return null;
    }

    /**
     * Set the name of the given unit to the given value.
     *
     * @param unit    The unit whose name to change.
     * @param newName The new name for the unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void setName(Unit unit, String newName) throws ModelException {

    }

    /**
     * Return the weight attribute of the given unit.
     *
     * @param unit The unit for which to return the attribute's value
     * @return The current weight of the unit
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getWeight(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Sets the weight attribute's value of the given unit to the given value.
     *
     * @param unit     The unit for which to change the attribute's value
     * @param newValue The new weight
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void setWeight(Unit unit, int newValue) throws ModelException {

    }

    /**
     * Return the strength attribute of the given unit.
     *
     * @param unit The unit for which to return the attribute's value
     * @return The current strength of the unit
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getStrength(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Sets the strength attribute's value of the given unit to the given value.
     *
     * @param unit     The unit for which to change the attribute's value
     * @param newValue The new strength
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void setStrength(Unit unit, int newValue) throws ModelException {

    }

    /**
     * Return the agility attribute of the given unit.
     *
     * @param unit The unit for which to return the attribute's value
     * @return The current agility of the unit
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getAgility(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Sets the agility attribute's value of the given unit to the given value.
     *
     * @param unit     The unit for which to change the attribute's value
     * @param newValue The new agility
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void setAgility(Unit unit, int newValue) throws ModelException {

    }

    /**
     * Return the toughness attribute of the given unit.
     *
     * @param unit The unit for which to return the attribute's value
     * @return The current toughness of the unit
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getToughness(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Sets the toughness attribute's value of the given unit to the given
     * value.
     *
     * @param unit     The unit for which to change the attribute's value
     * @param newValue The new toughness
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void setToughness(Unit unit, int newValue) throws ModelException {

    }

    /**
     * Return the maximum number of hitpoints for the given unit.
     *
     * @param unit The unit for which to return the maximum number of hitpoints
     * @return The maximum number of hitpoints for the given unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getMaxHitPoints(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Return the current number of hitpoints for the given unit.
     *
     * @param unit The unit for which to return the current number of hitpoints
     * @return The current number of hitpoints for the given unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getCurrentHitPoints(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Return the maximum number of stamina points for the given unit.
     *
     * @param unit The unit for which to return the maximum number of stamina
     *             points
     * @return The maximum number of stamina points for the given unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getMaxStaminaPoints(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Return the current number of stamina points for the given unit.
     *
     * @param unit The unit for which to return the current number of stamina
     *             points
     * @return The current number of stamina points for the given unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getCurrentStaminaPoints(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Move the given unit to an adjacent cube.
     *
     * @param unit The unit to move
     * @param dx   The amount of cubes to move in the x-direction; should be -1,
     *             0 or 1.
     * @param dy   The amount of cubes to move in the y-direction; should be -1,
     *             0 or 1.
     * @param dz   The amount of cubes to move in the z-direction; should be -1,
     *             0 or 1.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {

    }

    /**
     * Return the current speed of the given unit.
     *
     * @param unit The unit for which to retrieve the speed.
     * @return The speed of the given unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public double getCurrentSpeed(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Return whether the given unit is currently moving.
     *
     * @param unit The unit for which to retrieve the state.
     * @return true if the unit is currently moving; false otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isMoving(Unit unit) throws ModelException {
        return false;
    }

    /**
     * Enable sprinting mode for the given unit.
     *
     * @param unit The unit which should start sprinting.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void startSprinting(Unit unit) throws ModelException {

    }

    /**
     * Disable sprinting mode for the given unit.
     *
     * @param unit The unit which should stop sprinting.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void stopSprinting(Unit unit) throws ModelException {

    }

    /**
     * Return whether the given unit is currently sprinting.
     *
     * @param unit The unit for which to retrieve the state.
     * @return true if the unit is currently sprinting; false otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isSprinting(Unit unit) throws ModelException {
        return false;
    }

    /**
     * Return the current orientation of the unit.
     *
     * @param unit The unit for which to retrieve the orientation
     * @return The orientation of the unit, in radians.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public double getOrientation(Unit unit) throws ModelException {
        return 0;
    }

    /**
     * Start moving the given unit to the given cube.
     *
     * @param unit The unit that should start moving
     * @param cube The coordinate of the cube to move to, as an array of integers
     *             {x, y, z}.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void moveTo(Unit unit, int[] cube) throws ModelException {

    }

    /**
     * Return whether the given unit is currently working.
     *
     * @param unit The unit for which to retrieve the state
     * @return true if the unit is currently working; false otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isWorking(Unit unit) throws ModelException {
        return false;
    }

    /**
     * Make the given unit fight with another unit.
     *
     * @param attacker The unit that initiates the fight by attacking another unit
     * @param defender The unit that gets attacked and should defend itself
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void fight(Unit attacker, Unit defender) throws ModelException {

    }

    /**
     * Return whether the given unit is currently attacking another unit.
     *
     * @param unit The unit for which to retrieve the state
     * @return true if the unit is currently attacking another unit; false
     * otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isAttacking(Unit unit) throws ModelException {
        return false;
    }

    /**
     * Make the given unit rest.
     *
     * @param unit The unit that should start resting
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void rest(Unit unit) throws ModelException {

    }

    /**
     * Return whether the given unit is currently resting.
     *
     * @param unit The unit for which to retrieve the atate
     * @return true if the unit is currently resting; false otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isResting(Unit unit) throws ModelException {
        return false;
    }

    /**
     * Enable or disable the default behavior of the given unit.
     *
     * @param unit  The unit for which to enable or disable the default behavior
     * @param value true if the default behavior should be enabled; false
     *              otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {

    }

    /**
     * Returns whether the default behavior of the given unit is enabled.
     *
     * @param unit The unit for which to retrieve the default behavior state.
     * @return true if the default behavior is enabled; false otherwise
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
        return false;
    }
}
