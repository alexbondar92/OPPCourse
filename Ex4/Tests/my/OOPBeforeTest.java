package OOP.Tests.my;

import OOP.Solution.OOPBefore;
import org.junit.Test;

public class OOPBeforeTest
        extends BaseAnnotationTest {

    @Test
    public void targetMethod() {
        assertTargetMethod(OOPBefore.class);
    }

    @Test
    public void retentionRuntime() {
        assertRetentionRuntime(OOPBefore.class);
    }
}
