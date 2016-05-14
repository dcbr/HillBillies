package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.Command;

/**
 * Created by Bram on 27-4-2016.
 */
public abstract class Expression<T> extends Command<T>{

    public Expression(Expression<?>... subExpressions) throws IllegalArgumentException {// Expressions can only have other expressions as their children
        super(subExpressions);
    }

    @Override
    public final T process() throws NullPointerException{
        try {
            return evaluate();
        }catch(NullPointerException e){
            if(this.getRunner().isPaused()) return null;// Nothing wrong, runner is pausing
            throw new NullPointerException("Strange NullPointerException occurred.");
        }
    }

    public abstract T evaluate() throws NullPointerException;

}
