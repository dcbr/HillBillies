package hillbillies.tests;

import hillbillies.tests.facade.Part1TestPartial;
import hillbillies.tests.unit.UnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The Test Suite Class
 * @author Kenneth & Bram
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Part1TestPartial.class,
        UnitTest.class
})
public class TestSuite {
}
