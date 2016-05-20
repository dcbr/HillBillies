/**
 * 
 */
package hillbillies.part3.programs.expressions;

/**
 * Class representing the Not Boolean Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class Not extends UnaryExpression<Boolean, Boolean> {

	/**
	 * 
	 */
	public Not(Expression<Boolean> expression) throws IllegalArgumentException {
		super(Boolean.class, Boolean.class, expression);
	}

	/**
	 * @param value The value of the child expression. This value is guaranteed
	 *              to be not null.
	 * @return | !value
	 */
	@Override
	protected Boolean compute(Boolean value) {
		return !value;
	}

}
