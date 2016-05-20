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
     * @post The world this new Material belongs to, is equal to the given
     * world.
     * | new.getWorld() == world
     * @effect The owner of this new Material is set to
     * the given owner.
     * | this.setOwner(owner)
     * @post The weight of this new Material is a random
     * integer between MIN_WEIGHT and MAX_WEIGHT.
     * | MIN_WEIGHT <= new.getWeight() <= MAX_WEIGHT
     * @throws IllegalArgumentException When the owner is not a valid owner.
     * 		| ! isValidOwner(owner)
     * @throws IllegalStateException When the owner has reached its maximum number of owned Materials
     * | owner.getMaxNbOwnedMaterials()!=-1 && owner.getNbOwnedMaterials()>=getMaxNbOwnedMaterials()
     */
    public Material(World world, WorldObject owner) throws IllegalArgumentException{
        this.world = world;
        world.addMaterial(this);
        this.setOwner(owner);
        owner.addOwnedMaterial(this);
        this.weight = randInt(MIN_WEIGHT, MAX_WEIGHT);
    }

    @Override
    public void advanceTime(double dt) {
        if(!this.hasValidPosition() && this.getOwner()!=null){
            this.fallingPosition = this.getPosition();
            WorldObject owner = this.getOwner();
            this.setOwner(null);
            owner.removeOwnedMaterial(this);
        }
        if(this.getOwner() == null) {
            Vector cPos = this.getPosition();
            Vector cPosCube = cPos.getCubeCenterCoordinates();
            if (cPos.equals(cPosCube) && this.isValidPosition(cPos)) {
                Cube newOwner = this.getWorld().getCube(cPos.getCubeCoordinates());
                this.setOwner(newOwner);
                newOwner.addOwnedMaterial(this);
            } else {
                double speed = 3;
                Vector nextPos = cPos.add(new Vector(0, 0, -speed * dt));
                if (this.hasValidPosition() && ((cPosCube.isInBetween(2, cPos, nextPos) || cPos.Z() <= cPosCube.Z())))
                    this.fallingPosition = cPosCube;
                else if (nextPos.getCubeCenterCoordinates().isInBetween(2, cPos, nextPos) && isValidPosition(nextPos))
                    this.fallingPosition = nextPos.getCubeCenterCoordinates();
                else
                    this.fallingPosition = nextPos;
            }
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
        if(this.getOwner() != null)
        	this.getOwner().removeOwnedMaterial(this);
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
        if(this.getOwner()!=null){
        	if(this.getOwner() instanceof Cube)
        		return this.owner.getPosition().getCubeCenterCoordinates();
            return this.owner.getPosition();
        }
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
     * Check whether the given owner is a valid owner for
     * any Material.
     *
     * @param owner
     * The owner to check.
     * @return
     * | result == (owner==null || owner.getWorld()==this.world)
     */
    public boolean isValidOwner(WorldObject owner) {
        return owner == null || owner.getWorld() == this.world;
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

    /**
     * Check whether this Material has a valid position.
     * @return True when the material is owned by a Unit or
     *          its position is valid.
     *          | result == (this.getOwner() instanceof Unit) ||
     *          |               isValidPosition(this.getPosition())
     */
    private boolean hasValidPosition(){
        if(this.getOwner() instanceof Unit) return true;
        return isValidPosition(this.getPosition());
    }

    /**
     * Check whether the given position is a valid position for
     * this Material when it's not carried by a Unit.
     * @param position The position to check
     * @return True if the position's z-cube coordinate is 0
     *          (Material lies on the bottom of the world) or
     *          the cube beneath the material isn't passable.
     *          | result == (position.cubeZ()==0) ||
     *          |               !getWorld().getCube(position.getCubeCoordinates().add(new Vector(0,0,-1)).isPassable()
     */
    private boolean isValidPosition(Vector position){
        return position.cubeZ()==0 || !getWorld().getCube(position.getCubeCoordinates().add(new Vector(0,0,-1))).isPassable();
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
        WorldObject oldOwner = this.getOwner();
        this.setOwner(null);
        oldOwner.removeOwnedMaterial(this);
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
