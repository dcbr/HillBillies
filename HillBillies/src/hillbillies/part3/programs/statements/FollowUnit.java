package hillbillies.part3.programs.statements;

import hillbillies.model.Unit;
import hillbillies.activities.Follow;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task;
import hillbillies.part3.programs.Task.TaskBuilder;
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
	public FollowUnit(Expression unit, SourceLocation sourceLocation) {
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}

	@Override
	protected void execute(Task.TaskBuilder taskBuilder) {	
		taskBuilder.addActivity(new Follow(null,unit.evaluate()));
		
	}

}
