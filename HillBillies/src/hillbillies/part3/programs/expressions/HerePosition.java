package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class HerePosition implements Expression<Vector>{

    private final SourceLocation sourceLocation;

    /**
     *
     */
    public HerePosition(SourceLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Vector evaluate(TaskRunner taskRunner) {
        return taskRunner.getExecutingUnit().getPosition();
    }


}
