package hillbillies.part3.programs.statements;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;

/**
 * 
 * @author kenneth
 *
 */
public class FollowUnit extends Statement{
	private final Expression<Unit> unit;
	private final SourceLocation sourceLocation;
	
	/**
	 * 
	 */
	public FollowUnit(Expression<Unit> unit, SourceLocation sourceLocation) {
		super(unit);
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}

	@Override
	protected void execute() {
		this.getRunner().getExecutingUnit().follow(unit.run());// TODO: since we have access to the Task, getExecutingUnit is no longer needed
	}

}
