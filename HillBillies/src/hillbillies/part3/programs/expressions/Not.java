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

	@Override
	protected Boolean compute(Boolean value) {
		return !value;
	}

}
