package hillbillies.activities;

import hillbillies.model.Unit;
import hillbillies.utils.Vector;

/**
 * Created by Bram on 17-4-2016.
 */
public class Fall extends Move {

    /**
     * Variable registering the starting falling level of this unit.
     */
    private int fallingLevel = 0;

    public Fall(Unit unit){
        super(unit);
    }

    /**
     * Movement specific code which is called inside this Activity's advanceActivity method.
     * This should proceed the movement of the Unit.
     *
     * @param dt
     */
    @Override
    protected void advanceMove(double dt) {
        Vector cPos = unit.getPosition();
        Vector cPosCube = cPos.getCubeCenterCoordinates();
        if (cPos.equals(cPosCube) && unit.isLowerSolid(cPos) && unit.getWorld().getCube(cPos.getCubeCoordinates()).isPassable()) {
            setCurrentSpeed(0);
            unit.removeHitpoints(10*(int)(fallingLevel - cPos.cubeZ()));
            //setHitpoints((int)(getHitpoints()-(fallingLevel-cPos.Z())));
            fallingLevel = 0;
            this.requestFinish();
        } else {
            double speed = this.getCurrentSpeed();
            Vector nextPos = cPos.add(new Vector(0, 0, -speed * dt));
            if (unit.isLowerSolid(cPos) && unit.getWorld().getCube(cPos.getCubeCoordinates()).isPassable() && (cPosCube.isInBetween(2, cPos, nextPos) || cPos.Z() <= cPosCube.Z()))
                unit.setPosition(cPosCube);
            else if (nextPos.getCubeCenterCoordinates().isInBetween(2, cPos, nextPos) && unit.isLowerSolid(nextPos) && unit.getWorld().getCube(nextPos.getCubeCoordinates()).isPassable())
                unit.setPosition(nextPos.getCubeCenterCoordinates());
            else
                unit.setPosition(nextPos);
        }
    }

    /**
     * Activity specific code which is called when the Activity is started.
     * Method to let this unit fall.
     * @post	THe units stops its default behaviour when he was doing it.
     * 			|new.isDoingBehaviour
     * @effect	The unit's speed is changed
     * 			|new.getCurrentSpeed()
     * @effect	The units position is set to the center of the cube.
     * 			|new.getPosition()
     * @effect	The units current activity is changed to FALLING
     * 			|new.getCurrentActivity()
     * @effect	THe falling level is set to the Units Z Cube coordinate.
     * 			|this.fallingLevel = getPosition().cubeZ()
     */
    @Override
    protected void startActivity() {
        this.setCurrentSpeed(3d);
    	Vector pos = unit.getPosition();
        unit.setPosition(new Vector(pos.getCubeCenterCoordinates().X(),pos.getCubeCenterCoordinates().Y(), pos.Z() ));
        this.fallingLevel = pos.cubeZ();

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
        // This Activity cannot be interrupted
        assert false;
    }

    @Override
    public boolean isAbleTo(){
        return true;// Unit can fall at any moment, no matter what he's doing
    }

    @Override
    protected boolean shouldInterruptFor(Activity nextActivity){
        return false;// If a unit is falling, he can't do anything else
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    @Override
    public int getXp() {
        return 0;
    }
}
