package hillbillies.part3.programs.expressions;

import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class HerePosition extends Expression<Vector> {

    /**
     *
     */
    public HerePosition() {
        super();
    }

    @Override
    public Vector evaluate() throws NullPointerException {
        return (this.getRunner().getExecutingUnit().getPosition());
    }


}
