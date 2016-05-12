/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.utils.Vector;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;

/**
 * @author kenneth
 *
 */
public class SelectedPosition extends Expression<Vector> {

	private SourceLocation sourceLocation;

	/**
	 * 
	 */
	public SelectedPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Vector evaluate() {
		return this.getRunner().getSelectedCube().getPosition();//TODO
	}

}
