package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class IsFriend implements Expression<Boolean>{
	private final Expression<Unit> unit;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public IsFriend(Expression unit, SourceLocation sourceLocation) {
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}
	@Override
	public Boolean evaluate(TaskRunner taskRunner) {
		return taskRunner.getExecutingUnit().getFaction().equals(unit.evaluate(taskRunner).getFaction());
	}

}
