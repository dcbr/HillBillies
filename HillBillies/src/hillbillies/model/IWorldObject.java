package hillbillies.model;

import hillbillies.utils.Vector;

/**
 * Created by Bram on 3-4-2016.
 */
public interface IWorldObject {

    public void advanceTime(double dt);
    public IWorld getWorld();
    public Vector getPosition();
    public void terminate();
    public boolean isTerminated();

}
