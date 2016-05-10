package hillbillies.part3.programs;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Task;
import hillbillies.part3.programs.expressions.ReadVariable;
import hillbillies.part3.programs.statements.Assignment;
import hillbillies.part3.programs.statements.Statement;

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
     * Variable registering the task of this Statement.
     */
    private Task task;

    public Command(Command<?>... children) {
        this.children = Arrays.asList(children);
    }

    public <C extends Command<?>> HashSet<Integer> indicesOf(Class<C> type){
        return indicesSatisfying(type::isInstance);
    }

    public HashSet<Integer> indicesSatisfying(Predicate<Command<?>> condition){
        HashSet<Integer> indices = new HashSet<>();
        for(int i=0;i<children.size();i++)
            if(condition.test(children.get(i)) || children.get(i).indicesSatisfying(condition).size()!=0)
                indices.add(i);
        return indices;
    }

    public Command<?> getChild(int index){
        return this.children.get(index);
    }

    public List<Command<?>> getChildren(){
        return this.children;
    }

    /**
     * Return the task of this Command.
     */
    @Basic
    @Raw
    public Task getTask() {
        return this.task;
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
    public boolean isValidTask(Task task) {
        return task.getActivity()==null && !this.isTaskSet();
    }

    /**
     * Set the task of this Command to the given task.
     *
     * @param task
     * The new task for this Command.
     * @post The task of this new Command is equal to
     * the given task.
     * | new.getTask() == task
     * @throws IllegalArgumentException
     * The given task is not a valid task for this
     * Command.
     * | ! isValidTask(getTask())
     */
    @Raw
    public void setTask(Task task) throws IllegalArgumentException {
        if (!isValidTask(task))
            throw new IllegalArgumentException();
        for(Command<?> child : this.children)
            if(!child.isValidTask(task))
                throw new IllegalArgumentException();
        for (Command<?> child : this.children)
            child.setTask(task);
        this.task = task;
    }

    public boolean isTaskSet(){
        return this.task!=null;
    }

    public final T run() throws IllegalStateException{
        if(!this.isTaskSet())
            throw new IllegalStateException("This statement is not linked to any task yet, so it can't be executed.");
        if(!this.getTask().isRunning())
            throw new IllegalStateException("The task linked to this command is not running.");
        return process();
    }

    protected abstract T process();

    protected Task.TaskRunner getRunner() throws IllegalStateException{
        if(!this.isTaskSet())
            throw new IllegalStateException("This command is not linked to any task yet, so there's no runner available.");
        if(!this.getTask().isRunning())
            throw new IllegalStateException("The task linked to this command is not running yet, so there's no runner available.");
        return this.getTask().getRunner();
    }
}
