package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class IsSolid extends Expression<Boolean> {
	private final Expression<Vector> position;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public IsSolid(Expression<Vector> position, SourceLocation sourceLocation) {
		super(position);
		this.position = position;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Boolean evaluate() {
		return !this.getRunner().getExecutingWorld().getCube(position.run()).isPassable();
	}

}
