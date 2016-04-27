package hillbillies.part3.programs;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.activities.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing a Task
 * @author Kenneth & Bram
 * @version 1.0
 * @invar Each Task can have its name as name.
 * | canHaveAsName(this.getName())
 * @invar Each Task can have its priority as priority.
 * | canHaveAsPriority(this.getPriority())
 * @invar Each task must have proper activities.
 * | hasProperActivities()
 */
public class Task implements Iterable<Activity> {

    /**
     * Variable registering the name of this Task.
     */
    private final String name;
    /**
     * Variable registering the priority of this Task.
     */
    private final int priority;
    /**
     * Variable referencing a list collecting all the activities
     * of this task.
     *
     * @invar The referenced list is effective.
     * | activities != null
     * @invar Each activity registered in the referenced list is
     * effective and not yet terminated.
     * | for each activity in activities:
     * | ( (activity != null) &&
     * | (! activity.isTerminated()) )
     * @invar No activity is registered at several positions
     * in the referenced list.
     * | for each I,J in 0..activities.size()-1:
     * | ( (I == J) ||
     * | (activities.get(I) != activities.get(J))
     */
    private final List<Activity> activities;

    /**
     * Initialize this new Task with given name and priority.
     *
     * @param name
     * The name for this new Task.
     * @param priority
     * The priority for this new Task.
     * @post The name of this new Task is equal to the given
     * name.
     * | new.getName() == name
     * @post The priority of this new Task is equal to the given
     * priority.
     * | new.getPriority() == priority
     * @post The activities of this new Task are set to the given
     * list of activities.
     * | new.getNbActivities() == activities.size()
     * | foreach(Activity a in activities : new.hasAsActivity(a) == true)
     * @throws IllegalArgumentException
     * This new Task cannot have the given name as its name.
     * | ! canHaveAsName(this.getName())
     * @throws IllegalArgumentException
     * This new Task cannot have the given priority as its priority.
     * | ! canHaveAsPriority(this.getPriority())
     * @note Tasks should be constructed by using the TaskBuilder.
     */
    private Task(String name, int priority, List<Activity> activities) throws IllegalArgumentException {
        if (! canHaveAsName(name))
            throw new IllegalArgumentException();
        this.name = name;

        if (! canHaveAsPriority(priority))
            throw new IllegalArgumentException();
        this.priority = priority;

        this.activities = activities;
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
    @Immutable
    public int getPriority() {
        return this.priority;
    }
    /**
     * Check whether this Task can have the given priority as its priority.
     *
     * @param priority
     * The priority to check.
     * @return
     * | result ==
     */
    @Raw
    public boolean canHaveAsPriority(int priority) {
        return false;
    }

    /**
     * Return the activity associated with this task at the
     * given index.
     *
     * @param index
     * The index of the activity to return.
     * @throws IndexOutOfBoundsException
     * The given index is not positive or it exceeds the
     * number of activities for this task.
     * | (index < 1) || (index > getNbActivities())
     */
    @Basic
    @Raw
    public Activity getActivityAt(int index) throws IndexOutOfBoundsException {
    	return activities.get(index - 1);
    }
    /**
     * Return the number of activities associated with this task.
     */
    @Basic
    @Raw
    public int getNbActivities() {
    	return activities.size();
    }
    /**
     * Check whether this task can have the given activity
     * as one of its activities.
     *
     * @param activity
     * The activity to check.
     * @return True if and only if the given activity is effective.
     * | result ==
     * | (activity != null)
     */
    @Raw
    public boolean canHaveAsActivity(Activity activity) {
    	return (activity != null);
    }
    /**
     * Check whether this task can have the given activity
     * as one of its activities at the given index.
     *
     * @param activity
     * The activity to check.
     * @return False if the given index is not positive or exceeds the
     * number of activities for this task + 1.
     * | if ( (index < 1) || (index > getNbActivities()+1) )
     * | then result == false
     * Otherwise, false if this task cannot have the given
     * activity as one of its activities.
     * | else if ( ! this.canHaveAsActivity(activity) )
     * | then result == false
     * Otherwise, true if and only if the given activity is
     * not registered at another index than the given index.
     * | else result ==
     * | for each I in 1..getNbActivities():
     * | (index == I) || (getActivityAt(I) != activity)
     */
    @Raw
    public boolean canHaveAsActivityAt(Activity activity, int index) {
    	if ((index < 1) || (index > getNbActivities() + 1))
    	    return false;
    	if (!this.canHaveAsActivity(activity))
    	    return false;
    	for (int i = 1; i < getNbActivities(); i++)
    		if ((i != index) && (getActivityAt(i) == activity))
    		    return false;
    	return true;
    }
    /**
     * Check whether this task has proper activities attached to it.
     *
     * @return True if and only if this task can have each of the
     * activities attached to it as a activity at the given index.
     * | result ==
     * | for each I in 1..getNbActivities():
     * |   this.canHaveAsActivityAt(getActivityAt(I)
     */
    public boolean hasProperActivities() {
    	for (int i = 1; i <= getNbActivities(); i++) {
    		if (!canHaveAsActivityAt(getActivityAt(i), i))
    		    return false;
    	}
    	return true;
    }
    /**
     * Check whether this task has the given activity as one of its
     * activities.
     *
     * @param activity
     * The activity to check.
     * @return The given activity is registered at some position as
     * a activity of this task.
     * | for some I in 1..getNbActivities():
     * | getActivityAt(I) == activity
     */
    public boolean hasAsActivity(Activity activity) {
    	return activities.contains(activity);
    }

    /**
     * Returns an iterator over elements of type {@code Activity}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Activity> iterator() {
        return activities.iterator();
    }

    public static class TaskBuilder{

        private final String taskName;
        private final int taskPriority;
        private final List<Activity> taskActivities;

        public TaskBuilder(String taskName, int taskPriority){
            this.taskName = taskName;
            this.taskPriority = taskPriority;
            this.taskActivities = new ArrayList<>();
        }

        public Task build(){
            return new Task(taskName, taskPriority, taskActivities);
        }

        /**
         * Add the given activity to the list of taskActivities of the
         * newly constructed task.
         *
         * @param activity
         * The activity to be added.
         * @pre The given activity is effective and this newly constructed
         * task does not yet have the given activity as one of its activities.
         * | (activity != null) && (! this.hasAsActivity(activity))
         * @post The number of activities of this newly constructed task is
         * incremented by 1.
         * | new.getNbActivities() == getNbActivities() + 1
         * @post This newly constructed task will have the given activity as
         * its very last activity.
         * | new.getActivityAt(getNbActivities()+1) == activity
         */
        public TaskBuilder addActivity(final Activity activity) {
            assert(activity != null) && (!this.hasAsActivity(activity));
            taskActivities.add(activity);
            return this;
        }
        /**
         * Remove the given activity from the list of activities of this
         * newly constructed task.
         *
         * @param activity
         * The activity to be removed.
         * @pre The given activity is effective and this newly constructed
         * task has the given activity as one of its activities.
         * | (activity != null) &&
         * | this.hasAsActivity(activity)
         * @post The number of activities of this newly constructed task is
         * decremented by 1.
         * | new.getNbActivities() == getNbActivities() - 1
         * @post This newly constructed task will no longer have the given
         * activity as one of its activities.
         * | ! new.hasAsActivity(activity)
         * @post All activities registered at an index beyond the index at
         * which the given activity was registered, are shifted
         * one position to the left.
         * | for each I,J in 1..getNbActivities():
         * | if ( (getActivityAt(I) == activity) and (I < J) )
         * | then new.getActivityAt(J-1) == getActivityAt(J)
         */
        @Raw
        public TaskBuilder removeActivity(final Activity activity) {
            assert(activity != null) && this.hasAsActivity(activity);
            taskActivities.remove(activity);
            return this;
        }

        /**
         * Check whether this newly constructed task has the given
         * activity as one of its activities.
         *
         * @param activity
         * The activity to check.
         * @return The given activity is registered at some position as
         * a activity of this newly constructed task.
         * | for some I in 1..getNbActivities():
         * | getActivityAt(I) == activity
         */
        public boolean hasAsActivity(Activity activity) {
            return taskActivities.contains(activity);
        }

    }
}
