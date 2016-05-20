package hillbillies.tests.programs;

import hillbillies.activities.Work;
import hillbillies.model.*;
import hillbillies.part3.programs.*;
import hillbillies.part3.programs.expressions.*;
import hillbillies.part3.programs.statements.*;
import hillbillies.utils.Vector;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hillbillies.tests.util.TestHelper.advanceTimeFor;
import static hillbillies.tests.util.TestHelper.runStatementFor;
import static org.junit.Assert.*;

/**
 * Test class for the TaskFactory class
 * @author Kenneth & Bram
 * @version 1.0
 */
public class TaskFactoryTest {

    private static TaskFactory f;
    private static World w;
    private static Unit u;
    private static int[][][] terrain;
    //private static Statement defaultActivity;
    //private Task t;

    @BeforeClass
    public static void setUpClass(){
        f = new TaskFactory();
        terrain = new int[3][3][10];
        for(int i=1;i<8;i+=2) {
            terrain[1][1][i] = Terrain.ROCK.getId();
            terrain[1][1][i+1] = Terrain.WOOD.getId();
        }
        terrain[1][1][0] = Terrain.WORKSHOP.getId();
    }

    @Before
    public void setUp() throws Exception {
        w = new World(terrain, null);
        u = new Unit(w, "Unit", new Vector(0,0,0));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createTasks() throws Exception {
        List<int[]> selectedCubes = new ArrayList<>();
        Statement stmt = f.createPrint(new True(), null);
        List<Task> tasks = f.createTasks("blub", 10, stmt, selectedCubes);

        assertEquals(1, tasks.size());
        assertEquals("blub", tasks.get(0).getName());
        assertEquals(10, tasks.get(0).getPriority());
        assertEquals(stmt, tasks.get(0).getActivity());
        assertEquals(null, tasks.get(0).getSelectedCube());

        selectedCubes.addAll(Arrays.asList(new int[]{0,0,0}, new int[]{1,1,1}));
        tasks = f.createTasks("test", 25, stmt, selectedCubes);

        assertEquals(2, tasks.size());
        for(Task t : tasks){
            assertEquals("test", t.getName());
            assertEquals(25, t.getPriority());
            assertEquals(stmt, t.getActivity());
            assertTrue(t.getSelectedCube().equals(new Vector(0,0,0)) || t.getSelectedCube().equals(new Vector(1,1,1)));
        }
    }

    @Test
    public void createAssignment() throws Exception {
        Statement a = f.createAssignment("assignment", new True(), null);

        runStatementFor(u, a, 0.2);// No exceptions


        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAssignmentIllegal() throws IllegalArgumentException {
        Statement a1 = f.createAssignment("test", new True(), null);
        Statement a2 = f.createAssignment("test", new HerePosition(), null);
        Statement s = f.createSequence(Arrays.asList(a1, a2), null);

        runStatementFor(u, s, 0.2);
    }

    @Test
    public void createWhile() throws Exception {
        //new While(new HerePosition(),new Print(new True()));// Dit lukt niet
        new While(new False(),new Print(new False()));

        Expression<Boolean> condition = f.createIsAlive(f.createAny(null),null);
        Statement body = f.createPrint(new LiteralPosition(0,0,0),null);
        Statement whileStmt = f.createWhile(condition, body, null);

        Unit test = w.spawnUnit(false);

        runStatementFor(u, whileStmt, 0.1, 0.01);// No exceptions

        // Task is still running:
        assertTrue(u.getTask()!=null);
        assertEquals(1, u.getFaction().getScheduler().getNbTasks());

        test.terminate();

        advanceTimeFor(w, 0.1, 0.01);

        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWhileIllegal() throws IllegalArgumentException{
        f.createWhile(new HerePosition(),new Print(new True()), null);// Dit gaat wel, maar gaat illegalArgumentException throwen
    }

    @Test
    public void createIf() throws Exception {
        Expression<Boolean> condition = f.createCarriesItem(f.createThis(null),null);
        Statement ifBody = f.createWork(new LiteralPosition(1,0,0),null);
        Statement elseBody = f.createMoveTo(new LiteralPosition(0,1,0), null);
        Statement stmt = f.createIf(condition, ifBody, elseBody, null);

        new Boulder(w, u);
        runStatementFor(u, stmt, 1 + 500/u.getStrength());

        // Check whether ifBody is executed:
        assertEquals(new Vector(0,0,0), u.getPosition().getCubeCoordinates());
        assertFalse(u.isCarryingBoulder());
        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());

        u.stopDefaultBehaviour();

        runStatementFor(u, stmt, 2);

        // Check whether elseBody is executed:
        assertEquals(new Vector(0,1,0), u.getPosition().getCubeCoordinates());
        assertFalse(u.isCarryingBoulder());
        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
    }

    @Test
    public void createBreak() throws Exception {
        Expression<Boolean> condition = f.createTrue(null);
        Statement body = f.createIf(new CarriesItem(new This()), f.createBreak(null), null, null);
        Statement stmt = f.createWhile(condition, body, null);

        runStatementFor(u, stmt, 0.2);

        // Task is still running:
        assertTrue(u.getTask()!=null);
        assertEquals(1, u.getFaction().getScheduler().getNbTasks());

        new Boulder(w, u);

        advanceTimeFor(w, 0.2);

        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
    }

    @Test
    public void createPrint() throws Exception {
        Statement print = f.createPrint(new True(), null);

        runStatementFor(u, print, 0.2);
        System.out.println("True should be printed.");

        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
    }

    @Test
    public void createSequence() throws Exception {
        Statement sequence = f.createSequence(Arrays.asList(new Print(new True()), new Print(new False())), null);

        runStatementFor(u, sequence, 0.2);
        System.out.println("The above should print True, False.");

        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
    }

    @Test
    public void createMoveTo() throws Exception {
        Statement move = f.createMoveTo(new LiteralPosition(0,1,0), null);

        runStatementFor(u, move, 2);

        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
        assertEquals(new Vector(0,1,0), u.getPosition().getCubeCoordinates());
    }

    @Test
    public void createWork() throws Exception {
        Statement work = f.createWork(new LiteralPosition(1,0,0), null);

        new Boulder(w, w.getCube(new Vector(1,0,0)));

        runStatementFor(u, work, 1 + 500/u.getStrength());

        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
        assertTrue(u.isCarryingBoulder());
    }

    @Test
    public void createFollow() throws Exception {
        Statement follow = f.createFollow(new Any(), null);

        Unit test = new Unit(w, "Test", new Vector(2,2,0));

        runStatementFor(u, follow, 6);

        // Check whether task successfully finished
        assertEquals(null, u.getTask());
        assertEquals(0, u.getFaction().getScheduler().getNbTasks());
        assertTrue(w.getNeighbouringCubesPositions(u.getPosition().getCubeCoordinates()).contains(new Vector(2,2,0)));
    }

    @Test
    public void createAttack() throws Exception {

    }

    @Test
    public void createReadVariable() throws Exception {

    }

    @Test
    public void createIsSolid() throws Exception {

    }

    @Test
    public void createIsPassable() throws Exception {

    }

    @Test
    public void createIsFriend() throws Exception {

    }

    @Test
    public void createIsEnemy() throws Exception {

    }

    @Test
    public void createIsAlive() throws Exception {

    }

    @Test
    public void createCarriesItem() throws Exception {

    }

    @Test
    public void createNot() throws Exception {

    }

    @Test
    public void createAnd() throws Exception {

    }

    @Test
    public void createOr() throws Exception {

    }

    @Test
    public void createHerePosition() throws Exception {

    }

    @Test
    public void createLogPosition() throws Exception {

    }

    @Test
    public void createBoulderPosition() throws Exception {

    }

    @Test
    public void createWorkshopPosition() throws Exception {

    }

    @Test
    public void createSelectedPosition() throws Exception {

    }

    @Test
    public void createNextToPosition() throws Exception {

    }

    @Test
    public void createPositionOf() throws Exception {
    	Expression<Unit> unit= f.createThis(null);
    	Expression<Vector> pos = f.createPositionOf(unit, null);
    	Statement stmt = f.createPrint(pos, null);
    	runStatementFor(u, stmt, 0.1);
    	System.out.println("The above test should print" +  u.getPosition());
    }

    @Test
    public void createLiteralPosition() throws Exception {
    	Expression<Vector> pos = f.createLiteralPosition(5, 5, 5, null);
    	Statement stmt = f.createPrint(pos, null);
    	runStatementFor(u, stmt, 0.1);
    	Vector vec = new Vector (5,5,5);
    	System.out.println("The above test should print" + vec );
    }

    @Test
    public void createThis() throws Exception {
    	Expression<Unit> unit= f.createThis(null);
    	Expression<Vector> pos = f.createPositionOf(unit, null);
    	Statement stmt = f.createPrint(pos, null);
    	runStatementFor(u, stmt, 0.1);
    	System.out.println("The above test should print" +  u.getPosition());
    	stmt =  f.createPrint(unit, null);
    	runStatementFor(u, stmt, 0.1);
    	System.out.println("The above test should print" +  u);
    }

    @Test
    public void createFriend() throws Exception {
    	Faction fact = u.getFaction();
    	Unit u2 = new Unit(w);
    	while(fact!= u2.getFaction())
    		u2 = new Unit(w);
    	Expression<Unit> unit = f.createFriend(null);
    	Statement stmt = f.createPrint(unit, null);
    	runStatementFor(u, stmt, 0.1);
    	System.out.println("The above test should print" +  u2);
    }

    @Test
    public void createEnemy() throws Exception {
    	Unit u2 = new Unit(w);
    	Expression<Unit> unit = f.createEnemy(null);
    	Statement stmt = f.createPrint(unit, null);
    	runStatementFor(u, stmt, 0.1);
    	System.out.println("The above test should print" +  u2);
    }

    @Test
    public void createAny() throws Exception {
    	Expression<Unit> unit = f.createAny(null);
    	Statement stmt = f.createPrint(unit, null);
    	runStatementFor(u, stmt, 0.1, 0.01);
    	System.out.println("The above test should print null");
    	Unit any = new Unit(w);
    	Expression<Unit> unit2 = f.createAny(null);
    	Statement stmt2 = f.createPrint(unit2, null);
    	runStatementFor(u, stmt2, 0.1);
    	System.out.println("The above test should print" +  any);
    	
    }

    @Test
    public void createTrue() throws Exception {
    	System.out.println(u.getTask());
    	Statement stmt = f.createPrint(f.createTrue(null), null);
    	runStatementFor(u, stmt, 0.1);
    	System.out.println("The above test should print 'true'");
    }

    @Test
    public void createFalse() throws Exception {
    	Statement stmt = f.createPrint(f.createFalse(null), null);
    	runStatementFor(u, stmt, 0.2);
    	System.out.println("The above test should print 'false'");
    }

}