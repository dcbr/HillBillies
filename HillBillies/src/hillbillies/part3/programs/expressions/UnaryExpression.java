package hillbillies.part3.programs.expressions;

/**
 * Created by Bram on 14-5-2016.
 */
public abstract class UnaryExpression<E, T> extends Expression<T> {

    private Expression<E> expression;

    public UnaryExpression(Expression<E> expression) throws IllegalArgumentException {
        super(expression);
        this.expression = expression;
    }

    @Override
    public T evaluate() throws NullPointerException {
        E value = this.runChild(expression);
        if(value==null)
            throw new NullPointerException("The expression yields null.");
        return compute(value);
    }

    /**
     * Compute the value to be returned by this expression, given the value
     * of its child expression.
     * @param value The value of the child expression. This value is guaranteed
     *              to be not null.
     * @return The value this expression should return based on the given value
     *          of its child expression.
     */
    protected abstract T compute(E value);

}
