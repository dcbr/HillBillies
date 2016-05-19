package hillbillies.part3.programs.expressions;


import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Faction;
import hillbillies.model.Unit;


/**
 * Class representing the Enemy Unit Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class Enemy extends Expression<Unit> {

	/**
	 * 
	 */
	public Enemy() {
		super(Unit.class);
	}

	@Override
	public Unit evaluate() throws NullPointerException {
		Faction thisFaction = this.getRunner().getExecutingUnit().getFaction();
		Set<Unit> units = this.getRunner().getExecutingWorld().getUnits();
		units.removeIf(unit -> unit.getFaction()==thisFaction || unit.isFalling());
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