package hillbillies.part3.programs.expressions;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.Faction;
import hillbillies.model.IWorldObject;
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
		Faction thisFaction = this.getRunner().getExecutingUnit().getFaction();
		Set<IWorldObject> enemy = new HashSet<>();
		Set<Unit> units = this.getRunner().getExecutingWorld().getUnits();
		for (Unit unit : units){
			if(unit.getFaction() != thisFaction)
				enemy.add(unit);
		}
		if (!enemy.isEmpty()){
			this.getTask().stopRunning();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), enemy);
		Unit NearestUnit = (Unit) targetmove.getNearestObject();
		if(NearestUnit == null)
			this.getTask().stopRunning();
		return NearestUnit;
	}
}