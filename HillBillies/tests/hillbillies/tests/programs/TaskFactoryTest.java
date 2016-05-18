package hillbillies.tests.programs;

import hillbillies.part3.programs.expressions.Expression;
import hillbillies.part3.programs.TaskFactory;
import hillbillies.part3.programs.expressions.False;
import hillbillies.part3.programs.expressions.HerePosition;
import hillbillies.part3.programs.expressions.True;
import hillbillies.part3.programs.statements.Print;
import hillbillies.part3.programs.statements.While;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the TaskFactory class
 * @author Kenneth & Bram
 * @version 1.0
 */
public class TaskFactoryTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createTasks() throws Exception {

    }

    @Test
    public void createAssignment() throws Exception {

    }

    @Test
    public void createWhile() throws Exception {
        TaskFactory f = new TaskFactory();
        f.createWhile(new HerePosition(),new Print(new True()), null);// Dit gaat wel, maar gaat classcastException throwen zodra het wordt uitgevoerd
        //new While(new HerePosition(),new Print(new True()));// Dit lukt niet
        new While(new False(),new Print(new False()));
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