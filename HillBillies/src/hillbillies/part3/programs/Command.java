package hillbillies.part3.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Task;
import hillbillies.part3.programs.statements.Statement;

import java.util.*;
import java.util.function.Predicate;

/**
 * Abstract class representing a Command. A Command can be associated with a Task.
 * Once associated, this Command can be ran. The return type of this run method
 * is given by the generic parameter T.
 *
 * Statements are Commands that return void, they DO stuff or CONTROL the program flow.
 * Expressions are Commands that return a result, they CALCULATE stuff.
 *
 * @author Kenneth & Bram
 * @version 1.0
 */
public abstract class Command<T> implements Iterable<Command<?>> {
    /**
     * List referencing this Command's children
     */
    private final List<Command<?>> children;
    /**
     * Variable registering the current task. This is the task that
     * is currently running this Command.
     */
    private Task currentTask;
    /**
     * HashSet registering the already executed children.
     */
    private HashSet<Integer> executedChildren;

    /**
     * Create a new Command with given children.
     * @param children The child Commands of this new Command. It is important
     *                 that these children are given in the right order. This
     *                 is the order in which they will be executed the first
     *                 time.
     * @throws IllegalArgumentException
     *          When one of the given children is invalid.
     *          | for any(Command child in children : child==null || indicesSatisfying(command -> this==command).size()!=0)
     */
    public Command(Command<?>... children) throws IllegalArgumentException {
        this.children = new ArrayList<>(Arrays.asList(children));
        for(int i=0;i<this.children.size();i++)
            if(this.children.get(i)==null || this.indicesSatisfying(command -> this==command).size()!=0)
                // Child is null or it contains this Command in its subCommands
                throw new IllegalArgumentException("The child at index " + i + " is an invalid child for this command.");
        this.executedChildren = new HashSet<>();
    }

    /**
     * Return the indices of the child commands of the given type.
     * @param type The type to check for
     * @param <C> The type to check for
     * @return A HashSet containing the indices of the children
     *          of the given type.
     */
    protected <C extends Command<?>> HashSet<Integer> indicesOf(Class<C> type){
        return indicesSatisfying(type::isInstance);
    }

    /**
     * Return the indices of the child commands satisfying the given condition.
     * @param condition The condition to filter on.
     * @return A HashSet containing the indices of the children satisfying the
     *          given condition.
     */
    protected HashSet<Integer> indicesSatisfying(Predicate<Command<?>> condition){
        HashSet<Integer> indices = new HashSet<>();
        for(int i=0;i<children.size();i++)
            if(condition.test(children.get(i)) || children.get(i).indicesSatisfying(condition).size()!=0)
                indices.add(i);
        return indices;
    }

    /**
     * @return The child at the given index.
     */
    protected Command<?> getChild(int index){
        return this.children.get(index);
    }

    /**
     * @return The number of children this command has.
     */
    protected int getNbChildren(){
        return this.children.size();
    }

    /**
     * Return the current task of this Command.
     */
    @Basic
    @Raw
    protected Task getCurrentTask() {
        return this.currentTask;
    }

    /**
     * Check whether the given task is a valid task for
     * this Command.
     *
     * @param task
     * The task to check.
     * @return True when the task's activity is set to this Command.
     * | result == (task.getActivity()==this)
     */
    private boolean isValidTask(Task task) {
        return task.getActivity()==this;
    }

    /**
     * Set the currentTask of this Command to the given task.
     *
     * @param task
     * The new currentTask for this Command.
     * @pre The given task is a valid currentTask for this Command.
     *      | isValidTask(task)
     * @post The currentTask of this new Command is equal to
     * the given task.
     * | new.getCurrentTask() == task
     * @post The Command's progress is reset.
     *       | this.getCurrentChild() == 0 ; this.executedChildren.size() == 0
     */
    @Raw
    private void setCurrentTask(Task task) throws IllegalArgumentException {
        for (Command<?> child : this.children)
            child.setCurrentTask(task);
        this.currentTask = task;
        this.executedChildren.clear();
        this.currentChild = 0;
    }

    /**
     * Set the currentTask of this Command and all its children to null.
     */
    private void resetCurrentTask(){
        for(Command<?> child : this)
            child.resetCurrentTask();
        this.currentTask = null;
    }

    /**
     * @return Boolean value indicating whether this Command's currentTask is null or not.
     */
    private boolean isCurrentTaskSet(){
        return this.currentTask!=null;
    }

    /**
     * Run this command.
     * @return
     * @throws IllegalStateException
     * @throws NullPointerException
     *          Possibly by Expression -> use Liskov question mark?
     */
    private T run() throws IllegalStateException, NullPointerException{
        if(!this.isCurrentTaskSet())
            throw new IllegalStateException("This command is not linked to any task yet, so it can't be executed.");
        if(!this.getCurrentTask().isRunning() || this.getRunner().isPausing())
            return null;
        T result = process();
        this.currentChild = 0;
        this.executedChildren.clear();
        return result;
    }

    protected <E> E runChild(Command<E> child) throws IllegalStateException, IllegalArgumentException{
        if(!this.isCurrentTaskSet())
            throw new IllegalStateException("This command is not linked to any task yet, so it can't be executed.");
        if(this.getRunner().isStopping() || this.getRunner().isPausing())
            return null;// We are stopping / pausing
        int childIndex = this.children.indexOf(child);
        if(childIndex<currentChild && !executedChildren.contains(childIndex))
            throw new IllegalArgumentException("The given child command should be run before the currently running command.");
        setCurrentChild(childIndex);
        executedChildren.add(childIndex);
        return child.run();
    }

    public int getCurrentChild(){
        return this.currentChild;
    }

    protected void setCurrentChild(int childIndex){
        if(childIndex>=currentChild)
            this.currentChild = childIndex;
    }

    private int currentChild = 0;

    protected abstract T process() throws NullPointerException;

    public final void start(Task task) throws IllegalArgumentException, IllegalStateException{
        if(!isValidTask(task))
            throw new IllegalArgumentException("The given task does not have this Command as its activity.");

        this.setCurrentTask(task);

        if(this.getRunner().isStopping() || this.getRunner().isPausing())
            throw new IllegalStateException("The taskRunner linked to this command is stopping or pausing.");

        this.run();
        this.resetCurrentTask();
    }

    protected Task.TaskRunner getRunner() throws IllegalStateException{
        if(!this.isCurrentTaskSet())
            throw new IllegalStateException("This command is not linked to any task yet, so there's no runner available.");
        if(!this.getCurrentTask().isRunning())
            throw new IllegalStateException("The task linked to this command is not running yet, so there's no runner available.");
        return this.getCurrentTask().getRunner();
    }

    /**
     * Returns an iterator over elements of type {@code Command<?>}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Command<?>> iterator() {
        return this.children.iterator();
    }
}
