package OOP.Tests.my;

import org.junit.Assert;
import org.junit.Test;

import OOP.Provided.OOPExpectedException;
import OOP.Solution.OOPExceptionRule;
import OOP.Solution.OOPTest;
import OOP.Solution.OOPTestClass;

public class OOPExpectedExceptionImplTest
        extends BaseTest {

    @Test
    public void getExpectedException_success() {
        Class<? extends Exception> expected = IllegalArgumentException.class;
        Class<?> actual = OOPExpectedException.none().expect(expected).getExpectedException();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getExpectedException_Twice() {
        Class<? extends Exception> expected = IllegalArgumentException.class;
        Class<?> actual = OOPExpectedException.none().expect(expected).getExpectedException();
        Assert.assertEquals(expected, actual);

        expected = ArithmeticException.class;
        actual = OOPExpectedException.none().expect(expected).getExpectedException();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getExpectedException_none() {
        OOPExpectedException exception = OOPExpectedException.none();
        Assert.assertNull(exception.getExpectedException());
    }

    @Test
    public void expectMessage_empty() {
        OOPExpectedException exception = OOPExpectedException.none()
                .expect(IllegalArgumentException.class);
        Assert.assertTrue(exception.assertExpected(new IllegalArgumentException()));
    }

    @Test
    public void expectMessage_fail() {
        OOPExpectedException exception = OOPExpectedException.none()
                .expect(IllegalArgumentException.class)
                .expectMessage("expected");
        Assert.assertFalse(exception.assertExpected(new IllegalArgumentException()));
    }

    @Test
    public void expectMessage_success() {
        OOPExpectedException exception = OOPExpectedException.none()
                .expect(IllegalArgumentException.class)
                .expectMessage("aaa").expectMessage("bb c").expectMessage("a bbb");
        Assert.assertTrue(exception.assertExpected(new IllegalArgumentException("aaa bbb ccc")));
    }

    @Test
    public void assertExpected_true() {
        OOPExpectedException exception = OOPExpectedException.none()
                .expect(IllegalArgumentException.class);
        Assert.assertTrue(exception.assertExpected(new IllegalArgumentException()));
    }

    @Test
    public void assertExpected_false() {
        OOPExpectedException exception = OOPExpectedException.none()
                .expect(IllegalArgumentException.class);
        Assert.assertFalse(exception.assertExpected(new ArithmeticException()));
    }

    @Test
    public void assertExpected_inheritance() {
        OOPExpectedException exception = OOPExpectedException.none()
                .expect(Exception.class);
        Assert.assertTrue(exception.assertExpected(new IllegalArgumentException()));
    }

    @Test
    public void expectedExceptionReset() {
        runClassAllSuccess(ExpectedExceptionResetTest.class);
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class ExpectedExceptionResetTest {

        @OOPExceptionRule
        public OOPExpectedException expectedException = OOPExpectedException.none();

        @OOPTest(order = 1)
        public void test1() {
            expectedException.expect(IllegalArgumentException.class)
                    .expectMessage("exception");
            throw new IllegalArgumentException("exception");
        }

        @OOPTest(order = 2)
        public void test2() {
            expectedException.expect(IllegalArgumentException.class);
            throw new IllegalArgumentException();
        }
    }
}
