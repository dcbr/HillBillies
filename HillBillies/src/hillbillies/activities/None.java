package hillbillies.activities;

import hillbillies.model.Cube;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import java.util.ArrayList;
import java.util.List;

import static hillbillies.utils.Utils.randDouble;
import static hillbillies.utils.Utils.randInt;

/**
 * Created by Bram on 29-3-2016.
 */
public class None extends Activity {

    public None(Unit unit){
        super(unit);
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
    protected void advanceActivity(double dt) {
        if(this.isDefault())
            setDefaultBehaviour();
    }

    /**
     * Activity specific code to check whether this Activity can be started.
     *
     * @return True if this Activity can be started as the nextActivity of the currently active Activity.
     */
    @Override
    public boolean isAbleTo() {
        return !unit.isInitialRestMode() && !unit.isAttacking() && !unit.isWorking();
    }

    /**
     * Activity specific code to check whether this Activity can be stopped by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity stops.
     * @return True if this Activity should stop for nextActivity.
     */
    @Override
    protected boolean shouldStopFor(Activity nextActivity) {
        return true;// None can stop for any Activity
    }

    /**
     * Activity specific code to check whether this Activity can be interrupted by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if this Activity should be interrupted for nextActivity.
     */
    @Override
    protected boolean shouldInterruptFor(Activity nextActivity) {// TODO: if this is set to true, None will always be the lowest Activity on activityStack?
        return true;// None can be interrupted for any Activity
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    @Override
    public int getXp() {
        return 0;
    }

    @Override
    public String toString() {
        return "none";
    }

    /**
     * Set current activity of this Unit to a random activity.
     * @post The new current activity of this new Unit is equal to
     * a random activity.
     * | new.getCurrentActivity()
     * @post The new state of the default behaviour is equal to startDoingDefault()
     * | new.isDoingDefault() == startDoingDefault()
     * @throws IllegalStateException * The default behaviour is not activated for this unit.
     * |   !this.isDefault()
     */
    private void setDefaultBehaviour() throws IllegalStateException{
        if(!this.isDefault())
            throw new IllegalStateException("The default behaviour of unit is not activated");

        List<Cube> AdjCubes = new ArrayList<Cube>(unit.getWorld().getDirectlyAdjacentCubes(unit.getPosition().getCubeCoordinates()));
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < AdjCubes.size(); i++){
            units.addAll(unit.getWorld().getUnitsInCube(AdjCubes.get(i)));
        }
        units.removeIf(defender -> unit.getFaction() == defender.getFaction());
        int nb = 2;
        if (units.size() > 0)
            nb +=1;
        int activity = randInt(0,nb);
        if (activity ==0){
            if (unit.getHitpoints() == Unit.getMaxHitpoints(unit.getWeight(), unit.getToughness()) && unit.getStamina() == Unit.getMaxStamina(unit.getWeight(), unit.getToughness()))
                activity = randInt(1,nb);
            else unit.rest();
        }
        if (activity ==1){
			/*PathCalculator p = new PathCalculator(this.getPosition());
			Vector target = new Vector(-1,-1,-1);
			this.path = p.computePath(this.getPosition());
			int size = controlledPos.size();
			if( size == 0)
				activity = 2;
			*/
            //TODO: manier zoeken om alle bereikbare posities op te lijsten
            /*controller*/unit.requestNewActivity(new TargetMove(unit));

            /*if (this.isAbleToSprint() && randInt(0, 99) < 1){
                unit.sprint();
            }*/ //TODO: is het erg als dit weg is?
        }
        if (activity == 2) {
            List<Vector> workPositions = unit.getWorld().getDirectlyAdjacentCubesPositions(unit.getPosition());
            workPositions.add(unit.getPosition());
            unit.work(workPositions.get(randInt(0,workPositions.size()-1)));
        }
        if (activity == 3){
            unit.attack(units.get(randInt(0,units.size()-1)));
        }
    }
}
