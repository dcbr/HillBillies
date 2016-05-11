package hillbillies.part3.programs.expressions;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;


/**
 * @author kenneth
 *
 */
public class Enemy extends Expression<Unit> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public Enemy(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Unit evaluate() {
		Map<Vector, Unit> positions = new HashMap<>();
		Set<Unit> units = this.getRunner().getExecutingWorld().getUnits();
		for (Unit unit : units){
			if(unit.getFaction() != this.getRunner().getExecutingUnit().getFaction())
				positions.put(unit.getPosition().getCubeCoordinates(),unit);
		}
		if (!positions.isEmpty()){
			this.getTask().stopRunning();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), positions.keySet());
		Vector nearestPos = targetmove.getNearestPos();
		if(nearestPos == null)
			this.getTask().stopRunning();
		return positions.get(nearestPos);
	}
}