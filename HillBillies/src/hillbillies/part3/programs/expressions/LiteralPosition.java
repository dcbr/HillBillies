package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;


/**
 * @author kenneth
 *
 */
public class LiteralPosition extends Expression<Vector> {
	private Vector position;
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public LiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		this.position = new Vector(x,y,z);
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Vector evaluate() {
		return position;
	}

}