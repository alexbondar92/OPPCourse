package OOP.Tests.UnitTests;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import OOP.Provided.OOPResult;
import OOP.Solution.OOPResultImpl;
import OOP.Solution.OOPTestSummary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PartTwoUnitTests {

    OOPResult tSuccess = new OOPResultImpl(OOPResult.OOPTestResult.SUCCESS, null);
    OOPResult tFailed = new OOPResultImpl(OOPResult.OOPTestResult.FAILURE, "Test");
    OOPResult tError = new OOPResultImpl(OOPResult.OOPTestResult.ERROR, "Test");
    OOPResult tExceptionMismatch = new OOPResultImpl(OOPResult.OOPTestResult.EXPECTED_EXCEPTION_MISMATCH, "Test");

    HashMap<String, OOPResult> tEmptyMap;
    HashMap<String, OOPResult> tMap;
    OOPTestSummary tOOPTestSummary;

    @Before
    public void setUp() {
        tEmptyMap = new HashMap<>();
        tMap = new HashMap<>();

        tMap.putIfAbsent("a", tSuccess);
        tMap.putIfAbsent("b", tSuccess);
        tMap.putIfAbsent("c", tSuccess);
        tMap.putIfAbsent("d", tError);
        tMap.putIfAbsent("e", tError);
        tMap.putIfAbsent("f", tExceptionMismatch);
        tMap.putIfAbsent("g", tFailed);
    }

    @Test
    public void testSuccessNullMsg() {
        assertNull(tSuccess.getMessage());
    }

    @Test
    public void testEquals() {

        //noinspection SimplifiableJUnitAssertion
        assertFalse(tError.equals(tExceptionMismatch));
        //noinspection SimplifiableJUnitAssertion,EqualsWithItself
        assertTrue(tSuccess.equals(tSuccess));
        OOPResult tSuccessTwo = new OOPResultImpl(OOPResult.OOPTestResult.SUCCESS, "");
        //noinspection SimplifiableJUnitAssertion
        assertFalse(tSuccessTwo.equals(tSuccess));

        OOPResult tErrorTwo = new OOPResultImpl(OOPResult.OOPTestResult.ERROR, "Test");
        //noinspection SimplifiableJUnitAssertion
        assertTrue(tErrorTwo.equals(tError));

        //noinspection SimplifiableJUnitAssertion,EqualsBetweenInconvertibleTypes
        assertFalse("Test".equals(tFailed));

        OOPResult tErrorThree = new OOPResultImpl(OOPResult.OOPTestResult.ERROR, "Test2");
        //noinspection SimplifiableJUnitAssertion
        assertFalse(tErrorThree.equals(tError));
    }

    @Test
    public void testEmptyTestSummary() {
        tOOPTestSummary = new OOPTestSummary(tEmptyMap);

        assertEquals(tOOPTestSummary.getNumErrors(), 0);
        assertEquals(tOOPTestSummary.getNumErrors(), tOOPTestSummary.getNumExceptionMismatches());
        assertEquals(tOOPTestSummary.getNumExceptionMismatches(), tOOPTestSummary.getNumFailures());
        assertEquals(tOOPTestSummary.getNumFailures(), tOOPTestSummary.getNumSuccesses());
    }

    @Test
    public void testTestSummary() {
        tOOPTestSummary = new OOPTestSummary(tMap);

        assertEquals(tOOPTestSummary.getNumErrors(), 2);
        assertEquals(tOOPTestSummary.getNumSuccesses(), 3);
        assertEquals(tOOPTestSummary.getNumExceptionMismatches(), 1);
        assertEquals(tOOPTestSummary.getNumFailures(), 1);
    }
}
