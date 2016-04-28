package hillbillies.part3.programs.statements;

import hillbillies.part3.programs.Task;

/**
 * Created by Bram on 27-4-2016.
 */
public abstract class Statement {

    public void run(Task.TaskBuilder taskBuilder){
        if(!taskBuilder.breakLoop)
            execute(taskBuilder);
    }

    protected abstract void execute(Task.TaskBuilder taskBuilder);

}
