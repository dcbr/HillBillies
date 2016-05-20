package hillbillies.tests.programs;

import hillbillies.model.*;
import hillbillies.part3.programs.*;
import hillbillies.part3.programs.expressions.*;
import hillbillies.part3.programs.statements.*;
import hillbillies.utils.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static hillbillies.tests.util.TestHelper.advanceTimeFor;
import static hillbillies.tests.util.TestHelper.runStatementFor;

/**
 * Test class for the TaskFactory class
 * @author Kenneth & Bram
 * @version 1.0
 */
public class TaskFactoryTest {

    private static TaskFactory f;
    private static World w;
    private static Unit u;
    private static Statement defaultActivity;
    private Task t;

    @BeforeClass
    public static void setUpClass(){
        f = new TaskFactory();
        int[][][] terrain = new int[3][3][10];
        for(int i=1;i<8;i+=2) {
            terrain[1][1][i] = Terrain.ROCK.getId();
            terrain[1][1][i+1] = Terrain.WOOD.getId();
        }
        terrain[1][1][0] = Terrain.WORKSHOP.getId();
        w = new World(terrain, null);
        u = new Unit(w, "Unit", new Vector(0,0,0));
        defaultActivity = new Print(new LiteralPosition(0,0,0));
    }

    @Before
    public void setUp() throws Exception {
        t = new Task("test", 100, defaultActivity, new int[]{1,1,0});
        u.getFaction().getScheduler().addTask(t);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createTasks() throws Exception {

    }

    @Test
    public void createAssignment() throws Exception {
        Statement a1 = f.createAssignment("test", new True(), null);
        Statement a2 = f.createAssignment("test", new HerePosition(), null);
        Statement s = f.createSequence(Arrays.asList(a1, a2), null);// TODO: moet classcastexception throwen maar doet het niet

        runStatementFor(u, s, 0.2);
    }

    @Test
    public void createWhile() throws Exception {
        //new While(new HerePosition(),new Print(new True()));// Dit lukt niet
        new While(new False(),new Print(new False()));

        Expression<Boolean> condition = f.createIsAlive(f.createThis(null),null);
        Statement body = f.createPrint(new LiteralPosition(0,0,0),null);
        Statement whileStmt = f.createWhile(condition, body, null);
        t = new Task("task", 100, whileStmt, new int[]{0,0,0});
        u.getFaction().getScheduler().schedule(t, u);

        advanceTimeFor(w, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWhileIllegal() throws IllegalArgumentException{
        f.createWhile(new HerePosition(),new Print(new True()), null);// Dit gaat wel, maar gaat illegalArgumentException throwen
    }

    @Test
    public void createIf() throws Exception {

    }

    @Test
    public void createBreak() throws Exception {

    }

    @Test
    public void createPrint() throws Exception {

    }

    @Test
    public void createSequence() throws Exception {

    }

    @Test
    public void createMoveTo() throws Exception {

    }

    @Test
    public void createWork() throws Exception {

    }

    @Test
    public void createFollow() throws Exception {

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

    }

    @Test
    public void createLiteralPosition() throws Exception {

    }

    @Test
    public void createThis() throws Exception {

    }

    @Test
    public void createFriend() throws Exception {

    }

    @Test
    public void createEnemy() throws Exception {

    }

    @Test
    public void createAny() throws Exception {

    }

    @Test
    public void createTrue() throws Exception {

    }

    @Test
    public void createFalse() throws Exception {

    }

}