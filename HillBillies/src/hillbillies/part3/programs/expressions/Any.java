package hillbillies.part3.programs.expressions;


import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;


/**
 * @author kenneth
 *
 */
public class Any extends Expression<Unit> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public Any(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Unit evaluate() {
		return //TODO;
	}
}