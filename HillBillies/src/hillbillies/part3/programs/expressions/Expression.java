package hillbillies.part3.programs.expressions;

import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.Command;
import hillbillies.part3.programs.statements.Assignment;

/**
 * Created by Bram on 27-4-2016.
 */
public abstract class Expression<T> extends Command<T>{

    public Expression(Expression<?>... subExpressions){// Expressions can only have other expressions as their children
        super(subExpressions);
    }

    @Override
    public final T process(){
        return evaluate();
    }

    public abstract T evaluate();// Can be replaced by process

}
