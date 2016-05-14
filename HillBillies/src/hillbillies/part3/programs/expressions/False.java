/**
 *
 */
package hillbillies.part3.programs.expressions;

/**
 * @author kenneth
 *
 */
public class False extends Expression<Boolean> {

    /**
     *
     */
    public False() {
        super();
    }

    @Override
    public Boolean evaluate() throws NullPointerException {
        return false;
    }


}
