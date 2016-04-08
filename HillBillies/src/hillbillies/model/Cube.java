package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;

import java.util.*;

/**
 * Created by Bram on 1-4-2016.
 */
public class Cube extends WorldObject {

    /**
     * Constant reflecting the length of a cube side.
     */
    public static final double CUBE_SIDE_LENGTH = 1;



    public Cube(World world, Vector position){
        this(world, position, null);// Null will result in the default Terrain type -> Terrain.AIR
    }

    @Override
    public void advanceTime(double dt) {
        // TODO
    }

    /**
     * Initialize this new Cube at the given position in the given world with given terrain.
     *
     * @param world The world this new Cube belongs to.
     * @param position  The position of this new Cube.
     * @param terrain The terrain for this new Cube.
     * @effect This new Cube is initialized as a new WorldObject with
     *         given position in the given world.
     *       | super(world, position)
     * @post If the given terrain is a valid terrain for any Cube,
     * the terrain of this new Cube is equal to the given
     * terrain. Otherwise, the terrain of this new Cube is equal
     * to Terrain.AIR.
     * | if (isValidTerrain(terrain))
     * | then new.getTerrain() == terrain
     * | else new.getTerrain() == Terrain.AIR
     * @post This new cube has no materials yet.
     * | new.getNbMaterials() == 0
     */
    public Cube(World world, Vector position, Terrain terrain){
        super(world, position);
        if (! isValidTerrain(terrain))
            terrain = Terrain.AIR;
        setTerrain(terrain);
    }

    @Override
    protected boolean validatePosition(Vector position){
        // TODO
        return false;
    }

    /**
     * @invar The terrain of each Cube must be a valid terrain for any
     * Cube.
     * | isValidTerrain(getTerrain())
     */

    /**
     * Return the terrain of this Cube.
     */
    @Basic
    @Raw
    public Terrain getTerrain() {
    	return this.terrain;
    }
    /**
     * Check whether the given terrain is a valid terrain for
     * any Cube.
     *
     * @param terrain
     * The terrain to check.
     * @return
     * | result == (terrain != null)
     */
    public static boolean isValidTerrain(Terrain terrain) {
    	return terrain!=null;
    }
    /**
     * Set the terrain of this Cube to the given terrain.
     * @param terrain The new terrain for this Cube.
     * @post If the given terrain is a valid terrain for any Cube,
     * the terrain of this new Cube is equal to the given
     * terrain. Otherwise, the terrain of this new Cube is equal
     * to Terrain.AIR.
     * | if (isValidTerrain(terrain))
     * | then new.getTerrain() == terrain
     * | else new.getTerrain() == Terrain.AIR
     */
    @Raw
    public void setTerrain(Terrain terrain) {
    	if (!isValidTerrain(terrain))
            terrain = Terrain.AIR;
        this.terrain = terrain;
        if(!this.isPassable() && this.materials.size()>0){
            for(Material material : this.materials)
                material.terminate();
            this.materials.clear();
        }
    }
    /**
     * Variable registering the terrain of this Cube.
     */
    private Terrain terrain;

    public boolean isPassable(){
        return this.getTerrain().isPassable();
    }


    /**
     * Return the materials in this Cube.
     */
    @Basic
    @Raw
    @Immutable
    public List<Material> getMaterials() {
        return new ArrayList<>(this.materials);
    }

    public boolean containsMaterials(){
        return this.isPassable() && this.materials.size()>0;
    }


    /** TO BE ADDED TO THE CLASS INVARIANTS
     * @invar Each cube must have proper materials.
     * | hasProperMaterials()
     */

    /**
     * Return the material associated with this cube at the
     * given index.
     *
     * @param index
     * The index of the material to return.
     * @throws IndexOutOfBoundsException
     * The given index is not positive or it exceeds the
     * number of materials for this cube.
     * | (index < 1) || (index > getNbMaterials())
     */
    @Basic
    @Raw
    public Material getMaterialAt(int index) throws IndexOutOfBoundsException {
    	return materials.get(index - 1);
    }
    /**
     * Return the number of materials associated with this cube.
     */
    @Basic
    @Raw
    public int getNbMaterials() {
    	return materials.size();
    }
    /**
     * Check whether this cube can have the given material
     * as one of its materials.
     *
     * @param material
     * The material to check.
     * @return True if and only if the given material is effective
     * and that material can have this cube as its cube.
     * | result ==
     * | (material != null) &&
     * | Material.isValidCube(this)
     */
    @Raw
    public boolean canHaveAsMaterial(Material material) {
    	return (material != null) && (material.isValidOwner(this));
    }
    /**
     * Check whether this cube can have the given material
     * as one of its materials at the given index.
     *
     * @param material
     * The material to check.
     * @return False if the given index is not positive or exceeds the
     * number of materials for this cube + 1.
     * | if ( (index < 1) || (index > getNbMaterials()+1) )
     * | then result == false
     * Otherwise, false if this cube cannot have the given
     * material as one of its materials.
     * | else if ( ! this.canHaveAsMaterial(material) )
     * | then result == false
     * Otherwise, true if and only if the given material is
     * not registered at another index than the given index.
     * | else result ==
     * | for each I in 1..getNbMaterials():
     * | (index == I) || (getMaterialAt(I) != material)
     */
    @Raw
    public boolean canHaveAsMaterialAt(Material material, int index) {
    	if ((index < 1) || (index > getNbMaterials() + 1))
    	    return false;
    	if (!this.canHaveAsMaterial(material))
    	    return false;
    	for (int i = 1; i < getNbMaterials(); i++)
    		if ((i != index) && (getMaterialAt(i) == material))
    		    return false;
    	return true;
    }
    /**
     * Check whether this cube has proper materials attached to it.
     *
     * @return True if and only if this cube can have each of the
     * materials attached to it as a material at the given index,
     * and if each of these materials references this cube as
     * the cube to which they are attached.
     * | result ==
     * | for each I in 1..getNbMaterials():
     * | ( this.canHaveAsMaterialAt(getMaterialAt(I) &&
     * | (getMaterialAt(I).getCube() == this) )
     */
    public boolean hasProperMaterials() {
    	for (int i = 1; i <= getNbMaterials(); i++) {
    		if (!canHaveAsMaterialAt(getMaterialAt(i), i))
    		    return false;
    		if (getMaterialAt(i).getOwner() != this)
    		    return false;
    	}
    	return true;
    }
    /**
     * Check whether this cube has the given material as one of its
     * materials.
     *
     * @param material
     * The material to check.
     * @return The given material is registered at some position as
     * a material of this cube.
     * | for some I in 1..getNbMaterials():
     * | getMaterialAt(I) == material
     */
    public boolean hasAsMaterial(@Raw Material material) {
    	return materials.contains(material);
    }
    /**
     * Add the given material to the list of materials of this cube.
     *
     * @param material
     * The material to be added.
     * @pre The given material is effective and already references
     * this cube, and this cube does not yet have the given
     * material as one of its materials.
     * | (material != null) && (material.getCube() == this) &&
     * | (! this.hasAsMaterial(material))
     * @post The number of materials of this cube is
     * incremented by 1.
     * | new.getNbMaterials() == getNbMaterials() + 1
     * @post This cube has the given material as its very last material.
     * | new.getMaterialAt(getNbMaterials()+1) == material
     */
    public void addMaterial(@Raw Material material) {
    	assert(material != null) && (material.getOwner() == this) && (!this.hasAsMaterial(material));
    	materials.add(material);
    }
    /**
     * Remove the given material from the list of materials of this cube.
     *
     * @param material
     * The material to be removed.
     * @pre The given material is effective, this cube has the
     * given material as one of its materials, and the given
     * material does not reference any cube.
     * | (material != null) &&
     * | this.hasAsMaterial(material) &&
     * | (material.getCube() == null)
     * @post The number of materials of this cube is
     * decremented by 1.
     * | new.getNbMaterials() == getNbMaterials() - 1
     * @post This cube no longer has the given material as
     * one of its materials.
     * | ! new.hasAsMaterial(material)
     * @post All materials registered at an index beyond the index at
     * which the given material was registered, are shifted
     * one position to the left.
     * | for each I,J in 1..getNbMaterials():
     * | if ( (getMaterialAt(I) == material) and (I < J) )
     * | then new.getMaterialAt(J-1) == getMaterialAt(J)
     */
    @Raw
    public void removePurchase(Material material) {
    	assert(material != null) && this.hasAsMaterial(material) && (material.getOwner() == null);
    	materials.remove(material);
    }
    /**
     * Variable referencing a list collecting all the materials
     * of this cube.
     *
     * @invar The referenced list is effective.
     * | materials != null
     * @invar Each material registered in the referenced list is
     * effective and not yet terminated.
     * | for each material in materials:
     * | ( (material != null) &&
     * | (! material.isTerminated()) )
     * @invar No material is registered at several positions
     * in the referenced list.
     * | for each I,J in 0..materials.size()-1:
     * | ( (I == J) ||
     * | (materials.get(I) != materials.get(J))
     */
    private final List<Material> materials = new ArrayList <>();

    /**
     * Terminate this Cube.
     *
     * @post This Cube is terminated.
     * | new.isTerminated()
     * @post ...
     * | ...
     */
    @Override
	public void terminate() {
        this.isTerminated = true;
    }
    /**
     * Return a boolean indicating whether or not this Cube
     * is terminated.
     */
    @Override
	@Basic
    @Raw
    public boolean isTerminated() {
        return this.isTerminated;
    }
    /**
     * Variable registering whether this Cube is terminated.
     */
    private boolean isTerminated = false;
}
