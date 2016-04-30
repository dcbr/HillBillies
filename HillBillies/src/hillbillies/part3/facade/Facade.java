package hillbillies.part3.facade;

import hillbillies.model.*;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.Scheduler;
import hillbillies.part3.programs.Task;
import hillbillies.utils.*;
import ogp.framework.util.ModelException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Bram on 26-4-2016.
 */
public class Facade implements IFacade {// TODO: check if some methods throw Exceptions and cast them to ModelExceptions
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
        return new World(terrainTypes, modelListener);
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
        return world.getNbCubesX();
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
        return world.getNbCubesY();
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
        return world.getNbCubesZ();
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
        world.advanceTime(dt);
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
        try {
            return world.getCube(new Vector(x, y, z)).getTerrain().getId();
        }catch(IllegalArgumentException e){
            throw new ModelException("The given coordinates do not reference a valid position in this world.",e);
        }
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
        try {
            world.getCube(new Vector(x, y, z)).setTerrain(Terrain.fromId(value));
        }catch(IllegalArgumentException e){
            throw new ModelException("The given coordinates do not reference a valid position in this world.",e);
        }
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
        return world.connectedToBorder.isSolidConnectedToBorder(x,y,z);
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
        return world.spawnUnit(enableDefaultBehavior);
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
        world.addUnit(unit);
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
        return world.getUnits();
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
        return unit.isCarryingLog();
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
        return unit.isCarryingBoulder();
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
        return !unit.isTerminated();
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
        return unit.getXP();//TODO: toevoegen wanneer hoeveel wordt verdiend
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
        try{
            unit.work(new Vector(x,y,z));
        }catch(IllegalArgumentException e){
            throw new ModelException("The target cube is not a neighbouring cube.", e);
        }catch(IllegalStateException e){
            throw new ModelException("Unit is not able to work at this moment.", e);
        }
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
        return unit.getFaction();
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
        return faction.getUnits();
    }

    /**
     * Return all the active factions of the given world.
     *
     * @param world@return A set of all active (i.e., non-empty) factions in the world.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Faction> getActiveFactions(World world) throws ModelException {
        return world.getFactions();
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
        return boulder.getPosition().asArray();
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
        return world.getBoulders(true);
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
        return log.getPosition().asArray();
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
        return world.getLogs(true);
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
        return unit.getPosition().asArray();
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
        return Utils.ArrayConvert.doubleToInt(unit.getPosition().getCubeCoordinates().asArray());
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
        return unit.getName();
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
        try {
            unit.setName(newName);
        }catch(IllegalArgumentException e){
            throw new ModelException("Invalid newName for unit.", e);
        }
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
        return unit.getWeight();
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
        if (!Unit.isValidWeight(newValue, unit.getStrength(), unit.getAgility()))
            throw new ModelException("Invalid weight for this unit");
        unit.setWeight(newValue);
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
        return unit.getStrength();
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
        if (!Unit.isValidStrength(newValue))
            throw new ModelException("Invalid strength for this unit");
        unit.setStrength(newValue);
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
        return unit.getAgility();
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
        if (!Unit.isValidAgility(newValue))
            throw new ModelException("Invalid agility for this unit");
        unit.setAgility(newValue);
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
        return unit.getToughness();
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
        if (!Unit.isValidToughness(newValue))
            throw new ModelException("Invalid toughness for this unit");
        unit.setToughness(newValue);
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
        return Unit.getMaxHitpoints(unit.getWeight(), unit.getToughness());
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
        return unit.getHitpoints();
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
        return Unit.getMaxStamina(unit.getWeight(), unit.getToughness());
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
        return unit.getStamina();
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
        try{
            unit.moveToAdjacent(new Vector(dx,dy,dz));
        }catch(IllegalStateException e){
            throw new ModelException("Unit is not able to move at this moment.",e);
        }catch(IllegalArgumentException e){
            throw new ModelException("Invalid position",e);
        }
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
        return unit.getCurrentSpeed();
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
        return unit.isMoving();
    }

    /**
     * Enable sprinting mode for the given unit.
     *
     * @param unit The unit which should start sprinting.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void startSprinting(Unit unit) throws ModelException {
        try{
            unit.sprint();
        }catch(IllegalStateException e){
            throw new ModelException("The Unit is not able to sprint!", e);
        }
    }

    /**
     * Disable sprinting mode for the given unit.
     *
     * @param unit The unit which should stop sprinting.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void stopSprinting(Unit unit) throws ModelException {
        if(!unit.isSprinting())
            throw new ModelException("Unit was not sprinting");
        unit.stopSprint();
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
        return unit.isSprinting();
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
        return unit.getOrientation();
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
        try{
            unit.moveToTarget(new Vector (cube));
        }catch (IllegalStateException e){
            throw new ModelException("Unit is not able to move at this moment.",e );
        }catch (IllegalArgumentException e){
            throw new ModelException("The target is not a valid position", e);
        }
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
        return unit.isWorking();
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
        try{
            attacker.attack(defender);
        }catch(IllegalArgumentException e){
            throw new ModelException("Cannot attack that unit", e);
        }
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
        return unit.isAttacking();
    }

    /**
     * Make the given unit rest.
     *
     * @param unit The unit that should start resting
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void rest(Unit unit) throws ModelException {
        try{
            unit.rest();
        }catch(IllegalStateException e){
            throw new ModelException("This unit cannot rest at this moment", e);
        }
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
        return unit.isResting();
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
        if (value)
            unit.startDefaultBehaviour();
        else unit.stopDefaultBehaviour();
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
        return unit.isDefaultActive();
    }

    /**
     * Create a new instance of a Task factory.
     * <p>
     * <p>
     * This factory is used by the parser ({@link TaskParser}) to construct an
     * in-memory representation of your program. For example, when reading the
     * task description
     * <p>
     * <pre>
     * name: "test"
     * priority: 1
     * activities: moveTo here;
     * </pre>
     * <p>
     * the parser will create a Task object by (conceptually) executing the
     * following code:
     * <p>
     * <pre>
     * factory.createTask("test", 1, factory.createMoveTo(factory.createHerePosition()))
     * </pre>
     * <p>
     * on the returned factory object.
     * <p>
     * <p>
     * For testing, you may use the methods from {@link TaskParser} yourself, as
     * demonstrated in the partial test file {@link Part3TestPartial}.
     *
     * @return An instance of ITaskFactory. See the documentation of that
     * interface for an explanation of its parameters.
     */
    @Override
    public ITaskFactory<?, ?, Task> createTaskFactory() {
        return null;
    }

    /**
     * Returns whether the given task is well-formed.
     * <p>
     * A task is well-formed if
     * <ul>
     * <li>it is type-safe</li>
     * <li>there are no break statements outside loops</li>
     * <li>variables assigned before they are first used</li>
     * </ul>
     * See the assignment text for more details.
     *
     * @param task The task to check for well-formedness
     * @throws ModelException A precondition was violated or an exception was thrown.
     * @note Single-student groups may always return true for this method.
     */
    @Override
    public boolean isWellFormed(Task task) throws ModelException {
        return false;
    }

    /**
     * Returns the scheduler associated to the given faction.
     *
     * @param faction The faction of which to return the scheduler.
     * @return The scheduler associated to the given faction.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Scheduler getScheduler(Faction faction) throws ModelException {
        return faction.getScheduler();
    }

    /**
     * Schedule the given task for execution on the given scheduler.
     *
     * @param scheduler The scheduler on which the task should be scheduled.
     * @param task      The task to schedule.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void schedule(Scheduler scheduler, Task task) throws ModelException {

    }

    /**
     * Replace the given task by another task in the given scheduler.
     *
     * @param scheduler   The scheduler in which a task should be replaced
     * @param original    The task that needs to be replaced.
     * @param replacement The task that will replace the original task.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException {

    }

    /**
     * Returns whether the given tasks are all part of the given scheduler.
     *
     * @param scheduler The scheduler on which to check
     * @param tasks     The tasks to check
     * @return true if all given tasks are part of the scheduler; false
     * otherwise.
     * @throws ModelException A precondition was violated or an exception was thrown.s
     */
    @Override
    public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException {
        return false;
    }

    /**
     * Returns an iterator for all tasks currently managed by the given
     * scheduler.
     *
     * @param scheduler The scheduler for which to return an iterator.
     * @return An iterator that yields all scheduled tasks managed by the given
     * scheduler, independent of whether they're currently assigned to a
     * Unit or not. Completed tasks should not be part of the result.
     * The tasks should be delivered by decreasing priority (highest
     * priority first).
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException {
        return null;
    }

    /**
     * Return all schedulers in which the given task is present.
     *
     * @param task The task for which to retrieve the schedulers
     * @return A set of all schedulers that contain the given task.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException {
        return null;
    }

    /**
     * Return the unit currently assigned to the given task, if any.
     *
     * @param task The task for which to retrieve the unit
     * @return The unit that is currently assigned to the given task, or null if
     * the task is not assigned to a unit.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Unit getAssignedUnit(Task task) throws ModelException {
        return null;
    }

    /**
     * Return the task currently assigned to the given unit, if any.
     *
     * @param unit The unit for which to retrieve the assigned task
     * @return The task that is currently assigned to the given unit, or null if
     * the unit does not have an assigned task.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public Task getAssignedTask(Unit unit) throws ModelException {
        return null;
    }

    /**
     * Return the name of the given task.
     *
     * @param task The task of which to retrieve the name
     * @return The name of the given task.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public String getName(Task task) throws ModelException {
        return task.getName();
    }

    /**
     * Return the priority of the given task.
     *
     * @param task The task of which to retrieve the priority
     * @return The priority of the given task.
     * @throws ModelException A precondition was violated or an exception was thrown.
     */
    @Override
    public int getPriority(Task task) throws ModelException {
        return task.getPriority();
    }
}
