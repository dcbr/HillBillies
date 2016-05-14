package hillbillies.part3.programs.expressions;

/**
 * Created by Bram on 14-5-2016.
 */
public abstract class BinaryExpression<L,R,T> extends Expression<T> {

    private Expression<L> leftExpression;
    private Expression<R> rightExpression;

    public BinaryExpression(Expression<L> left, Expression<R> right) throws IllegalArgumentException{
        super(left, right);
        this.leftExpression = left;
        this.rightExpression = right;
    }

    @Override
    public T evaluate() throws NullPointerException {
        L leftValue = this.runChild(leftExpression);
        R rightValue = this.runChild(rightExpression);
        if(leftValue==null || rightValue==null)
            throw new NullPointerException("The left or right expression yield null.");
        return combine(leftValue, rightValue);
    }

    protected abstract T combine(L leftValue, R rightValue);
}
