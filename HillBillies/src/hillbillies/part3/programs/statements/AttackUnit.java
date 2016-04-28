package hillbillies.part3.programs.statements;

import hillbillies.model.Unit;
import hillbillies.activities.Attack;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task.TaskBuilder;
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
	protected void execute(TaskBuilder taskBuilder) {
		taskBuilder.addActivity(new Attack(null,unit.evaluate(taskBuilder)));
		
	}

}
