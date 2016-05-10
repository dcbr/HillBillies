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
public class WorkAt extends Statement{
	private final Expression<Cube> position;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public WorkAt(Expression<Cube> position, SourceLocation sourceLocation) {
		super(position);
		this.position = position;
		this.sourceLocation = sourceLocation;
	}
	@Override
	public void execute() {
		this.getRunner().getExecutingUnit().work(position.run().getPosition());
		this.getRunner().waitFor(unit -> unit.isExecuting(None.class));
	}

}
