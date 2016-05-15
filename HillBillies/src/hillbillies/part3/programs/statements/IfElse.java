package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;

/**
 * Created by Bram on 28-4-2016.
 */
public class IfElse extends Statement {

    public static final int IF_INDEX = 1, ELSE_INDEX = 2;
    private final Expression<Boolean> condition;
    private final Statement ifBody, elseBody;

    public IfElse(Expression<Boolean> condition, Statement ifBody, Statement elseBody) throws IllegalArgumentException{
        super(condition, ifBody, elseBody);
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    @Override
    public void execute() {
        if(this.getCurrentChild()!=ELSE_INDEX && (this.getCurrentChild()==IF_INDEX || this.runChild(condition)))
            this.runChild(ifBody);
        else
            this.runChild(elseBody);
    }
}
