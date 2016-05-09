package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;

/**
 * Created by Bram on 28-4-2016.
 */
public class IfElse extends Statement {

    private final Expression<Boolean> condition;
    private final Statement ifBody, elseBody;
    private final SourceLocation sourceLocation;

    public IfElse(Expression<Boolean> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
        super(condition, ifBody, elseBody);
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public void execute() {
        if(condition.run())
            ifBody.run();
        else
            elseBody.run();
    }
}
