package hillbillies.part3.programs.expressions;

import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Boulder;
import hillbillies.utils.Vector;

/**
 * Class representing the Boulder Vector Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class BoulderPosition extends Expression<Vector> {

	/**
	 * 
	 */
	public BoulderPosition() {
		super(Vector.class);
	}

	@Override
	public Vector evaluate() throws NullPointerException {
		Set<Boulder> boulders = this.getRunner().getExecutingWorld().getBoulders(true);
		if (boulders.isEmpty()){
			this.getRunner().stop();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), boulders);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getRunner().stop();
		return nearestPos;
	}

}