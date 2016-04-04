package hillbillies.activities;

import hillbillies.model.Unit;

/**
 * Abstract base class for each Activity a Unit can perform
 * @author Kenneth & Bram
 * @version 1.0
 */
public abstract class Activity {

    /**
     * Variable registering this activity's progress.
     */
    protected double activityProgress;
    /**
     * Variable registering whether the default behaviour of this unit is activated.
     * Variable registering whether the current activity is being executed or not.
     */
    private boolean isDefault, isActive;
    protected Unit unit;

    public Activity(Unit unit){
        this.activityProgress = 0d;
        this.unit = unit;
        this.isDefault = false;
        this.isActive = false;
    }

    public final void start(){
        this.start(false);
    }
    public final void start(boolean isDefault) throws IllegalStateException{
        if(!isDefault && !isAbleTo())
            throw new IllegalStateException("This unit cannot " + this.toString() + " at this moment");
        else if(isDefault)
            stopDoingDefault();// TODO: fix this
        // this.stateDefault -= 1; (if stateDefault == 3) ??
        this.activityProgress = 0d;
        this.startActivity();
        this.isActive = true;
    }
    public abstract void startActivity();

    public final void stop(){
        //this.interruptActivity();// First interrupt and then stop activity or directly stop it?
        this.stopActivity();
        this.activityProgress = 0d;
        this.isActive = false;
    }
    public abstract void stopActivity();

    public final void interrupt(){
        this.interruptActivity();
        this.isActive = false;
    }
    public abstract void interruptActivity();

    // Inheriting classes should implement a more advanced version of this method, but still call this super method to increase activityProgress
    public final void advanceTime(double dt){
        this.advanceActivity(dt);
        this.activityProgress += dt;
    }

    public abstract void advanceActivity(double dt);

    public abstract boolean isAbleTo();

    //TODO: add shouldCancel method?
    public abstract boolean shouldInterrupt(Activity activity);

    public void setDefault(boolean enable){
        this.isDefault = enable;
    }

    public boolean isDefault(){
        return this.isDefault;
    }

    public boolean isActive(){
        return this.isActive;
    }

    public double getActivityProgress(){
        return this.activityProgress;
    }

    public long getUnitId(){
        return this.unit.getId();
    }
}
