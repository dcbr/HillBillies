package hillbillies.part3.programs.expressions;

import hillbillies.model.Terrain;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class WorkshopPosition implements Expression<Vector> {
	private SourceLocation sourceLoation;
	/**
	 * 
	 */
	public WorkshopPosition(SourceLocation sourceLocation) {
		this.sourceLoation = sourceLocation;
	}

	@Override
	public Vector evaluate(TaskRunner taskRunner) {
		return taskRunner.getExecutingWorld().getCube(Terrain.WORKSHOP); //TODO -> aangezien workshops niet van cube kunnen veranderen miss een lijst bijhouden van waar ze staan?
	}

}