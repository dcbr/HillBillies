package hillbillies.part3.programs.expressions;

import hillbillies.model.Cube;
import hillbillies.model.Log;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;

/**
 * @author kenneth
 *
 */
public class LogPosition extends Expression<Cube> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public LogPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Cube evaluate() {
		return this.getRunner().getExecutingWorld().getCube(Log); //TODO
	}

}
