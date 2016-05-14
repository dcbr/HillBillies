package hillbillies.part3.programs.expressions;


import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Faction;
import hillbillies.model.Unit;


/**
 * @author kenneth
 *
 */
public class Enemy extends Expression<Unit> {

	/**
	 * 
	 */
	public Enemy() {
		super();
	}

	@Override
	public Unit evaluate() throws NullPointerException {
		Faction thisFaction = this.getRunner().getExecutingUnit().getFaction();
		Set<Unit> units = this.getRunner().getExecutingWorld().getUnits();
		units.removeIf(unit -> unit.getFaction()==thisFaction || unit.isFalling());
		if (units.isEmpty()){
			this.getRunner().interrupt();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), units);
		Unit NearestUnit = (Unit) targetmove.getNearestObject();
		if(NearestUnit == null)
			this.getRunner().interrupt();
		return NearestUnit;
	}
}