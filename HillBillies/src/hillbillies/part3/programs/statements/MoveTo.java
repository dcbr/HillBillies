package hillbillies.part3.programs.statements;

import hillbillies.activities.None;
import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;

/**
 * @author kenneth
 *
 */
public class MoveTo extends Statement{
	private final Expression<Cube> cube;
	private final SourceLocation sourceLocation;
	
	/**
	 * 
	 */
	public MoveTo(Expression<Cube> cube, SourceLocation sourceLocation) {
		super(cube);
		this.cube = cube;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute() {
		this.getRunner().getExecutingUnit().moveToTarget(cube.run().getPosition().getCubeCoordinates());
		this.getRunner().waitFor(unit -> unit.isExecuting(None.class));
	}

}
