package hillbillies.part3.programs.expressions;

import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class IsPassable extends UnaryExpression<Vector, Boolean> {

	/**
	 * 
	 */
	public IsPassable(Expression<Vector> position) throws IllegalArgumentException {
		super(position);
	}

	/**
	 * Compute the value to be returned by this expression, given the value
	 * of its child expression.
	 *
	 * @param position The value of the child expression. This value is guaranteed
	 *              to be not null.
	 * @return The value this expression should return based on the given value
	 * of its child expression.
	 */
	@Override
	protected Boolean compute(Vector position) {
		return this.getRunner().getExecutingWorld().getCube(position).isPassable();
	}

}
