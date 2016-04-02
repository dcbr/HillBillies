package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;

/**
 * Abstract class representing World Objects.
 * @author Kenneth & Bram
 * @version 1.0
 *
 * @invar The position of each WorldObject must be a valid position for any
 * WorldObject.
 * | isValidPosition(getPosition())
 */
public abstract class WorldObject {

    protected IWorld world;

    /**
     * Initialize this new WorldObject with given position.
     *
     * @param world The World this WorldObject belongs to.
     * @param position
     * The position for this new WorldObject.
     * @effect The position of this new WorldObject is set to
     * the given position.
     * | this.setPosition(position)
     */
    public WorldObject(IWorld world, Vector position){
        this.world = world;
        this.setPosition(position);
    }

    public abstract void advanceTime(double dt);

    protected abstract boolean validatePosition(Vector position);

    /**
     * Initialize this new WorldObject with given position.
     *
     * @param position
     * The position for this new WorldObject.
     * @effect The position of this new WorldObject is set to
     * the given position.
     * | this.setPosition(position)
     */
    public WorldObject(Vector position) throws IllegalArgumentException {
        this.setPosition(position);
    }
    /**
     * Return the position of this WorldObject.
     */
    @Basic
    @Raw
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
    protected void setPosition(Vector position) throws IllegalArgumentException {
        if (! isValidPosition(position))
            throw new IllegalArgumentException();
        this.position = position;
    }
    /**
     * Variable registering the position of this WorldObject.
     */
    private Vector position;
}
