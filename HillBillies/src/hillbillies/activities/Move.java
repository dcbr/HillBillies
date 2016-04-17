package hillbillies.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import static hillbillies.utils.Utils.*;

/**
 * Minpunten:
 *  - Geen private setters in unit
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
     * Variable registering the next position and target position of this unit.
     */
    private Vector nextPosition, targetPosition;
    /**
     * Variable registering whether this unit is sprinting.
     */
    private boolean isSprinting = false;
    /**
     * Variable registering the current speed of this unit.
     */
    private double currentSpeed = 0.0;

    public Move(Unit unit){
        super(unit);
    }

    @Override
    public void interruptActivity() {
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

    protected abstract void advanceMove(double dt);

    /**
     * Return a boolean indicating whether or not this unit
     * is able to move. (When not in default mode!)
     */
    @Override
    public boolean isAbleTo() {
        return !unit.isInitialRestMode() && !unit.isAttacking() && !unit.isWorking();
    }

    @Override
    public boolean shouldInterruptFor(Activity nextActivity) {
        return nextActivity instanceof Attack;
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
     * Return a boolean indicating whether or not this person
     * is able to sprint.
     */
    public boolean isAbleToSprint(){
        return this.isActive() && unit.getStamina()> Unit.MIN_STAMINA;
    }

    /**
     * Return the next position of this unit.
     */
    @Basic
    @Raw
    public Vector getNextPosition(){
        return this.nextPosition.clone();
    }

    /**
     * Set the next position of this Unit to the given position.
     *
     * @param position
     * The next position for this Unit.
     * @post The next position of this new Unit is equal to
     * the given position.
     * | new.getNextPosition() == position
     * @throws IllegalArgumentException * The given position is not a valid position for any
     * Unit.
     * | ! isValidPosition(getPosition())
     */
    @Raw
    public void setNextPosition(Vector position) throws IllegalArgumentException {
        if (position!=null && ! unit.isValidPosition(position))
            throw new IllegalArgumentException("Invalid position");
        this.nextPosition = position;
    }

    public boolean isSprinting(){
        return this.isActive() && this.isSprinting;
    }

    public double getCurrentSpeed(){
        return this.currentSpeed;
    }

    protected void setCurrentSpeed(double speed){
        this.currentSpeed = speed;
    }

    /**
     * Retrieve the Unit's sprinting speed
     * @param direction The direction the Unit is sprinting in, this is a vector with norm 1
     * @return
     */
    protected double getSprintSpeed(Vector direction){
        return 2*this.getWalkingSpeed(direction);
    }

    /**
     * Retrieve the Unit's walking speed
     * @param direction The direction the Unit is walking in, this is a vector with norm 1
     * @return
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

    protected boolean isValidNextPosition(Vector fromPosition, Vector nextPosition){
        if(fromPosition==null || nextPosition==null)
            throw new IllegalArgumentException("The from and next position must be effective positions in order to check their validity.");
        if(!unit.isValidPosition(nextPosition)) return false;// Check if it's a valid position itself
        for(Vector d : nextPosition.difference(fromPosition).decompose()){
            if(!unit.isValidPosition(fromPosition.add(d)) || !unit.isValidPosition(nextPosition.difference(d))) return false;// Check if surrounding positions are valid too (prevent corner glitch)
        }
        return true;
    }
}
