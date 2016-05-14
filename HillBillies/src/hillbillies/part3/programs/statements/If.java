package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.expressions.Expression;

/**
 * Created by Bram on 14-5-2016.
 */
public class If extends Statement {

    private final Expression<Boolean> condition;
    private final Statement ifBody;

    public If(Expression<Boolean> condition, Statement ifBody) throws IllegalArgumentException{
        super(condition, ifBody);
        this.condition = condition;
        this.ifBody = ifBody;
    }

    @Override
    public void execute() {
        if(this.runChild(condition))
            this.runChild(ifBody);
    }

}
