package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;

import static hillbillies.utils.Utils.randInt;

/**
 * A class representing a raw material
 * @author Kenneth & Bram
 * @version 1.0
 *
 * @invar Each Material can have its world as world.
 * | canHaveAsWorld(this.getWorld())
 * @invar The owner of each Material must be a valid owner for any
 * Material.
 * | isValidOwner(getOwner())
 * @invar Each Material can have its weight as weight.
 * | canHaveAsWeight(this.getWeight())
 *
 */
public class Material implements IWorldObject {

    /**
     * Constant reflecting the minimum weight of each Material.
     */
    public static final int MIN_WEIGHT = 10;
    /**
     * Constant reflecting the maximum weight of each Material.
     */
    public static final int MAX_WEIGHT = 10;

    /**
     * Variable registering the world this Material belongs to.
     */
    private final World world;
    /**
     * Variable registering the Material's current owner.
     */
    private WorldObject owner;
    /**
     * Variable registering the weight of this Material.
     */
    private final int weight;
    /**
     * Variable registering whether this Material is terminated.
     */
    private boolean isTerminated = false;
    /**
     * Variable registering the Material's position while it's falling.
     */
    private Vector fallingPosition;

    /**
     * Initialize this new Material in the given world with the given owner.
     * @param world The world for this new Material.
     * @param owner The owner for this new Material
     * @post The world this new Material belongs to is equal to the given
     * world.
     * | new.getWorld() == world
     * @effect The owner of this new Material is set to
     * the given owner.
     * | this.setOwner(owner)
     * @post The weight of this new Material is a random
     * integer between MIN_WEIGHT and MAX_WEIGHT.
     * | MIN_WEIGHT <= new.getWeight() <= MAX_WEIGHT
     * @throws IllegalArgumentException
     * This new Material cannot have the given world as its world.
     * | ! canHaveAsWorld(this.getWorld())
     */
    public Material(World world, WorldObject owner) throws IllegalArgumentException{
        if (! canHaveAsWorld(world))
            throw new IllegalArgumentException();
        this.world = world;
        this.setOwner(owner);
        this.weight = randInt(MIN_WEIGHT, MAX_WEIGHT);
    }

    @Override
    public void advanceTime(double dt) {
        if(!this.hasValidPosition()){
            this.fallingPosition = this.getPosition();
            this.setOwner(null);
        }
        //TODO: (for next part) move falling code to separate place
        Vector cPos = this.getPosition();
        Vector cPosCube = cPos.getCubeCenterCoordinates();
        if (cPos.equals(cPosCube) && this.hasValidPosition()){
            this.setOwner(this.getWorld().getCube(cPos.getCubeCoordinates()));
        }
        else{
            double speed = 3;
            Vector nextPos = cPos.add(new Vector(0,0,-speed*dt));
            if (this.hasValidPosition() && ((cPosCube.isInBetween(2, cPos, nextPos) || cPos.Z() <= cPosCube.Z() )))
                this.fallingPosition = cPosCube;
            else if(nextPos.getCubeCenterCoordinates().isInBetween(2, cPos, nextPos) && isValidPosition(nextPos))
                this.fallingPosition = nextPos.getCubeCenterCoordinates();
            else
                this.fallingPosition = nextPos;
        }
    }

    //region Setters
    /**
     * Set the owner of this Material to the given owner.
     *
     * @param owner
     * The new owner for this Material.
     * @post The owner of this new Material is equal to the given owner.
     * | new.getOwner() == owner
     * @throws IllegalArgumentException
     * The given owner is not a valid owner for any Material.
     * | ! isValidOwner(getOwner())
     */
    @Raw
    public void setOwner(WorldObject owner) throws IllegalArgumentException {
        if (! isValidOwner(owner))
            throw new IllegalArgumentException();
        this.owner = owner;
    }
    //endregion

    //region Getters
    /**
     * Return the position of this Material.
     * @return The position of this Material.
     *          This position equals the position of this Material's current owner.
     */
    @Override
    public Vector getPosition(){
        if(this.getOwner()!=null)
            return this.owner.getPosition();
        else
            return fallingPosition;
    }

    /**
     * Return the world this Material belongs to.
     */
    @Basic
    @Raw
    @Immutable
    @Override
    public World getWorld() {
        return this.world;
    }
    /**
     * Return the owner of this Material.
     */
    @Basic
    @Raw
    public WorldObject getOwner() {
        return this.owner;
    }

    /**
     * Return the weight of this Material.
    */
    @Basic
    @Raw
    @Immutable
    public int getWeight() {
        return this.weight;
    }
    //endregion

    //region Checkers
    /**
     * Check whether this Material can have the given world as its world.
     *
     * @param world
     * The world to check.
     * @return
     * | result ==
     */
    @Raw
    public boolean canHaveAsWorld(World world) {
        // TODO
        return true;
    }

    /**
     * Check whether the given owner is a valid owner for
     * any Material.
     *
     * @param owner
     * The owner to check.
     * @return
     * | result ==
     */
    public boolean isValidOwner(WorldObject owner) {
        if(owner == null) return true;
        if(owner instanceof Cube){
            // TODO: check if Cube doesn't contain other Materials
            return owner.getWorld() == this.world;
        }else if(owner instanceof Unit){
            Unit carrier = (Unit)owner;
            return owner.getWorld() == this.world && carrier.getCarriedMaterial() == this;
        }
        return false;
    }

    /**
     * Check whether this Material can have the given weight as its weight.
     *
     * @param weight
     * The weight to check.
     * @return
     * | result == (10 <= weight <= 50)
     */
    @Raw
    public boolean canHaveAsWeight(int weight) {
        return 10 <= weight && weight <= 50;
    }

    private boolean hasValidPosition(){
        if(this.getOwner() instanceof Unit) return true;
        return isValidPosition(this.getPosition());
    }

    private boolean isValidPosition(Vector position){
        return getPosition().cubeZ()==0 || !getWorld().getCube(this.getPosition().add(new Vector(0,0,-1))).isPassable();
    }
    //endregion

    //region Destructor
    /**
     * Terminate this Material.
     *
     * @post This Material is terminated.
     * | new.isTerminated()
     * @post ...
     * | ...
     */
    @Override
    public void terminate() {
        this.isTerminated = true;
    }
    /**
     * Return a boolean indicating whether or not this Material
     * is terminated.
     */
    @Basic
    @Raw
    @Override
    public boolean isTerminated() {
        return this.isTerminated;
    }
    //endregion

}
