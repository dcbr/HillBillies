package hillbillies.part3.programs.statements;

import hillbillies.model.Unit;
import hillbillies.activities.Attack;
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
		this.unit = unit;
		this.sourceLocation = sourceLocation;
	}

	@Override
	protected void execute(TaskRunner taskRunner) {
		Unit attacker = taskRunner.getExecutingUnit();
		attacker.attack(unit.evaluate(taskRunner));
	}

}
