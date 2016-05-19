package hillbillies.part3.programs.statements;

import hillbillies.activities.None;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;

/**
 * 
 * @author kenneth
 *
 */
public class FollowUnit extends Statement{

	private final Expression<Unit> unit;

	/**
	 * 
	 */
	public FollowUnit(Expression<Unit> unit) throws IllegalArgumentException {
		super(unit);
		if(!unit.checkType(Unit.class))
			throw new IllegalArgumentException("The given unit Expression is not of the generic type Unit.");
		this.unit = unit;
	}

	@Override
	protected void execute() {
		Unit leader = this.runChild(unit);
		this.getRunner().getExecutingUnit().follow(leader);// TODO: since we have access to the Task, getExecutingUnit is no longer needed
		this.getRunner().waitFor(unit -> unit.isExecuting(None.class));
	}

}
