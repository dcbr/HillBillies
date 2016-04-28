package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.Task.TaskBuilder;

/**
 * Created by Bram on 27-4-2016.
 */
public interface Expression<T> {

    public T evaluate(TaskBuilder taskBuilder);

}
