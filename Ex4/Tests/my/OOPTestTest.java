package OOP.Tests.my;

import org.junit.Assert;
import org.junit.Test;

import OOP.Solution.OOPTest;

public class OOPTestTest
        extends BaseAnnotationTest {

    @Test
    public void targetMethod() {
        assertTargetMethod(OOPTest.class);
    }

    @Test
    public void retentionRuntime() {
        assertRetentionRuntime(OOPTest.class);
    }

    @Test
    public void defaultTag()
            throws NoSuchMethodException {
        String expected = "";
        String actual = new Object() {

            @OOPTest
            public void method() {
            }
        }.getClass()
                .getDeclaredMethod("method")
                .getDeclaredAnnotation(OOPTest.class).tag();
        Assert.assertEquals(expected, actual);
    }
}
