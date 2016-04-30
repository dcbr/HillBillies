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
public class And implements Expression<Boolean>{

    private final Expression<Boolean> left, right;
    private final SourceLocation sourceLocation;

    /**
     *
     */
    public And(Expression<Boolean> left, Expression<Boolean> right, SourceLocation sourceLocation) {
        this.left = left;
        this.right = right;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Boolean evaluate(TaskRunner taskRunner) {
        return left.evaluate(taskRunner) && right.evaluate(taskRunner);
    }


}
