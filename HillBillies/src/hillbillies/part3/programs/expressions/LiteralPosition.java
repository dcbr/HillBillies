package hillbillies.part3.programs.expressions;

import hillbillies.utils.Vector;


/**
 * @author kenneth
 *
 */
public class LiteralPosition extends Expression<Vector> {

	private Vector position;
	/**
	 * 
	 */
	public LiteralPosition(int x, int y, int z) {
		super();
		this.position = new Vector(x,y,z);
	}

	@Override
	public Vector evaluate() {
		return position;
	}

}