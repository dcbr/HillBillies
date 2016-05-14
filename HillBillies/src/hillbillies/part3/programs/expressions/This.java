package hillbillies.part3.programs.expressions;


import hillbillies.model.Unit;


/**
 * @author kenneth
 *
 */
public class This extends Expression<Unit> {

	/**
	 * 
	 */
	public This() {
		super();
	}

	@Override
	public Unit evaluate() throws NullPointerException {
		return this.getRunner().getExecutingUnit();
	}

}