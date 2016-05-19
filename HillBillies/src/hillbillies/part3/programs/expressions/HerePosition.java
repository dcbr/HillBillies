package hillbillies.part3.programs.expressions;

import hillbillies.utils.Vector;

/**
 * Class representing the Here Vector Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class HerePosition extends Expression<Vector> {

    /**
     *
     */
    public HerePosition() {
        super(Vector.class);
    }

    @Override
    public Vector evaluate() throws NullPointerException {
        return (this.getRunner().getExecutingUnit().getPosition());
    }


}
