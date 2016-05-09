package hillbillies.part3.programs.expressions;

import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class HerePosition extends Expression<Cube> {

    private final SourceLocation sourceLocation;

    /**
     *
     */
    public HerePosition(SourceLocation sourceLocation) {
        super();
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Cube evaluate() {
        return this.getRunner().getExecutingWorld().getCube(this.getRunner().getExecutingUnit().getPosition());
    }


}
