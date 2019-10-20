package OOP.Tests.my;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import OOP.Solution.OOPTest;
import OOP.Solution.OOPTestSummary;
import OOP.Solution.OOPUnitCore;

public class BaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected void expectException(Class<? extends Throwable> exception) {
        expectedException.expect(exception);
    }

    protected void runClassTag(Class<?> c, String tag, int successes, int failures, int errors, int mismatches) {
        OOPTestSummary result;
        if (tag == null) {
            result = OOPUnitCore.runClass(c);
        } else {
            result = OOPUnitCore.runClass(c, tag);
        }
        Assert.assertNotNull(result);
        Assert.assertEquals(successes, result.getNumSuccesses());
        Assert.assertEquals(failures, result.getNumFailures());
        Assert.assertEquals(errors, result.getNumErrors());
        Assert.assertEquals(mismatches, result.getNumExceptionMismatches());
    }

    protected void runClass(Class<?> c, int successes, int failures, int errors, int mismatches) {
        runClassTag(c, "", successes, failures, errors, mismatches);
    }

    private int getNumOfTestMethods(Class<?> c, String tag) {
        return (int) Stream.concat(Arrays.stream(c.getMethods()), Arrays.stream(c.getDeclaredMethods()))
                .filter(m ->
                        tag == null ? m.isAnnotationPresent(OOPTest.class) :
                                m.isAnnotationPresent(OOPTest.class) &&
                                        m.getAnnotation(OOPTest.class).tag().equals(tag)

                ).map(Method::getName).distinct().count();
    }

    private int getNumOfTestMethods(Class<?> c) {
        return getNumOfTestMethods(c, null);
    }

    protected void runClassTagAllSuccess(Class<?> c, String tag) {
        runClassTag(c, tag,
                getNumOfTestMethods(c, tag),
                0,
                0,
                0);
    }

    protected void runClassSuccess(Class<?> c, int success) {
        runClass(c,
                success,
                0,
                0,
                0);
    }

    protected void runClassAllSuccess(Class<?> c) {
        runClassTagAllSuccess(c, null);
    }

    protected void runClassAllFail(Class<?> c) {
        runClass(c,
                0,
                getNumOfTestMethods(c),
                0,
                0);
    }

    protected void runClassAllError(Class<?> c) {
        runClass(c,
                0,
                0,
                getNumOfTestMethods(c),
                0);
    }

    protected void runClassAllMismatch(Class<?> c) {
        runClass(c,
                0,
                0,
                0,
                getNumOfTestMethods(c));
    }

    protected void runClassSuccessAndFailure(Class<?> c, int success, int failures) {
        runClass(c,
                success,
                failures,
                0,
                0);
    }

    protected void runClassSuccessAndError(Class<?> c, int success, int errors) {
        runClass(c,
                success,
                0,
                errors,
                0);
    }

    protected void runClassSuccessAndMismatch(Class<?> c, int success, int mismatch) {
        runClassTagSuccessAndMismatch(c, null, success, mismatch);
    }

    protected void runClassTagSuccessAndMismatch(Class<?> c, String tag, int success, int mismatch) {
        runClassTag(c, tag,
                success,
                0,
                0,
                mismatch);
    }

    protected void runClassTagFailAndError(Class<?> c, String tag, int failures, int errors) {
        runClassTag(c, tag,
                0,
                failures,
                errors,
                0);
    }
}
