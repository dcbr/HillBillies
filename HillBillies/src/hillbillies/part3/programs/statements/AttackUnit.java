package hillbillies.part3.programs.statements;

import hillbillies.activities.None;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;

/**
 * @author kenneth
 *
 */
public class AttackUnit extends Statement{
	private final Expression<Unit> unit;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public AttackUnit(Expression<Unit> unit, SourceLocation sourceLocation) {
		super(unit);
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}

	@Override
	protected void execute() {
		Unit attacker = this.getRunner().getExecutingUnit();
		attacker.attack(unit.run());
		this.getRunner().waitFor(unit -> unit.isExecuting(None.class));
	}

}
