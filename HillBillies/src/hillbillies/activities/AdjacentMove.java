package hillbillies.activities;

import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

/**
 * Created by Bram on 17-4-2016.
 */
public class AdjacentMove extends Move {

    private final Vector nextPosition;

    public AdjacentMove(Unit unit, Vector direction) throws IllegalArgumentException{
        super(unit);
        Vector nextPosition = unit.getPosition().getCubeCenterCoordinates().add(direction);
        if (nextPosition==null || !isValidNextPosition(unit.getPosition(), nextPosition))
            throw new IllegalArgumentException("Invalid position to move to.");
        this.nextPosition = nextPosition;
    }

    public AdjacentMove(Unit unit, int dx, int dy, int dz){
        this(unit, new Vector(dx,dy,dz));
    }

    /**
     * Activity specific code which is called when the Activity is started.
     */
    @Override
    protected void startActivity() {

    }

    /**
     * Activity specific code which is called when the Activity is stopped.
     */
    @Override
    protected void stopActivity() {

    }

    /**
     * Activity specific code which is called when the Activity is interrupted.
     * This code is also called before stopActivity when the Activity is stopped.
     */
    @Override
    protected void interruptActivity() {

    }

    /**
     * Activity specific code which is called when advanceTime of this Activity is called.
     *
     * @param dt
     */
    @Override
    protected void advanceMove(double dt) {
        Vector cpos = unit.getPosition();
        if (nextPosition.equals(cpos)) {
            this.setSuccess();
            this.requestFinish();// TODO: maybe replace setSuccess by requestFinish(boolean success)
        } else {
            Vector difference = nextPosition.difference(cpos);
            double d = difference.length();
            double v = this.isSprinting() ? getSprintSpeed(difference) : getWalkingSpeed(difference);
            this.setCurrentSpeed(v);
            Vector dPos = difference.multiply(v / d * dt);
            Vector velocity = difference.multiply(v / d);
            Vector newPos = cpos.add(dPos);
            for (int i = 0; i < 3; i++) {
                if (getNextPosition().isInBetween(i, cpos, newPos)) {
                    double[] a = newPos.asArray();
                    a[i] = getNextPosition().get(i);
                    newPos = new Vector(a);
                }
            }
            unit.setPosition(newPos);
            unit.setOrientation((float) Math.atan2(velocity.Y(), velocity.X()));
        }
    }

    /**
     * Activity specific code to check whether this Activity can be started.
     *
     * @return True if this Activity can be started as the nextActivity of the currently active Activity.
     */
    @Override
    public boolean isAbleTo() {
        return false;
    }

    /**
     * Activity specific code to check whether this Activity can be stopped by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity stops.
     * @return True if this Activity should stop for nextActivity.
     */
    @Override
    protected boolean shouldStopFor(Activity nextActivity) {
        return false;
    }

    /**
     * Activity specific code to check whether this Activity can be interrupted by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if this Activity should be interrupted for nextActivity.
     */
    @Override
    protected boolean shouldInterruptFor(Activity nextActivity) {
        return false;
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    @Override
    public int getXp() {
        return Move.MOVE_XP;
    }
}
