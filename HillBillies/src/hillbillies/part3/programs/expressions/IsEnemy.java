package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class IsEnemy extends Expression<Boolean> {
	private final Expression<Unit> unit;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public IsEnemy(Expression<Unit> unit, SourceLocation sourceLocation) {
		super(unit);
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Boolean evaluate() {
		return !this.getRunner().getExecutingUnit().getFaction().equals(unit.run().getFaction());
	}
}
