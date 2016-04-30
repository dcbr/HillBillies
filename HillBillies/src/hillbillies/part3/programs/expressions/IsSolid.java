package hillbillies.part3.programs.expressions;

import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class IsSolid implements Expression<Boolean>{
	private final Expression<Cube> cube;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public IsSolid(Expression<Cube> cube, SourceLocation sourceLocation) {
		this.cube = cube;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Boolean evaluate(TaskRunner taskRunner) {
		return !cube.evaluate(taskRunner).isPassable();
	}

}
