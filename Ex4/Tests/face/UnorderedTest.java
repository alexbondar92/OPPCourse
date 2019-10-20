package OOP.Tests.face;

import OOP.Solution.OOPAfter;
import OOP.Solution.OOPBefore;
import OOP.Solution.OOPSetup;
import OOP.Solution.OOPTest;
import OOP.Solution.OOPTestClass;

import static OOP.Tests.face.TestFunctions.fail;
import static OOP.Tests.face.TestFunctions.shouldPass;

/**
 * Created by elran on 05/01/17.
 */
@OOPTestClass(OOPTestClass.OOPTestClassType.UNORDERED)
public class UnorderedTest {

    protected int a = 0;
    protected int c = 0;
    protected int testSetup = 0;
    @SuppressWarnings("FieldCanBeLocal")
    private int b = 0;

    @OOPSetup()
    private void setupFather() {
        shouldPass(0, testSetup);
        testSetup++;
    }

    @OOPBefore({"overRideThis"})
    private void beforeFather() {
        shouldPass(0, a);
        a++;
    }

    @OOPTest()
    protected void overRideThis() {
        a = 2;
    }

    @OOPAfter("overRideThis")
    private void afterFather() {
        if (!this.getClass().equals(UnorderedTest.class)) {
            shouldPass(4, a);
        } else {
            shouldPass(2, a);
        }
    }

    @OOPTest()
    private void testNotOverridden() {
        shouldPass(0, b);
    }

    @OOPBefore({"testAfterBeforeOverRide"})
    protected void beforeFather2() {
        fail();
    }

    @OOPTest()
    protected void testAfterBeforeOverRide() {
        shouldPass(1, c);
        c++;
    }

    @OOPAfter({"testAfterBeforeOverRide"})
    protected void afterFather2() {
        fail();
    }
}
