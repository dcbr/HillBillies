package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;


/**
 * @author kenneth
 *
 */
public class PositionOfUnit extends Expression<Vector> {
	private Expression<Unit> unit;
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public PositionOfUnit(Expression<Unit> unit, SourceLocation sourceLocation) {
		super(unit);
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Vector evaluate() {
		return unit.evaluate().getPosition();
	}

}