/**
 * 
 */
package hillbillies.part3.programs.expressions;

/**
 * @author kenneth
 *
 */
public class Not extends UnaryExpression<Boolean, Boolean> {

	/**
	 * 
	 */
	public Not(Expression<Boolean> expression) throws IllegalArgumentException {
		super(expression);
	}

	@Override
	protected Boolean compute(Boolean value) {
		return !value;
	}

}
