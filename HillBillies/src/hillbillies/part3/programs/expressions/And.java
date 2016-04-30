/**
 *
 */
package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task.TaskBuilder;

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
    public Boolean evaluate(TaskBuilder taskBuilder) {
        return left.evaluate(taskBuilder) && right.evaluate(taskBuilder);
    }


}
