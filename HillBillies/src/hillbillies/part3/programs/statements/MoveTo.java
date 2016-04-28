package hillbillies.part3.programs.statements;

import hillbillies.activities.TargetMove;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task;
import hillbillies.part3.programs.expressions.Expression;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class MoveTo extends Statement{
	private final Expression<Vector> position;
	private final SourceLocation sourceLocation;
	
	/**
	 * 
	 */
	public MoveTo(Expression<Vector> position, SourceLocation sourceLocation) {
		this.position = position;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Task.TaskBuilder taskBuilder) {
		taskBuilder.addActivity(new TargetMove(null, position.evaluate()));
		
	}

}
