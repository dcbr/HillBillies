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
        if(!condition.checkType(Boolean.class))
            throw new IllegalArgumentException("The given condition Expression is not of the generic type Boolean.");
        this.condition = condition;
        this.ifBody = ifBody;
    }

    @Override
    public void execute() {
        if(this.getCurrentChild()==1 || this.runChild(condition))
            this.runChild(ifBody);
    }

}
