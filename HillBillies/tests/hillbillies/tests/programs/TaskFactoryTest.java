package hillbillies.tests.programs;

import hillbillies.part3.programs.expressions.Expression;
import hillbillies.part3.programs.TaskFactory;
import hillbillies.part3.programs.expressions.False;
import hillbillies.part3.programs.expressions.HerePosition;
import hillbillies.part3.programs.expressions.True;
import hillbillies.part3.programs.statements.Print;
import hillbillies.part3.programs.statements.While;
import org.junit.Test;

/**
 * Created by Bram on 27-4-2016.
 */
public class TaskFactoryTest {
    @Test
    public void createWhile() throws Exception {
        TaskFactory f = new TaskFactory();
        f.createWhile(new HerePosition(),new Print(new True()), null);// Dit gaat wel, maar gaat classcastException throwen zodra het wordt uitgevoerd
        //new While(new HerePosition(),new Print(new True()));// Dit lukt niet
        new While(new False(),new Print(new False()));
    }

}