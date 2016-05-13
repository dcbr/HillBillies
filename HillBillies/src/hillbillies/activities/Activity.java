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
     * Variable registering whether the current activity was performed successfully or not.
     */
    private boolean isDefault, isActive, success;
    /**
     * Final variable referencing the Unit this Activity is bound to.
     */
    protected final Unit unit;
    /**
     * Final variable referencing this Activity's parentActivity
     */
    protected final Activity parentActivity;

    /**
     * Initialize a new Activity which is bound to the given Unit.
     * This means that this Activity can only be executed by the given Unit.
     * The new Activity has no parentActivity.
     * @param unit The Unit this new Activity is bound to.
     * @post This new Activity is bounded to the given unit.
     *      | new.unit = unit
     * @post This new Activity has no Activity set as its parent.
     *      | new.parentActivity = null
     */
    public Activity(Unit unit){
        this(null, unit);
    }

    /**
     * Initialize a new Activity which is bound to the given Unit and has parentActivity
     * as its parent Activity.
     * This means that this Activity can only be executed by the given Unit.
     * @param parentActivity The parent Activity of this Activity
     * @param unit The Unit this new Activity is bound to.
     * @post This new Activity is bounded to the given unit.
     *      | new.unit = unit
     * @post This new Activity has the given parentActivity set as its parent.
     *      | new.parentActivity = parentActivity
     */
    public Activity(Activity parentActivity, Unit unit){
        this.parentActivity = parentActivity;
        this.activityProgress = 0d;
        this.unit = unit;
        this.isDefault = false;
        this.isActive = false;
        this.success = false;
    }

    /**
     * Start this Activity with default mode disabled.
     * @effect Start this Activity with default mode disabled.
     *          | this.start(false)
     * @throws IllegalStateException
     *          When this Unit is not able to execute this Activity at this moment.
     *          Or when this Activity is not set as the Unit's current Activity.
     *          | !isAbleTo() || !unit.isCurrentActivity(this)
     */
    public final void start() throws IllegalStateException{
        this.start(false);
    }

    /**
     * Start this Activity in the specified default mode.
     * @param isDefault Enables/Disables the default mode
     * @throws IllegalStateException
     *          When this Unit is not able to execute this Activity at this moment and
     *          when default mode is not enabled. Or when this Activity is not set as
     *          the Unit's current Activity.
     *          | (!isDefault && !isAbleTo()) || !controller.isCurrentActivity(this)
     */
    public final void start(boolean isDefault) throws IllegalStateException{
        if(!isDefault && !isAbleTo())
            throw new IllegalStateException("This unit cannot " + this.toString() + " at this moment");
        if(!unit.isCurrentActivity(this))// Sort of extra check to assure this is only called from within unit
            throw new IllegalStateException("This unit's current activity is not set to this activity!");
        this.isDefault = isDefault;
        this.activityProgress = 0d;
        this.isActive = true;
        this.startActivity();
    }

    /**
     * Activity specific code which is called when the Activity is started.
     */
    protected abstract void startActivity();

    /**
     * Stop this Activity
     */
    public final void stop(){
        this.interruptActivity();// First interrupt and then stop activity
        this.stopActivity();
        this.activityProgress = 0d;
        this.isActive = false;
        if(this.wasSuccessful())
            unit.addXP(this.getXp());
    }

    /**
     * Activity specific code which is called when the Activity is stopped.
     */
    protected abstract void stopActivity();

    /**
     * Interrupt this Activity
     * @param nextActivity The Activity which will be started after this one is interrupted.
     * @throws IllegalStateException
     *          When this Activity should not interrupt for nextActivity
     *          | !shouldInterruptFor(nextActivity)
     */
    public final void interrupt(Activity nextActivity) throws IllegalStateException{
        if(!shouldInterruptFor(nextActivity))
            throw new IllegalStateException("This Activity cannot be interrupted by the next Activity");
        this.interruptActivity();
        this.isActive = false;
    }

    /**
     * Activity specific code which is called when the Activity is interrupted.
     * This code is also called before stopActivity when the Activity is stopped.
     */
    protected abstract void interruptActivity();

    /**
     * Advance the game-time of this Activity
     * @param dt The amount of game-time to progress with
     */
    public final void advanceTime(double dt){
        this.advanceActivity(dt);
        this.activityProgress += dt;
    }

    /**
     * Activity specific code which is called when advanceTime of this Activity is called.
     */
    protected abstract void advanceActivity(double dt);

    /**
     * Activity specific code to check whether this Activity can be started.
     * @return True if this Activity can be started as the nextActivity of the currently active Activity.
     */
    public abstract boolean isAbleTo();

    /**
     * Activity specific code to check whether this Activity can be interrupted by nextActivity.
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if this Activity should be interrupted for nextActivity.
     */
    protected abstract boolean shouldInterruptFor(Activity nextActivity);

    /**
     * Set the default mode.
     * @param enable The new state of the default mode.
     * @post The new state of the default mode equals enable
     *          | new.isDefault()==enable
     */
    public void setDefault(boolean enable){
        this.isDefault = enable;
        if(!enable)
            this.requestFinish();
    }

    /**
     * Check whether default mode is enabled.
     * @return True if default mode is enabled.
     */
    public boolean isDefault(){
        return this.isDefault;
    }

    /**
     * Check whether this Activity is active.
     * @return True when this Activity is active.
     */
    public boolean isActive(){
        return this.isActive;
    }

    /**
     * Get this Activity's progress.
     * @return The Activity's progress.
     */
    public double getActivityProgress(){
        return this.activityProgress;
    }

    /**
     * Get the Id of the Unit this Activity is bound to.
     * @return The Id of the Unit this Activity is bound to.
     */
    public long getUnitId(){
        return this.unit.getId();
    }

    /**
     * Request this Activity's finish. This will stop the current Activity, if possible, and
     * resume the previous Activity in stack.
     * @see hillbillies.model.Unit#requestActivityFinish(Activity)
     * @effect Calling requestFinish without a success parameter will set success to false.
     *          | requestFinish(false)
     */
    protected void requestFinish(){
        requestFinish(false);
    }

    /**
     * Request this Activity's finish. This will stop the current Activity, if possible, and
     * resume the previous Activity in stack.
     * @see hillbillies.model.Unit#requestActivityFinish(Activity)
     * @post The activity's success flag will be set to the given value of success.
     *          | this.wasSuccessful() == success
     */
    protected void requestFinish(boolean success){
        this.success = success;
        this.unit.requestActivityFinish(this);
    }

    /**
     * Returns true when the Activity is stopped and was completed successfully.
     */
    public boolean wasSuccessful(){
        return !this.isActive() && this.success;
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    public abstract int getXp();

    /**
     * Method to check whether the given activity is this Activity's parentActivity.
     * @param activity The activity to check
     * @return True when the given activity is this Activity's parentActivity
     *          | result == (this.parentActivity==activity)
     */
    public boolean isParentActivity(Activity activity){
        return this.parentActivity==activity;
    }
}
