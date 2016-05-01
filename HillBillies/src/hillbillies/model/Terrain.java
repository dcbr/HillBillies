package hillbillies.model;

/**
 * Class representing a Terrain type
 * @author Kenneth & Bram
 * @version 1.0
 */
public enum Terrain {
    /**
     * All possible terrain types are initialized here with given id and
     * given passable value.
     */
    AIR(0,true),
    ROCK(1,false),
    WOOD(2,false),
    WORKSHOP(3,true);

    /**
     * The id of the terrain type
     */
    private int id;
    /**
     * Boolean value indicating whether this terrain type is passable or not
     */
    private boolean passable;

    /**
     * Initialize a new terrain type with given id and passable state.
     * @param id The id of this new type
     * @param passable The passable value of this new type
     * @post The id of this new type is set to the given id
     *          | new.getId() == id
     * @post The new type's passable value is set to the given passable value
     *          | new.isPassable() == passable
     */
    Terrain(int id, boolean passable){
        this.id = id;
        this.passable = passable;
    }

    /**
     * @return A boolean value indicating whether this terrain type is passable or not
     */
    public boolean isPassable(){
        return this.passable;
    }

    /**
     * @return The id of this terrain type
     */
    public int getId(){
        return this.id;
    }

    /**
     * Static method to receive the terrain type, given its id.
     * @param id The id of the terrain type to retrieve
     * @return The corresponding terrain type
     */
    public static Terrain fromId(int id){
        return Terrain.values()[id];
    }
}
