package hillbillies.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Cube;
import hillbillies.model.Terrain;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

/**
 * Created by Bram on 29-3-2016.
 */
public class Work extends Activity {

    /**
     * Constant reflecting XP gaining after a work.
     */
    private static final int WORK_XP = 10;

    private float workDuration;
    /**
     * Variable registering the workCube.
     */
    private final Cube workCube;

    public Work(Unit unit, Vector workPosition) throws IllegalArgumentException{
        super(unit);
        Cube workCube = unit.getWorld().getCube(workPosition.getCubeCoordinates());
        if (!isValidWorkCube(workCube))
            throw new IllegalArgumentException("The given workPosition is not a valid position to work on.");
        this.workCube = workCube;
    }

    /**
     * Return the time this unit shall be working.
     * @param	strength
     * 			The strength to check against.
     * @return 	The time a unit need to work is positive for all units.
     *       	| result >= 0
     */
    public static int getWorkingTime(int strength) {
        return (500/strength);
    }

    @Override
    public void startActivity() {
        this.workDuration = getWorkingTime(unit.getStrength());
        Vector workDirection = workCube.getPosition().getCubeCoordinates().difference(unit.getPosition().getCubeCoordinates());
        if(workDirection.X()!=0 || workDirection.Y()!=0)
            unit.setOrientation((float) Math.atan2(workDirection.Y(),workDirection.X()));
    }

    @Override
    public void stopActivity() {
        this.workDuration = 0f;
    }

    @Override
    public void interruptActivity() {

    }

    @Override
    public void advanceActivity(double dt) {
        if (activityProgress >= this.getWorkDuration()) {
            if(unit.isCarryingMaterial()){
                if(workCube.isPassable()){
                    unit.dropCarriedMaterial(this.workCube);
                }
            }else if(workCube.getTerrain()== Terrain.WORKSHOP && workCube.containsLogs() && workCube.containsBoulders()){
                workCube.getBoulder().terminate();
                workCube.getLog().terminate();
                if(unit.getWeight()!=Unit.MAX_WEIGHT)
                    unit.setWeight(unit.getWeight() + 1);
                if(unit.getToughness()!=Unit.MAX_TOUGHNESS)
                    unit.setToughness(unit.getToughness() + 1);
            }else if(workCube.containsBoulders()){
                unit.setCarriedMaterial(workCube.getBoulder());
            }else if(workCube.containsLogs()){
                unit.setCarriedMaterial(workCube.getLog());
            }else if(workCube.getTerrain() == Terrain.WOOD){
                workCube.setTerrain(Terrain.AIR);
            }else if(workCube.getTerrain() == Terrain.ROCK){
                workCube.setTerrain(Terrain.AIR);
            }
            this.requestFinish(true);
        }
    }

    /**
     * Return a boolean indicating whether or not this unit
     * is able to work. (When not in default mode!)
     */
    @Override
    public boolean isAbleTo() {
        return !unit.isInitialRestMode() && !unit.isAttacking() && this.isAccessible(workCube.getPosition());
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
        return WORK_XP;
    }

    @Override
    public String toString() {
        return "work";
    }

    /**
     * Return the work duration of this unit.
     */
    public float getWorkDuration(){
        return this.workDuration;
    }

    /**
     * Check whether the given workCube is a valid workCube for
     * this Unit.
     *
     * @param workCube
     * The workCube to check.
     * @return
     * | result == this.getPosition().getCubeCoordinates().equals(workCube.getPosition()) ||
     *				this.getWorld().getNeighbouringCubesPositions(this.getPosition().getCubeCoordinates()).contains(workCube.getPosition())
     */
    public boolean isValidWorkCube(Cube workCube) {
        return workCube.getWorld()==unit.getWorld() &&
                (unit.getPosition().getCubeCoordinates().equals(workCube.getPosition()) ||
                        unit.getWorld().getNeighbouringCubesPositions(unit.getPosition().getCubeCoordinates()).contains(workCube.getPosition()));
    }
    /**
     * Check whether a position is an accessible position to work on to from units position.
     * @param unit The working unit.
     * @param position The position to work on.
     * @return True if position is indeed a accessible position to work on from the units position
     *          | if(foreach Vector d in nextPosition.difference(fromPosition).decompose() :
     *          |       !unit.getWorld().isCubePassable(unit.getPosition().add(d)) || !unit.getWorld.isCubePassable(position.difference(d))
     *          |			where unit.getPosition().add(d) != position && unit.getPosition() != position && d!=Vector(0,0,0)
     *          |           	result==false
     *          | else
     *          |       result==true
     * @throws IllegalArgumentException
     *          When units position or otherPosition are not effective.
     *          | unit.getPosition()==null || nextPosition==null
     */
    protected static boolean isAccessible(Unit unit, Vector position) throws IllegalArgumentException{
        if(unit==null || position==null)
            throw new IllegalArgumentException("The other position must be an effective position in order to check his validity.");
    	Vector unitPosition = unit.getPosition().getCubeCoordinates();
    	position = position.getCubeCoordinates();

        for(Vector d : position.difference(unitPosition).decompose()){
        	if(!d.equals(new Vector(0,0,0))){
        		Vector pos1 = unitPosition.add(d);
        		Vector pos2 = position.difference(d);
        		if(!(pos1.equals(position) || unit.getWorld().isCubePassable(pos1)) ||!(pos2.equals(unitPosition) || unit.getWorld().isCubePassable(pos2)))
        			return false;// Check if surrounding positions are valid too (prevent corner glitch)
        	}
        }	
        return true;
    }
    private boolean isAccessible(Vector position) throws IllegalArgumentException{
    	return isAccessible(unit, position);
    }
}
