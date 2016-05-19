package hillbillies.part3.programs.expressions;

/**
 * Class representing a generic BinaryExpression.
 * The generic type L is the type of the leftExpression of this BinaryExpression.
 * The generic type R is the type of the rightExpression of this BinaryExpression.
 * The generic type T is the return type of this BinaryExpression.
 * @author Kenneth & Bram
 * @version 1.0
 */
public abstract class BinaryExpression<L,R,T> extends Expression<T> {

    private Expression<L> leftExpression;
    private Expression<R> rightExpression;

    public BinaryExpression(Class<T> resultType, Class<L> leftType, Expression<L> left, Class<R> rightType, Expression<R> right) throws IllegalArgumentException{
        super(resultType, left, right);
        if(!left.checkType(leftType))
            throw new IllegalArgumentException("The given left Expression's generic type does not correspond to this BinaryExpression's generic leftType.");
        if(!right.checkType(rightType))
            throw new IllegalArgumentException("The given right Expression's generic type does not correspond to this BinaryExpression's generic rightType.");
        this.leftExpression = left;
        this.rightExpression = right;
    }

    @Override
    public T evaluate() throws NullPointerException {
        L leftValue = this.runChild(leftExpression);
        R rightValue = this.runChild(rightExpression);
        if(leftValue==null || rightValue==null)
            throw new NullPointerException("The left or right expression yield null.");// Is catched inside Expression's process method
        return combine(leftValue, rightValue);
    }

    protected abstract T combine(L leftValue, R rightValue);
}
