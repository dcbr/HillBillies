package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task;
import hillbillies.part3.programs.expressions.Expression;
/**
 * 
 * @author kenneth
 *
 */
public class Print extends Statement {
	private final Expression value;
	private final SourceLocation sourceLocation;
	public Print(Expression value, SourceLocation sourceLocation) {
		this.value = value;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Task.TaskBuilder taskBuilder) {
		System.out.println(value.evaluate());
	}

}
