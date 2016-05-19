/**
 *
 */
package hillbillies.part3.programs.expressions;

/**
 * Class representing the True Boolean Expression
 * @author Kenneth & Bram
 * @version 1.0
 */
public class True extends Expression<Boolean> {

    /**
     *
     */
    public True() {
        super(Boolean.class);
    }

    @Override
    public Boolean evaluate() {
        return true;
    }


}
