package hillbillies.part3.programs.expressions;


import java.util.HashSet;
import java.util.Set;

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
		Set<Vector> positions = new HashSet<>();
		Set<Unit> units = this.getRunner().getExecutingWorld().getUnits();
		for (Unit unit : units){
			if(unit.getFaction() != this.getRunner().getExecutingUnit().getFaction())
				positions.add(unit.getPosition().getCubeCoordinates());
		}
		return //TODO;
	}
}