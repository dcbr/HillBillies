package hillbillies.part3.programs;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.expressions.*;
import hillbillies.part3.programs.statements.*;
import hillbillies.utils.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing the ITaskFactory interface
 * @author Kenneth & Bram
 * @version 1.0
 */
public class TaskFactory implements ITaskFactory<Expression<?>, Statement, Task> {

    /**
     * Create a list of tasks from the given arguments.
     *
     * @param name          The name of the task
     * @param priority      The initial priority of the task
     * @param activity      The activity of the task. Most likely this is a sequence
     *                      statement.
     * @param selectedCubes A list of cube coordinates (each represented as an array {x,
     *                      y, z}) that were selected by the player in the GUI.
     * @return A list of new task instances. One task instance should be created
     * for each selectedCube coordinate. If selectedCubes is empty and
     * the 'selected' expression does not occur in the activity, a list
     * with exactly one Task instance should be returned.
     */
    @Override
    public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
        List<Task> tasks = new ArrayList<>();
        for(int[] cubeCoordinates : selectedCubes)
            tasks.add(new Task(name, priority, activity, cubeCoordinates));
        if(selectedCubes.isEmpty())
            tasks.add(new Task(name, priority, activity, null));
        return tasks;
    }

    /**
     * Create a statement that represents the assignment of a variable.
     *
     * @param variableName   The name of the assigned variable
     * @param value          The value of the assigned variable
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createAssignment(String variableName, Expression<?> value, SourceLocation sourceLocation) {
        return new Assignment<>(variableName, value);
    }

    /**
     * Create a statement that represents a while loop.
     *
     * @param condition      The condition of the while loop
     * @param body           The body of the loop (most likely this is a sequence
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createWhile(Expression<?> condition, Statement body, SourceLocation sourceLocation) {
        return new While((Expression<Boolean>)condition, body);
    }

    /**
     * Create an if-then-else statement.
     *
     * @param condition      The condition of the if statement
     * @param ifBody         * The body of the if-part, which must be executed when the
     *                       condition evaluates to true
     * @param elseBody       The body of the else-part, which must be executed when the
     *                       condition evaluates to false. Can be null if no else clause is
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createIf(Expression<?> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
        if(elseBody!=null)
            return new IfElse((Expression<Boolean>)condition, ifBody, elseBody);
        else
            return new If((Expression<Boolean>)condition, ifBody);
    }

    /**
     * Create a break statement that immediately terminates the enclosing loop.
     *
     * @param sourceLocation The location in the source code of this statement
     * @note Students working alone may return null.
     */
    @Override
    public Statement createBreak(SourceLocation sourceLocation) {
        return new Break();
    }

    /**
     * Create a print statement that prints the value obtained by evaluating the
     * given expression.
     *
     * @param value          The expression to evaluate and print
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createPrint(Expression<?> value, SourceLocation sourceLocation) {
        return new Print(value);
    }

    /**
     * Create a sequence of statements.
     *
     * @param statements     The statements that must be executed in the given order.
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
        return new Sequence(statements);
    }

    /**
     * Create a moveTo statement
     *
     * @param position       The position to which to move
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createMoveTo(Expression<?> position, SourceLocation sourceLocation) {
    	return new MoveTo((Expression<Vector>)position);
    }

    /**
     * Create a work statement
     *
     * @param position       The position on which to work
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createWork(Expression<?> position, SourceLocation sourceLocation) {
        return new WorkAt((Expression<Vector>)position);
    }

    /**
     * Create a follow statement
     *
     * @param unit           The unit to follow
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createFollow(Expression<?> unit, SourceLocation sourceLocation) {
        return new FollowUnit((Expression<Unit>)unit); //TODO
    }

    /**
     * Create an attack statement
     *
     * @param unit           The unit to attack
     * @param sourceLocation The location in the source code of this statement
     */
    @Override
    public Statement createAttack(Expression<?> unit, SourceLocation sourceLocation) {
        return new AttackUnit((Expression<Unit>)unit);
    }

    /**
     * Create an expression that evaluates to the current value of the given
     * variable.
     *
     * @param variableName   The name of the variable to read.
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<?> createReadVariable(String variableName, SourceLocation sourceLocation) {
        return new ReadVariable(variableName);
    }

    /**
     * Create an expression that evaluates to true when the given position
     * evaluates to a solid position; false otherwise.
     *
     * @param position       The position expression
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createIsSolid(Expression<?> position, SourceLocation sourceLocation) {
        return new IsSolid((Expression<Vector>)position);
    }

    /**
     * Create an expression that evaluates to true when the given position
     * evaluates to a passable position; false otherwise.
     *
     * @param position       The position expression
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createIsPassable(Expression<?> position, SourceLocation sourceLocation) {
        return new IsPassable((Expression<Vector>)position);
    }

    /**
     * Create an expression that evaluates to true when the given unit evaluates
     * to a unit of the same faction; false otherwise.
     *
     * @param unit           The unit expression
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createIsFriend(Expression<?> unit, SourceLocation sourceLocation) {
        return new IsFriend((Expression<Unit>)unit);
    }

    /**
     * Create an expression that evaluates to true when the given unit evaluates
     * to a unit of another faction; false otherwise.
     *
     * @param unit           The unit expression
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createIsEnemy(Expression<?> unit, SourceLocation sourceLocation) {
        return new IsEnemy((Expression<Unit>)unit);
    }

    /**
     * Create an expression that evaluates to true when the given unit evaluates
     * to a unit that is alive; false otherwise.
     *
     * @param unit           The unit expression
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createIsAlive(Expression<?> unit, SourceLocation sourceLocation) {
        return new IsAlive((Expression<Unit>)unit);
    }

    /**
     * Create an expression that evaluates to true when the given unit evaluates
     * to a unit that carries an item; false otherwise.
     *
     * @param unit           The unit expression
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createCarriesItem(Expression<?> unit, SourceLocation sourceLocation) {
        return new CarriesItem((Expression<Unit>)unit);
    }

    /**
     * Create an expression that evaluates to true when the given expression
     * evaluates to false, and vice versa.
     *
     * @param expression        The expression to negate. This must be a Boolean expression
     * @param sourceLocation    The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createNot(Expression<?> expression, SourceLocation sourceLocation) {
        return new Not((Expression<Boolean>)expression);
    }

    /**
     * Create an expression that evaluates to true when both the left and right
     * expression evaluate to true; false otherwise.
     *
     * @param left           The left expression. This must be a Boolean Expression
     * @param right          The right expression. This must be a Boolean Expression
     * @param sourceLocation The location in the source code of this expression
     * @note short-circuit: the right expression does not need to be evaluated
     * when the left expression evaluates to false.
     */
    @Override
    public Expression<Boolean> createAnd(Expression<?> left, Expression<?> right, SourceLocation sourceLocation) {
        return new And((Expression<Boolean>)left, (Expression<Boolean>)right);
    }

    /**
     * Create an expression that evaluates to false only when the left and right
     * expression evaluate to false; true otherwise.
     *
     * @param left           The left expression. This must be a Boolean Expression
     * @param right          The right expression. This must be a Boolean Expression
     * @param sourceLocation The location in the source code of this expression
     * @note short-circuit: the right expression does not need to be evaluated
     * when the left expression evaluates to true.
     */
    @Override
    public Expression<Boolean> createOr(Expression<?> left, Expression<?> right, SourceLocation sourceLocation) {
        return new Or((Expression<Boolean>)left, (Expression<Boolean>)right);
    }

    /**
     * Create an expression that evaluates to the current position of the unit
     * that is executing the task.
     *
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Vector> createHerePosition(SourceLocation sourceLocation) {
        return new HerePosition();
    }

    /**
     * Create an expression that evaluates to the position of a log.
     *
     * @param sourceLocation The location in the source code of this expression
     * @note for groups of two students, this needs to be the log closest to the
     * unit that is executing the task.
     */
    @Override
    public Expression<Vector> createLogPosition(SourceLocation sourceLocation) {
        return new LogPosition();
    }

    /**
     * Create an expression that evaluates to the position of a boulder.
     *
     * @param sourceLocation The location in the source code of this expression
     * @note for groups of two students, this needs to be the boulder closest to
     * the unit that is executing the task.
     */
    @Override
    public Expression<Vector> createBoulderPosition(SourceLocation sourceLocation) {
        return new BoulderPosition();
    }

    /**
     * Create an expression that evaluates to the position of a workshop.
     *
     * @param sourceLocation The location in the source code of this expression
     * @note for groups of two students, this needs to be the workshop closest
     * to the unit that is executing the task.
     */
    @Override
    public Expression<Vector> createWorkshopPosition(SourceLocation sourceLocation) {
        return new WorkshopPosition();
    }

    /**
     * Create an expression that evaluates to the position selected by the user
     * in the GUI.
     *
     * @param sourceLocation The location in the source code of this expression
     * @note Students working alone may return null.
     */
    @Override
    public Expression<Vector> createSelectedPosition(SourceLocation sourceLocation) {
        return new SelectedPosition();
    }

    /**
     * Create an expression that evaluates to a position next to the given
     * position.
     *
     * @param position       The position. This must be a Vector Expression
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Vector> createNextToPosition(Expression<?> position, SourceLocation sourceLocation) {
        return new NextToPosition((Expression<Vector>)position); //TODO zie NTP
    }

    /**
     * Create an expression that evaluates to the position of the given unit.
     *
     * @param unit           The unit. This must be a Unit Expression
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Vector> createPositionOf(Expression<?> unit, SourceLocation sourceLocation) {
        return new PositionOfUnit((Expression<Unit>)unit);
    }

    /**
     * Create an expression that evaluates to a static position with a given
     * coordinate.
     *
     * @param x              The x coordinate of the literal position
     * @param y              The y coordinate of the literal position
     * @param z              The z coordinate of the literal position
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Vector> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
        return new LiteralPosition(x, y, z);
    }

    /**
     * Create an expression that evaluates to the unit that is currently
     * executing the task.
     *
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Unit> createThis(SourceLocation sourceLocation) {
        return new This();
    }

    /**
     * Create an expression that evaluates to a unit that is part of the same
     * faction as the unit currently executing the task.
     *
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Unit> createFriend(SourceLocation sourceLocation) {
        return new Friend();
    }

    /**
     * Create an expression that evaluates to a unit that is not part of the
     * same faction as the unit currently executing the task.
     *
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Unit> createEnemy(SourceLocation sourceLocation) {
        return new Enemy();
    }

    /**
     * Create an expression that evaluates to any unit (other than this).
     *
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Unit> createAny(SourceLocation sourceLocation) {
        return new Any();
    }

    /**
     * Create an expression that evaluates to true.
     *
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createTrue(SourceLocation sourceLocation) {
        return new True();
    }

    /**
     * Create an expression that evaluates to false.
     *
     * @param sourceLocation The location in the source code of this expression
     */
    @Override
    public Expression<Boolean> createFalse(SourceLocation sourceLocation) {
        return new False();
    }
}
