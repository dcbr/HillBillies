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
public class SelectedPosition extends Expression<Cube> {

	private SourceLocation sourceLocation;

	/**
	 * 
	 */
	public SelectedPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Cube evaluate() {
		return this.getRunner().getSelectedCube();
	}

}
