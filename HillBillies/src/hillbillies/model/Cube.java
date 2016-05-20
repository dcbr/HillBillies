package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.utils.Vector;

import java.util.*;
import java.util.function.BiConsumer;

import static hillbillies.utils.Utils.randInt;

/**
 * Created by Bram on 1-4-2016.
 */
public class Cube extends WorldObject {

    /**
     * Constant reflecting the length of a cube side.
     */
    public static final double CUBE_SIDE_LENGTH = 1;
    /**
     * Listener reflecting the terrain change.
     */
    private BiConsumer<Terrain, Cube> terrainChangeListener;
    /**
     * Constant reflecting the collapsing time a cube side.
     */
    private double collapseTime = -1;
    /**
     * 
     * @param world
     * @param position
     * @param terrainChangeListener
     */
    public Cube(World world, Vector position, BiConsumer<Terrain, Cube> terrainChangeListener){
        this(world, position, null, terrainChangeListener);// Null will result in the default Terrain type -> Terrain.AIR
    }

    public void collapse() throws IllegalStateException{
        if(this.getTerrain().isPassable())
            throw new IllegalStateException("A passable cube cannot be collapsed.");
        this.collapseTime = 4d;
    }
    /**
     * Check whether this cube is collapsing.
     * @return True if and only if the collapsing tie is positive.
     * |result == (this.collapseTime>=0d)
     */
    public boolean isCollapsing(){
        return this.collapseTime>=0d;
    }

    @Override
    public void advanceTime(double dt) {
        if(this.collapseTime>0d){
            this.collapseTime-=dt;
            if(this.collapseTime<=0d){
                this.collapseTime = -1;

                this.setTerrain(Terrain.AIR);
            }
        }
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
     * @throws NullPointerException if the given world or position are not effective.
     * |world == null || position==null
     * @throws IllegalArgumentException * The given position is not a valid position for any
     * WorldObject.
     * | ! isValidPosition(getPosition())
     */
    public Cube(World world, Vector position, Terrain terrain, BiConsumer<Terrain, Cube> terrainChangeListener){
        super(world, position);
        if (! isValidTerrain(terrain))
            terrain = Terrain.AIR;
        this.terrainChangeListener = terrainChangeListener;
        setTerrain(terrain);
    }

    @Override
    protected boolean validatePosition(Vector position){
        return this.getWorld().getCube(position)==null || this.getWorld().getCube(position)==this;
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
        Terrain oldTerrain = this.terrain;
        this.terrain = terrain;
        if(!this.isPassable() && this.getNbOwnedMaterials()>0){
            for(Material material : this.ownedMaterials)
                material.terminate();
            this.ownedMaterials.clear();
        }
        if(oldTerrain!=null && !oldTerrain.isPassable() && terrain.isPassable()){// Cube collapsed
            if(randInt(0, 99) < 25){
                if(oldTerrain == Terrain.ROCK)
                    new Boulder((World)this.getWorld(), this);
                if(oldTerrain == Terrain.WOOD)
                    new Log((World)this.getWorld(), this);
            }
        }
        this.terrainChangeListener.accept(oldTerrain, this);
    }
    /**
     * Variable registering the terrain of this Cube.
     */
    private Terrain terrain;
	/**
	 * Check whether this cube is passable.
	 * @return True if and only if the terrain of this cube is passable.
	 *       | result == this.getTerrain().isPassable()
	 */
    public boolean isPassable(){
        return this.getTerrain().isPassable();
    }

	/**
	 * Check whether this cube contains a material.
	 * @return True if and only if this cube is passable and this cube have more then 0 materials.
	 *       | result == this.isPassable() && this.getNbOwnedMaterials()>0
	 */
    public boolean containsMaterials(){
        return this.isPassable() && this.getNbOwnedMaterials()>0;
    }
	/**
	 * Check whether this cube contains a log.
	 * @return True if and only if this cube contains materials of type log.
	 *       | result == containsMaterialType(Log.class)
	 */
    public boolean containsLogs(){
        return containsMaterialType(Log.class);
    }
	/**
	 * Check whether this cube contains a boulder.
	 * @return True if and only if this cube contains materials of type boulder.
	 *       | result == containsMaterialType(Boulder.class)
	 */
    public boolean containsBoulders(){
        return containsMaterialType(Boulder.class);
    }
	/**
	 * Check whether this cube contains a the given type of material.
	 * @return False if this cube contains no materials.
	 *       | if (!this.containsMaterials()) 
	 *       |	result == false
	 */
    public boolean containsMaterialType(Class<? extends Material> material){
        if(this.containsMaterials()){
            for(Material m : this.ownedMaterials)
                if(material.isInstance(m))
                    return true;
        }
        return false;
    }
    /**
     * Method to get a log from this cube
     * @return  A log.
     * 		| getMaterialOfType(Log.class)
     */
    public Log getLog(){
        return getMaterialOfType(Log.class);
    }
    /**
    * Method to get a boulder from this cube
    * @return  A Boulder.
    * 		| getMaterialOfType(Boulder.class)
    */
    public Boulder getBoulder(){
        return getMaterialOfType(Boulder.class);
    }
    /**
    * Method to get a T from this cube
    * @param material The material.
    * @return  It will return that given material.
    * 		| result == material.class
    * @throws IllegalArgumentException When this cube doesn't contain a material of given type.
    * 		|(!this.containsMaterialType(material))
    */
    public <T extends Material> T getMaterialOfType(Class<T> material){
        if(!this.containsMaterialType(material))
            throw new IllegalArgumentException("This cube doesn't contain a material of given type.");
        for(Material m : this.ownedMaterials)
            if(material.isInstance(m))
                return (T)m;
        assert false;
        return null;// Will never happen
    }

    @Override
    public int getMaxNbOwnedMaterials() {
        return -1;
    }

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
