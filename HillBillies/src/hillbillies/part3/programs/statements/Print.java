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

	public Print(Expression<?> value) throws IllegalArgumentException {
		super(value);
		this.value = value;
	}

	@Override
	public void execute() {
		System.out.println(this.runChild(value));
	}

}
