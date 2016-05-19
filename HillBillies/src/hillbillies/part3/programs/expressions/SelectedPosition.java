/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.utils.Vector;

/**
 * Class representing the SelectedPosition Vector Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class SelectedPosition extends Expression<Vector> {

	/**
	 * 
	 */
	public SelectedPosition() {
		super(Vector.class);
	}

	@Override
	public Vector evaluate() throws NullPointerException {
		return this.getRunner().getSelectedCube().getPosition();
	}

}
