package OOP.Tests.face;

import OOP.Provided.OOPAssertionFailure;
import OOP.Solution.OOPTestSummary;
import OOP.Solution.OOPUnitCore;

/**
 * Created by elran on 08/01/17.
 */
public class TestFunctions {

    public static void fail() {
        testEquals(0, 1);
    }

    public static void success() {
        testEquals(0, 0);
    }

    public static void testEquals(Object a, Object b)
            throws OOPAssertionFailure {
        OOPUnitCore.assertEquals(a, b);
    }

    public static int shouldPass(Object a, Object b) {
        try {
            testEquals(a, b);
            return 1;
        } catch (OOPAssertionFailure e) {
            testFailed(a.toString() + " is not " + b.toString());
        }
        return 0;
    }

    public static int shouldntPass(Object a, Object b) {
        boolean thrown = false;
        try {
            testEquals(a, b);
        } catch (OOPAssertionFailure e) {
            thrown = true;
        }
        if (thrown) {
            testFailed(a.toString() + " is not " + b.toString());
            return 0;
        }
        return 1;
    }

    public static void testFailed(String msg) {
        System.out.println(String.format("\u001B[31mTest failed in file: %s, line: %d, msg: %s\u001B[0m", new Throwable().getStackTrace()[2].getFileName(), new Throwable().getStackTrace()[2].getLineNumber(), msg));
    }

    public static void launchTest(Class<?> aClass, int successNum, int failNum, int errorNum) {
        System.out.println("Launching test on class: " + aClass.getName());
        OOPTestSummary result = OOPUnitCore.runClass(aClass);
        int successCount = 0;
        successCount += shouldPass(successNum, result.getNumSuccesses());
        successCount += shouldPass(failNum, result.getNumFailures());
        successCount += shouldPass(errorNum, result.getNumErrors());
        if (successCount == 3) {
            System.out.println("\u001B[32mTest numbers matched on class: " + aClass.getName() + "\u001B[0m");
        } else {
            System.out.println("\u001B[31mTest numbers mismatch on class: " + aClass.getName() + "\u001B[0m");
        }
    }

    public static class WrapperCloneable
            implements Cloneable {

        Integer a;
        int wasCloned = 0;

        WrapperCloneable(int a) {
            this.a = a;
        }

        @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException", "MethodDoesntCallSuperMethod"})
        @Override
        protected WrapperCloneable clone() {
            wasCloned = 1;
            //noinspection BoxingBoxedValue
            return new WrapperCloneable(new Integer(a));
        }
    }

    public static class WrapperCopyCtor {

        Integer a;
        int wasCopied = 0;

        WrapperCopyCtor(int a) {
            this.a = a;
        }

        WrapperCopyCtor(WrapperCopyCtor another) {
            wasCopied = 1;
            //noinspection BoxingBoxedValue
            this.a = new Integer(another.a);
        }
    }

    public static class WrapThis<E> {

        E a;

        WrapThis(E a) {
            this.a = a;
        }
    }

    public static class WrapperNoOther {

        Integer a;

        WrapperNoOther(int a) {
            this.a = a;
        }
    }

    public static class WrapperAllOptions
            implements Cloneable {

        Integer a;
        int whoUsed = 0;

        WrapperAllOptions(int a) {
            this.a = a;
        }

        WrapperAllOptions(WrapperAllOptions another) {
            if (this != another) {
                whoUsed = 2;
                //noinspection BoxingBoxedValue
                this.a = new Integer(another.a);
            }
        }

        @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException", "MethodDoesntCallSuperMethod"})
        @Override
        protected WrapperAllOptions clone() {
            whoUsed = 1;
            //noinspection BoxingBoxedValue
            return new WrapperAllOptions(new Integer(a));
        }
    }

    public static class ExceptionDummy
            extends Exception {

    }
}
