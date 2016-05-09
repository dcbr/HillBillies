/**
 *
 */
package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class And extends Expression<Boolean> {

    private final Expression<Boolean> left, right;
    private final SourceLocation sourceLocation;

    /**
     *
     */
    public And(Expression<Boolean> left, Expression<Boolean> right, SourceLocation sourceLocation) {
        super(left, right);
        this.left = left;
        this.right = right;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Boolean evaluate() {
        return left.run() && right.run();
    }


}
