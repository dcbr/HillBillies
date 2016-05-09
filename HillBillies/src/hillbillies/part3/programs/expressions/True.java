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
public class True extends Expression<Boolean> {

    private final SourceLocation sourceLocation;

    /**
     *
     */
    public True(SourceLocation sourceLocation) {
        super();
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Boolean evaluate() {
        return true;
    }


}
