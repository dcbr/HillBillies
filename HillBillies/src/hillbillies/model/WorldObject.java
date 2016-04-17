package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;

/**
 * Abstract class representing World Objects.
 * @author Kenneth & Bram
 * @version 1.0
 *
 * @invar The world of each WorldObject must be a valid world for any
 * WorldObject.
 * | isValidWorld(getWorld())
 * @invar The position of each WorldObject must be a valid position for any
 * WorldObject.
 * | isValidPosition(getPosition())
 */
public abstract class WorldObject implements IWorldObject {

    /**
     * Variable registering the world of this WorldObject.
     */
    private IWorld world;

    /**
     * Initialize this new WorldObject with given position.
     *
     * @param world The World this WorldObject belongs to.
     * @param position The position for this new WorldObject.
     * @effect The world this new WorldObject belongs to is set
     * to the given world.
     * | this.setWorld(world)
     * @effect The position of this new WorldObject is set to
     * the given position.
     * | this.setPosition(position)
     */
    public WorldObject(IWorld world, Vector position){
        this.setWorld(world);
        this.setPosition(position);
    }

    @Override
    public abstract void advanceTime(double dt);

    protected abstract boolean validatePosition(Vector position);

    /**
     * Return the position of this WorldObject.
     */
    @Basic
    @Raw
    @Override
    public Vector getPosition() {
        return this.position.clone();
    }
    /**
     * Check whether the given position is a valid position for
     * any WorldObject.
     *
     * @param position
     * The position to check.
     * @return
     * | result == this.world.isValidPosition(position)
     */
    public boolean isValidPosition(Vector position) {
        return position!= null && this.world.isValidPosition(position) && this.validatePosition(position);
    }
    /**
     * Set the position of this WorldObject to the given position.
     *
     * @param position
     * The new position for this WorldObject.
     * @post The position of this new WorldObject is equal to
     * the given position.
     * | new.getPosition() == position
     * @throws IllegalArgumentException * The given position is not a valid position for any
     * WorldObject.
     * | ! isValidPosition(getPosition())
     */
    @Raw
    public void setPosition(Vector position) throws IllegalArgumentException {
        if (! isValidPosition(position))
            throw new IllegalArgumentException();
        this.position = position;
    }
    /**
     * Variable registering the position of this WorldObject.
     */
    private Vector position;


    /**
     * Return the world of this WorldObject.
     */
    @Override
	@Basic
    @Raw
    public IWorld getWorld() {
        return this.world;
    }
    /**
     * Check whether the given world is a valid world for
     * any WorldObject.
     *
     * @param world
     * The world to check.
     * @return
     * | result ==
     */
    public static boolean isValidWorld(IWorld world) {
        return true;// TODO: is this necessary?
    }
    /**
     * Set the world of this WorldObject to the given world.
     *
     * @param world
     * The new world for this WorldObject.
     * @post The world of this new WorldObject is equal to
     * the given world.
     * | new.getWorld() == world
     * @throws IllegalArgumentException
     * The given world is not a valid world for any
     * WorldObject.
     * | ! isValidWorld(getWorld())
     */
    @Raw
    public void setWorld(IWorld world) throws IllegalArgumentException {
        if (! isValidWorld(world))
            throw new IllegalArgumentException();
        IWorld oldWorld = this.world;
        this.world = world;
        if(this.position!=null && !this.isValidPosition(this.getPosition())){
            this.world = oldWorld;
            throw new IllegalArgumentException("The WorldObject's current position is not valid in the new world.");
        }
    }
}
