package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * Created by Bram on 28-4-2016.
 */
public class Break extends Statement {

    public Break(SourceLocation sourceLocation){

    }

    @Override
    public void execute(TaskRunner taskRunner) {
        taskRunner.breakLoop = true;
    }
}
