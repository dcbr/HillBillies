package hillbillies.part3.programs.expressions;

import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Cube;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class WorkshopPosition extends Expression<Vector> {

	/**
	 * 
	 */
	public WorkshopPosition() {
		super();
	}

	@Override
	public Vector evaluate() throws NullPointerException {
		Set<Cube> workshops = this.getRunner().getExecutingWorld().getWorkshops();
		if (workshops.isEmpty()){
			this.getRunner().interrupt();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), workshops);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getRunner().interrupt();
		return nearestPos;
	}

}