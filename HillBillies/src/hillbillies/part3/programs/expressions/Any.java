package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.model.IWorldObject;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;


/**
 * @author kenneth
 *
 */
public class Any extends Expression<Unit> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public Any(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Unit evaluate() {
		Unit thisUnit = this.getRunner().getExecutingUnit();
		Set<IWorldObject> any = new HashSet<>();
		Set<Unit> units = this.getRunner().getExecutingWorld().getUnits();
		for (Unit unit : units){
			if(unit != thisUnit)
				any.add(unit);
		}
		if (!any.isEmpty()){
			this.getTask().stopRunning();
			return null;
		}
		TargetMove targetmove = new TargetMove(this.getRunner().getExecutingUnit(), any);
		Unit NearestUnit = (Unit) targetmove.getNearestObject();
		if(NearestUnit == null)
			this.getTask().stopRunning();
		return NearestUnit;
	}
}