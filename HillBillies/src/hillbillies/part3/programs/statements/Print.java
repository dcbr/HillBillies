package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;
/**
 * 
 * @author kenneth
 *
 */
public class Print extends Statement {
	private final Expression<?> value;
	private final SourceLocation sourceLocation;
	public Print(Expression<?> value, SourceLocation sourceLocation) {
		super(value);
		this.value = value;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute() {
		System.out.println(value.run());
	}

}
