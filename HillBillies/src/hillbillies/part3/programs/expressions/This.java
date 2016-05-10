package hillbillies.part3.programs.expressions;


import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;


/**
 * @author kenneth
 *
 */
public class This extends Expression<Unit> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public This(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Unit evaluate() {
		return this.getRunner().getExecutingUnit();
	}

}