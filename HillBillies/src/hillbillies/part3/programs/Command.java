package hillbillies.part3.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Task;
import hillbillies.part3.programs.statements.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
 *
 * @invar The task of each Command must be a valid task for any
 * Statement.
 * | isValidTask(getTask())
 */
public abstract class Command<T> {
    private final List<Command<?>> children;
    /**
     * Variable registering the current task. This is the task that
     * is currently running this Command.
     */
    private Task currentTask;

    public Command(Command<?>... children) throws IllegalArgumentException {
        this.children = new ArrayList<>(Arrays.asList(children));
        for(int i=0;i<this.children.size();i++)
            if(this.children.get(i)==null || this.indicesSatisfying(command -> this==command).size()!=0)
                // Child is null or it contains this Command in its subCommands
                throw new IllegalArgumentException("The child at index " + i + " is an invalid child for this command.");
    }

    protected <C extends Command<?>> HashSet<Integer> indicesOf(Class<C> type){
        return indicesSatisfying(type::isInstance);
    }

    protected HashSet<Integer> indicesSatisfying(Predicate<Command<?>> condition){
        HashSet<Integer> indices = new HashSet<>();
        for(int i=0;i<children.size();i++)
            if(condition.test(children.get(i)) || children.get(i).indicesSatisfying(condition).size()!=0)
                indices.add(i);
        return indices;
    }

    protected Command<?> getChild(int index){
        return this.children.get(index);
    }

    protected List<Command<?>> getChildren(){
        return this.children;
    }

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
     * @return True when the task's activity is still null and
     *          this Command doesn't have a task yet.
     * | result == (task.getActivity()==null) && (!this.isTaskSet())
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
     */
    @Raw
    private void setCurrentTask(Task task) throws IllegalArgumentException {
        for (Command<?> child : this.children)
            child.setCurrentTask(task);
        this.currentTask = task;
    }

    private void resetCurrentTask(){
        for(Command<?> child : this.children)
            child.resetCurrentTask();
        this.currentTask = null;
    }

    private boolean isCurrentTaskSet(){
        return this.currentTask!=null;
    }

    /**
     *
     * @return
     * @throws IllegalStateException
     * @throws NullPointerException
     *          Possibly by Expression -> use Liskov question mark?
     */
    private T run() throws IllegalStateException, NullPointerException{
        if(!this.isCurrentTaskSet())
            throw new IllegalStateException("This command is not linked to any task yet, so it can't be executed.");
        if(!this.getCurrentTask().isRunning() || this.getRunner().isPaused())
            return null;
        return process();// TODO: change this by a directly call to process? since the preconditions are already checked
    }

    protected <E> E runChild(Command<E> child) throws IllegalStateException, IllegalArgumentException{
        if(!this.isCurrentTaskSet())
            throw new IllegalStateException("This command is not linked to any task yet, so it can't be executed.");
        if(!this.getRunner().isRunning() || this.getRunner().isPaused())
            return null;// We are stopping / pausing
        int childIndex = this.getChildren().indexOf(child);
//        if(childIndex<currentChild)
//            throw new IllegalArgumentException("The given child command should be run before the currently running command.");
        setCurrentChild(childIndex);
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

        if(!this.getRunner().isRunning() || this.getRunner().isPaused())
            throw new IllegalStateException("The task linked to this command is not running.");

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
}
