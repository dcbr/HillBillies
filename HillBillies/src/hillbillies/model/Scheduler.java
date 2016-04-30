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
public class Scheduler {

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
     * This new Scheduler cannot have the given faction as its faction.
     * | ! canHaveAsFaction(this.getFaction())
     */
    public Scheduler(@Raw Faction faction) throws IllegalArgumentException {
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
     * Variable registering the faction of this Scheduler.
     */
    private final Faction faction;

    /**
     * Check whether this scheduler has the given task as one of its
     * tasks.
     *
     * @param task
     * The task to check.
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
        for(Task task : tasks)
            if(!hasAsTask(task))
                return false;
        return true;
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
     * | Task.isValidScheduler(this)
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
     * Variable referencing a priority queue collecting all
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
    private final Queue<Task> tasks = new PriorityQueue<>(new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            return t2.getPriority()-t1.getPriority();
        }
    });
}
