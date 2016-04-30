package hillbillies.part3.programs;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.Faction;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

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
    	return (task != null) && (Task.isValidScheduler(this));
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
    		if (!task.getSchedulers().contains(this))// TODO make method containsScheduler?
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
     * @pre The given task is effective and already references
     * this scheduler.
     * | (task != null) && (task.getScheduler() == this)
     * @post This scheduler has the given task as one of its tasks.
     * | new.hasAsTask(task)
     */
    public void addTask(@Raw Task task) {
    	assert(task != null) && (task.getSchedulers().contains(this));
    	tasks.add(task);
    }
    /**
     * Remove the given task from the set of tasks of this scheduler.
     *
     * @param task
     * The task to be removed.
     * @pre This scheduler has the given task as one of
     * its tasks, and the given task does not
     * reference any scheduler.
     * | this.hasAsTask(task) &&
     * | (task.getScheduler() == null)
     * @post This scheduler no longer has the given task as
     * one of its tasks.
     * | ! new.hasAsTask(task)
     */
    @Raw
    public void removeTask(Task task) {
    	assert this.hasAsTask(task) && (!task.getSchedulers().contains(this));
    	tasks.remove(task);
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
