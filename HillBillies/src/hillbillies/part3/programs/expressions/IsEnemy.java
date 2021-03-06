package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;

/**
 * Class representing the IsEnemy Boolean Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class IsEnemy extends UnaryExpression<Unit, Boolean> {

	/**
	 * 
	 */
	public IsEnemy(Expression<Unit> unit) throws IllegalArgumentException {
		super(Boolean.class, Unit.class, unit);
	}

	/**
	 * Compute the value to be returned by this expression, given the value
	 * of its child expression.
	 *
	 * @param unit The value of the child expression. This value is guaranteed
	 *              to be not null.
	 * @return False if the given unit's faction is equal to the executing unit's
	 * 			faction. True otherwise.
	 */
	@Override
	protected Boolean compute(Unit unit) {
		return !this.getRunner().getExecutingUnit().getFaction().equals(unit.getFaction());
	}
}
