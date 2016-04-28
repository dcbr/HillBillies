package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task;
import hillbillies.part3.programs.expressions.Expression;

/**
 * Created by Bram on 28-4-2016.
 */
public class IfElse extends Statement {

    private final Expression<Boolean> condition;
    private final Statement ifBody, elseBody;
    private final SourceLocation sourceLocation;

    public IfElse(Expression<Boolean> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public void execute(Task.TaskBuilder taskBuilder) {
        if(condition.evaluate())
            ifBody.execute(taskBuilder);
        else
            elseBody.execute(taskBuilder);
    }
}
