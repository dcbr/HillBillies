package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.activities.TargetMove.Path;
import hillbillies.model.Cube;
import hillbillies.model.Log;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class LogPosition extends Expression<Vector> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public LogPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Vector evaluate() {
		Set<Vector> positions = new HashSet<>();
		Set<Log> logs = this.getRunner().getExecutingWorld().getLogs(true);
		for (Log log : logs){
			positions.add(log.getPosition().getCubeCoordinates());
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
