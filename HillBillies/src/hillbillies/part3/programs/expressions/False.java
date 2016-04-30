/**
 *
 */
package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task.TaskBuilder;

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
    public Boolean evaluate(TaskBuilder taskBuilder) {
        return false;
    }


}
