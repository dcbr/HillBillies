/**
 *
 */
package hillbillies.part3.programs.expressions;

/**
 * @author kenneth
 *
 */
public class True extends Expression<Boolean> {

    /**
     *
     */
    public True() {
        super();
    }

    @Override
    public Boolean evaluate() {
        return true;
    }


}
