package hillbillies.tests;

import hillbillies.tests.facade.*;
import hillbillies.tests.model.*;
import hillbillies.tests.programs.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The Test Suite Class
 * @author Kenneth & Bram
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        /*Part1TestPartial.class,
        Part2TestPartial.class,*/
        Part3TestPartial.class,
        UnitTest.class,
        UtilsTest.class,
        WorldTest.class,
        SchedulerTest.class,
        TaskFactoryTest.class,
        MaterialTest.class
})
public class TestSuite {
}
