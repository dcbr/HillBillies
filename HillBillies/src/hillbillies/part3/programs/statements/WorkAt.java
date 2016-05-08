package hillbillies.part3.programs.statements;

import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;
/**
 * @author kenneth
 *
 */
public class WorkAt extends Statement{
	private final Expression<Cube> position;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public WorkAt(Expression<Cube> position, SourceLocation sourceLocation) {
		this.position = position;
		this.sourceLocation = sourceLocation;
	}
	@Override
	public void execute(TaskRunner taskRunner) {
		taskRunner.getExecutingUnit().work(position.evaluate(taskRunner).getPosition());
	}

}
