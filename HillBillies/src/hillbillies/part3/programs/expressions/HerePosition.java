package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class HerePosition extends Expression<Vector> {

    private final SourceLocation sourceLocation;

    /**
     *
     */
    public HerePosition(SourceLocation sourceLocation) {
        super();
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Vector evaluate() {
        return (this.getRunner().getExecutingUnit().getPosition());
    }


}
