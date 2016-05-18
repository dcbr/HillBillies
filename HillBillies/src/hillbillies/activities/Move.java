package hillbillies.activities;

import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import static hillbillies.utils.Utils.*;

import java.util.List;

/**
 * Minpunten:
 *  - Geen private setters in unit -> maybe save Unit properties in Objects instead of primitives (Integer instead of int)
 *      and give each activity a reference to these properties?
 */

/**
 * Class representing the Move Activity
 * @author Kenneth & Bram
 * @version 1.0
 */
public abstract class Move extends Activity {
    /**
     * Constant reflecting the stamina points a unit loses while sprinting.
     */
    public static final int SPRINT_STAMINA_LOSS = 1;// Stamina loss per interval when sprinting
    /**
     * Constant reflecting the interval a sprinting unit loses a SPRINT_STAMINA_LOSS.
     */
    public static final double SPRINT_STAMINA_LOSS_INTERVAL = 0.1d;// Unit will loose SPRINT_STAMINA_LOSS every SPRINT_STAMINA_LOSS_INTERVAL seconds when sprinting
    /**
     * Constant reflecting XP gaining after a move.
     */
    protected static final int MOVE_XP = 1;

    /**
     * Variable registering whether this unit is sprinting.
     */
    private boolean isSprinting = false;
    /**
     * Variable registering the current speed of this unit.
     */
    private double currentSpeed = 0.0;

    /**
     * Create a new Move Activity for the given Unit.
     * @param unit The unit which will perform the movement.
     * @effect Create a new Move Activity for the given Unit with sprinting disabled.
     *          | this(unit, false)
     */
    public Move(Unit unit){
        this(null, unit, false);
    }

    /**
     * Create a new Move Activity for the given Unit with sprinting set to the specified value.
     * @param unit The unit which will perform the movement.
     * @param sprinting Boolean indicating whether the unit will start sprinting or not.
     * @post The Unit is sprinting if it is able to do so
     *          | if(isAbleToSprint())
     *          |   new.isSprinting() == sprinting
     *          | else
     *          |   new.isSprinting() == false
     */
    public Move(Unit unit, boolean sprinting){
        this(null, unit, sprinting);
    }

    /**
     * Create a new Move Activity for the given Unit with sprinting set to the specified value.
     * And the given parentActivity as parent Activity.
     * @param parentActivity The parent of this Move Activity.
     * @param unit The unit which will perform the movement.
     * @param sprinting Boolean indicating whether the unit will start sprinting or not.
     * @post The Unit is sprinting if it is able to do so
     *          | if(isAbleToSprint())
     *          |   new.isSprinting() == sprinting
     *          | else
     *          |   new.isSprinting() == false
     */
    public Move(Activity parentActivity, Unit unit, boolean sprinting){
        super(parentActivity, unit);
        if(sprinting)
            this.sprint();
    }

    /**
     * Activity specific code which is called when the Activity is stopped.
     * @effect  The unit stops sprinting when this Activity is stopped.
     *          | stopSprint()
     */
    @Override
    protected void stopActivity(){
        this.stopSprint();
    }

    @Override
    public void advanceActivity(double dt) {
        if(this.isSprinting){
            int newStamina = unit.getStamina()-SPRINT_STAMINA_LOSS*getIntervalTicks(activityProgress, dt, SPRINT_STAMINA_LOSS_INTERVAL);
            if(newStamina<=0){
                newStamina = 0;
                stopSprint();
            }
            unit.setStamina(newStamina);
        }
        advanceMove(dt);
        if(this.isDefault() && !this.isSprinting && this.isAbleToSprint() && randInt(0, 99) < 1)
            this.sprint();
    }

    /**
     * Movement specific code which is called inside this Activity's advanceActivity method.
     * This should proceed the movement of the Unit.
     */
    protected abstract void advanceMove(double dt);

    /**
     * Return a boolean indicating whether or not this unit
     * is able to move. (When not in default mode!)
     */
    @Override
    public boolean isAbleTo() {
        return !unit.isInitialRestMode() && !unit.isAttacking() && !unit.isWorking();
    }

    /**
     * Movement will be interrupted if nextActivity is an instance of the Attack Activity.
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if nextActivity is an instance of Attack
     *          | nextActivity instanceof Attack
     */
    @Override
    protected boolean shouldInterruptFor(Activity nextActivity) {
        return false;// A general movement cannot be interrupted
    }

    @Override
    public String toString() {
        return "move";
    }

    /**
     * Method to let the Unit sprint.
     * @post the unit is sprinting.
     * 		| new.isSprinting() == true
     * @throws IllegalStateException * if the unit isn't able to sprint.
     * | !this.isAbleToSprint()
     */
    public void sprint() throws IllegalStateException{
        if(!this.isAbleToSprint())
            throw new IllegalStateException("The Unit is not able to sprint!");
        this.isSprinting = true;
    }
    /**
     * Method to let the Unit sprint.
     * @post the unit stop sprinting.
     * 		| new.isSprinting() == false
     */
    public void stopSprint(){
        this.isSprinting = false;
    }

    /**
     * Return a boolean indicating whether or not this unit
     * is able to sprint.
     */
    public boolean isAbleToSprint(){
        return /*this.isActive() &&*/ unit.getStamina()> Unit.MIN_STAMINA;
    }

    /**
     * Return a boolean indicating whether or not this unit is sprinting.
     */
    public boolean isSprinting(){
        return this.isActive() && this.isSprinting;
    }

    /**
     * Get the speed at which the unit is currently moving.
     */
    public double getCurrentSpeed(){
        return this.currentSpeed;
    }

    /**
     * Sets the speed at which the unit is currently moving.
     * @param speed The new movement speed
     * @post    The unit's currentSpeed is set to speed
     *          | new.getCurrentSpeed() == speed
     */
    protected void setCurrentSpeed(double speed){
        this.currentSpeed = speed;
    }

    /**
     * Retrieve the Unit's sprinting speed
     * @param direction The direction the Unit is sprinting in
     */
    protected double getSprintSpeed(Vector direction){
        return 2*this.getWalkingSpeed(direction);
    }

    /**
     * Retrieve the Unit's walking speed
     * @param direction The direction the Unit is walking in
     */
    protected double getWalkingSpeed(Vector direction){
        if(direction.Z()<-0.5) return 1.2*this.getBaseSpeed();
        else if(direction.Z()>0.5) return 0.5*this.getBaseSpeed();
        else return this.getBaseSpeed();
    }

    /**
     * Retrieve the Unit's base speed
     * @return
     */
    private double getBaseSpeed(){
        return 1.5*(unit.getStrength()+unit.getAgility())/(200*unit.getWeight()/100);
    }

    /**
     * Check whether nextPosition is a valid position to move to from fromPosition.
     * @param fromPosition The position at which the movement will start
     * @param nextPosition The position to move to
     * @return True if nextPosition is indeed a valid position to move to from fromPosition
     *          | if(!unit.isValidPosition(nextPosition))
     *          |       result==false
     *          | if(foreach Vector d in nextPosition.difference(fromPosition).decompose() :
     *          |       unit.isValidPosition(fromPosition.add(d)) && unit.isValidPosition(nextPosition.difference(d))
     *          |           result==true
     *          | else
     *          |       result==false
     * @throws IllegalArgumentException
     *          When fromPosition or nextPosition are not effective.
     *          | fromPosition==null || nextPosition==null
     */
    protected boolean isValidNextPosition(Vector fromPosition, Vector nextPosition) throws IllegalArgumentException{
        return isValidNextPosition(unit, fromPosition, nextPosition);
    }

    public static boolean isValidNextPosition(Unit unit, Vector fromPosition, Vector nextPosition){
        if(fromPosition==null || nextPosition==null)
            throw new IllegalArgumentException("The from and next position must be effective positions in order to check their validity.");
        if(!unit.isValidPosition(nextPosition)) return false;// Check if it's a valid position itself
        List<Vector> a = nextPosition.difference(fromPosition).decompose();
        for(Vector d : nextPosition.difference(fromPosition).decompose()){
            if(!unit.isValidPosition(fromPosition.add(d)) || !unit.isValidPosition(nextPosition.difference(d))) 
            	return false;// Check if surrounding positions are valid too (prevent corner glitch)
        }
        return true;
    }
}
