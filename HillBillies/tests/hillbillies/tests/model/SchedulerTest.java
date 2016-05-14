package hillbillies.tests.model;

import hillbillies.model.*;
import hillbillies.part3.programs.expressions.LiteralPosition;
import hillbillies.part3.programs.statements.Print;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Bram on 13-5-2016.
 */
public class SchedulerTest {

    private static LobbyWorld lobby = LobbyWorld.lobby;
    private static Faction faction1, faction2;
    private static Scheduler scheduler1, scheduler2;
    private static Unit unit11, unit12, unit13, unit21, unit22;
    private static Task task1, task2;

    @BeforeClass
    public static void setUpClass() throws Exception {
        unit11 = new Unit(lobby);
        unit12 = new Unit(lobby);
        unit13 = new Unit(lobby);
        lobby.addNewFaction();// Create second faction
        unit21 = new Unit(lobby);
        unit22 = new Unit(lobby);
        faction1 = unit11.getFaction();
        faction2 = unit21.getFaction();
        scheduler1 = faction1.getScheduler();
        scheduler2 = faction2.getScheduler();
        task1 = new Task("task1",100,new Print(new LiteralPosition(0,0,0)),new int[]{0,0,0});
        task2 = new Task("task2",200,new Print(new LiteralPosition(0,0,0)),new int[]{0,0,0});
    }

    @Before
    public void setUp() throws Exception {
        if(scheduler1.hasAsTask(task1))
            scheduler1.removeTask(task1);
        if(scheduler1.hasAsTask(task2))
            scheduler1.removeTask(task2);
        if(scheduler2.hasAsTask(task1))
            scheduler2.removeTask(task1);
        if(scheduler2.hasAsTask(task2))
            scheduler2.removeTask(task2);

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

        assertFalse(scheduler2.hasAsTask(null));
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

        assertFalse(scheduler2.hasAsTasks(null));
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

    }

    @Test
    public void getTaskSatisfying() throws Exception {

    }

    @Test
    public void getHighestPriorityAssignableTask() throws Exception {

    }

    @Test
    public void getHighestPriorityNotRunningTask() throws Exception {

    }

    @Test
    public void getAllTasks() throws Exception {

    }

    @Test
    public void schedule() throws Exception {

    }

    @Test
    public void deschedule() throws Exception {

    }

    @Test
    public void iterator() throws Exception {

    }

}