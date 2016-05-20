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

    /**
     * Variable referencing the left hand-side of this BinaryExpression.
     */
    private Expression<L> leftExpression;
    /**
     * Variable referencing the right hand-side of this BinaryExpression.
     */
    private Expression<R> rightExpression;

    /**
     * Initialize a new BinaryExpression with given left and right expressions.
     * @param resultType The type returned by this BinaryExpression
     * @param leftType The type returned by the leftExpression
     * @param left The leftExpression
     * @param rightType The type returned by the rightExpression
     * @param right The rightExpression
     * @throws IllegalArgumentException
     *          When the types of left or right expression do not match
     *          | !left.checkType(leftType) || !right.checkType(rightType)
     */
    public BinaryExpression(Class<T> resultType, Class<L> leftType, Expression<L> left, Class<R> rightType, Expression<R> right) throws IllegalArgumentException{
        super(resultType, left, right);
        if(!left.checkType(leftType))
            throw new IllegalArgumentException("The given left Expression's generic type does not correspond to this BinaryExpression's generic leftType.");
        if(!right.checkType(rightType))
            throw new IllegalArgumentException("The given right Expression's generic type does not correspond to this BinaryExpression's generic rightType.");
        this.leftExpression = left;
        this.rightExpression = right;
    }

    /**
     * Evaluate this BinaryExpression
     * @return The result of this BinaryExpression
     * @throws NullPointerException
     *          When the evaluation of left or right expression yields null
     *          | runChild(leftExpression)==null || runChild(rightExpression)==null
     */
    @Override
    public T evaluate() throws NullPointerException {
        L leftValue = this.runChild(leftExpression);
        R rightValue = this.runChild(rightExpression);
        if(leftValue==null || rightValue==null)
            throw new NullPointerException("The left or right expression yield null.");// Is catched inside Expression's process method
        return combine(leftValue, rightValue);
    }

    /**
     * Combine the results of the evaluation of leftExpression and
     * rightExpression in order to get the result of this
     * BinaryExpression's evaluation.
     * @param leftValue The value returned by leftExpression's evaluation.
     *                  This value is guaranteed to be not null.
     * @param rightValue The value returned by rightExpression's evaluation.
     *                   This value is guaranteed to be not null.
     * @return The result of the combination of both values.
     */
    protected abstract T combine(L leftValue, R rightValue);
}
