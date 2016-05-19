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
public class WorkAt extends Statement{
	private final Expression<Vector> position;
	/**
	 * 
	 */
	public WorkAt(Expression<Vector> position) throws IllegalArgumentException {
		super(position);
		if(!position.checkType(Vector.class))
			throw new IllegalArgumentException("The given position Expression is not of the generic type Vector.");
		this.position = position;
	}
	@Override
	public void execute() {
		Vector workPosition = this.runChild(position);
		this.getRunner().getExecutingUnit().work(workPosition);
		this.getRunner().waitFor(unit -> unit.isExecuting(None.class));
	}

}
