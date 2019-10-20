package OOP.Tests.my;

import org.junit.Assert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class BaseAnnotationTest {

    protected void assertTarget(Class<?> c, ElementType expected) {
        ElementType[] values = c.getAnnotation(Target.class).value();
        Assert.assertEquals(1, values.length);
        Assert.assertEquals(expected, values[0]);
    }

    protected void assertTargetType(Class<?> c) {
        assertTarget(c, ElementType.TYPE);
    }

    protected void assertTargetMethod(Class<?> c) {
        assertTarget(c, ElementType.METHOD);
    }

    protected void assertTargetField(Class<?> c) {
        assertTarget(c, ElementType.FIELD);
    }

    protected void assertRetention(Class<?> c, RetentionPolicy expected) {
        RetentionPolicy actual = c.getAnnotation(Retention.class).value();
        Assert.assertEquals(expected, actual);
    }

    protected void assertRetentionRuntime(Class<?> c) {
        assertRetention(c, RetentionPolicy.RUNTIME);
    }
}
