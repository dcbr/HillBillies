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
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}

	@Override
	protected void execute(TaskRunner taskRunner) {
		taskRunner.getExecutingUnit().follow(unit.evaluate(taskRunner));
	}

}
