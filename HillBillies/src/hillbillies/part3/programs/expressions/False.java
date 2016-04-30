/**
 *
 */
package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class False implements Expression<Boolean>{

    private final SourceLocation sourceLocation;

    /**
     *
     */
    public False(SourceLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Boolean evaluate(TaskRunner taskRunner) {
        return false;
    }


}
