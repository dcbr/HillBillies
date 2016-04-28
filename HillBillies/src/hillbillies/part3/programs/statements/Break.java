package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task;
import org.stringtemplate.v4.ST;

/**
 * Created by Bram on 28-4-2016.
 */
public class Break extends Statement {

    public Break(SourceLocation sourceLocation){

    }

    @Override
    public void execute(Task.TaskBuilder taskBuilder) {
        taskBuilder.breakLoop = true;
    }
}
