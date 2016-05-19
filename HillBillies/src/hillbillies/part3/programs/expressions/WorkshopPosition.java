package hillbillies.part3.programs.expressions;

import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Cube;
import hillbillies.utils.Vector;

/**
 * Class representing the WorkshopPosition Vector Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class WorkshopPosition extends Expression<Vector> {

	/**
	 * 
	 */
	public WorkshopPosition() {
		super(Vector.class);
	}

	@Override
	public Vector evaluate() throws NullPointerException {
		Set<Cube> workshops = this.getRunner().getExecutingWorld().getWorkshops();
		if (workshops.isEmpty()){
			this.getRunner().stop();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), workshops);
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getRunner().stop();
		return nearestPos;
	}

}