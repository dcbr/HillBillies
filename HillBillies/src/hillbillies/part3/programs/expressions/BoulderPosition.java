package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Task.TaskRunner;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class BoulderPosition extends Expression<Vector> {
	private SourceLocation sourceLoation;
	/**
	 * 
	 */
	public BoulderPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLoation = sourceLocation;
	}

	@Override
	public Vector evaluate() {
		Unit unit = this.getRunner().getExecutingUnit();
		Set<Vector> positions = new HashSet<>();
		Set<Boulder> boulders = this.getRunner().getExecutingWorld().getBoulders(true);
		for (Boulder boulder : boulders){
			positions.add(boulder.getPosition().getCubeCoordinates());
		}
		if (!positions.isEmpty()){
			this.getTask().stopRunning();
			return null;
		}
		TargetMove targetmove = new TargetMove(unit, positions);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getTask().stopRunning();
		return nearestPos;
	}

}