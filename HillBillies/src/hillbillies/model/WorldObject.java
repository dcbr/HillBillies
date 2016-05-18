package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;

import java.util.*;

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
 * @invar Each worldObject must have proper ownedMaterials.
 * | hasProperOwnedMaterials()
 */
public abstract class WorldObject implements IWorldObject {

    /**
     * Variable registering the world of this WorldObject.
     */
    private IWorld world;

    /**
     * Initialize this new WorldObject with given position in the
     * given world with no ownedMaterials yet.
     *
     * @param world The World this WorldObject belongs to.
     * @param position The position for this new WorldObject.
     * @effect The world this new WorldObject belongs to is set
     * to the given world.
     * | this.setWorld(world)
     * @effect The position of this new WorldObject is set to
     * the given position.
     * | this.setPosition(position)
     * @post This new worldObject has no ownedMaterials yet.
     * | new.getNbOwnedMaterials() == 0
     */
    public WorldObject(IWorld world, Vector position){
        this.setWorld(world);
        this.setPosition(position);
    }

    @Override
    public abstract void advanceTime(double dt);

    /**
     * Subclasses must implement this method. The result must be
     * true when the given position is a valid position for
     * the worldObject. The given position is not null and
     * is already checked for validity inside the current
     * world. (See isValidPosition)
     * @param position The position to check
     * @pre The given position is effective and already checked
     *      inside the current world.
     *      | position != null && this.getWorld().isValidPosition(position)
     * @return True if the given position is a valid position
     *          for this worldObject.
     */
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
        IWorld oldWorld = this.world;
        this.world = world;
        if(this.position!=null && !this.isValidPosition(this.getPosition())){
            this.world = oldWorld;
            throw new IllegalArgumentException("The WorldObject's current position is not valid in the new world.");
        }
    }

    /**
     * Return the ownedMaterial associated with this worldObject at the
     * given index.
     *
     * @param index
     * The index of the ownedMaterial to return.
     * @throws IndexOutOfBoundsException
     * The given index is not positive or it exceeds the
     * number of ownedMaterials for this worldObject.
     * | (index < 1) || (index > getNbOwnedMaterials())
     */
    @Basic
    @Raw
    public Material getOwnedMaterialAt(int index) throws IndexOutOfBoundsException {
    	return ownedMaterials.get(index - 1);
    }
    /**
     * Return the number of ownedMaterials associated with this worldObject.
     */
    @Basic
    @Raw
    public int getNbOwnedMaterials() {
    	return ownedMaterials.size();
    }
    /**
     * Check whether this worldObject can have the given material
     * as one of its ownedMaterials.
     *
     * @param material
     * The material to check.
     * @return True if and only if the given material is effective
     * and that material can have this worldObject as its owner and
     * the maximum number of owned Materials isn't reached yet.
     * | result ==
     * | (material != null) &&
     * | material.isValidOwner(this) &&
     * | getNbOwnedMaterials()<getMaxNbOwnedMaterials()
     */
    @Raw
    public boolean canHaveAsOwnedMaterial(Material material) {
    	return (material != null) && (material.isValidOwner(this)) &&
                (getMaxNbOwnedMaterials()==-1 || getNbOwnedMaterials()<getMaxNbOwnedMaterials());
    }
    /**
     * Check whether this worldObject can have the given material
     * as one of its ownedMaterials at the given index.
     *
     * @param material
     * The material to check.
     * @return False if the given index is not positive or exceeds the
     * number of ownedMaterials for this worldObject + 1.
     * | if ( (index < 1) || (index > getNbOwnedMaterials()+1) )
     * | then result == false
     * Otherwise, false if this worldObject cannot have the given
     * material as one of its ownedMaterials.
     * | else if ( ! this.canHaveAsOwnedMaterial(material) )
     * | then result == false
     * Otherwise, true if and only if the given material is
     * not registered at another index than the given index.
     * | else result ==
     * | for each I in 1..getNbOwnedMaterials():
     * | (index == I) || (getOwnedMaterialAt(I) != material)
     */
    @Raw
    public boolean canHaveAsOwnedMaterialAt(Material material, int index) {
    	if ((index < 1) || (index > getNbOwnedMaterials() + 1))
    	    return false;
    	if (!this.canHaveAsOwnedMaterial(material))
    	    return false;
    	for (int i = 1; i < getNbOwnedMaterials(); i++)
    		if ((i != index) && (getOwnedMaterialAt(i) == material))
    		    return false;
    	return true;
    }
    /**
     * Check whether this worldObject has proper ownedMaterials attached to it.
     *
     * @return True if and only if this worldObject can have each of the
     * ownedMaterials attached to it as a ownedMaterial at the given index,
     * and if each of these ownedMaterials references this worldObject as
     * the owner to which they are attached. False if the number of owned
     * materials of this worldObject exceeds the maximum number of owned
     * materials.
     * | result == getNbOwnedMaterials()<=getMaxNbOwnedMaterials &&
     * | for each I in 1..getNbOwnedMaterials():
     * | ( this.canHaveAsOwnedMaterialAt(getOwnedMaterialAt(I) &&
     * | (getOwnedMaterialAt(I).getWorldObject() == this) )
     */
    public boolean hasProperOwnedMaterials() {
        if(getMaxNbOwnedMaterials()!=-1 && getNbOwnedMaterials()>getMaxNbOwnedMaterials())
            return false;
    	for (int i = 1; i <= getNbOwnedMaterials(); i++) {
    		if (!canHaveAsOwnedMaterialAt(getOwnedMaterialAt(i), i))
    		    return false;
    		if (getOwnedMaterialAt(i).getOwner() != this)
    		    return false;
    	}
    	return true;
    }
    /**
     * Check whether this worldObject has the given ownedMaterial as one of its
     * ownedMaterials.
     *
     * @param ownedMaterial
     * The ownedMaterial to check.
     * @return The given ownedMaterial is registered at some position as
     * an ownedMaterial of this worldObject.
     * | for some I in 1..getNbOwnedMaterials():
     * | getOwnedMaterialAt(I) == ownedMaterial
     */
    public boolean hasAsOwnedMaterial(@Raw Material ownedMaterial) {
    	return ownedMaterials.contains(ownedMaterial);
    }
    /**
     * Add the given material to the list of ownedMaterials of this worldObject.
     *
     * @param material
     * The material to be added.
     * @pre The given material is effective and already references
     * this worldObject, and this worldObject does not yet have the given
     * material as one of its ownedMaterials.
     * | (material != null) && (material.getWorldObject() == this) &&
     * | (! this.hasAsOwnedMaterial(material))
     * @post The number of ownedMaterials of this worldObject is
     * incremented by 1.
     * | new.getNbOwnedMaterials() == getNbOwnedMaterials() + 1
     * @post This worldObject has the given material as its very last ownedMaterial.
     * | new.getOwnedMaterialAt(getNbOwnedMaterials()+1) == ownedMaterial
     */
    public void addOwnedMaterial(@Raw Material material) {
    	assert(material != null) && (material.getOwner() == this) && (!this.hasAsOwnedMaterial(material));
        if(getMaxNbOwnedMaterials()!=-1 && getNbOwnedMaterials()>=getMaxNbOwnedMaterials())
            throw new IllegalStateException("This WorldObject has reached its maximum number of owned Materials");
    	ownedMaterials.add(material);
    }
    /**
     * Remove the given ownedMaterial from the list of ownedMaterials of this worldObject.
     *
     * @param ownedMaterial
     * The ownedMaterial to be removed.
     * @pre The given ownedMaterial is effective, this worldObject has the
     * given ownedMaterial as one of its ownedMaterials, and the given
     * ownedMaterial does not reference this worldObject anymore.
     * | (ownedMaterial != null) &&
     * | this.hasAsOwnedMaterial(ownedMaterial) &&
     * | (ownedMaterial.getOwner() != this)
     * @post The number of ownedMaterials of this worldObject is
     * decremented by 1.
     * | new.getNbOwnedMaterials() == getNbOwnedMaterials() - 1
     * @post This worldObject no longer has the given ownedMaterial as
     * one of its ownedMaterials.
     * | ! new.hasAsOwnedMaterial(ownedMaterial)
     * @post All ownedMaterials registered at an index beyond the index at
     * which the given ownedMaterial was registered, are shifted
     * one position to the left.
     * | for each I,J in 1..getNbOwnedMaterials():
     * | if ( (getOwnedMaterialAt(I) == ownedMaterial) and (I < J) )
     * | then new.getOwnedMaterialAt(J-1) == getOwnedMaterialAt(J)
     */
    @Raw
    public void removeOwnedMaterial(Material ownedMaterial) {
    	assert(ownedMaterial != null) && this.hasAsOwnedMaterial(ownedMaterial) && (ownedMaterial.getOwner() != this);
    	ownedMaterials.remove(ownedMaterial);
    }
    /**
     * Variable referencing a list collecting all the ownedMaterials
     * of this worldObject.
     *
     * @invar The referenced list is effective.
     * | ownedMaterials != null
     * @invar Each ownedMaterial registered in the referenced list is
     * effective and not yet terminated.
     * | for each ownedMaterial in ownedMaterials:
     * | ( (ownedMaterial != null) &&
     * | (! ownedMaterial.isTerminated()) )
     * @invar No ownedMaterial is registered at several positions
     * in the referenced list.
     * | for each I,J in 0..ownedMaterials.size()-1:
     * | ( (I == J) ||
     * | (ownedMaterials.get(I) != ownedMaterials.get(J))
     */
    protected final List<Material> ownedMaterials = new LinkedList<>();

    /**
     * Return the ownedMaterials of this WorldObject.
     */
    @Basic
    @Raw
    @Immutable
    public List<Material> getMaterials() {
        return new LinkedList<>(this.ownedMaterials);
    }

    /**
     * Return the maximum number of materials this worldObject can own.
     * If there is no limit, return -1.
     */
    public abstract int getMaxNbOwnedMaterials();
}
