package hillbillies.part3.programs.expressions;

import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Boulder;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class BoulderPosition extends Expression<Vector> {

	/**
	 * 
	 */
	public BoulderPosition() {
		super();
	}

	@Override
	public Vector evaluate() throws NullPointerException {
		Set<Boulder> boulders = this.getRunner().getExecutingWorld().getBoulders(true);
		if (boulders.isEmpty()){
			this.getRunner().interrupt();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), boulders);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getRunner().interrupt();
		return nearestPos;
	}

}