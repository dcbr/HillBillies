package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.model.Terrain;
import hillbillies.model.Cube;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class WorkshopPosition extends Expression<Vector> {
	private SourceLocation sourceLoation;
	/**
	 * 
	 */
	public WorkshopPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLoation = sourceLocation;
	}

	@Override
	public Vector evaluate() {
		Set<Vector> positions = new HashSet<>();
		Set<Cube> workshops = this.getRunner().getExecutingWorld().getWorkshops();
		for (Cube workshop : workshops){
			positions.add(workshop.getPosition().getCubeCoordinates());
		}
		return this.getRunner().getExecutingWorld().getCube(Terrain.WORKSHOP); //TODO -> aangezien workshops niet van cube kunnen veranderen miss een lijst bijhouden van waar ze staan?
	}

}