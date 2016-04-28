package hillbillies.part3.programs.expressions;

import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task.TaskBuilder;

/**
 * @author kenneth
 *
 */
public class IsPassable implements Expression{

	private final Expression<Cube> cube;
	private final SourceLocation sourceLocation;

	/**
	 * 
	 */
	public IsPassable(Expression<Cube> cube, SourceLocation sourceLocation) {
		this.cube = cube;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(TaskBuilder taskBuilder) {
		return cube.evaluate(taskBuilder).isPassable();
	}

}
