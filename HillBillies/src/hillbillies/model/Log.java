package hillbillies.model;

/**
 * Created by Bram on 2-4-2016.
 */
public class Log extends Material {

    /**
     * Initialize this new Log in the given world with given owner.
     *
     * @param world The world this new Log belongs to.
     * @param owner The owner of this new Log.
     * @effect This new Log is initialized as a new Material with
     *         given owner in the given world.
     *       | super(world, owner)
     */
    public Log(World world, WorldObject owner){
        super(world, owner);
    }
}
