package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task.TaskBuilder;

/**
 * @author kenneth
 *
 */
public class IsEnemy implements Expression{
	private final Expression<Unit> unit;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public IsEnemy(Expression unit, SourceLocation sourceLocation) {
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}
	@Override
	public Object evaluate(TaskBuilder taskBuilder) {
		
		return !unit.evaluate(taskBuilder).getFaction().equals(thisUnit.getFaction());
	}
}
