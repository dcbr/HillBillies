/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;

/**
 * @author kenneth
 *
 */
public class SelectedPosition implements Expression<T>{

	private SourceLocation sourceLocation;

	/**
	 * 
	 */
	public SelectedPosition(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	@Override
	public T evaluate(TaskRunner taskRunner) {
		return null;//TODO
	}

}
