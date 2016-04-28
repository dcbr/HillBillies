package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expressions.Expression;

/**
 * Created by Bram on 27-4-2016.
 */
public class While implements Statement {

    private final Expression<Boolean> condition;
    private final Statement body;
    private final SourceLocation sourceLocation;

    public While(Expression<Boolean> condition, Statement body, SourceLocation sourceLocation){
        this.condition = condition;
        this.body = body;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public void execute() {
        while(condition.evaluate()){
            body.execute();
        }
    }
}
