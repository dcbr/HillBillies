package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.model.Terrain;
import hillbillies.activities.TargetMove;
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
		if (!positions.isEmpty()){
			this.getTask().stopRunning();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), positions);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getTask().stopRunning();
		return nearestPos;
	}

}