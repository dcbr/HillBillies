package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Boulder;
import hillbillies.model.IWorldObject;
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
		Set<? extends IWorldObject> boulders = this.getRunner().getExecutingWorld().getBoulders(true);
		if (!boulders.isEmpty()){
			this.getTask().stopRunning();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), (Set<IWorldObject>) boulders);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getTask().stopRunning();
		return nearestPos;
	}

}