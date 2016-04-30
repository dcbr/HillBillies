package hillbillies.part3.programs.statements;

import hillbillies.model.Task.TaskRunner;

/**
 * Created by Bram on 27-4-2016.
 */
public abstract class Statement {

    public void run(TaskRunner taskRunner){
        if(!taskRunner.breakLoop)
            execute(taskRunner);
    }

    protected abstract void execute(TaskRunner taskRunner);

}
