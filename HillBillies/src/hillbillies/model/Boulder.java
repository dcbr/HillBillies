package hillbillies.model;

/**
 * Class representing a Boulder
 * @author Kenneth & Bram
 * @version 1.0
 */
public class Boulder extends Material {

    /**
     * Initialize this new Boulder in the given world with given owner.
     *
     * @param world The world this new Boulder belongs to.
     * @param owner The owner of this new Boulder.
     * @effect This new Boulder is initialized as a new Material with
     *         given owner in the given world.
     *       | super(world, owner)
     */
    public Boulder(World world, WorldObject owner){
        super(world, owner);
    }
}
