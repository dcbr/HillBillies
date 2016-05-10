package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.part3.programs.VariableCollection;
import hillbillies.part3.programs.expressions.Expression;

import java.util.*;
import java.util.function.Predicate;

import hillbillies.part3.programs.statements.Statement;
import hillbillies.utils.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * Class representing a Task
 * @author Kenneth & Bram
 * @version 1.0
 * @invar Each Task can have its name as name.
 * | canHaveAsName(this.getName())
 * @invar The priority of each Task must be a valid priority for any
 * Task.
 * | isValidPriority(getPriority())
 * @invar Each Task can have its activity as activity.
 * | canHaveAsActivity(this.getActivity())
 * @invar The assignedUnit of each Task must be a valid assignedUnit for any
 * Task.
 * | isValidAssignedUnit(getAssignedUnit())
 * @invar Each task must have proper schedulers.
 * | hasProperSchedulers()
 */
public class Task implements Comparable<Task> {

    /**
     * Variable registering the name of this Task.
     */
    private final String name;
    /**
     * Variable registering the priority of this Task.
     */
    private int priority;
    /**
     * Variable registering the activity of this Task.
     */
    private final Statement activity;
    /**
     * Variable registering the assignedUnit of this Task.
     */
    private Unit assignedUnit;
    /**
     * Variable registering the selectedCube of this Task.
     */
    private final Vector selectedCube;
    /**
     * Variable referencing a set collecting all the schedulers
     * of this task.
     *
     * @invar The referenced set is effective.
     * | schedulers != null
     * @invar Each scheduler registered in the referenced list is
     * effective and not yet terminated.
     * | for each scheduler in schedulers:
     * | ( (scheduler != null) &&
     * | (! scheduler.isTerminated()) )
     */
    private final Set<Scheduler> schedulers = new HashSet<>();

    /**
     * Initialize this new Task with given name, priority and activity.
     * This task has no schedulers yet, nor an assignedUnit.
     *
     * @param name
     * The name for this new Task.
     * @param priority
     * The priority for this new Task.
     * @param activity
     * The activity for this new Task.
     * @param selectedCube
     * The selectedCube for this new Task.
     * @post The name of this new Task is equal to the given
     * name.
     * | new.getName() == name
     * @effect The priority of this new Task is set to
     * the given priority.
     * | this.setPriority(priority)
     * @post The activity of this new Task is equal to the given
     * activity.
     * | new.getActivity() == activity
     * @post The selectedCube of this new Task is equal to the given
     * selectedCube.
     * | new.getSelectedCube() == new Vector(selectedCube)
     * @post This new task has no schedulers yet.
     * | new.getNbSchedulers() == 0
     * @throws IllegalArgumentException
     * This new Task cannot have the given name as its name.
     * | ! canHaveAsName(this.getName())
     * @throws IllegalArgumentException
     * This new Task cannot have the given activity as its activity.
     * | ! canHaveAsActivity(this.getActivity())
     */
    public Task(String name, int priority, Statement activity, int[] selectedCube) throws IllegalArgumentException {
        if (! canHaveAsName(name))
            throw new IllegalArgumentException();
        this.name = name;

        this.setPriority(priority);

        if (! canHaveAsActivity(activity))
            throw new IllegalArgumentException();
        activity.setTask(this);
        this.activity = activity;

        this.selectedCube = new Vector(selectedCube);
    }
    /**
     * Return the name of this Task.
     */
    @Basic
    @Raw
    @Immutable
    public String getName() {
        return this.name;
    }
    /**
     * Check whether this Task can have the given name as its name.
     *
     * @param name
     * The name to check.
     * @return
     * | result ==
     */
    @Raw
    public boolean canHaveAsName(String name) {
        return false;
    }

    /**
     * Return the priority of this Task.
     */
    @Basic
    @Raw
    public int getPriority() {
        return this.priority;
    }
    /**
     * Check whether the given priority is a valid priority for
     * any Task.
     *
     * @param priority
     * The priority to check.
     * @return
     * | result ==
     */
    public static boolean isValidPriority(int priority) {
        return true;
    }
    /**
     * Set the priority of this Task to the given priority.
     *
     * @param priority
     * The new priority for this Task.
     * @post The priority of this new Task is equal to
     * the given priority.
     * | new.getPriority() == priority
     * @throws IllegalArgumentException * The given priority is not a valid priority for any
     * Task.
     * | ! isValidPriority(getPriority())
     */
    @Raw
    private void setPriority(int priority) throws IllegalArgumentException {
        if (! isValidPriority(priority))
            throw new IllegalArgumentException();
        this.priority = priority;
    }

    /**
     * Decrease this task's priority by 1.
     * @post The new priority of this task is the old priority minus 1.
     *          | new.getPriority() == getPriority()-1
     */
    public void decreasePriority(){
        this.setPriority(this.getPriority()-1);
    }

    /**
     * Return the activity of this Task.
     */
    @Basic
    @Raw
    @Immutable
    public Statement getActivity() {
        return this.activity;
    }
    /**
     * Check whether this Task can have the given activity as its activity.
     *
     * @param activity
     * The activity to check.
     * @return
     * | result ==
     */
    @Raw
    public boolean canHaveAsActivity(Statement activity) {
        return false;// TODO check well-formedness?
    }

    /**
     * Return the assignedUnit of this Task.
     */
    @Basic
    @Raw
    public Unit getAssignedUnit() {
        return this.assignedUnit;
    }
    /**
     * Check whether the given assignedUnit is a valid assignedUnit for
     * any Task.
     *
     * @param assignedUnit
     * The assignedUnit to check.
     * @return
     * | if(!Unit.isValidTask(this))    result == false
     * | if(assignedUnit==null)         result == !this.isRunning()
     * | else                           result == this.hasAsScheduler(assignedUnit.getFaction().getScheduler())
     */
    public boolean isValidAssignedUnit(Unit assignedUnit) {
        if(!Unit.isValidTask(this))
            return false;
        if(assignedUnit==null)
            return !this.isRunning();
        else
            return this.hasAsScheduler(assignedUnit.getFaction().getScheduler());
    }
    /**
     * Set the assignedUnit of this Task to the given assignedUnit.
     *
     * @param assignedUnit
     * The new assignedUnit for this Task.
     * @post The assignedUnit of this new Task is equal to
     * the given assignedUnit.
     * | new.getAssignedUnit() == assignedUnit
     * @throws IllegalArgumentException * The given assignedUnit is not a valid assignedUnit for any
     * Task.
     * | ! isValidAssignedUnit(getAssignedUnit())
     */
    @Raw
    public void setAssignedUnit(Unit assignedUnit) throws IllegalArgumentException {
        if (! isValidAssignedUnit(assignedUnit))
            throw new IllegalArgumentException();
        this.assignedUnit = assignedUnit;
    }

    /**
     * Return the selectedCube of this Task.
     */
    @Basic
    @Raw
    @Immutable
    public Vector getSelectedCube() {
        return this.selectedCube;
    }

    /**
     * Check whether this task has the given scheduler as one of its
     * schedulers.
     *
     * @param scheduler
     * The scheduler to check.
     */
    @Basic
    @Raw
    public boolean hasAsScheduler(@Raw Scheduler scheduler) {
    	return schedulers.contains(scheduler);
    }
    /**
     * Check whether this task can have the given scheduler
     * as one of its schedulers.
     *
     * @param scheduler
     * The scheduler to check.
     * @return True if and only if the given scheduler is effective.
     * | result ==
     * | (scheduler != null)
     */
    @Raw
    public boolean canHaveAsScheduler(Scheduler scheduler) {
    	return (scheduler != null);
    }
    /**
     * Check whether this task has proper schedulers attached to it.
     *
     * @return True if and only if this task can have each of the
     * schedulers attached to it as one of its schedulers,
     * and if each of these schedulers references this task as
     * the task to which they are attached.
     * | for each scheduler in Scheduler:
     * | if (hasAsScheduler(scheduler))
     * | then canHaveAsScheduler(scheduler) &&
     * | (scheduler.getTask() == this)
     */
    public boolean hasProperSchedulers() {
    	for (Scheduler scheduler: schedulers) {
    		if (!canHaveAsScheduler(scheduler))
    		    return false;
    		if (!scheduler.hasAsTask(this))
    		    return false;
    	}
    	return true;
    }
    /**
     * Return the number of schedulers associated with this task.
     *
     * @return The total number of schedulers collected in this task.
     * | result ==
     * | card({scheduler:Scheduler | hasAsScheduler({scheduler)})
     */
    public int getNbSchedulers() {
    	return schedulers.size();
    }
    /**
     * Add the given scheduler to the set of schedulers of this task.
     *
     * @param scheduler
     * The scheduler to be added.
     * @pre The given scheduler is effective and already references
     * this task.
     * | (scheduler != null) && (scheduler.getTask() == this)
     * @post This task has the given scheduler as one of its schedulers.
     * | new.hasAsScheduler(scheduler)
     * @note To add a scheduler, use the Scheduler's addTask method.
     */
    protected void addScheduler(@Raw Scheduler scheduler) {
    	assert(scheduler != null) && (scheduler.hasAsTask(this));
    	schedulers.add(scheduler);
    }
    /**
     * Remove the given scheduler from the set of schedulers of this task.
     *
     * @param scheduler
     * The scheduler to be removed.
     * @pre This task has the given scheduler as one of
     * its schedulers, and the given scheduler doesn't
     * reference this task anymore.
     * | this.hasAsScheduler(scheduler) &&
     * | (scheduler.hasAsTask(this))
     * @post This task no longer has the given scheduler as
     * one of its schedulers.
     * | ! new.hasAsScheduler(scheduler)
     * @note To remove a scheduler, use the Scheduler's removeTask method.
     */
    protected void removeScheduler(@Raw Scheduler scheduler) {
    	assert this.hasAsScheduler(scheduler) && (!scheduler.hasAsTask(this));
    	schedulers.remove(scheduler);
        if(this.isRunning() && assignedUnit.getFaction().getScheduler()==scheduler){
            stopRunning();
            this.getAssignedUnit().setTask(null);
            this.setAssignedUnit(null);
        }
    }

    public Set<Scheduler> getSchedulers(){
        return new HashSet<>(schedulers);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Task o) throws NullPointerException{
        return this.getPriority()-o.getPriority();
    }

    public TaskRunner run(){
        if(runner==null){
            runner = new TaskRunner(this.getSelectedCube());
        }
        if(runner.isRunning())
            throw new IllegalStateException("This task is already running.");
        runner.start();
        return runner;
    }

    public TaskRunner getRunner(){
        if(!isRunning())
            throw new IllegalStateException("This task is not running.");
        return runner;
    }

    public void stopRunning() throws IllegalStateException{// Note: call scheduler's deschedule method to properly interrupt the task's execution
        if(runner==null || !runner.isRunning())
            throw new IllegalStateException("This task is not running.");
        runner.stop();
        this.decreasePriority();
    }

    public boolean isRunning(){
        return runner!=null && runner.isRunning();
    }

    public void finish() throws IllegalStateException{
        // TODO: check if runner has reached the last statement (maybe introduce end statement?)
        stopRunning();
        for(Scheduler s : this.schedulers)
            s.removeTask(this);
    }

    private TaskRunner runner;

    public class TaskRunner{

        private final Vector selectedCubeCoordinates;
        private final VariableCollection assignedVariables;
        private final LinkedList<Integer> savedState;
        private boolean isRunning, isPaused;

        private TaskRunner(Vector selectedCubeCoordinates){
            this.selectedCubeCoordinates = selectedCubeCoordinates;
            this.assignedVariables = new VariableCollection();
            this.savedState = new LinkedList<>();
            this.isRunning = false;
            this.isPaused = false;
        }

        public Unit getExecutingUnit(){
            return Task.this.assignedUnit;
        }

        public IWorld getExecutingWorld(){
            return getExecutingUnit().getWorld();
        }

        public Cube getSelectedCube(){ return getExecutingWorld().getCube(Task.this.getSelectedCube()); }

        public <T> void assignVariable(String variableName, Expression<T> value) throws ClassCastException{
            if(!this.assignedVariables.contains(variableName))
                this.assignedVariables.add(variableName, value);
            else {
                this.assignedVariables.assign(variableName, value);
            }
        }

        public boolean isVariableAssigned(String variableName){
            return this.assignedVariables.contains(variableName);
        }

        public <T> Expression<T> getVariableValue(String variableName) throws IllegalArgumentException, ClassCastException{
            if(!this.isVariableAssigned(variableName))
                throw new IllegalArgumentException("This variable isn't assigned.");
            return this.assignedVariables.getValue(variableName);
        }

        public boolean breakLoop = false;

        public void start(){
            this.isRunning = true;
        }

        public void interrupt(){
            this.isPaused = true;
        }

        public void resume(){
            this.isPaused = false;
            this.resumeCondition = unit -> true;
        }

        public void stop(){
            this.isRunning = false;
        }

        public boolean isRunning(){
            return this.isRunning;
        }

        public boolean isPaused(){ return this.isPaused; }

        public void advanceTask(double dt){
            assert this.isRunning;
            if(this.isPaused() && this.resumeCondition.test(this.getExecutingUnit()))
                this.resume();
            this.dt = dt;
            Task.this.getActivity().run();
            if(!this.isPaused()){
                // Program finished successfully
                Task.this.finish();
            }
        }

        public double getDt(){
            return dt;// tODO
        }

        public void consumeDt(){
            this.dt -= 0.001;
            if(this.dt<0d)
                this.dt = 0d;
        }

        private double dt = 0d;

        public void saveState(int index){
            this.savedState.addFirst(index);
        }

        public int resumeState(){
            return this.savedState.removeFirst();
        }

        public boolean isResuming(){
            return !this.savedState.isEmpty();
        }

        public void waitFor(Predicate<Unit> resumeCondition){
            this.interrupt();
            this.resumeCondition = resumeCondition;
        }

        private Predicate<Unit> resumeCondition = unit -> true;

    }
}
