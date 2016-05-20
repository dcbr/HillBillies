/**
 * 
 */
package hillbillies.part3.programs.expressions;

import hillbillies.model.Unit;

/**
 * Class representing the CarriesItem Boolean Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class CarriesItem extends UnaryExpression<Unit, Boolean> {

	/**
	 * 
	 */
	public CarriesItem(Expression<Unit> unit) throws IllegalArgumentException {
		super(Boolean.class, Unit.class, unit);
	}

	/**
	 * @param unit The value of the child expression. This value is guaranteed
	 *              to be not null.
	 * @return | unit.isCarryingMaterial()
	 */
	@Override
	protected Boolean compute(Unit unit) {
		return unit.isCarryingMaterial();
	}

}
