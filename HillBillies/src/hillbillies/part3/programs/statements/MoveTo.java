package hillbillies.part3.programs.statements;

import hillbillies.activities.None;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class MoveTo extends Statement{
	private final Expression<Vector> position;
	private final SourceLocation sourceLocation;
	
	/**
	 * 
	 */
	public MoveTo(Expression<Vector> position, SourceLocation sourceLocation) {
		super(position);
		this.position = position;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute() {
		this.getRunner().getExecutingUnit().moveToTarget(position.run().getCubeCoordinates());
		this.getRunner().waitFor(unit -> unit.isExecuting(None.class));
	}

}
