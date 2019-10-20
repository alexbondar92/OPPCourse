package OOP.Tests.face;

import OOP.Provided.OOPExpectedException;
import OOP.Solution.OOPAfter;
import OOP.Solution.OOPBefore;
import OOP.Solution.OOPExceptionRule;
import OOP.Solution.OOPSetup;
import OOP.Solution.OOPTest;
import OOP.Solution.OOPTestClass;

import static OOP.Tests.face.TestFunctions.ExceptionDummy;
import static OOP.Tests.face.TestFunctions.WrapThis;
import static OOP.Tests.face.TestFunctions.WrapperAllOptions;
import static OOP.Tests.face.TestFunctions.WrapperCloneable;
import static OOP.Tests.face.TestFunctions.WrapperCopyCtor;
import static OOP.Tests.face.TestFunctions.WrapperNoOther;
import static OOP.Tests.face.TestFunctions.fail;
import static OOP.Tests.face.TestFunctions.shouldPass;
import static OOP.Tests.face.TestFunctions.success;

/**
 * Created by elran on 08/01/17.
 */
@OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
public class TestOrdered {

    @OOPExceptionRule
    public OOPExpectedException expectedException = OOPExpectedException.none();

    public int b = 0;
    public int d = 0;
    protected int c = 0;
    protected int testBackup = 0;
    WrapperAllOptions allOptionsA = new WrapperAllOptions(1), allOptionsB = null;
    WrapperCloneable cloneableA = new WrapperCloneable(2), cloneableB = null;
    WrapperCopyCtor copyCtorA = new WrapperCopyCtor(3), copyCtorB = null;
    WrapperNoOther noOtherA = new WrapperNoOther(4), noOtherB = null;
    WrapThis<WrapperAllOptions> allOptionsT = new WrapThis<>(null);
    WrapThis<WrapperCloneable> cloneableT = new WrapThis<>(null);
    WrapThis<WrapperCopyCtor> copyCtorT = new WrapThis<>(null);
    WrapThis<WrapperNoOther> noOtherT = new WrapThis<>(null);
    private int a = 0;
    private int testMulti = 0;
    private boolean[] ran = new boolean[6];

    @OOPTest(order = 4)
    public void test4() {
        shouldPass(4, a); /// check if nothing else ran between test4 and test3
        a++;
    }

    @OOPTest(order = 2)
    private void test2() {
        shouldPass(1, a); // check if setup runs only once
        a++;
        fail(); //throws exception
    }

    @OOPTest(order = 1)
    private void test1() {
        shouldPass(1, a); // check if setup runs first
        success();
    }

    @OOPBefore({"test3"})
    protected void beforetest3_1() {
        shouldPass(2, a); // check if test1 and test2 ran first
        a++;
    }

    @OOPTest(order = 3)
    protected void test3() {
        shouldPass(3, a); // check if before ran
        a++;
    }

    @OOPSetup
    private void setup() {
        shouldPass(0, a); // check if nothing ran yet
        a++;

        for (int i = 0; i < ran.length; i++) {
            ran[i] = false;
        }
    }

    @OOPAfter({"test4"})
    public void aftertest4_1() {
        shouldPass(5, a); // check if test4 ran
        a++;
    }

    @OOPTest(order = 5)
    public void test5() {
        shouldPass(6, a); // check if after was ran
        shouldPass(1, b);
        b++;
    }

    @OOPBefore({"test5", "test6"})
    private void beforeTests5and6_1() {
        b++;
    }

    @OOPAfter({"test5", "test6"})
    private void afterTests5and6_1() {
        b++;
    }

    @OOPTest(order = 7)
    public void test7() {
        shouldPass(6, b);
    }

    @OOPTest(order = 6)
    public void test6() {
        shouldPass(4, b);
        b++;
    }

    @OOPTest(order = 8)
    public void test8() // this should be a success test
    {
        expectedException.expect(IllegalArgumentException.class);
        throw new IllegalArgumentException();
    }

    @OOPTest(order = 9)
    public void test9() // we expect exception dummy but get AssertionFail therefore should fail
    {
        expectedException.expect(ExceptionDummy.class);
        fail();
    }

    @OOPTest(order = 10)
    public void test10()
    //throws
    //ExceptionDummy// we expect exception failure but get another exception therefore should error
    {
        //this is for the next tests
        noOtherT.a = noOtherA;
        cloneableT.a = cloneableA;
        copyCtorT.a = copyCtorA;
        allOptionsT.a = allOptionsA;

        expectedException.expect(ExceptionDummy.class);
        //throw new ExceptionDummy();
    }

    @OOPBefore({"test11"})
    public void beforeTest11_1()
            throws ExceptionDummy {
        shouldPass(0, testBackup);
        testBackup++;
        throw new ExceptionDummy();
    }

    @OOPTest(order = 11)
    public void test11() {
        fail(); // this test shouldn't run
    }

    @OOPTest(order = 12)
    public void test12() {
        if (getClass() == TestOrdered.class) {
            shouldPass(0, testBackup); // make sure the backup worked
        }
        testBackup++;
    }

    @OOPAfter({"test12"})
    public void aftertest12_1()
            throws ExceptionDummy {
        if (getClass() == TestOrdered.class) {
            shouldPass(1, testBackup); // make sure the backup worked
        }
        testBackup++;
        throw new ExceptionDummy();
    }

    @OOPTest(order = 13)
    public void test13() {
        if (this.getClass().equals(TestOrdered.class)) { //relevant only for father
            shouldPass(1, testBackup); // make sure the backup was right after the after test failed
            shouldPass(6, a);
            shouldPass(6, b); // make sure other fields didnt change
            shouldPass(null, noOtherB);
            if (noOtherT.a != noOtherA) {
                shouldPass(0, 1);//pointers should be the same
            }
            shouldPass(1, cloneableT.a.wasCloned); // make sure clone was called
            shouldPass(null, cloneableB);
            if (cloneableT.a == cloneableA) {
                shouldPass(0, 1);//pointers shouldn't be the same
            }
            shouldPass(1, copyCtorA.wasCopied);// make sure copy ctor was called
            shouldPass(null, copyCtorB);
            if (copyCtorT.a == copyCtorA) {
                shouldPass(0, 1);//pointers shouldn't be the same
            }
            shouldPass(1, allOptionsT.a.whoUsed); // cloneable has priority
            shouldPass(null, allOptionsB);
            if (allOptionsT.a == allOptionsA) {
                shouldPass(0, 1);//pointers shouldn't be the same
            }
        } else {
            //if son all my fields should be shallow?
            if (noOtherT.a != noOtherA) {
                shouldPass(0, 1);//pointers should be the same
            }
            if (cloneableT.a != cloneableA) {
                shouldPass(0, 1);//pointers should be the same
            }
            if (copyCtorT.a != copyCtorA) {
                shouldPass(0, 1);//pointers should be the same
            }
            if (allOptionsT.a != allOptionsA) {
                shouldPass(0, 1);//pointers should be the same
            }
        }
    }

    @OOPBefore({"test14"})
    public void beforeTest14_1() {
        ran[0] = true;
    }

    @OOPBefore({"test14"})
    public void beforeTest14_2() {
        ran[1] = true;
    }

    @OOPBefore({"test14"})
    public void beforeTest14_3() {
        ran[2] = true;
    }

    @OOPTest(order = 14)
    public void test14() {
        for (int i = 0; i < 3; i++) {
            shouldPass(true, ran[i]); // test if multiple before ran
        }
    }

    @OOPAfter({"test14"})
    public void afterTest14_1() {
        ran[3] = true;
    }

    @OOPAfter({"test14"})
    public void afterTest14_2() {
        ran[4] = true;
    }

    @OOPAfter({"test14"})
    public void afterTest14_3() {
        ran[5] = true;
    }

    @OOPTest(order = 15)
    public void test15() {
        for (int i = 0; i < 6; i++) {
            shouldPass(true, ran[i]); // test if multiple before and after ran
        }
    }

    @OOPTest(order = 16)
    protected void test16() {
        c = 1;
    }

    @OOPBefore({"test17"})
    private void FbeforeTest17_1() {
        shouldPass(0, d);
        d++;
    }

    @OOPAfter({"test17"})
    public void FafterTest17_1() {
        shouldPass(4, d);
        d++;
    }
}
