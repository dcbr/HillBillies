package hillbillies.activities;

import be.kuleuven.cs.som.annotate.Basic;
import hillbillies.model.Unit;

/**
 * Created by Bram on 29-3-2016.
 */
public class Work extends Activity {

    private float workDuration;

    public Work(Unit unit){
        super(unit);
    }

    /**
     * Return the time this unit shall be working.
     * @param	strength
     * 			The strength to check against.
     * @return 	The time a unit need to work is positive for all units.
     *       	| result >= 0
     */
    public static int getWorkingTime(int strength) {
        return (500/strength);// TODO: moet dit geen float zijn?
    }

    @Override
    public void startActivity() {
        this.workDuration = getWorkingTime(unit.getStrength());
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
        if (this.activityProgress >= this.workDuration)
            unit.activityController.requestActivityFinish(this);
    }

    /**
     * Return a boolean indicating whether or not this unit
     * is able to work. (When not in default mode!)
     */
    @Override
    public boolean isAbleTo() {
        return !unit.isInitialRestMode() && !unit.isAttacking();
    }

    @Override
    public boolean shouldInterrupt(Activity activity) {
        return activity instanceof Attack;
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
}
