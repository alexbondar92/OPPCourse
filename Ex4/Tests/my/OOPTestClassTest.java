package OOP.Tests.my;

import org.junit.Assert;
import org.junit.Test;

import OOP.Solution.OOPTestClass;

public class OOPTestClassTest
        extends BaseAnnotationTest {

    @Test
    public void targetType() {
        assertTargetType(OOPTestClass.class);
    }

    @Test
    public void retentionRuntime() {
        assertRetentionRuntime(OOPTestClass.class);
    }

    @Test
    public void defaultValue() {
        @OOPTestClass
        class Test {

        }
        OOPTestClass.OOPTestClassType expected = OOPTestClass.OOPTestClassType.UNORDERED;
        OOPTestClass.OOPTestClassType actual = Test.class
                .getDeclaredAnnotation(OOPTestClass.class).value();
        Assert.assertEquals(expected, actual);
    }
}
