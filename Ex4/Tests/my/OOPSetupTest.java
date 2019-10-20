package OOP.Tests.my;

import org.junit.Test;

import OOP.Solution.OOPSetup;

public class OOPSetupTest
        extends BaseAnnotationTest {

    @Test
    public void targetMethod() {
        assertTargetMethod(OOPSetup.class);
    }

    @Test
    public void retentionRuntime() {
        assertRetentionRuntime(OOPSetup.class);
    }
}
