package hillbillies.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import static hillbillies.utils.Utils.randInt;

/**
 * Minpunten:
 *  - Geen private setters in unit
 */

/**
 * Class representing the Move Activity
 * @author Kenneth & Bram
 * @version 1.0
 */
public class Move extends Activity {

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
    public void startActivity() {

    }

    @Override
    public void stopActivity() {
        this.interruptActivity();
        this.nextPosition = null;
        this.targetPosition = null;
    }

    @Override
    public void interruptActivity() {
        this.stopSprint();
    }

    @Override
    public void advanceActivity(double dt) {
        if(this.isSprinting){
            int newStamina = unit.getStamina()-Unit.SPRINT_STAMINA_LOSS*Unit.getIntervalTicks(activityProgress, dt, Unit.SPRINT_STAMINA_LOSS_INTERVAL);// TODO: move constants to this class
            if(newStamina<=0){
                newStamina = 0;
                stopSprint();
            }
            unit.setStamina(newStamina);
        }
        Vector cpos = unit.getPosition();
        if(targetPosition!=null){
            int dx = 0, dy = 0, dz = 0;
            if (targetPosition.cubeX() - cpos.cubeX() < 0)
                dx = -1;
            else if(targetPosition.cubeX() - cpos.cubeX() > 0)
                dx = 1;
            if (targetPosition.cubeY() - cpos.cubeY() < 0)
                dy = -1;
            else if(targetPosition.cubeY() - cpos.cubeY() > 0)
                dy = 1;
            if (targetPosition.cubeZ() - cpos.cubeZ() < 0)
                dz = -1;
            else if(targetPosition.cubeZ() - cpos.cubeZ() > 0)
                dz = 1;
            //this.stateDefault +=1; ??
            moveToAdjacent(new Vector(dx, dy, dz));
        }
        if(getNextPosition()!=null){
            if(getNextPosition().equals(cpos)){
                setNextPosition(null);
                unit.setCurrentActivity(new None(unit));
            }
            else{
                Vector difference = getNextPosition().difference(cpos);
                double d = difference.length();
                currentSpeed = this.isSprinting ? unit.getSprintSpeed(difference) : unit.getWalkingSpeed(difference);// Move getspeed methods to this class
                Vector dPos = difference.multiply(currentSpeed/d*dt);
                Vector velocity = difference.multiply(currentSpeed/d);
                Vector newPos = cpos.add(dPos);
                for(int i=0;i< 3;i++){
                    if(getNextPosition().isInBetween(i,cpos,newPos)){
                        double[] a = newPos.asArray();
                        a[i] = getNextPosition().get(i);
                        newPos = new Vector(a);
                    }
                }
                unit.setPosition(newPos);
                unit.setOrientation((float)Math.atan2(velocity.Y(),velocity.X()));
            }
        }

        if(this.isDefault() && !this.isSprinting && this.isAbleToSprint() && randInt(0, 99) < 1)
            this.sprint();
        super.advanceTime(dt);
    }

    /**
     * Return a boolean indicating whether or not this unit
     * is able to move. (When not in default mode!)
     */
    @Override
    public boolean isAbleTo() {
        return !unit.isInitialRestMode() && !unit.isAttacking() && !unit.isWorking();
    }

    @Override
    public String toString() {
        return "move";
    }

    /**
     * Method to let the Unit move to an adjacent cube.
     * @param direction The direction the Unit should move towards. Since this method can only be used to move to
     *                  neighbouring cubes, each element of the array must have a value of (-)1 or 0.
     * @post	This unit stops the default behaviour if it was doing this
     * 			| new.isDoingBehaviour
     * @throws IllegalStateException
     * 			When this Unit is not able to move at this moment
     * 			| this.isAbleToMove() == false
     * @throws IllegalArgumentException
     * 			When the given direction points to an invalid position
     * 			| isValidPosition(this.getPosition().getCubeCenterCoordinates().add(direction)) == false
     */
    public void moveToAdjacent(Vector direction) throws IllegalStateException, IllegalArgumentException{
        if(!this.isDefault()){
            if(!unit.isAbleToMove() )
                throw new IllegalStateException("Unit is not able to move at this moment.");
        }
        if(this.getStateDefault() ==2)
            this.stopDoingDefault();
        if(this.getStateDefault() >=1)
            this.stateDefault -=1;
        // setNextPosition throws the exception
        this.setNextPosition(unit.getPosition().getCubeCenterCoordinates().add(direction));
        setCurrentActivity(hillbillies.model.Activity.MOVE);
    }

    public void moveToAdjacent(int dx, int dy, int dz){
        this.targetPosition = null;
        this.moveToAdjacent(new Vector(dx,dy,dz));
    }
    /**
     * Method to let the Unit move to a target.
     * @param targetPosition
     * 			The direction the Unit should move towards.
     * @post	This unit stops the default behaviour if it was doing this
     * 			| new.isDoingBehaviour
     * @post	if this is the default behaviour, set the stateDefault to a new state.
     * 			| new.isDoingBehaviour
     * @throws IllegalStateException * If this unit isn't able to move.
     * 		|!this.isAbleToMove()
     */
    public void moveToTarget(Vector targetPosition) throws IllegalStateException{
        if(!this.isDefault()){
            if(!unit.isAbleToMove())
                throw new IllegalStateException("Unit is not able to move at this moment.");
        }
        if(this.getStateDefault() ==2)
            this.stopDoingDefault();
        if(this.getStateDefault() == 3)
            this.stateDefault -=1;

        setCurrentActivity(hillbillies.model.Activity.MOVE);
        this.targetPosition = targetPosition.getCubeCenterCoordinates();// TODO: make setter and getter for targetPosition and check for invalid positions
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
        if (position!=null && ! Unit.isValidPosition(position))
            throw new IllegalArgumentException("Invalid position");
        this.nextPosition = position;
    }

    public boolean isSprinting(){
        return this.isActive() && this.isSprinting;
    }

    public double getCurrentSpeed(){
        return this.currentSpeed;
    }

    /**
     * Retrieve the Unit's sprinting speed
     * @param direction The direction the Unit is sprinting in, this is a vector with norm 1
     * @return
     */
    private double getSprintSpeed(Vector direction){
        return 2*this.getWalkingSpeed(direction);
    }

    /**
     * Retrieve the Unit's walking speed
     * @param direction The direction the Unit is walking in, this is a vector with norm 1
     * @return
     */
    private double getWalkingSpeed(Vector direction){
        if(direction.Z()>0) return 1.2*this.getBaseSpeed();
        else if(direction.Z()<0) return 0.5*this.getBaseSpeed();
        else return this.getBaseSpeed();
    }

    /**
     * Retrieve the Unit's base speed
     * @return
     */
    private double getBaseSpeed(){
        return 1.5*(unit.getStrength()+unit.getAgility())/(200*unit.getWeight()/100);
    }
}
