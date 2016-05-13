package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.activities.TargetMove.Path;
import hillbillies.model.Cube;
import hillbillies.model.IWorldObject;
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
		Set<? extends IWorldObject> logs = this.getRunner().getExecutingWorld().getLogs(true);
		if (!logs.isEmpty()){
			this.getCurrentTask().stopRunning();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), (Set<IWorldObject>) logs);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getCurrentTask().stopRunning();
		return nearestPos;
	}

}
