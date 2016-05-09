package hillbillies.part3.programs.expressions;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;

/**
 * @author kenneth
 *
 */
public class BoulderPosition extends Expression<Cube> {
	private SourceLocation sourceLoation;
	/**
	 * 
	 */
	public BoulderPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLoation = sourceLocation;
	}

	@Override
	public Cube evaluate() {
		return this.getRunner().getExecutingWorld().getCube(Boulder); //TODO
	}

}