package hillbillies.part3.programs.statements;

import hillbillies.activities.TargetMove;
import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task;
import hillbillies.part3.programs.expressions.Expression;

/**
 * @author kenneth
 *
 */
public class MoveTo extends Statement{
	private final Expression<Cube> cube;
	private final SourceLocation sourceLocation;
	
	/**
	 * 
	 */
	public MoveTo(Expression<Cube> cube, SourceLocation sourceLocation) {
		this.cube = cube;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Task.TaskBuilder taskBuilder) {
		taskBuilder.addActivity(new TargetMove(null, cube.evaluate(taskBuilder).getPosition().getCubeCenterCoordinates()));
		
	}

}