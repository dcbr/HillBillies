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
     * @throws IllegalArgumentException When the owner is not a valid owner.
     * 	| ! isValidOwner(owner)
     * @throws IllegalStateException When the owner has reached its maximum number of owned Materials
     * 	| owner.getMaxNbOwnedMaterials()!=-1 && owner.getNbOwnedMaterials()>=getMaxNbOwnedMaterials()
     * @throws NullPointerException When the world is not an effective world 
     * 			or when the owner is not effective.
     * 	|world == null || owner == null
     */
    public Boulder(World world, WorldObject owner){
        super(world, owner);
    }
}
