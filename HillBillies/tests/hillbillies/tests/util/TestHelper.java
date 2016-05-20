package hillbillies.tests.util;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.expressions.Expression;
import hillbillies.part3.programs.statements.Statement;

/**
 * Created by Bram on 19-5-2016.
 */
public class TestHelper {

    /**
     * Helper method to advance time for the given world by some time.
     *
     * @param time
     *            The time, in seconds, to advance.
     * @param step
     *            The step size, in seconds, by which to advance.
     */
    public static void advanceTimeFor(World world, double time, double step) throws IllegalArgumentException {
        if(step>0.2d || step<=0d)
            throw new IllegalArgumentException("Illegal step");
        if(world==null)
            throw new IllegalArgumentException("Illegal world");
        if(time<0d)
            throw new IllegalArgumentException("Illegal time");
        int n = (int) (time / step);
        for (int i = 0; i < n; i++)
            world.advanceTime(step);
        if(time - n * step > 0)
            world.advanceTime(time - n * step);
    }

    /**
     * Helper method to advance time for the given world by some time.
     *
     * @param time
     *            The time, in seconds, to advance.
     * @effect
     *          AdvanceTimeFor with step size equal to 0.2
     */
    public static void advanceTimeFor(World world, double time) throws IllegalArgumentException {
        advanceTimeFor(world, time, 0.2d);
    }

    public static void runStatementFor(Unit unit, Statement s, double time, double step){
        if(!(unit.getWorld() instanceof World))
            throw new IllegalArgumentException("Unit must be part of a valid World.");
        Task t = new Task("t", 100, s, new int[]{0,0,0});
        unit.getFaction().getScheduler().addTask(t);
        unit.getFaction().getScheduler().schedule(t, unit);
        if(!unit.isDefaultActive())
            unit.startDefaultBehaviour();
        advanceTimeFor((World)unit.getWorld(), time, step);
    }

    public static void runStatementFor(Unit unit, Statement s, double time){
        runStatementFor(unit, s, time, 0.2d);
    }

}
