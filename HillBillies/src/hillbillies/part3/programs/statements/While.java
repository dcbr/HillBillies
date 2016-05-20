package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.Command;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;

/**
 * Created by Bram on 27-4-2016.
 */
public class While extends Statement {

    public static final int BODY_INDEX = 1;
    private final Expression<Boolean> condition;
    private final Statement body;

    public While(Expression<Boolean> condition, Statement body) throws IllegalArgumentException{
        super(condition, body);
        if(!condition.checkType(Boolean.class))
            throw new IllegalArgumentException("The given condition Expression is not of the generic type Boolean.");
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute() {
        boolean resuming = this.getCurrentChild()==BODY_INDEX;
        while(resuming || runChild(condition)){
            resuming = false;
            runChild(body);
            if(this.getRunner().breakLoop){
                this.getRunner().breakLoop = false;
                break;
            }
            if(this.getRunner().isStopping() || this.getRunner().isPausing())
                break;
        }
    }

    @Override
    protected <E> E runChild(Command<E> child) throws IllegalStateException, IllegalArgumentException {
        return super.runChild(child);
    }

    @Override
    public boolean checkBreak(){
        return true;
    }
}
