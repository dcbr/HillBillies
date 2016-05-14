/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;

/**
 * @author kenneth
 *
 */
public class CarriesItem extends UnaryExpression<Unit, Boolean> {

	/**
	 * 
	 */
	public CarriesItem(Expression<Unit> unit) throws IllegalArgumentException {
		super(unit);
	}

	@Override
	protected Boolean compute(Unit unit) {
		return unit.isCarryingMaterial();
	}

}
