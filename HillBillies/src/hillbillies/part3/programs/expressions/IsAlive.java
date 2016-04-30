/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task.TaskBuilder;

/**
 * @author kenneth
 *
 */
public class IsAlive implements Expression<Boolean>{

	private final Expression <Unit> unit;
	private final SourceLocation sourceLocation;

	/**
	 * 
	 */
	public IsAlive(Expression<Unit> unit, SourceLocation sourceLocation) {
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Boolean evaluate(TaskBuilder taskBuilder) {
		return unit.evaluate(taskBuilder).isTerminated();
	}

}
