package OOP.Tests.UnitTests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import OOP.Provided.OOPAssertionFailure;
import OOP.Solution.OOPUnitCore;

import static junit.framework.TestCase.fail;

public class PartThreeUnitTests {

    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();
    OOPUnitCore tUnitCore;

    @Test
    public void testFail() {
        mExpectedException.expect(OOPAssertionFailure.class);
        OOPUnitCore.fail();
    }

    @Test
    public void testAssertEqualsUnequal() {
        mExpectedException.expect(OOPAssertionFailure.class);
        String s = "yes";
        String t = "no";

        OOPUnitCore.assertEquals(s, t);
    }

    @Test
    public void testAssertEqualsEqual() {
        String s = "yes";
        String t = "yes";

        try {
            OOPUnitCore.assertEquals(s, t);
        } catch (Exception e) {
            fail();
        }
    }
}
