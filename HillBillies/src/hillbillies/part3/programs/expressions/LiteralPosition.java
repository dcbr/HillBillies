package hillbillies.part3.programs.expressions;

import hillbillies.utils.Vector;


/**
 * Class representing the LiteralPosition Vector Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class LiteralPosition extends Expression<Vector> {

	private Vector position;
	/**
	 * 
	 */
	public LiteralPosition(int x, int y, int z) {
		super(Vector.class);
		this.position = new Vector(x,y,z);
	}

	@Override
	public Vector evaluate() {
		return position;
	}

}