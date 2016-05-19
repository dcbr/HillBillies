/**
 *
 */
package hillbillies.part3.programs.expressions;

/**
 * Class representing the False Boolean Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class False extends Expression<Boolean> {

    /**
     *
     */
    public False() {
        super(Boolean.class);
    }

    @Override
    public Boolean evaluate() throws NullPointerException {
        return false;
    }


}
