package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;

/**
 * @author kenneth
 *
 */
public class IsEnemy extends UnaryExpression<Unit, Boolean> {

	/**
	 * 
	 */
	public IsEnemy(Expression<Unit> unit) throws IllegalArgumentException {
		super(unit);
	}

	/**
	 * Compute the value to be returned by this expression, given the value
	 * of its child expression.
	 *
	 * @param unit The value of the child expression. This value is guaranteed
	 *              to be not null.
	 * @return The value this expression should return based on the given value
	 * of its child expression.
	 */
	@Override
	protected Boolean compute(Unit unit) {
		return !this.getRunner().getExecutingUnit().getFaction().equals(unit.getFaction());
	}
}
