/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task.TaskBuilder;

/**
 * @author kenneth
 *
 */
public class Not implements Expression{

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
	public Object evaluate(TaskBuilder taskBuilder) {
		return !expression.evaluate(taskBuilder);
	}
	

}
