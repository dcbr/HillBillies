package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;

import java.util.List;

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
    }
    /**
     * Variable registering the terrain of this Cube.
     */
    private Terrain terrain;

    public boolean isPassable(){
        return this.getTerrain().isPassable();
    }

    /** TO BE ADDED TO CLASS HEADING
     * @invar Each Cube can have its materials as materials.
     * | canHaveAsMaterials(this.getMaterials())
     */
    /**
     * Initialize this new Cube with given materials.
     *
     * @param materials
     * The materials for this new Cube.
     * @post The materials of this new Cube is equal to the given
     * materials.
     * | new.getMaterials() == materials
     * @throws IllegalArgumentException
     * This new Cube cannot have the given materials as its materials.
     * | ! canHaveAsMaterials(this.getMaterials())
     */
    public Cube(List<Material> materials) throws IllegalArgumentException {
        if (! canHaveAsMaterials(materials))
            throw new IllegalArgumentException();
        this.materials = materials;
    }
    /**
     * Return the materials of this Cube.
     */
    @Basic
    @Raw
    @Immutable
    public List<Material> getMaterials() {
        return this.materials;
    }
    /**
     * Check whether this Cube can have the given materials as its materials.
     *
     * @param materials
     * The materials to check.
     * @return
     * | result ==
     */
    @Raw
    public boolean canHaveAsMaterials(List<Material> materials) {
        return false;
    }
    /**
     * Variable registering the materials of this Cube.
     */
    private final List<Material> materials;
}
