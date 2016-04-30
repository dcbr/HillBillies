/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class Not implements Expression<Boolean>{

	private final Expression<Boolean> expression;
	private final SourceLocation sourceLocation;

	/**
	 * 
	 */
	public Not(Expression<Boolean> expression, SourceLocation sourceLocation) {
		this.expression = expression;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Boolean evaluate(TaskRunner taskRunner) {
		return !expression.evaluate(taskRunner);
	}
	

}
