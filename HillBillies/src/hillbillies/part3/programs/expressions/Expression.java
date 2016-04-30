package hillbillies.part3.programs.expressions;

import hillbillies.model.Task.TaskRunner;

/**
 * Created by Bram on 27-4-2016.
 */
public interface Expression<T> {

    public T evaluate(TaskRunner taskRunner);

}
