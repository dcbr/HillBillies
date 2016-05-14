package hillbillies.part3.programs.expressions;

import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Log;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class LogPosition extends Expression<Vector> {

	/**
	 * 
	 */
	public LogPosition() {
		super();
	}

	@Override
	public Vector evaluate() throws NullPointerException {
		Set<Log> logs = this.getRunner().getExecutingWorld().getLogs(true);
		if (logs.isEmpty()){
			this.getRunner().interrupt();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), logs);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getRunner().interrupt();
		return nearestPos;
	}

}
