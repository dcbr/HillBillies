package hillbillies.activities;

import hillbillies.model.Unit;

import static hillbillies.utils.Utils.*;

/**
 * Created by Bram on 29-3-2016.
 */
public class Rest extends Activity {

    /**
     * Constant reflecting the interval when a unit has to rest.
     */
    public static final double REST_INTERVAL = 3*60;// Unit will rest every REST_INTERVAL seconds
    /**
     * Constant reflecting the interval wherein a resting unit recovers getRestHitpointsGain() hitpoints.
     */
    public static final double REST_HITPOINTS_GAIN_INTERVAL = 0.2d;
    /**
     * Constant reflecting the interval wherein a resting unit recovers getRestStaminaGain() stamina.
     */
    public static final double REST_STAMINA_GAIN_INTERVAL = 0.2d;

    /**
     * Variable registering the hitpoints a unit recovered during the current rest period.
     */
    private double restHitpoints = 0d;
    /**
     * Variable registering the stamina points a unit recovered during the current rest period.
     */
    private double restStamina = 0d;

    public Rest(Unit unit){
        super(unit);
    }

    @Override
    public void startActivity() {
        this.restHitpoints = 0d;
        this.restStamina = 0d;
    }

    @Override
    public void stopActivity() {
        this.restHitpoints = 0d;
        this.restStamina = 0d;
    }

    @Override
    public void interruptActivity() {

    }

    @Override
    public void advanceActivity(double dt) {
        int maxHp = Unit.getMaxHitpoints(unit.getWeight(), unit.getToughness());
        int maxSt = Unit.getMaxStamina(unit.getWeight(), unit.getToughness());
        double extraTime = -1d;
        if(maxHp == unit.getHitpoints() && maxSt==unit.getStamina())
            this.requestFinish();
        if(unit.getHitpoints()<maxHp){
            double extraRestHitpoints = getIntervalTicks(activityProgress, dt, REST_HITPOINTS_GAIN_INTERVAL)*this.getRestHitpointsGain();
            int extraHitpoints = getIntervalTicks(restHitpoints, extraRestHitpoints, 1d);
            int newHitpoints = unit.getHitpoints() + extraHitpoints;
            double newRestHitpoints = restHitpoints + extraRestHitpoints;
            if(newHitpoints>=maxHp) {
                newHitpoints = maxHp;
                double neededExtraRestHitpoints = maxHp - unit.getHitpoints() - restHitpoints % 1;
                int neededTicks = (int)Math.ceil(neededExtraRestHitpoints/this.getRestHitpointsGain());
                double neededTime = REST_HITPOINTS_GAIN_INTERVAL*neededTicks - activityProgress % REST_HITPOINTS_GAIN_INTERVAL;
                extraTime = dt - neededTime;
                assert extraTime >= 0;
            }
            unit.setHitpoints(newHitpoints);
            restHitpoints = newRestHitpoints;
        }
        if((unit.getHitpoints()==maxHp && extraTime != 0d) && unit.getStamina()<maxSt){
            if(extraTime > 0d)
                dt = extraTime;
            double extraRestStamina = getIntervalTicks(activityProgress, dt, REST_STAMINA_GAIN_INTERVAL)*this.getRestStaminaGain();
            int extraStamina = getIntervalTicks(restStamina, extraRestStamina, 1d);
            int newStamina = unit.getStamina() + extraStamina;
            double newRestStamina = restStamina + extraRestStamina;
            if(newStamina>=maxSt){
                newStamina = maxSt;
                newRestStamina = 0;
                restHitpoints = 0;
                this.requestFinish();
            }
            unit.setStamina(newStamina);
            restStamina = newRestStamina;
        }
    }

    /**
     * Return a boolean indicating whether or not this unit
     * is able to rest. (When not in default mode!)
     */
    @Override
    public boolean isAbleTo() {
        return !unit.isAttacking();
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
        return 0;
    }

    @Override
    public String toString() {
        return "rest";
    }

    public boolean isInitialRestMode(){
        return this.isActive() && (this.restHitpoints + this.restStamina < 1d);
    }

    private double getRestHitpointsGain(){
        return unit.getToughness()/200d;
    }
    private double getRestStaminaGain(){
        return unit.getToughness()/100d;
    }
}
