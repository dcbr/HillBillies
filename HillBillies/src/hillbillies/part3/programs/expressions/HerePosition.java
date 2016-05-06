package hillbillies.part3.programs.expressions;

import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class HerePosition implements Expression<Cube>{

    private final SourceLocation sourceLocation;

    /**
     *
     */
    public HerePosition(SourceLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Cube evaluate(TaskRunner taskRunner) {
        return taskRunner.getExecutingWorld().getCube(taskRunner.getExecutingUnit().getPosition());
    }


}
