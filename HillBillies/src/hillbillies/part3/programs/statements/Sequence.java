package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

import java.util.List;

/**
 * Created by Bram on 28-4-2016.
 */
public class Sequence extends Statement {

    private final List<Statement> statements;

    public Sequence(List<Statement> statements) throws IllegalArgumentException{
        super(statements.toArray(new Statement[]{}));
        this.statements = statements;
    }

    @Override
    public void execute() {
        int i = this.getCurrentChild();
        while(i < statements.size())
            this.runChild(statements.get(i++));
    }
}
