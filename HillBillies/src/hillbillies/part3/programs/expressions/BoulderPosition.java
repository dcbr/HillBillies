package hillbillies.part3.programs.expressions;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class BoulderPosition implements Expression<Cube> {
	private SourceLocation sourceLoation;
	/**
	 * 
	 */
	public BoulderPosition(SourceLocation sourceLocation) {
		this.sourceLoation = sourceLocation;
	}

	@Override
	public Cube evaluate(TaskRunner taskRunner) {
		return taskRunner.getExecutingWorld().getCube(Boulder); //TODO
	}

}