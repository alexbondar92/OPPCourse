package OOP.Tests.my;

import org.junit.Test;

import OOP.Solution.OOPAfter;

public class OOPAfterTest
        extends BaseAnnotationTest {

    @Test
    public void targetMethod() {
        assertTargetMethod(OOPAfter.class);
    }

    @Test
    public void retentionRuntime() {
        assertRetentionRuntime(OOPAfter.class);
    }
}
