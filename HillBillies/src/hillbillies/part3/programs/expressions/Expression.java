package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.Command;

/**
 * Created by Bram on 27-4-2016.
 */
public abstract class Expression<T> extends Command<T>{

    private final Class<T> type;

    public Expression(Class<T> type, Expression<?>... subExpressions) throws IllegalArgumentException {// Expressions can only have other expressions as their children
        super(subExpressions);
        this.type = type;
    }

    @Override
    public final T process() throws NullPointerException{
        try {
            return evaluate();
        }catch(NullPointerException e){
            if(this.getRunner().isPausing() || this.getRunner().isStopping()) return null;// Nothing wrong, runner is pausing or stopping
            throw new NullPointerException("Strange NullPointerException occurred.");
        }
    }

    public abstract T evaluate() throws NullPointerException;

    public boolean checkType(Class<T> type){
        return this.type==null || type.isAssignableFrom(this.type);
    }

}
