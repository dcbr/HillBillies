package hillbillies.part3.programs.expressions;


import hillbillies.model.Unit;


/**
 * Class representing the This Unit Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class This extends Expression<Unit> {

	/**
	 * 
	 */
	public This() {
		super(Unit.class);
	}

	@Override
	public Unit evaluate() throws NullPointerException {
		return this.getRunner().getExecutingUnit();
	}

}