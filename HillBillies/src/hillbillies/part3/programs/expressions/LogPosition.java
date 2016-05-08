package hillbillies.part3.programs.expressions;

import hillbillies.model.Cube;
import hillbillies.model.Log;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;

/**
 * @author kenneth
 *
 */
public class LogPosition implements Expression<Cube> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public LogPosition(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Cube evaluate(TaskRunner taskRunner) {
		return taskRunner.getExecutingWorld().getCube(Log); //TODO
	}

}
