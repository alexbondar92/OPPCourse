package OOP.Tests.my;

import org.junit.Test;

import OOP.Solution.OOPExceptionRule;

public class OOPExceptionRuleTest
        extends BaseAnnotationTest {

    @Test
    public void targetField() {
        assertTargetField(OOPExceptionRule.class);
    }

    @Test
    public void retentionRuntime() {
        assertRetentionRuntime(OOPExceptionRule.class);
    }
}
