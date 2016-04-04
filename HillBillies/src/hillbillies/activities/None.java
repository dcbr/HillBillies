package hillbillies.activities;

import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import static hillbillies.utils.Utils.randInt;

/**
 * Created by Bram on 29-3-2016.
 */
public class None extends Activity {

    public None(Unit unit){
        super(unit);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void stopActivity() {

    }

    @Override
    public void interruptActivity() {

    }

    @Override
    public void advanceActivity(double dt) {
        if(this.isDefault())
            setDefaultBehaviour();
    }

    @Override
    public boolean isAbleTo() {
        return !unit.isInitialRestMode() && !unit.isAttacking() && !unit.isWorking();
    }

    @Override
    public boolean shouldInterrupt(Activity activity) {
        return false;
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
     * |   !this.isDefaultActive()
     */
    public void setDefaultBehaviour() throws IllegalStateException{
        if(!this.isDefault())
            throw new IllegalStateException("The default behaviour of unit is not activated");
        //this.startDoingDefault();
        int activity = randInt(0,2);
        switch(activity) {
            case 0:
                unit.moveToTarget(new Vector(Math.random() * (Unit.MAX_POSITION.X() - Unit.MIN_POSITION.X()) + Unit.MIN_POSITION.X(),
                        Math.random() * (Unit.MAX_POSITION.Y() - Unit.MIN_POSITION.Y()) + Unit.MIN_POSITION.Y(),
                        Math.random() * (Unit.MAX_POSITION.Z() - Unit.MIN_POSITION.Z()) + Unit.MIN_POSITION.Z()));
                if (unit.isAbleToSprint() && randInt(0, 99) < 1) {
                    unit.sprint();
                }
                break;
            case 1:
                unit.work();
                break;
            case 2:
                unit.rest();
                break;
        }
    }
}
