/**
 *
 */
package hillbillies.part3.programs.expressions;

/**
 * Class representing the And Boolean Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class And extends BinaryExpression<Boolean, Boolean, Boolean> {

    /**
     *
     */
    public And(Expression<Boolean> left, Expression<Boolean> right) throws IllegalArgumentException{
        super(Boolean.class, Boolean.class, left, Boolean.class, right);
    }

    @Override
    protected Boolean combine(Boolean leftValue, Boolean rightValue) {
        return leftValue && rightValue;
    }

}
