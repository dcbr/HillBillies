/**
 *
 */
package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;

/**
 * @author kenneth
 *
 */
public class Or extends BinaryExpression<Boolean, Boolean, Boolean> {

    /**
     *
     */
    public Or(Expression<Boolean> left, Expression<Boolean> right) throws IllegalArgumentException {
        super(left, right);
    }

    @Override
    protected Boolean combine(Boolean leftValue, Boolean rightValue) {
        return leftValue || rightValue;
    }


}
