package hillbillies.part3.programs.statements;

import hillbillies.activities.Work;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;
import hillbillies.utils.Vector;
/**
 * @author kenneth
 *
 */
public class WorkAt extends Statement{
	private final Expression<Vector> position;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public WorkAt(Expression position, SourceLocation sourceLocation) {
		this.position = position;
		this.sourceLocation = sourceLocation;
	}
	@Override
	public void execute(TaskRunner taskRunner) {
		taskRunner.getExecutingUnit().work(position.evaluate(taskRunner));
	}

}
