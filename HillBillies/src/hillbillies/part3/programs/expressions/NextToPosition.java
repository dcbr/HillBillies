package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Cube;
import hillbillies.utils.Vector;

/**
 * Class representing the NextTo UnaryExpression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class NextToPosition extends UnaryExpression<Vector, Vector> {

	/**
	 * 
	 */
	public NextToPosition(Expression<Vector> position) throws IllegalArgumentException {
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
	protected Vector compute(Vector position) {
		Set<Cube> positions = new HashSet<>();
		this.getRunner().getExecutingWorld().getNeighbouringCubesSatisfying(
				positions,
				position,
				cube -> this.getRunner().getExecutingUnit().isValidPosition(cube.getPosition()),
				cube -> cube
		);
		Vector nextTo = new TargetMove(this.getRunner().getExecutingUnit(), positions).getNearestPos();
		if(nextTo == null){
			// No accessible positions available => stop activity
			this.getRunner().stop();// TODO: check if taskRunner is stopped before executing next statement
		}
		return nextTo;
	}

}
