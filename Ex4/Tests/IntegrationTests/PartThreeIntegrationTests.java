package OOP.Tests.IntegrationTests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

import OOP.Provided.OOPExpectedException;
import OOP.Solution.OOPAfter;
import OOP.Solution.OOPBefore;
import OOP.Solution.OOPExceptionRule;
import OOP.Solution.OOPSetup;
import OOP.Solution.OOPTest;
import OOP.Solution.OOPTestClass;
import OOP.Solution.OOPTestSummary;
import OOP.Solution.OOPUnitCore;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PartThreeIntegrationTests {

    public static int counter = 1;
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    @Before
    public void setUp() {
        FirstIntTest.num_of_test_methods = 0;
        FirstIntTest.num_of_before_methods = 0;
        FirstIntTest.num_of_after_methods = 0;
        FirstIntTest.test = 0;
    }

    @Test
    public void testRunningAllSetupMethods() {
        OOPUnitCore.runClass(FirstIntTest.class, "");
        assertEquals(FirstIntTest.test, 2);
    }

    @Test
    public void testRunningAllSetupMethodsInheritance() {
        OOPUnitCore.runClass(SecondIntTest.class, "");
        assertEquals(FirstIntTest.test, 5);
    }

    @Test
    public void testRunningTestMethodsWithTag() {
        OOPUnitCore.runClass(FirstIntTest.class, "yes");
        assertEquals(FirstIntTest.num_of_test_methods, 5);
    }

    @Test
    public void testRunningTestMethodsWithInheritanceNoTag() {
        OOPUnitCore.runClass(SecondIntTest.class, "");
        assertEquals(FirstIntTest.num_of_test_methods, 2);
    }

    @Test
    public void testRunningTestMethodsWithInheritanceWithTag() {
        OOPUnitCore.runClass(SecondIntTest.class, "yes");
        assertEquals(FirstIntTest.num_of_test_methods, 2);
    }

    @Test
    public void testRunningBeforeMethodsNoTag() {
        OOPUnitCore.runClass(FirstIntTest.class, "");
        assertEquals(FirstIntTest.num_of_before_methods, -255);
    }

    @Test
    public void testRunningBeforeBeforeTest() {
        OOPUnitCore.runClass(FirstIntTest.class, "");
        assertEquals(FirstIntTest.num_of_before_methods, -255);
    }

    @Test
    public void testRunningBeforeMethodsWithTag() {
        OOPUnitCore.runClass(FirstIntTest.class, "yes");
        assertEquals(FirstIntTest.num_of_before_methods, -254);
    }

    @Test
    public void testRunningBeforeMethodsNoTagInheritance() {
        OOPUnitCore.runClass(SecondIntTest.class, "");
        assertEquals(FirstIntTest.num_of_before_methods, 2);
    }

    @Test
    public void testRunningBeforeMethodsWithTagInheritance() {
        OOPUnitCore.runClass(SecondIntTest.class, "yes");
        assertEquals(FirstIntTest.num_of_before_methods, 1);
    }

    @Test
    public void testRunningBeforeTestsAfterEachTest() {
        OOPUnitCore.runClass(FirstIntTest.class, "yes");
        assertTrue(FirstIntTest.before_each);
    }

    @Test
    public void testRunningAfterMethodsAfterTest() {
        OOPUnitCore.runClass(FirstIntTest.class, "yes");
        assertEquals(FirstIntTest.num_of_after_methods, 3);
    }

    @Test
    public void testAfterOrderAfterEachTest() {
        OOPUnitCore.runClass(FirstIntTest.class, "yes");
        assertTrue(FirstIntTest.after_each);
    }

    @Test
    public void testRunningAfterMethodsNoTag() {
        OOPUnitCore.runClass(FirstIntTest.class);
        assertEquals(FirstIntTest.num_of_after_methods, 5);
    }

    @Test
    public void testRunningAfterMethodsWithTag() {
        OOPUnitCore.runClass(FirstIntTest.class, "yes");
        assertEquals(FirstIntTest.num_of_after_methods, 3);
    }

    @Test
    public void testRunningAfterMethodsNoTagInheritance() {
        OOPUnitCore.runClass(SecondIntTest.class, "");
        assertEquals(FirstIntTest.num_of_before_methods, 2);
    }

    @Test
    public void testRunningAfterMethodsWithTagInheritance() {
        OOPUnitCore.runClass(SecondIntTest.class, "yes");
        assertEquals(FirstIntTest.num_of_before_methods, 1);
    }

    @Test
    public void testASimplePassingTest() {
        OOPTestSummary testSummary = OOPUnitCore.runClass(ThirdIntTest.class, "test1");
        assertEquals(testSummary.getNumSuccesses(), 1);
    }

    @Test
    public void testASimpleFailingTest() {
        OOPTestSummary testSummary = OOPUnitCore.runClass(ThirdIntTest.class, "test2");
        assertEquals(testSummary.getNumFailures(), 1);
    }

    @Test
    public void testASimpleErrorTest() {
        OOPTestSummary testSummary = OOPUnitCore.runClass(ThirdIntTest.class, "test3");
        assertEquals(testSummary.getNumErrors(), 1);
    }

    @Test
    public void testASimpleCaughtExpectedExceptionWithCorrectMessage() {
        OOPTestSummary testSummary = OOPUnitCore.runClass(ThirdIntTest.class, "test4");
        assertEquals(testSummary.getNumSuccesses(), 1);
    }

    @Test
    public void testASimpleCaughtExpectedExceptionWithIncorrectMessage() {
        OOPTestSummary testSummary = OOPUnitCore.runClass(ThirdIntTest.class, "test5");
        assertEquals(testSummary.getNumExceptionMismatches(), 1);
    }

    @Test
    public void testRunningAnOrderedClass() {
        OOPUnitCore.runClass(FourthIntTest.class, "");
        assertTrue(FourthIntTest.order_test);
    }

    @Test
    public void testTryingToRunClassWithNoTestAnnotation() {
        mExpectedException.expect(IllegalArgumentException.class);
        OOPUnitCore.runClass(FifthIntTest.class);
    }

    @Test
    public void testTryingToRunNull() {
        mExpectedException.expect(IllegalArgumentException.class);
        OOPUnitCore.runClass(FifthIntTest.class);
    }

    @Test
    public void testTryingToRunClassWithNoTestAnnotationTag() {
        mExpectedException.expect(IllegalArgumentException.class);
        OOPUnitCore.runClass(FifthIntTest.class);
    }

    @Test
    public void testTryingToRunNullTag() {
        mExpectedException.expect(IllegalArgumentException.class);
        OOPUnitCore.runClass(FifthIntTest.class);
    }

    @Test
    public void testOrderedTestsWithInheritance() {
        OOPUnitCore.runClass(SixthIntTest.class, "");
        assertTrue(SixthIntTest.inheritance_order_test);
    }

    @Test
    public void testOrderedTestsWithInheritanceUnordered() {
        OOPUnitCore.runClass(EighthIntTest.class, "");
        assertTrue(EighthIntTest.unordered_inheritance_test);
    }

    @Test
    public void testBeforeInheritanceFatherBeforeSon() {
        OOPUnitCore.runClass(SecondIntTest.class, "");
        assertTrue(SecondIntTest.before_inherit_test);
    }

    @Test
    public void testBeforeInheritanceSonBeforeFather() {
        OOPUnitCore.runClass(SecondIntTest.class, "");
        assertTrue(SecondIntTest.after_inherit_test);
    }

    @Test
    public void testNullifyingExpectedBetweenTests() {
        OOPTestSummary testSummary = OOPUnitCore.runClass(NinthIntTest.class);
        assertEquals(testSummary.getNumSuccesses(), 2);
        assertEquals(testSummary.getNumErrors(), 2);
    }

    @Test
    public void testFailingOOPBefore() {
        OOPTestSummary testSummary = OOPUnitCore.runClass(TenthIntTest.class);
        assertEquals(testSummary.getNumErrors(), 1);
    }

    @Test
    public void testFailingOOPAfter() {
        OOPTestSummary testSummary = OOPUnitCore.runClass(EleventhIntTest.class, "");
        assertEquals(testSummary.getNumErrors(), 1);
    }

    @Test
    public void testObjectBackupOOPBeforeCrash() {
        OOPUnitCore.runClass(TenthIntTest.class, "");
        assertEquals(0, TenthIntTest.before_crashed);
    }

    @Test
    public void testObjectBackupOOPAfterCrash() {
        OOPUnitCore.runClass(EleventhIntTest.class, "");
        assertEquals(0, EleventhIntTest.after_crashed);
    }

    @Test
    public void testSkipsTestsIfFailedBefore() {
        OOPUnitCore.runClass(TenthIntTest.class, "");
        assertTrue(TenthIntTest.was_not_invoked);
    }

    @Test
    public void testRunningEmptyStringTestsIfTagless() {
        OOPUnitCore.runClass(FirstIntTest.class);
        assertEquals(6, FirstIntTest.num_of_test_methods);
    }

    @Test
    public void testBackingUpPrivateBefore()
            throws NoSuchFieldException, IllegalAccessException {
        OOPUnitCore.runClass(TenthIntTest.class);
        Field test_field = TenthIntTest.class.getDeclaredField("before_private_crashed");
        test_field.setAccessible(true);
        int test_value = test_field.getInt(TenthIntTest.class);
        assertEquals(0, test_value);
    }

    @Test
    public void testBackingUpPrivateAfter()
            throws NoSuchFieldException, IllegalAccessException {
        OOPUnitCore.runClass(EleventhIntTest.class);
        Field test_field = EleventhIntTest.class.getDeclaredField("after_private_crashed");
        test_field.setAccessible(true);
        int test_value = test_field.getInt(EleventhIntTest.class);
        assertEquals(0, test_value);
    }

    @Test
    public void testObjectBackUpCloningOOPBeforeCrash()
            throws NoSuchFieldException, IllegalAccessException {
        Date before_test_field = (Date) TenthIntTest.class.getDeclaredField("date").get(TenthIntTest.class);
        OOPUnitCore.runClass(TenthIntTest.class);
        Date after_test_field = (Date) TenthIntTest.class.getDeclaredField("date").get(TenthIntTest.class);
        //noinspection deprecation
        assertEquals(1, before_test_field.getDate());
        //noinspection deprecation
        assertEquals(1, before_test_field.getMonth());
        //noinspection deprecation
        assertEquals(1, before_test_field.getYear());
        //noinspection SimplifiableJUnitAssertion
        assertFalse(before_test_field == after_test_field);
    }

    @Test
    public void testObjectBackUpCloningOOPAfterCrash()
            throws NoSuchFieldException, IllegalAccessException {
        Date before_test_field = (Date) EleventhIntTest.class.getDeclaredField("date").get(EleventhIntTest.class);
        OOPUnitCore.runClass(EleventhIntTest.class);
        Date after_test_field = (Date) EleventhIntTest.class.getDeclaredField("date").get(EleventhIntTest.class);
        //noinspection deprecation
        assertEquals(1, before_test_field.getDate());
        //noinspection deprecation
        assertEquals(1, before_test_field.getMonth());
        //noinspection deprecation
        assertEquals(1, before_test_field.getYear());
        //noinspection SimplifiableJUnitAssertion
        assertFalse(before_test_field == after_test_field);
    }

    @Test
    public void testObjectBackUpCopyConstructorOOPBeforeCrash()
            throws NoSuchFieldException, IllegalAccessException {
        String before_test_field = (String) TenthIntTest.class.getDeclaredField("test_string").get(TenthIntTest.class);
        OOPUnitCore.runClass(TenthIntTest.class);
        String after_test_field = (String) TenthIntTest.class.getDeclaredField("test_string").get(TenthIntTest.class);
        //noinspection SimplifiableJUnitAssertion,StringEquality
        assertFalse(before_test_field == after_test_field);
        //noinspection SimplifiableJUnitAssertion
        assertTrue(before_test_field.equals(after_test_field));
        assertEquals(after_test_field, "Hello");
    }

    @Test
    public void testObjectBackUpCopyConstructorOOPAfterCrash()
            throws NoSuchFieldException, IllegalAccessException {
        String before_test_field = (String) EleventhIntTest.class.getDeclaredField("test_string").get(EleventhIntTest.class);
        OOPUnitCore.runClass(EleventhIntTest.class);
        String after_test_field = (String) EleventhIntTest.class.getDeclaredField("test_string").get(EleventhIntTest.class);
        //noinspection SimplifiableJUnitAssertion,StringEquality
        assertFalse(before_test_field == after_test_field);
        //noinspection SimplifiableJUnitAssertion
        assertTrue(before_test_field.equals(after_test_field));
        assertEquals(after_test_field, "Hello");
    }

    @Test
    public void testRunningTestsByOrderOfInheritance() {
        OOPUnitCore.runClass(ThirteenthIntTest.class);
        assertEquals(111111, TwelfthIntTest.orderTest);
    }

    @Test
    public void testBeforeFailedForOneTestNotForOther() {
        OOPTestSummary oopTestSummary = OOPUnitCore.runClass(FourteenthIntClass.class);
        assertEquals(1, oopTestSummary.getNumSuccesses());
    }

    @Test
    public void testAfterFailedForOneTestNotForOther() {
        OOPTestSummary oopTestSummary = OOPUnitCore.runClass(FourteenthIntClass.class);
        assertEquals(1, oopTestSummary.getNumSuccesses());
    }

    @Test
    public void testRunningAllTestWithTaglessClass() {
        OOPTestSummary oopTestSummary = OOPUnitCore.runClass(SecondIntTest.class);
        assertEquals(4, oopTestSummary.getNumSuccesses());
    }

    @OOPTestClass
    public static class FirstIntTest {

        public static int test = 0;
        public static int num_of_test_methods = 0;
        public static int num_of_before_methods = 0;
        public static int num_of_after_methods = 0;
        public static boolean before_each;
        public static boolean after_each;
        public static boolean before_inherit_test = false;
        public static boolean after_inherit_test = false;
        public boolean our_before_went_first = false;
        public boolean our_after_went_last = true;
        private int test_correct_order = 0;
        private boolean before = false;
        private boolean after = false;

        @OOPSetup
        public void first_setUp() {
            test++;
        }

        @OOPSetup
        public void second_SetUP() {
            test++;
        }

        public void third_setUP() {
            test++;
        }

        @OOPTest(order = 1, tag = "yes")
        public void first_test() {
            after = true;
            num_of_test_methods += 4;

            if (test_correct_order == 1) {
                test_correct_order += 10;
            }
        }

        @OOPTest(order = 2)
        public void second_test() {
            num_of_test_methods++;
            before = true;
        }

        @OOPTest(order = 3, tag = "yes")
        public void fourth_test() {
            num_of_test_methods++;
            if (test_correct_order == 11111) {
                test_correct_order += 100000;
            }
        }

        @OOPAfter(value = {"first_test"})
        public void firstAfter() {
            num_of_after_methods++;
            if (!after) {
                num_of_after_methods += 500;
            }

            if (test_correct_order == 11) {
                test_correct_order += 100;
            }
        }

        @OOPAfter(value = {"second_test", "fourth_test"})
        public void secondAfter() {
            if (test_correct_order >= 111111) {
                test_correct_order += 1000000;
            }
            num_of_after_methods++;
        }

        @OOPAfter(value = {"second_test", "fourth_test"})
        public void fourthAfter() {
            if (test_correct_order >= 111111) {
                test_correct_order += 10000000;
                after_each = true;
            }
            our_after_went_last = false;
            num_of_after_methods++;
        }

        @OOPBefore(value = {"second_test", "fourth_test"})
        public void firstBefore() {
            num_of_before_methods++;
            if (!before) {
                num_of_before_methods -= 256;
            }

            test_correct_order += 1000;
        }

        @OOPBefore(value = {"first_test"})
        public void secondBefore() {
            if (test_correct_order == 0) {
                test_correct_order += 1;
            }
            num_of_before_methods++;
        }

        @OOPBefore(value = {"fourth_test"})
        public void fourthBefore() {
            test_correct_order += 10000;
            if (test_correct_order < 111111) {
                before_each = true;
            }
            our_before_went_first = true;
        }
    }

    @OOPTestClass
    public static class SecondIntTest
            extends FirstIntTest {

        @OOPSetup
        public void first_setUp() {
            test += 3;
        }

        @OOPSetup
        public void fourth_setUP() {
            test++;
        }

        @OOPTest(order = 3, tag = "yes")
        public void third_test() {
            num_of_test_methods++;
        }

        @OOPTest(order = 2, tag = "yes")
        public void first_test() {
            num_of_test_methods++;
        }

        @OOPTest(order = 1)
        public void fourth_test() {
            num_of_test_methods++;
        }

        @OOPBefore(value = {"first_test"})
        public void firstBefore() {
            num_of_before_methods++;
        }

        @OOPBefore(value = {"second_test"})
        public void secondBefore() {
            num_of_before_methods++;
        }

        @OOPBefore(value = {"fourth_test"})
        public void thirdBefore() {
            num_of_before_methods++;
            if (our_before_went_first) {
                before_inherit_test = true;
            }
        }

        @OOPAfter(value = {"first_test"})
        public void firstAfter() {
            num_of_after_methods++;
        }

        @OOPAfter(value = {"second_test"})
        public void secondAfter() {
            num_of_after_methods++;
        }

        @OOPAfter(value = {"fourth_test"})
        public void thirdAfter() {

            num_of_after_methods++;
            if (our_after_went_last) {
                after_inherit_test = true;
            }
        }
    }

    @OOPTestClass
    public static class ThirdIntTest {

        @OOPExceptionRule
        public OOPExpectedException mExpectedException = OOPExpectedException.none();

        @OOPTest(order = 1, tag = "test1")
        public void passingTest() {
        }

        @OOPTest(order = 2, tag = "test2")
        public void failingTest() {
            OOPUnitCore.fail();
        }

        @OOPTest(order = 3, tag = "test3")
        public void errorTest()
                throws IOException {
            throw new IOException();
        }

        @OOPTest(order = 4, tag = "test4")
        public void caughtExceptionTest()
                throws IOException {
            mExpectedException.expect(IOException.class);
            mExpectedException.expectMessage("correct message");
            throw new IOException("correct message");
        }

        @OOPTest(order = 5, tag = "test5")
        public void caughtIncorrect()
                throws IOException {
            mExpectedException.expect(IOException.class);
            mExpectedException.expectMessage("incorrect message");
            //noinspection SpellCheckingInspection
            throw new IOException("inco message");
        }
    }

    @OOPTestClass(value = OOPTestClass.OOPTestClassType.ORDERED)
    public static class FourthIntTest {

        public static boolean order_test = false;
        public boolean order_testOne = false;
        public boolean order_testTwo = false;

        @OOPTest(order = 2)
        public void firstTest() {
            order_testOne = true;
        }

        @OOPTest(order = 3)
        public void secondTest() {
            if (order_testOne && order_testTwo) {
                order_test = true;
            }
        }

        @OOPTest(order = 1)
        public void thirdTest() {
            order_testTwo = true;
        }
    }

    public static class FifthIntTest {

    }

    @OOPTestClass(value = OOPTestClass.OOPTestClassType.ORDERED)
    public static class SixthIntTest
            extends FourthIntTest {

        public static boolean inheritance_order_test = false;
        private boolean order_testThree = false;

        @OOPTest(order = 4)
        public void fourthTest() {
            order_testThree = true;
            //noinspection ConstantConditions
            if (order_testOne && !order_testTwo && order_testThree) {
                inheritance_order_test = true;
            }
        }

        @OOPTest(order = 1)
        public void thirdTest() {
            order_testTwo = false;
        }
    }

    @OOPTestClass()
    public static class SeventhIntTest {

        public boolean first = false;
        public boolean second = false;

        @OOPTest(order = 2)
        public void firstTest() {
            first = true;
        }

        @OOPTest(order = 3)
        public void secondTest() {
            second = true;
        }
    }

    @OOPTestClass(value = OOPTestClass.OOPTestClassType.ORDERED)
    public static class EighthIntTest
            extends SeventhIntTest {

        public static boolean unordered_inheritance_test = false;

        @OOPTest(order = 3)
        public void thirdTest() {
            if (first && second) {
                unordered_inheritance_test = true;
            }
        }
    }

    @OOPTestClass
    public static class NinthIntTest {

        @OOPExceptionRule
        public static OOPExpectedException mExpectedException = OOPExpectedException.none();

        @OOPTest(order = 1)
        public void firstTest()
                throws IOException {
            mExpectedException.expect(IOException.class);
            throw new IOException();
        }

        @OOPTest(order = 2)
        public void secondTest()
                throws IOException {
            throw new IOException();
        }

        @OOPTest(order = 3)
        public void thirdTest() {
            mExpectedException.expect(NullPointerException.class);
            throw new NullPointerException();
        }

        @OOPTest(order = 4)
        public void fourthTest() {
            throw new NullPointerException();
        }
    }

    @OOPTestClass
    public static class TenthIntTest {

        public static int before_crashed = 0;
        public static boolean was_not_invoked = true;
        @SuppressWarnings("deprecation")
        public static Date date = new Date(1, 1, 1);
        public static String test_string = "Hello";
        private static int before_private_crashed = 0;

        @OOPBefore(value = {"first_test"})
        public void failing_before()
                throws IOException {
            before_private_crashed = 1;
            before_crashed = 1;
            //noinspection deprecation
            date = new Date(2, 2, 2);
            test_string = "Goodbye";
            throw new IOException();
        }

        @OOPTest(order = 1)
        public void first_test() {
            was_not_invoked = false;
        }

        @OOPTest(order = 2)
        public void second_test() {
        }
    }

    @OOPTestClass
    public static class EleventhIntTest {

        public static int after_crashed = 0;
        @SuppressWarnings("deprecation")
        public static Date date = new Date(1, 1, 1);
        public static String test_string = "Hello";
        private static int after_private_crashed = 0;

        @OOPAfter(value = {"first_test"})
        public void failing_after()
                throws IOException {
            test_string = "Goodbye";
            after_crashed = 1;
            after_private_crashed = 1;
            //noinspection deprecation
            date = new Date(2, 2, 2);
            throw new IOException();
        }

        @OOPTest(order = 1)
        public void first_test() {
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class TwelfthIntTest {

        public static int orderTest = 0;

        @OOPTest(order = 1)
        public void first_test() {
            if (orderTest == 0) {
                orderTest += 1;
            }
        }

        @OOPTest(order = 2)
        public void second_test() {
            if (orderTest == 11) {
                orderTest += 100;
            }
        }

        @OOPTest(order = 3)
        public void third_test() {
            if (orderTest == 1111) {
                orderTest += 10000;
            }
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class ThirteenthIntTest
            extends TwelfthIntTest {

        @OOPTest(order = 1)
        public void first_test_two() {
            if (orderTest == 1) {
                orderTest += 10;
            }
        }

        @OOPTest(order = 2)
        public void second_test_two() {
            if (orderTest == 111) {
                orderTest += 1000;
            }
        }

        @OOPTest(order = 3)
        public void third_test_two() {
            if (orderTest == 11111) {
                orderTest += 100000;
            }
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class FourteenthIntClass {

        @OOPBefore(value = {"first_test", "second_test"})
        public void failing_before()
                throws Exception {
            if (counter == 1) {
                counter--;
                throw new Exception("trash Exception");
            }
        }

        @OOPAfter(value = {"second_test", "third_test"})
        public void failing_after()
                throws Exception {
            if (counter == 0) {
                counter++;
                throw new Exception("trash Exception");
            }
        }

        @OOPTest(order = 1)
        public void first_test() {
        }

        @OOPTest(order = 2)
        public void second_test() {
        }

        @OOPTest(order = 3)
        public void third_test() {
        }
    }
}
