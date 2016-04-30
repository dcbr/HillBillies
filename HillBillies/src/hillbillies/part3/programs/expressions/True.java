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
public class True implements Expression<Boolean>{

    private final SourceLocation sourceLocation;

    /**
     *
     */
    public True(SourceLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Boolean evaluate(TaskRunner taskRunner) {
        return true;
    }


}
