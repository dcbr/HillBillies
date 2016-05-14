package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;
import hillbillies.utils.Vector;


/**
 * Class representing the Position UnaryExpression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class PositionOfUnit extends UnaryExpression<Unit, Vector> {

	/**
	 * 
	 */
	public PositionOfUnit(Expression<Unit> unit) throws IllegalArgumentException {
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
	protected Vector compute(Unit unit) {
		return unit.getPosition();
	}

}