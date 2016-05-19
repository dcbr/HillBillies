package hillbillies.activities;

import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

/**
 * Class for the Adjacent Move Activity of a Unit.
 * @author Kenneth & Bram
 * @version 1.0
 */
public class AdjacentMove extends Move {

    /**
     * Variable registering the unit's next position, this is the position the unit is moving to.
     */
    private final Vector nextPosition;
    /**
     * Variable registering this AdjacentMove's parentActivity, if any.
     */
    private TargetMove targetMove = null;

    /**
     * Initialize a new AdjacentMove for the given unit in the given direction.
     * @param unit The unit to move
     * @param direction The direction in which the unit should move. The magnitude
     *                  of each coordinate of this direction must be 1. And the
     *                  unit must be able to reach the destination.
     * @effect Initialize a new AdjacentMove without a parentActivity and with
     *          sprinting set to false.
     *          | this(unit, direction, false, null)
     * @throws NullPointerException
     *          When the given direction is not effective.
     *          | direction == null
     * @throws IllegalArgumentException
     *          When the destination (this is the vector sum of the unit's current
     *          position and the given direction)
     *          | destination = unit.getPosition().getCubeCenterCoordinates().add(direction)
     *          is not reachable.
     *          | !isValidNextPosition(unit.getPosition(), destination)
     */
    public AdjacentMove(Unit unit, Vector direction) throws NullPointerException, IllegalArgumentException{
        this(unit, direction, false, null);
    }

    /**
     * Initialize a new AdjacentMove for the given unit in the given direction.
     * The unit will start sprinting if sprinting is set to true.
     * If extendedMovement is not null, this AdjacentMove's parentActivity is set
     * to the given extendedMovement and this AdjacentMove is a part of an
     * extended movement.
     * @param unit The unit to move
     * @param direction The direction in which the unit should move. The magnitude
     *                  of each coordinate of this direction must be 1. And the
     *                  unit must be able to reach the destination.
     * @param sprinting Boolean value indicating whether the unit should sprint
     *                  while performing this AdjacentMove
     * @param extendedMovement ParentActivity of this AdjacentMove. This is the
     *                         TargetMove who invoked this AdjacentMove.
     * @post A new Activity for the given unit is created. Once start() is called
     *          the unit will start moving in the given direction.
     * @throws NullPointerException
     *          When the given direction is not effective.
     *          | direction == null
     * @throws IllegalArgumentException
     *          When the destination (this is the vector sum of the unit's current
     *          position and the given direction)
     *          | destination = unit.getPosition().getCubeCenterCoordinates().add(direction)
     *          is not reachable.
     *          | !isValidNextPosition(unit.getPosition(), destination)
     */
    public AdjacentMove(Unit unit, Vector direction, boolean sprinting, TargetMove extendedMovement)
            throws IllegalArgumentException, NullPointerException{
        super(extendedMovement, unit, sprinting);
        Vector nextPosition = unit.getPosition().getCubeCenterCoordinates().add(direction);
        if (!isValidNextPosition(unit.getPosition(), nextPosition))
            throw new IllegalArgumentException("Invalid position to move to.");
        this.nextPosition = nextPosition;
        this.targetMove = extendedMovement;
    }

    /**
     * Activity specific code which is called when the Activity is started.
     */
    @Override
    protected void startActivity() {

    }

    /**
     * Activity specific code which is called when the Activity is stopped.
     * @post The unit will stop sprinting if this AdjacentMove isn't a part
     *          of an extended movement.
     */
    @Override
    protected void stopActivity() {
        // Only stop sprinting when this is an individual adjacentMove, otherwise stop sprinting will be handled by
        // TargetMove.
        if(this.targetMove==null)
            super.stopActivity();
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
            this.requestFinish(true);
        } else {
            Vector difference = nextPosition.difference(cpos);
            double d = difference.length();
            double v = this.isSprinting() ? getSprintSpeed(difference) : getWalkingSpeed(difference);
            this.setCurrentSpeed(v);
            Vector dPos = difference.multiply(v / d * dt);
            Vector velocity = difference.multiply(v / d);
            Vector newPos = cpos.add(dPos);
            for (int i = 0; i < 3; i++) {
                if (nextPosition.isInBetween(i, cpos, newPos)) {
                    double[] a = newPos.asArray();
                    a[i] = nextPosition.get(i);
                    newPos = new Vector(a);
                }
            }
            unit.setPosition(newPos);
            unit.setOrientation((float) Math.atan2(velocity.Y(), velocity.X()));
        }
    }

    /**
     * Return a boolean indicating whether or not this unit
     * is able to perform an adjacent movement. (When not in default mode!)
     */
    @Override
    public boolean isAbleTo() {
        return super.isAbleTo() || unit.isExecuting(Move.class);// New movement to perform => stop Activity and perform new one
    }

    /**
     * Activity specific code to check whether this Activity can be interrupted by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if this Activity should be interrupted for nextActivity.
     */
    @Override
    protected boolean shouldInterruptFor(Activity nextActivity) {
        return super.shouldInterruptFor(nextActivity);
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    @Override
    public int getXp() {
        return Move.MOVE_XP;
    }

    @Override
    public void sprint(){
        super.sprint();
        if(targetMove!=null) targetMove.sprint();// Let previous activity in stack (extended movement) know we are sprinting
    }

    @Override
    public void stopSprint(){
        super.stopSprint();
        if(targetMove!=null) targetMove.stopSprint();// Let previous activity in stack (extended movement) know we stopped sprinting
    }
    /*public Vector getNextPosition(){
    	return this.nextPosition.clone();
    }*/
}
