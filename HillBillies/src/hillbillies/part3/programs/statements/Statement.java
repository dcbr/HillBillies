package hillbillies.part3.programs.statements;

import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.Command;
import hillbillies.part3.programs.expressions.ReadVariable;

import java.util.HashSet;

/**
 * Abstract class representing a single statement.
 * @author Kenneth & Bram
 * @version 1.0
 */
public abstract class Statement extends Command<Void> {

    /**
     * The children must be specified in the order they will be executed.
     * @param children
     */
    public Statement(Command<?>... children){
        super(children);
    }

    @Override
    protected final Void process() {
        TaskRunner runner = this.getRunner();
        if(runner.isResuming()){
            currentChild = runner.resumeState();
            execute();// Execute without consuming dt
        }
        else if(!runner.breakLoop && runner.getDt()>0 && !runner.isPaused()) {
            this.getRunner().consumeDt();
            execute();// Otherwise execute and consume dt
        }
        else if(this.getRunner().isPaused()){
            this.getRunner().saveState(currentChild);// If we are interrupting, save the current state
        }
        else if(this.getRunner().getDt()==0d){
            this.getRunner().interrupt();// If all dt is consumed, start interrupting
        }
        return null;
    }

    protected abstract void execute();

    protected void runChild(Statement child){
        int childIndex = this.getChildren().indexOf(child);
        if(childIndex<currentChild)
            throw new IllegalArgumentException("The given child statement should be run before the currently running statement.");
        currentChild = childIndex;
        child.run();
    }

    public int getCurrentChild(){
        return this.currentChild;
    }

    private int currentChild = 0;

    public final boolean check(){
        return checkVariableAccess() && checkBreak();
    }

    protected boolean checkVariableAccess(){
        return checkVariableAccess(new HashSet<>());
    }

    protected boolean checkVariableAccess(HashSet<String> assignedVariables){
        for(Command child : this.getChildren()){
            if(child instanceof ReadVariable && !assignedVariables.contains(((ReadVariable)child).getVariableName()))
                return false;
            if(child instanceof Statement && !((Statement)child).checkVariableAccess(assignedVariables))
                return false;
            if(child instanceof Assignment)
                assignedVariables.add(((Assignment)child).getVariableName());
        }
        return true;
    }

    protected boolean checkBreak(){
        for(Command child : this.getChildren())
            if(child instanceof Break || (child instanceof Statement && !((Statement)child).checkBreak()))
                return false;
        return true;
    }

}
