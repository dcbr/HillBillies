/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.model.Cube;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;

/**
 * @author kenneth
 *
 */
public class SelectedPosition implements Expression<Cube>{

	private SourceLocation sourceLocation;

	/**
	 * 
	 */
	public SelectedPosition(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Cube evaluate(TaskRunner taskRunner) {
		return taskRunner.getSelectedCube();
	}

}
