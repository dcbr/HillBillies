/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class SelectedPosition extends Expression<Vector> {

	/**
	 * 
	 */
	public SelectedPosition() {
		super();
	}

	@Override
	public Vector evaluate() throws NullPointerException {
		return this.getRunner().getSelectedCube().getPosition();
	}

}
