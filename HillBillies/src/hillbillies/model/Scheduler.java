package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;

import java.util.*;
import java.util.function.Predicate;

/**
 * Class representing a Faction's Task Scheduler
 * @author Kenneth & Bram
 * @version 1.0
 * @invar Each Scheduler can have its faction as faction.
 * | hasProperFaction()
 * @invar Each scheduler must have proper tasks.
 * | hasProperTasks()
 */
public class Scheduler implements Iterable<Task> {

    /**
     * Variable registering the faction of this Scheduler.
     */
    private final Faction faction;
    /**
     * Variable referencing a sorted set collecting all
     * the tasks of this scheduler.
     *
     * @invar The referenced set is effective.
     * | tasks != null
     * @invar Each task registered in the referenced list is
     * effective and not yet terminated.
     * | for each task in tasks:
     * | ( (task != null) &&
     * | (! task.isTerminated()) )
     */
    private final SortedSet<Task> tasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            return t2.getPriority()-t1.getPriority();
        }
    });

    /**
     * Initialize this new Scheduler with given faction and no tasks yet.
     *
     * @param faction
     * The faction for this new Scheduler.
     * @post The faction of this new Scheduler is equal to the given
     * faction.
     * | new.getFaction() == faction
     * @post This new scheduler has no tasks yet.
     * | new.getNbTasks() == 0
     * @throws IllegalArgumentException
     *          When the given faction is not effective
     *          | faction == null
     */
    public Scheduler(@Raw Faction faction) throws IllegalArgumentException {
        if(faction==null)
            throw new IllegalArgumentException("The given faction is not effective.");
        this.faction = faction;
    }

    /**
     * Return the faction of this Scheduler.
     */
    @Basic
    @Raw
    @Immutable
    public Faction getFaction() {
        return this.faction;
    }

    /**
     * Check whether the Faction associated to this Scheduler is
     * a proper Faction for this Scheduler.
     *
     * @return
     * | result == (faction.getScheduler()==this)
     */
    public boolean hasProperFaction() {
        return faction.getScheduler()==this;
    }

    /**
     * Check whether this scheduler has the given task as one of its
     * tasks.
     *
     * @param task
     * The task to check.
     * @return True when the given task is part of this scheduler
     *          | tasks.contains(task)
     */
    @Basic
    @Raw
    public boolean hasAsTask(@Raw Task task) {
    	return tasks.contains(task);
    }

    /**
     * Check whether this scheduler has all the tasks in the given
     * collection as one of its tasks.
     * @param tasks The collection of tasks to check
     * @return True if all tasks are part of this scheduler.
     *          | if(foreach(Task task in tasks : hasAsTask(task)))
     *          |      result == true
     *          | else
     *          |      result == false
     */
    public boolean hasAsTasks(Collection<Task> tasks){
        return this.tasks.containsAll(tasks);
    }

    /**
     * Check whether this scheduler can have the given task
     * as one of its tasks.
     *
     * @param task
     * The task to check.
     * @return True if and only if the given task is effective
     * and that task is a valid task for a scheduler.
     * | result ==
     * | (task != null) &&
     * | task.canHaveAsScheduler(this)
     */
    @Raw
    public boolean canHaveAsTask(Task task) {
    	return (task != null) && (task.canHaveAsScheduler(this));
    }

    /**
     * Check whether this scheduler has proper tasks attached to it.
     *
     * @return True if and only if this scheduler can have each of the
     * tasks attached to it as one of its tasks,
     * and if each of these tasks references this scheduler as
     * the scheduler to which they are attached.
     * | for each task in Task:
     * | if (hasAsTask(task))
     * | then canHaveAsTask(task) &&
     * | (task.getScheduler() == this)
     */
    public boolean hasProperTasks() {
    	for (Task task: tasks) {
    		if (!canHaveAsTask(task))
    		    return false;
    		if (!task.hasAsScheduler(this))
    		    return false;
    	}
    	return true;
    }

    /**
     * Return the number of tasks associated with this scheduler.
     *
     * @return The total number of tasks collected in this scheduler.
     * | result ==
     * | card({task:Task | hasAsTask({task)})
     */
    public int getNbTasks() {
    	return tasks.size();
    }

    /**
     * Add the given task to the set of tasks of this scheduler.
     *
     * @param task
     * The task to be added.
     * @pre The given task is effective and does not reference
     * this scheduler yet.
     * | (task != null) && (!task.hasAsScheduler(this))
     * @post This scheduler has the given task as one of its tasks.
     * | new.hasAsTask(task)
     * @post The given task has this scheduler as one of its schedulers.
     * | task.hasAsScheduler(this)
     */
    public void addTask(@Raw Task task) {
    	assert(task != null) && (!task.hasAsScheduler(this));
    	tasks.add(task);
        task.addScheduler(this);
    }

    /**
     * Remove the given task from the set of tasks of this scheduler.
     *
     * @param task
     * The task to be removed.
     * @pre This scheduler has the given task as one of
     * its tasks, and the given task still references
     * this scheduler.
     * | this.hasAsTask(task) &&
     * | (task.hasAsScheduler(this))
     * @post This scheduler no longer has the given task as
     * one of its tasks.
     * | ! new.hasAsTask(task)
     * @post The given task no longer has this scheduler as
     * one of its schedulers.
     * | ! task.hasAsScheduler(this)
     */
    @Raw
    public void removeTask(Task task) {
    	assert this.hasAsTask(task) && (task.hasAsScheduler(this));
        tasks.remove(task);
        task.removeScheduler(this);
    }

    /**
     * Replace the given oldTask by the given newTask.
     * @param oldTask The task to replace
     * @param newTask The new task
     * @effect The oldTask is removed
     *          | this.removeTask(oldTask)
     * @effect The newTask is added
     *          | this.addTask(newTask)
     * @throws IllegalArgumentException
     *          When oldTask or newTask do not satisfy removeTask's respectively addTask's preconditions.
     *          | !this.hasAsTask(oldTask) || !oldTask.hasAsScheduler(this) ||
     *          | newTask == null || newTask.hasAsScheduler(this)
     */
    public void replaceTask(Task oldTask, Task newTask){
        if(!this.hasAsTask(oldTask) || !(oldTask.hasAsScheduler(this)))
            throw new IllegalArgumentException("The given oldTask does not satisfy removeTask's preconditions.");
        if((newTask == null) || (newTask.hasAsScheduler(this)))
            throw new IllegalArgumentException("The given newTask does not satisfy addTask's preconditions.");
        removeTask(oldTask);
        addTask(newTask);
    }

    /**
     * Get a collection of all tasks in this scheduler satisfying the given condition.
     * @param condition The condition to select upon
     * @return A collection of all tasks in this scheduler satisfying the given condition.
     *          | foreach(Task task in result : condition.test(task) == true)
     */
    public Collection<Task> getAllTasksSatisfying(Predicate<Task> condition){
        Set<Task> result = new HashSet<>();
        for(Task task : tasks){
            if(condition.test(task))
                result.add(task);
        }
        return result;
    }

    /**
     * Get the first task which satisfies the given condition. The tasks are
     * iterated in decreasing order of priority. So the first task that
     * satisfies the given condition is also the task with the highest priority
     * which satisfies the given condition.
     * @param condition The condition to select upon
     * @return The Task with highest priority AND which satisfies the given
     *          condition. Null is returned if no such task exists in this
     *          scheduler.
     *          | if(exists(Task task in tasks : condition.test(task)))
     *          |   result == Task task in tasks : condition.test(task) AND
     *          |               not exists(Task other in tasks :
     *          |                       other.priority > task.priority)
     *          | else
     *          |   result == null
     */
    public Task getTaskSatisfying(Predicate<Task> condition){
        for(Task task : tasks)
            if(condition.test(task))
                return task;
        return null;
    }

    /**
     * @return The task with highest priority in this scheduler which is
     *          not currently assigned to a Unit.
     * @effect Get the highest priority task which has no assigned Unit
     *          | getTaskSatisfying(task -> task.getAssignedUnit()==null)
     */
    public Task getHighestPriorityAssignableTask(){
        return getTaskSatisfying(task -> task.getAssignedUnit()==null);
    }

    /**
     * @return The task with highest priority in this scheduler which is
     *          currently not being executed.
     * @effect Get the highest priority task which is not running
     *          | getTaskSatisfying(task -> !task.isRunning())
     */
    public Task getHighestPriorityNotRunningTask(){
        return getTaskSatisfying(task -> !task.isRunning());
    }

    /**
     * @return A collection containing all the tasks which are added to this scheduler.
     */
    public Collection<Task> getAllTasks(){
        return getAllTasksSatisfying(task -> true);
    }

    /**
     * Schedule the given task for the given unit.
     * @param task The task to schedule
     * @param unit The unit to assign the task to
     * @post If the task has this scheduler as its Scheduler AND the task is not running
     *       at this moment AND the given unit is effective, the unit's Task is set to
     *       the given task and the task's assigned Unit is set to the given unit.
     *       | unit.getTask() == task
     *       | task.getAssignedUnit() == unit
     * @throws NullPointerException
     *          When the given task is not effective
     *          | task == null
     */
    public void schedule(Task task, Unit unit) throws NullPointerException{
        if(task.hasAsScheduler(this) && !task.isRunning() && unit!=null){
            unit.setTask(task);
            task.setAssignedUnit(unit);
        }
    }

    /**
     * Deschedule the given task
     * @param task The task to deschedule
     * @post If the task has this scheduler as its Scheduler AND the task is currently
     *       running, the task will be stopped, the task's assignedUnit's Task will be
     *       set to null and the task's assignedUnit will be set to null.
     *       | new task.isRunning() == false
     *       | new (task.getAssignedUnit()).getTask() == null
     *       | new task.getAssignedUnit() == null
     * @throws NullPointerException
     */
    public void deschedule(Task task) throws NullPointerException{
        if(task.hasAsScheduler(this) && task.isRunning()){
            task.stopRunning();
            task.getAssignedUnit().setTask(null);
            task.setAssignedUnit(null);
        }
    }

    /**
     * Returns an iterator over elements of type {@code Task}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
