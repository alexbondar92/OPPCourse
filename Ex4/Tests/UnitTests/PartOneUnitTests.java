package OOP.Tests.UnitTests;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;

import OOP.Provided.OOPExpectedException;
import OOP.Solution.OOPAfter;
import OOP.Solution.OOPBefore;
import OOP.Solution.OOPExceptionRule;
import OOP.Solution.OOPExpectedExceptionImpl;
import OOP.Solution.OOPSetup;
import OOP.Solution.OOPTest;
import OOP.Solution.OOPTestClass;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class PartOneUnitTests
        extends TestUtilityClass {

    OOPExpectedException mException;
    private Annotation tTestClassAnnotation;
    private Annotation tExceptionRuleAnnotation;
    private Annotation tAfterAnnotation;
    private Annotation tTestAnnotation;
    private Annotation tTestTwoAnnotation;
    private Annotation tBeforeAnnotation;
    private Annotation tSetupAnnotation;

    @Before
    public void setUp() {
        mException = OOPExpectedException.none();
        try {
            tTestClassAnnotation = TestOne.class.getAnnotation(OOPTestClass.class);
            tExceptionRuleAnnotation =
                    TestOne.class.
                            getField("mExpectedException")
                            .getAnnotation(OOPExceptionRule.class);

            tAfterAnnotation = TestOne.class.
                    getDeclaredMethod("setAfter").getAnnotation(OOPAfter.class);
            tTestAnnotation = TestOne.class.
                    getDeclaredMethod("testOne").getAnnotation(OOPTest.class);
            tTestTwoAnnotation = TestOne.class.
                    getDeclaredMethod("testTwo").getAnnotation(OOPTest.class);
            tBeforeAnnotation = TestOne.class.
                    getDeclaredMethod("beforeTest").getAnnotation(OOPBefore.class);
            tSetupAnnotation = TestOne.class.
                    getDeclaredMethod("setUp").getAnnotation(OOPSetup.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTestClassAnnotationRuntime() {
        assertNotNull(tTestClassAnnotation);
    }

    @Test
    public void testRuleRuntimeRetention() {
        assertNotNull(tExceptionRuleAnnotation);
    }

    @Test
    public void testAfterAnnotationRetention() {
        assertNotNull(tAfterAnnotation);
    }

    @Test
    public void testBeforeAnnotationRetention() {
        assertNotNull(tBeforeAnnotation);
    }

    @Test
    public void testSetupAnnotationRetention() {
        assertNotNull(tSetupAnnotation);
    }

    @Test
    public void testExpectedExpect() {
        mException.expect(IOException.class);
        assertEquals(mException.getExpectedException(), IOException.class);
    }

    @Test
    public void testGetEmptyExpectedException() {
        //noinspection SimplifiableJUnitAssertion
        assertEquals(mException.getExpectedException(), null);
    }

    @Test
    public void testAssertExceptionInheritance() {
        mException.expect(Exception.class);
        IOException e = new IOException();
        assertTrue(mException.assertExpected(e));
    }

    @Test
    public void testAssertExceptionSubstring() {
        mException.expect(Exception.class);
        mException.expectMessage("ring").expectMessage("on");
        Exception e = new Exception("substring onion");
        Exception e2 = new Exception("strong ringer");
        Exception e3 = new Exception("substring");
        assertTrue(mException.assertExpected(e));
        assertTrue(mException.assertExpected(e2));
        assertFalse(mException.assertExpected(e3));
    }

    @Test
    public void testAssertExceptionFather() {
        mException.expect(IOException.class);
        Exception e = new Exception();
        assertFalse(mException.assertExpected(e));
    }

    @Test
    public void testPassNullAssertException() {
        mException.expect(IOException.class);
        assertFalse(mException.assertExpected(null));
    }

    @Test
    public void testExpectedMultipleMessages() {
        mException.expect(Exception.class);
        try {
            mException.expectMessage("BBB").expectMessage("CCC");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testOOPTestClassDefaultValues() {
        assertEquals(
                getAnnotationMember(tTestClassAnnotation, "value").toString()
                , "UNORDERED");
    }

    @Test
    public void testOOPAfterSetValues() {
        String[] test = {"One"};
        String[] actual = (String[]) getAnnotationMember(tAfterAnnotation, "value");
        //noinspection SimplifiableJUnitAssertion
        assertTrue(test[0].equals(actual[0]));
    }

    @Test
    public void testOOPBeforeSetValues() {
        String[] test = {"One", "Two", "Three"};
        String[] actual = (String[]) getAnnotationMember(tBeforeAnnotation, "value");
        assertEquals(test.length, actual.length);
    }

    @Test
    public void testOOPTestDefaultValues() {
        int firstValue = (int) getAnnotationMember(tTestAnnotation, "order");
        String secondValue = (String) getAnnotationMember(tTestAnnotation, "tag");

        assertEquals(firstValue, 1);
        assertEquals(secondValue, "");
    }

    @Test
    public void testOOPTestSetValues() {
        int firstValue = (int) getAnnotationMember(tTestTwoAnnotation, "order");
        String secondValue = (String) getAnnotationMember(tTestTwoAnnotation, "tag");

        assertEquals(firstValue, 2);
        assertEquals(secondValue, "Breakfast");
    }

    @OOPTestClass
    public class TestOne {

        @OOPExceptionRule
        public OOPExpectedException mExpectedException = OOPExpectedExceptionImpl.none();

        @OOPBefore(value = {"One", "Two", "Three"})
        public void beforeTest() {
        }

        @OOPSetup
        public void setUp() {

        }

        @OOPAfter(value = {"One"})
        public void setAfter() {
        }

        @OOPTest(order = 1)
        public void testOne() {
        }

        @OOPTest(order = 2, tag = "Breakfast")
        public void testTwo() {
        }
    }
}
