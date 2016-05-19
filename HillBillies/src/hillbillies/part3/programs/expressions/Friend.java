package hillbillies.part3.programs.expressions;


import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Unit;


/**
 * Class representing the Friend Unit Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class Friend extends Expression<Unit> {

	/**
	 * 
	 */
	public Friend() {
		super(Unit.class);
	}

	@Override
	public Unit evaluate() throws NullPointerException {
		Unit thisUnit = this.getRunner().getExecutingUnit();
		Set<Unit> units = this.getRunner().getExecutingWorld().getUnits();
		units.removeIf(unit -> unit.getFaction()!=thisUnit.getFaction() || unit.isFalling() || unit == thisUnit);
		if (units.isEmpty()){
			this.getRunner().stop();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), units);
		Unit NearestUnit = (Unit) targetmove.getNearestObject();
		if(NearestUnit == null)
			this.getRunner().stop();
		return NearestUnit;
	}
}