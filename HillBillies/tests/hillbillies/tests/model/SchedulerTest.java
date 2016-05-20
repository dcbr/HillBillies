package hillbillies.tests.model;

import hillbillies.model.*;
import hillbillies.part3.programs.expressions.LiteralPosition;
import hillbillies.part3.programs.expressions.SelectedPosition;
import hillbillies.part3.programs.statements.Assignment;
import hillbillies.part3.programs.statements.MoveTo;
import hillbillies.part3.programs.statements.Print;
import hillbillies.utils.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Bram on 13-5-2016.
 */
public class SchedulerTest {

    private static LobbyWorld lobby = LobbyWorld.lobby;
    private static Faction faction1, faction2;
    private static Scheduler scheduler1, scheduler2;
    private static Unit unit11, unit12, unit13, unit21, unit22;
    private static Task task1, task2, task3;

    @BeforeClass
    public static void setUpClass() throws Exception {
        unit11 = new Unit(lobby, "UnitAA", new Vector(0,0,0));
        unit12 = new Unit(lobby, "UnitAB", new Vector(1,0,0));
        unit13 = new Unit(lobby, "UnitAC", new Vector(0,1,0));
        lobby.addNewFaction();// Create second faction
        unit21 = new Unit(lobby, "UnitBA", new Vector(0,0,1));
        unit22 = new Unit(lobby, "UnitBB", new Vector(1,1,1));
        faction1 = unit11.getFaction();
        faction2 = unit21.getFaction();
        scheduler1 = faction1.getScheduler();
        scheduler2 = faction2.getScheduler();
        task1 = new Task("task1",100,new Print(new LiteralPosition(0,0,0)),new int[]{0,0,0});
        task2 = new Task("task2",200,new Assignment<>("blub", new SelectedPosition()),new int[]{0,0,0});
        task3 = new Task("task3",200,new MoveTo(new SelectedPosition()),new int[]{0,0,0});
    }

    @Before
    public void setUp() throws Exception {
        if(scheduler1.hasAsTask(task1))
            scheduler1.removeTask(task1);
        if(scheduler1.hasAsTask(task2))
            scheduler1.removeTask(task2);
        if(scheduler1.hasAsTask(task3))
            scheduler1.removeTask(task3);
        if(scheduler2.hasAsTask(task1))
            scheduler2.removeTask(task1);
        if(scheduler2.hasAsTask(task2))
            scheduler2.removeTask(task2);
        if(scheduler2.hasAsTask(task3))
            scheduler2.removeTask(task3);

        scheduler1.addTask(task1);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor(){
        Faction f = new Faction();
        Scheduler s = f.getScheduler();
        assertTrue(s.getFaction()==f);
        assertTrue(s.getNbTasks()==0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorIllegal() throws IllegalArgumentException{
        new Scheduler(null);
    }

    @Test
    public void getFaction() throws Exception {
        assertTrue(scheduler1.getFaction()==faction1);
        assertTrue(scheduler2.getFaction()==faction2);
    }

    @Test
    public void hasProperFaction() throws Exception {
        assertTrue(scheduler1.hasProperFaction());
        assertTrue(scheduler2.hasProperFaction());
    }

    @Test
    public void hasAsTask() throws Exception {
        assertFalse(scheduler2.hasAsTask(task1));
        assertTrue(scheduler1.hasAsTask(task1));
        scheduler1.addTask(task2);
        assertTrue(scheduler1.hasAsTask(task2));
        scheduler1.removeTask(task1);
        assertFalse(scheduler1.hasAsTask(task1));
    }

    @Test(expected = NullPointerException.class)
    public void hasAsTaskIllegal() throws NullPointerException{
        scheduler2.hasAsTask(null);
    }

    @Test
    public void hasAsTasks() throws Exception {
        Collection<Task> tasks = Arrays.asList(task1, task2);
        assertFalse(scheduler2.hasAsTasks(tasks));
        assertFalse(scheduler1.hasAsTasks(tasks));
        scheduler1.addTask(task2);
        assertTrue(scheduler1.hasAsTasks(tasks));
        scheduler1.removeTask(task1);
        assertFalse(scheduler1.hasAsTasks(tasks));
    }

    @Test(expected = NullPointerException.class)
    public void hasAsTasksIllegal() throws NullPointerException{
        scheduler2.hasAsTasks(null);
    }

    @Test
    public void canHaveAsTask() throws Exception {
        assertTrue(scheduler1.canHaveAsTask(task1));
        assertTrue(scheduler1.canHaveAsTask(task2));
        assertFalse(scheduler1.canHaveAsTask(null));
    }

    @Test
    public void hasProperTasks() throws Exception {
        assertTrue(scheduler2.hasProperTasks());
        assertTrue(scheduler1.hasProperTasks());
        scheduler1.addTask(task2);
        assertTrue(scheduler1.hasProperTasks());
    }

    @Test
    public void getNbTasks() throws Exception {
        assertEquals(0, scheduler2.getNbTasks());
        scheduler1.addTask(task2);
        assertEquals(2, scheduler1.getNbTasks());
        scheduler1.removeTask(task1);
        assertEquals(1, scheduler1.getNbTasks());
    }

    @Test
    public void addTask() throws Exception {
        scheduler1.addTask(task2);
        assertTrue(scheduler1.hasAsTask(task2));
        assertTrue(task2.hasAsScheduler(scheduler1));
    }

    @Test
    public void removeTask() throws Exception {
        scheduler1.removeTask(task1);
        assertFalse(scheduler1.hasAsTask(task1));
        assertFalse(task1.hasAsScheduler(scheduler1));
    }

    @Test
    public void replaceTask() throws Exception {
        scheduler1.replaceTask(task1, task2);
        assertFalse(scheduler1.hasAsTask(task1));
        assertTrue(scheduler1.hasAsTask(task2));
        assertFalse(task1.hasAsScheduler(scheduler1));
        assertTrue(task2.hasAsScheduler(scheduler1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceTaskIllegal1() throws IllegalArgumentException{
        scheduler1.replaceTask(null, task2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceTaskIllegal2() throws IllegalArgumentException{
        scheduler1.replaceTask(task2, task2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceTaskIllegal3() throws IllegalArgumentException{
        scheduler1.replaceTask(task1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceTaskIllegal4() throws IllegalArgumentException{
        scheduler1.replaceTask(task1, task1);
    }

    @Test
    public void getAllTasksSatisfying() throws Exception {
        scheduler1.addTask(task2);
        Collection<Task> assignmentTasks = scheduler1.getAllTasksSatisfying(task -> task.getActivity() instanceof Assignment);// task2
        Collection<Task> sameNamedTasks = scheduler1.getAllTasksSatisfying(task -> task.getName().startsWith("task"));// both task1 and task2
        Collection<Task> lowPriorityTasks = scheduler1.getAllTasksSatisfying(task -> task.getPriority()<20);// empty

        assertTrue(assignmentTasks.contains(task2));
        assertFalse(assignmentTasks.contains(task1));
        assertEquals(1, assignmentTasks.size());

        assertTrue(sameNamedTasks.contains(task1));
        assertTrue(sameNamedTasks.contains(task2));
        assertEquals(2, sameNamedTasks.size());

        assertFalse(lowPriorityTasks.contains(task1));
        assertFalse(lowPriorityTasks.contains(task2));
        assertEquals(0, lowPriorityTasks.size());
    }

    @Test
    public void getTaskSatisfying() throws Exception {
        scheduler1.addTask(task2);
        Task assignmentTask = scheduler1.getTaskSatisfying(task -> task.getActivity() instanceof Assignment);// task2
        Task sameNamedTask = scheduler1.getTaskSatisfying(task -> task.getName().startsWith("task"));// task2 because it has higher priority than task1
        Task lowPriorityTask = scheduler1.getTaskSatisfying(task -> task.getPriority() < 20);// null

        assertEquals(task2, assignmentTask);
        assertEquals(task2, sameNamedTask);
        assertEquals(null, lowPriorityTask);
    }

    @Test
    public void getHighestPriorityAssignableTask() throws Exception {
        scheduler1.addTask(task2);
        assertEquals(task2, scheduler1.getHighestPriorityAssignableTask());
        scheduler1.schedule(task2, unit11);
        assertEquals(task1, scheduler1.getHighestPriorityAssignableTask());
        scheduler1.schedule(task1, unit12);
        assertEquals(null, scheduler1.getHighestPriorityAssignableTask());
        scheduler1.deschedule(task2);
        assertEquals(task2, scheduler1.getHighestPriorityAssignableTask());
    }

    @Test
    public void getHighestPriorityNotRunningTask() throws Exception {
        scheduler1.addTask(task2);
        assertEquals(task2, scheduler1.getHighestPriorityNotRunningTask());
        scheduler1.schedule(task2, unit11);
        assertEquals(task1, scheduler1.getHighestPriorityNotRunningTask());
        scheduler1.schedule(task1, unit12);
        assertEquals(null, scheduler1.getHighestPriorityNotRunningTask());
        scheduler1.deschedule(task2);
        assertEquals(task2, scheduler1.getHighestPriorityNotRunningTask());
    }

    @Test
    public void getAllTasks() throws Exception {
        scheduler1.addTask(task2);
        assertTrue(scheduler1.getAllTasks().contains(task1));
        assertTrue(scheduler1.getAllTasks().contains(task2));
    }

    @Test
    public void schedule() throws Exception {
        scheduler1.schedule(task1, unit11);
        assertTrue(task1.getAssignedUnit()==unit11);
        assertTrue(unit11.getTask()==task1);
    }

    @Test
    public void scheduleIllegal() throws Exception {
        scheduler1.schedule(task2, unit11);// task2 is not part of scheduler1
        assertFalse(task2.getAssignedUnit()==unit11);
        assertFalse(unit11.getTask()==task2);

        scheduler1.addTask(task2);
        scheduler1.schedule(task2, unit11);// Valid schedule
        scheduler1.schedule(task2, unit12);// Invalid schedule, task2 is already scheduled
        assertFalse(unit12.getTask()==task2);
        assertFalse(task2.getAssignedUnit()==unit12);
        scheduler1.schedule(task1, unit11);// Invalid schedule, unit11 has already a scheduled task
        assertFalse(unit11.getTask()==task1);
        assertFalse(task1.getAssignedUnit()==unit11);

        scheduler1.schedule(task1, unit21);// Unit's faction has not scheduler1 as its Scheduler
        assertFalse(task1.getAssignedUnit()==unit21);
        assertFalse(unit21.getTask()==task1);
    }

    @Test(expected = NullPointerException.class)
    public void scheduleIllegal2() throws NullPointerException{
        scheduler1.schedule(null, unit11);
    }

    @Test(expected = NullPointerException.class)
    public void scheduleIllegal3() throws NullPointerException{
        scheduler1.schedule(task1, null);
    }

    @Test
    public void deschedule() throws Exception {
        scheduler1.schedule(task1, unit11);
        scheduler2.addTask(task2);
        scheduler2.schedule(task2, unit21);

        scheduler1.deschedule(task2);// No effect
        assertEquals(unit21, task2.getAssignedUnit());
        assertEquals(task2, unit21.getTask());

        scheduler1.deschedule(task1);
        assertFalse(task1.isRunning());
        assertEquals(null, task1.getAssignedUnit());
        assertEquals(null, unit11.getTask());
    }

    @Test(expected = NullPointerException.class)
    public void descheduleIllegal() throws NullPointerException{
        scheduler1.deschedule(null);
    }

    @Test
    public void iterator() throws Exception {
        scheduler1.addTask(task2);
        scheduler1.addTask(task3);
        Iterator<Task> iterator = scheduler1.iterator();
        assertTrue(iterator.next().getPriority()>=iterator.next().getPriority());
        assertEquals(task1, iterator.next());
    }

}