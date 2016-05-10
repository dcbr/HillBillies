package hillbillies.part3.programs.expressions;


import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;


/**
 * @author kenneth
 *
 */
public class Enemy extends Expression<Unit> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public Enemy(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Unit evaluate() {
		return //TODO;
	}
}