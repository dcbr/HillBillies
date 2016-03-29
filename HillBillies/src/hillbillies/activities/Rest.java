package hillbillies.activities;

import hillbillies.model.Unit;

/**
 * Created by Bram on 29-3-2016.
 */
public class Rest extends Activity {

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
        unit.restTimer = 0d;// Reset restTimer, since we are already resting now
    }

    @Override
    public void stopActivity() {
        this.restHitpoints = 0d;
        this.restStamina = 0d;
        unit.restTimer = 0d;// Rest-timer will now always be reset as soon as resting stops
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
            unit.setCurrentActivity(new None(unit));
        if(unit.getHitpoints()<maxHp){
            double extraRestHitpoints = Unit.getIntervalTicks(activityProgress, dt, Unit.REST_HITPOINTS_GAIN_INTERVAL)*unit.getRestHitpointsGain();
            int extraHitpoints = Unit.getIntervalTicks(restHitpoints, extraRestHitpoints, 1d);
            int newHitpoints = unit.getHitpoints() + extraHitpoints;
            double newRestHitpoints = restHitpoints + extraRestHitpoints;
            if(newHitpoints>=maxHp) {
                newHitpoints = maxHp;
                double neededExtraRestHitpoints = maxHp - unit.getHitpoints() - restHitpoints % 1;
                int neededTicks = (int)Math.ceil(neededExtraRestHitpoints/unit.getRestHitpointsGain());
                double neededTime = Unit.REST_HITPOINTS_GAIN_INTERVAL*neededTicks - activityProgress % Unit.REST_HITPOINTS_GAIN_INTERVAL;
                extraTime = dt - neededTime;
                assert extraTime >= 0;
            }
            unit.setHitpoints(newHitpoints);
            restHitpoints = newRestHitpoints;
        }
        if((unit.getHitpoints()==maxHp && extraTime != 0d) && unit.getStamina()<maxSt){
            if(extraTime > 0d)
                dt = extraTime;
            double extraRestStamina = Unit.getIntervalTicks(activityProgress, dt, Unit.REST_STAMINA_GAIN_INTERVAL)*unit.getRestStaminaGain();
            int extraStamina = Unit.getIntervalTicks(restStamina, extraRestStamina, 1d);
            int newStamina = unit.getStamina() + extraStamina;
            double newRestStamina = restStamina + extraRestStamina;
            if(newStamina>=maxSt){
                newStamina = maxSt;
                newRestStamina = 0;
                restHitpoints = 0;
                unit.setCurrentActivity(new None(unit));
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

    @Override
    public String toString() {
        return "rest";
    }

    public boolean isInitialRestMode(){
        return this.isActive() && (this.restHitpoints + this.restStamina < 1d);
    }
}
