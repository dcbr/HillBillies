package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

import java.util.List;

/**
 * Created by Bram on 28-4-2016.
 */
public class Sequence extends Statement {

    private final List<Statement> statements;
    private final SourceLocation sourceLocation;

    public Sequence(List<Statement> statements, SourceLocation sourceLocation){
        super(statements.toArray(new Statement[]{}));
        this.statements = statements;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public void execute() {
        for(Statement s : statements)
            s.run();
    }
}
