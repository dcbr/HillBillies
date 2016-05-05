package hillbillies.part3.programs.expressions;

import java.util.List;

import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class NextToPosition implements Expression<Vector>{
	private Expression<Vector> position;
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public NextToPosition(Expression<Vector> position, SourceLocation sourceLocation) {
		this.position = position;
		this.sourceLocation =sourceLocation;
		
	}

	@Override
	public Vector evaluate(TaskRunner taskRunner) {
		//TODO: kijken in pathfinding of er iets gelijkaardig bestaat
	}

}
