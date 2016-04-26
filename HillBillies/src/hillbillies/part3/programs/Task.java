package hillbillies.part3.programs;

import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a Task
 * @author Kenneth & Bram
 * @version 1.0
 * @invar Each Task can have its name as name.
 * | canHaveAsName(this.getName())
 * @invar Each Task can have its priority as priority.
 * | canHaveAsPriority(this.getPriority())
 */
public class Task {

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
     * @throws IllegalArgumentException
     * This new Task cannot have the given name as its name.
     * | ! canHaveAsName(this.getName())
     * @throws IllegalArgumentException
     * This new Task cannot have the given priority as its priority.
     * | ! canHaveAsPriority(this.getPriority())
     */
    public Task(String name, int priority) throws IllegalArgumentException {
        if (! canHaveAsName(name))
            throw new IllegalArgumentException();
        this.name = name;

        if (! canHaveAsPriority(priority))
            throw new IllegalArgumentException();
        this.priority = priority;
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
     * Variable registering the name of this Task.
     */
    private final String name;

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
     * Variable registering the priority of this Task.
     */
    private final int priority;
}
