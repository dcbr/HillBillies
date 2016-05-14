package hillbillies.tests.programs;

import hillbillies.part3.programs.expressions.Expression;
import hillbillies.part3.programs.TaskFactory;
import hillbillies.part3.programs.expressions.HerePosition;
import hillbillies.part3.programs.expressions.True;
import hillbillies.part3.programs.statements.Print;
import org.junit.Test;

/**
 * Created by Bram on 27-4-2016.
 */
public class TaskFactoryTest {
    @Test
    public void createWhile() throws Exception {
        TaskFactory f = new TaskFactory();
        f.createWhile(new HerePosition(),new Print(new True()), null);
    }

}