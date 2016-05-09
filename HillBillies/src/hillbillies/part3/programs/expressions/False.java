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
public class False extends Expression<Boolean> {

    private final SourceLocation sourceLocation;

    /**
     *
     */
    public False(SourceLocation sourceLocation) {
        super();
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Boolean evaluate() {
        return false;
    }


}
