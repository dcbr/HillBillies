package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.Command;

/**
 * Class representing an Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public abstract class Expression<T> extends Command<T>{

    /**
     * Variable referencing this Expression's generic type.
     */
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

    /**
     * Evaluate this Expression
     * @return The result this Expression evaluates to.
     * @throws NullPointerException
     *          A subclass may throw a NullPointerException.
     *          | ? true
     */
    public abstract T evaluate() throws NullPointerException;

    /**
     * Check this Expression's return type against the given type.
     * @param type The type to check against.
     * @return True if the types match or this Expression's type
     *          is set to null.
     * @note This method is used to guarantee type safety, because
     *       TaskFactory uses raw types to create Expressions and
     *       Statements.
     */
    public boolean checkType(Class<T> type){
        return this.type==null || type.isAssignableFrom(this.type);
    }

}
