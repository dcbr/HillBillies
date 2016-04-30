package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.expressions.Expression;

/**
 * Created by Bram on 27-4-2016.
 */
public class Assignment<T> extends Statement {

    private final String variableName;
    private final Expression<T> value;
    private final SourceLocation sourceLocation;

    public Assignment(String variableName, Expression<T> value, SourceLocation sourceLocation){
        this.variableName = variableName;
        this.value = value;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public void execute(TaskRunner taskRunner) {
        taskRunner.assignVariable(variableName, value);
    }
}
