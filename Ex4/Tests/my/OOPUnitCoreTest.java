package OOP.Tests.my;

import org.junit.Test;

import java.util.Date;

import OOP.Provided.OOPAssertionFailure;
import OOP.Provided.OOPExpectedException;
import OOP.Solution.OOPAfter;
import OOP.Solution.OOPBefore;
import OOP.Solution.OOPExceptionRule;
import OOP.Solution.OOPSetup;
import OOP.Solution.OOPTest;
import OOP.Solution.OOPTestClass;
import OOP.Solution.OOPUnitCore;

public class OOPUnitCoreTest
        extends BaseTest {

    @Test
    public void assertEquals_numbers_success() {
        OOPUnitCore.assertEquals(1, 1);
    }

    @Test
    public void assertEquals_numbers_fail() {
        expectException(OOPAssertionFailure.class);
        OOPUnitCore.assertEquals(1, 2);
    }

    @Test
    public void assertEquals_strings_success() {
        OOPUnitCore.assertEquals("string", "string");
    }

    @Test
    public void assertEquals_strings_fail() {
        expectException(OOPAssertionFailure.class);
        OOPUnitCore.assertEquals("string", "string2");
    }

    @Test
    public void assertEquals_success() {
        OOPUnitCore.assertEquals(this, this);
    }

    @Test
    public void assertEquals_fail() {
        expectException(OOPAssertionFailure.class);
        OOPUnitCore.assertEquals(new Object(), new Object());
    }

    @Test
    public void assertEquals_firstNull() {
        expectException(OOPAssertionFailure.class);
        OOPUnitCore.assertEquals(null, new Object());
    }

    @Test
    public void assertEquals_secondNull() {
        expectException(OOPAssertionFailure.class);
        OOPUnitCore.assertEquals(new Object(), null);
    }

    @Test
    public void assertEquals_bothNull() {
        OOPUnitCore.assertEquals(null, null);
    }

    @Test
    public void fail() {
        expectException(OOPAssertionFailure.class);
        OOPUnitCore.fail();
    }

    @Test
    public void runClass1Arg_null() {
        expectException(IllegalArgumentException.class);
        //noinspection ConstantConditions
        OOPUnitCore.runClass(null);
    }

    @Test
    public void runClass1Arg_notATestClass() {
        expectException(IllegalArgumentException.class);
        OOPUnitCore.runClass(Object.class);
    }

    @Test
    public void runClass2Args_nullClass() {
        expectException(IllegalArgumentException.class);
        //noinspection ConstantConditions
        OOPUnitCore.runClass(null, "tag");
    }

    @Test
    public void runClass2Args_nullTag() {
        expectException(IllegalArgumentException.class);
        //noinspection ConstantConditions
        OOPUnitCore.runClass(NullTagTest.class, null);
    }

    @Test
    public void runClass2Args_notATestClass() {
        expectException(IllegalArgumentException.class);
        OOPUnitCore.runClass(Object.class, "tag");
    }

    @Test
    public void runClass_privateConstructor() {
        runClassAllSuccess(PrivateConstructorTest.class);
    }

    @Test
    public void runClass_setUp_called() {
        runClassAllSuccess(SetupCalledTest.class);
    }

    @Test
    public void runClass_setUp_inheritance() {
        runClassAllSuccess(SetupInheritanceTest3.class);
    }

    @Test
    public void runClass_setUp_inheritanceOrder() {
        runClassAllSuccess(SetupInheritanceOrderTest3.class);
    }

    @Test
    public void runClass_setUp_override() {
        runClassAllSuccess(SetupOverrideTest4.class);
    }

    @Test
    public void runClass_before_called() {
        runClassAllSuccess(BeforeCalledTest.class);
    }

    @Test
    public void runClass_before_calledTwice() {
        runClassAllSuccess(BeforeCalledTwiceTest.class);
    }

    @Test
    public void runClass_before_twoBeforeCalledTwice() {
        runClassAllSuccess(BeforeTwoBeforeCalledTwiceTest.class);
    }

    @Test
    public void runClass_before_inheritance() {
        runClassAllSuccess(BeforeInheritanceTest3.class);
    }

    @Test
    public void runClass_before_inheritanceOrder() {
        runClassAllSuccess(BeforeInheritanceOrderTest3.class);
    }

    @Test
    public void runClass_before_override() {
        runClassAllSuccess(BeforeOverrideTest3.class);
    }

    @Test
    public void runClass_before_error() {
        runClassAllError(BeforeErrorTest.class);
    }

    @Test
    public void runClass_before_proceedsAfterError() {
        runClassSuccessAndError(BeforeProceedsAfterErrorTest.class, 2, 2);
    }

    @Test
    public void runClass_after_called() {
        runClassAllSuccess(AfterCalledTest.class);
    }

    @Test
    public void runClass_after_calledTwice() {
        runClassAllSuccess(AfterCalledTwiceTest.class);
    }

    @Test
    public void runClass_after_twoAfterCalledTwice() {
        runClassAllSuccess(AfterTwoAfterCalledTwiceTest.class);
    }

    @Test
    public void runClass_after_inheritance() {
        runClassAllSuccess(AfterInheritanceTest3.class);
    }

    @Test
    public void runClass_after_inheritanceOrder() {
        runClassAllSuccess(AfterInheritanceOrderTest3.class);
    }

    @Test
    public void runClass_after_override() {
        runClassAllSuccess(AfterOverrideTest3.class);
    }

    @Test
    public void runClass_after_error() {
        runClassAllError(AfterErrorTest.class);
    }

    @Test
    public void runClass_after_crashAfterFailure() {
        runClassAllError(AfterCrashAfterFailureTest.class);
    }

    @Test
    public void runClass_after_proceedsAfterError() {
        runClassSuccessAndError(AfterProceedsAfterErrorTest.class, 2, 2);
    }

    @Test
    public void runClass_after_calledAfterFailure() {
        runClassSuccessAndFailure(AfterCalledAfterFailureTest.class, 1, 1);
    }

    @Test
    public void runClass_after_calledAfterMismatch() {
        runClassSuccessAndMismatch(AfterCalledAfterMismatchTest.class, 1, 1);
    }

    @Test
    public void runClass_after_calledAfterError() {
        runClassSuccessAndError(AfterCalledAfterErrorTest.class, 1, 1);
    }

    @Test
    public void runClass_beforeAndAfter() {
        runClassAllSuccess(BeforeAndAfterTest.class);
    }

    @Test
    public void runClass_types_success() {
        runClassAllSuccess(TypesSuccessTest.class);
    }

    @Test
    public void runClass_types_successExpectedError() {
        runClassAllSuccess(TypesSuccessExpectedErrorTest.class);
    }

    @Test
    public void runClass_types_successInheritance() {
        runClassSuccess(TypesSuccessInheritanceTest3.class, 3);
    }

    @Test
    public void runClass_types_failure() {
        runClassAllFail(TypesFailureTest.class);
    }

    @Test
    public void runClass_types_mismatchDifferentException() {
        runClassAllMismatch(TypesMismatchDifferentExceptionTest.class);
    }

    @Test
    public void runClass_types_mismatchDifferentMessage() {
        runClassAllMismatch(TypesMismatchDifferentMessageTest.class);
    }

    @Test
    public void runClass_types_errorNoExpectedException() {
        runClassAllError(TypesErrorNoExpectedExceptionTest.class);
    }

    @Test
    public void runClass_types_errorExpectedExceptionWasNotThrown() {
        runClassAllError(TypesErrorExpectedExceptionWasNotThrownTest.class);
    }

    @Test
    public void runClass_ordered() {
        runClassAllSuccess(OrderedTest.class);
    }

    @Test
    public void runClass_ordered_inheritanceAlsoOrdered() {
        runClassAllSuccess(OrderedInheritanceAlsoOrderedTest3.class);
    }

    @Test
    public void runClass_ordered_inheritanceNotOrdered() {
        runClassAllSuccess(OrderedInheritanceNotOrderedTest3.class);
    }

    @Test
    public void runClass_tag() {
        runClassTagAllSuccess(TagTest.class, "test");
    }

    @Test
    public void runClass_tag_doesNotExist() {
        runClassTagAllSuccess(TagDoesNotExistTest.class, "no tag");
    }

    @Test
    public void runClass_tag_notTaggedNotCalled() {
        runClassTagSuccessAndMismatch(TagNotTaggedNotCalledTest2.class, "tag1", 1, 1);
        runClassTagFailAndError(TagNotTaggedNotCalledTest2.class, "tag2", 1, 1);
    }

    @Test
    public void runClass_tag_runAllIgnoreTags() {
        runClassAllSuccess(TagRunAllIgnoreTagsTest3.class);
    }

    @Test
    public void runClass_orderAndTag() {
        runClassTagAllSuccess(OrderAndTagTest3.class, "tag1");
        runClassTagAllSuccess(OrderAndTagTest3.class, "tag2");
    }

    @Test
    public void runClass_recovery_beforePrimitive() {
        runClassSuccessAndError(RecoveryBeforePrimitiveTest.class, 1, 1);
    }

    @Test
    public void runClass_recovery_afterPrimitive() {
        runClassSuccessAndError(RecoveryAfterPrimitiveTest.class, 1, 1);
    }

    @Test
    public void runClass_recovery_beforeCloneable() {
        runClassSuccessAndError(RecoveryBeforeCloneableTest.class, 1, 1);
    }

    @Test
    public void runClass_recovery_afterCloneable() {
        runClassSuccessAndError(RecoveryAfterCloneableTest.class, 1, 1);
    }

    @Test
    public void runClass_recovery_beforeCopyConstructor() {
        runClassSuccessAndError(RecoveryBeforeCopyConstructorTest.class, 1, 1);
    }

    @Test
    public void runClass_recovery_afterCopyConstructor() {
        runClassSuccessAndError(RecoveryAfterCopyConstructorTest.class, 1, 1);
    }

    @Test
    public void runClass_recovery_beforeNotCloneableOrCopy() {
        runClassSuccessAndError(RecoveryBeforeNotCloneableOrCopyTest.class, 1, 1);
    }

    @Test
    public void runClass_recovery_afterNotCloneableOrCopy() {
        runClassSuccessAndError(RecoveryAfterNotCloneableOrCopyTest.class, 1, 1);
    }

    @OOPTestClass
    public static class NullTagTest {

    }

    @OOPTestClass
    public static class PrivateConstructorTest {

        private PrivateConstructorTest() {
        }

        @OOPTest
        public void test() {
        }
    }

    @OOPTestClass
    public static class SetupCalledTest {

        private int called = 0;

        @OOPSetup
        public void setUp() {
            called++;
        }

        @OOPTest
        public void testCalled() {
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass
    public static class SetupInheritanceTest1 {

        protected int called = 0;

        @OOPSetup
        public void setUpTest1() {
            called++;
        }
    }

    @OOPTestClass
    public static class SetupInheritanceTest2
            extends SetupInheritanceTest1 {

        @OOPSetup
        public void setUpTest2() {
            called++;
        }
    }

    @OOPTestClass
    public static class SetupInheritanceTest3
            extends SetupInheritanceTest2 {

        @OOPSetup
        public void setUpTest3() {
            called++;
        }

        @OOPTest
        public void testCalled() {
            OOPUnitCore.assertEquals(3, called);
        }
    }

    @OOPTestClass
    public static class SetupInheritanceOrderTest1 {

        protected int called = 0;

        @OOPSetup
        public void setUpTest1() {
            called = 1;
        }
    }

    @OOPTestClass
    public static class SetupInheritanceOrderTest2
            extends SetupInheritanceOrderTest1 {

        @OOPSetup
        public void setUpTest2() {
            called = 2;
        }
    }

    @OOPTestClass
    public static class SetupInheritanceOrderTest3
            extends SetupInheritanceOrderTest2 {

        @OOPSetup
        public void setUpTest3() {
            called = 3;
        }

        @OOPTest
        public void testCalled() {
            OOPUnitCore.assertEquals(3, called);
        }
    }

    @OOPTestClass
    public static class SetupOverrideTest1 {

        protected int called = 0;

        @OOPSetup
        public void setUpTest1() {
            called++;
        }
    }

    @OOPTestClass
    public static class SetupOverrideTest2
            extends SetupOverrideTest1 {

        @OOPSetup
        public void setUpTest2() {
            called++;
        }
    }

    @OOPTestClass
    public static class SetupOverrideTest3
            extends SetupOverrideTest2 {

        @OOPSetup
        @Override
        public void setUpTest1() {
            called++;
        }
    }

    @OOPTestClass
    public static class SetupOverrideTest4
            extends SetupOverrideTest3 {

        @OOPSetup
        @Override
        public void setUpTest2() {
            called++;
        }

        @OOPTest
        public void testCalled() {
            OOPUnitCore.assertEquals(2, called);
        }
    }

    @OOPTestClass
    public static class BeforeCalledTest {

        private int called = 0;

        @OOPBefore("testCalled")
        public void before() {
            called++;
        }

        @OOPTest
        public void testCalled() {
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class BeforeCalledTwiceTest {

        private int called = 0;

        @OOPBefore({"testCalled", "testCalled2"})
        public void before() {
            called++;
        }

        @OOPTest(order = 1)
        public void testCalled() {
            OOPUnitCore.assertEquals(1, called);
        }

        @OOPTest(order = 2)
        public void testCalled2() {
            OOPUnitCore.assertEquals(2, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class BeforeTwoBeforeCalledTwiceTest {

        private int called = 0;

        @OOPBefore({"testCalled", "testCalled2"})
        public void before1() {
            called++;
        }

        @OOPBefore({"testCalled", "testCalled2"})
        public void before2() {
            called++;
        }

        @OOPTest(order = 1)
        public void testCalled() {
            OOPUnitCore.assertEquals(2, called);
        }

        @OOPTest(order = 2)
        public void testCalled2() {
            OOPUnitCore.assertEquals(4, called);
        }
    }

    @OOPTestClass
    public static class BeforeInheritanceTest1 {

        protected int called = 0;

        @OOPBefore("testCalled")
        public void beforeTest1() {
            called++;
        }
    }

    @OOPTestClass
    public static class BeforeInheritanceTest2
            extends BeforeInheritanceTest1 {

        @OOPBefore("testCalled")
        public void beforeTest2() {
            called++;
        }
    }

    @OOPTestClass
    public static class BeforeInheritanceTest3
            extends BeforeInheritanceTest2 {

        @OOPBefore("testCalled")
        public void beforeTest3() {
            called++;
        }

        @OOPTest
        public void testCalled() {
            OOPUnitCore.assertEquals(3, called);
        }
    }

    @OOPTestClass
    public static class BeforeInheritanceOrderTest1 {

        protected int called = 0;

        @OOPBefore("testCalled")
        public void beforeTest1() {
            called = 1;
        }
    }

    @OOPTestClass
    public static class BeforeInheritanceOrderTest2
            extends BeforeInheritanceOrderTest1 {

        @OOPBefore("testCalled")
        public void beforeTest2() {
            called = 2;
        }
    }

    @OOPTestClass
    public static class BeforeInheritanceOrderTest3
            extends BeforeInheritanceOrderTest2 {

        @OOPBefore("testCalled")
        public void beforeTest3() {
            called = 3;
        }

        @OOPTest
        public void testCalled() {
            OOPUnitCore.assertEquals(3, called);
        }
    }

    @OOPTestClass
    public static class BeforeOverrideTest1 {

        protected int called = 0;

        @OOPBefore("testCalled")
        public void beforeTest1() {
            called++;
        }
    }

    @OOPTestClass
    public static class BeforeOverrideTest2
            extends BeforeOverrideTest1 {

        @OOPBefore("testCalled")
        public void beforeTest2() {
            called++;
        }
    }

    @OOPTestClass
    public static class BeforeOverrideTest3
            extends BeforeOverrideTest2 {

        @OOPBefore("testCalled")
        @Override
        public void beforeTest1() {
            called++;
        }

        @OOPTest
        public void testCalled() {
            OOPUnitCore.assertEquals(2, called);
        }
    }

    @OOPTestClass
    public static class BeforeErrorTest {

        @OOPBefore("testCalled")
        public void before() {
            throw new IllegalArgumentException();
        }

        @OOPTest
        public void testCalled() {
        }
    }

    @OOPTestClass
    public static class BeforeProceedsAfterErrorTest {

        @OOPBefore({"testCalled2", "testCalled4"})
        public void beforeSuccess() {
        }

        @OOPBefore({"testCalled1", "testCalled3"})
        public void beforeError() {
            throw new IllegalArgumentException();
        }

        @OOPTest
        public void testCalled1() {
        }

        @OOPTest
        public void testCalled2() {
        }

        @OOPTest
        public void testCalled3() {
        }

        @OOPTest
        public void testCalled4() {
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterCalledTest {

        private int called = 0;

        @OOPAfter("testCalled")
        public void after() {
            called++;
        }

        @OOPTest(order = 1)
        public void testCalled() {
            OOPUnitCore.assertEquals(0, called);
        }

        @OOPTest(order = 2)
        public void testCalledAfter() {
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterCalledTwiceTest {

        private int called = 0;

        @OOPAfter({"testCalled1", "testCalled2"})
        public void after() {
            called++;
        }

        @OOPTest(order = 1)
        public void testCalled1() {
            OOPUnitCore.assertEquals(0, called);
        }

        @OOPTest(order = 2)
        public void testCalled2() {
            OOPUnitCore.assertEquals(1, called);
        }

        @OOPTest(order = 3)
        public void testCalledAfter() {
            OOPUnitCore.assertEquals(2, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterTwoAfterCalledTwiceTest {

        private int called = 0;

        @OOPAfter({"testCalled1", "testCalled2"})
        public void after1() {
            called++;
        }

        @OOPAfter({"testCalled1", "testCalled2"})
        public void after2() {
            called++;
        }

        @OOPTest(order = 1)
        public void testCalled1() {
            OOPUnitCore.assertEquals(0, called);
        }

        @OOPTest(order = 2)
        public void testCalled2() {
            OOPUnitCore.assertEquals(2, called);
        }

        @OOPTest(order = 3)
        public void testCalledAfter() {
            OOPUnitCore.assertEquals(4, called);
        }
    }

    @OOPTestClass
    public static class AfterInheritanceTest1 {

        protected int called = 0;

        @OOPAfter("testCalled")
        public void afterTest1() {
            called++;
        }
    }

    @OOPTestClass
    public static class AfterInheritanceTest2
            extends AfterInheritanceTest1 {

        @OOPAfter("testCalled")
        public void afterTest2() {
            called++;
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterInheritanceTest3
            extends AfterInheritanceTest2 {

        @OOPAfter("testCalled")
        public void afterTest3() {
            called++;
        }

        @OOPTest(order = 1)
        public void testCalled() {
            OOPUnitCore.assertEquals(0, called);
        }

        @OOPTest(order = 2)
        public void testCalledAfter() {
            OOPUnitCore.assertEquals(3, called);
        }
    }

    @OOPTestClass
    public static class AfterInheritanceOrderTest1 {

        protected int called = 0;

        @OOPAfter("testCalled")
        public void afterTest1() {
            called = 1;
        }
    }

    @OOPTestClass
    public static class AfterInheritanceOrderTest2
            extends AfterInheritanceOrderTest1 {

        @OOPAfter("testCalled")
        public void afterTest2() {
            called = 2;
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterInheritanceOrderTest3
            extends AfterInheritanceOrderTest2 {

        @OOPAfter("testCalled")
        public void afterTest3() {
            called = 3;
        }

        @OOPTest(order = 1)
        public void testCalled() {
            OOPUnitCore.assertEquals(0, called);
        }

        @OOPTest(order = 2)
        public void testCalledAfter() {
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass
    public static class AfterOverrideTest1 {

        protected int called = 0;

        @OOPAfter("testCalled")
        public void afterTest1() {
            called++;
        }
    }

    @OOPTestClass
    public static class AfterOverrideTest2
            extends AfterOverrideTest1 {

        @OOPAfter("testCalled")
        public void afterTest2() {
            called++;
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterOverrideTest3
            extends AfterOverrideTest2 {

        @OOPAfter("testCalled")
        @Override
        public void afterTest1() {
            called++;
        }

        @OOPTest(order = 1)
        public void testCalled() {
            OOPUnitCore.assertEquals(0, called);
        }

        @OOPTest(order = 2)
        public void testCalledAfter() {
            OOPUnitCore.assertEquals(2, called);
        }
    }

    @OOPTestClass
    public static class AfterErrorTest {

        @OOPAfter("testCalled")
        public void after() {
            throw new IllegalArgumentException();
        }

        @OOPTest
        public void testCalled() {
        }
    }

    @OOPTestClass
    public static class AfterCrashAfterFailureTest {

        @OOPAfter("test")
        public void after() {
            throw new IllegalArgumentException();
        }

        @OOPTest
        public void test() {
            OOPUnitCore.fail();
        }
    }

    @OOPTestClass
    public static class AfterProceedsAfterErrorTest {

        @OOPAfter({"testCalled2", "testCalled4"})
        public void afterSuccess() {
        }

        @OOPAfter({"testCalled1", "testCalled3"})
        public void afterError() {
            throw new IllegalArgumentException();
        }

        @OOPTest
        public void testCalled1() {
        }

        @OOPTest
        public void testCalled2() {
        }

        @OOPTest
        public void testCalled3() {
        }

        @OOPTest
        public void testCalled4() {
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterCalledAfterFailureTest {

        private int called;

        @OOPAfter("test1")
        public void after() {
            called++;
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.fail();
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterCalledAfterMismatchTest {

        @OOPExceptionRule
        private OOPExpectedException expectedException = OOPExpectedException.none();

        private int called;

        @OOPAfter("test1")
        public void after() {
            called++;
        }

        @OOPTest(order = 1)
        public void test1() {
            expectedException.expect(IllegalArgumentException.class);
            throw new ArithmeticException();
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class AfterCalledAfterErrorTest {

        @OOPExceptionRule
        private OOPExpectedException expectedException = OOPExpectedException.none();

        private int called;

        @OOPAfter("test1")
        public void after() {
            called++;
        }

        @OOPTest(order = 1)
        public void test1() {
            expectedException.expect(IllegalArgumentException.class);
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class BeforeAndAfterTest {

        private int called = 0;

        @OOPBefore("testCalled")
        public void before() {
            called++;
        }

        @OOPAfter("testCalled")
        public void after() {
            called++;
        }

        @OOPTest(order = 1)
        public void testCalled() {
            OOPUnitCore.assertEquals(1, called);
        }

        @OOPTest(order = 2)
        public void testCalledAfter() {
            OOPUnitCore.assertEquals(2, called);
        }
    }

    @OOPTestClass
    public static class TypesSuccessTest {

        private int called = 0;

        @OOPTest
        public void test() {
            called++;
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass
    public static class TypesSuccessExpectedErrorTest {

        @OOPExceptionRule
        OOPExpectedException expectedException = OOPExpectedException.none();

        @OOPTest
        public void test() {
            expectedException.expect(IllegalArgumentException.class)
                    .expectMessage("error");
            throw new IllegalArgumentException("error");
        }
    }

    @OOPTestClass
    public static class TypesSuccessInheritanceTest1 {

        @OOPTest
        public void test1() {
        }
    }

    @OOPTestClass
    public static class TypesSuccessInheritanceTest2
            extends TypesSuccessInheritanceTest1 {

        @OOPTest
        public void test2() {
        }
    }

    @OOPTestClass
    public static class TypesSuccessInheritanceTest3
            extends TypesSuccessInheritanceTest2 {

        @OOPTest
        public void test3() {
        }
    }

    @OOPTestClass
    public static class TypesFailureTest {

        @OOPTest
        public void test() {
            OOPUnitCore.assertEquals(1, 2);
        }
    }

    @OOPTestClass
    public static class TypesMismatchDifferentExceptionTest {

        @OOPExceptionRule
        OOPExpectedException expectedException = OOPExpectedException.none();

        @OOPTest
        public void test() {
            expectedException.expect(IllegalArgumentException.class)
                    .expectMessage("error");
            throw new ArithmeticException("error");
        }
    }

    @OOPTestClass
    public static class TypesMismatchDifferentMessageTest {

        @OOPExceptionRule
        OOPExpectedException expectedException = OOPExpectedException.none();

        @OOPTest
        public void test() {
            expectedException.expect(IllegalArgumentException.class)
                    .expectMessage("error");
            throw new IllegalArgumentException("different message");
        }
    }

    @OOPTestClass
    public static class TypesErrorNoExpectedExceptionTest {

        @OOPTest
        public void test() {
            throw new IllegalArgumentException();
        }
    }

    @OOPTestClass
    public static class TypesErrorExpectedExceptionWasNotThrownTest {

        @OOPExceptionRule
        OOPExpectedException expectedException = OOPExpectedException.none();

        @OOPTest
        public void test() {
            expectedException.expect(IllegalArgumentException.class);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class OrderedTest {

        private int called;

        @OOPTest(order = 2)
        public void test1Order2() {
            called++;
            OOPUnitCore.assertEquals(2, called);
        }

        @OOPTest(order = 3)
        public void test2Order3() {
            called++;
            OOPUnitCore.assertEquals(3, called);
        }

        @OOPTest(order = 1)
        public void test3Order1() {
            called++;
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class OrderedInheritanceAlsoOrderedTest1 {

        protected int called;

        @OOPTest(order = 2)
        public void test1Method1() {
            called++;
            OOPUnitCore.assertEquals(2, called);
        }

        @OOPTest(order = 7)
        public void test1Method2() {
            called++;
            OOPUnitCore.assertEquals(7, called);
        }

        @OOPTest(order = 5)
        public void test1Method3() {
            called++;
            OOPUnitCore.assertEquals(5, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class OrderedInheritanceAlsoOrderedTest2
            extends OrderedInheritanceAlsoOrderedTest1 {

        @OOPTest(order = 6)
        public void test2Method1() {
            called++;
            OOPUnitCore.assertEquals(6, called);
        }

        @OOPTest(order = 1)
        public void test2Method2() {
            called++;
            OOPUnitCore.assertEquals(1, called);
        }

        @OOPTest(order = 8)
        public void test2Method3() {
            called++;
            OOPUnitCore.assertEquals(8, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class OrderedInheritanceAlsoOrderedTest3
            extends OrderedInheritanceAlsoOrderedTest2 {

        @OOPTest(order = 3)
        public void test3Method1() {
            called++;
            OOPUnitCore.assertEquals(3, called);
        }

        @OOPTest(order = 4)
        public void test3Method2() {
            called++;
            OOPUnitCore.assertEquals(4, called);
        }

        @OOPTest(order = 9)
        public void test3Method3() {
            called++;
            OOPUnitCore.assertEquals(9, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class OrderedInheritanceNotOrderedTest1 {

        protected int called;

        @OOPTest(order = 6)
        public void test1Method1() {
            called++;
            OOPUnitCore.assertEquals(6 + 3, called);
        }

        @OOPTest(order = 2)
        public void test1Method2() {
            called++;
            OOPUnitCore.assertEquals(2 + 3, called);
        }

        @OOPTest(order = 1)
        public void test1Method3() {
            called++;
            OOPUnitCore.assertEquals(1 + 3, called);
        }
    }

    public static class OrderedInheritanceNotOrderedTest2
            extends OrderedInheritanceNotOrderedTest1 {

        @OOPTest
        public void test2Method1() {
            called++;
            boolean rightRange = called >= 1 && called <= 3;
            OOPUnitCore.assertEquals(true, rightRange);
        }

        @OOPTest
        public void test2Method2() {
            called++;
            boolean rightRange = called >= 1 && called <= 3;
            OOPUnitCore.assertEquals(true, rightRange);
        }

        @OOPTest
        public void test2Method3() {
            called++;
            boolean rightRange = called >= 1 && called <= 3;
            OOPUnitCore.assertEquals(true, rightRange);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class OrderedInheritanceNotOrderedTest3
            extends OrderedInheritanceNotOrderedTest2 {

        @OOPTest(order = 5)
        public void test3Method1() {
            called++;
            OOPUnitCore.assertEquals(5 + 3, called);
        }

        @OOPTest(order = 4)
        public void test3Method2() {
            called++;
            OOPUnitCore.assertEquals(4 + 3, called);
        }

        @OOPTest(order = 3)
        public void test3Method3() {
            called++;
            OOPUnitCore.assertEquals(3 + 3, called);
        }
    }

    @OOPTestClass
    public static class TagTest {

        @OOPTest(tag = "test")
        public void test() {
        }
    }

    @OOPTestClass
    public static class TagDoesNotExistTest {

        @OOPTest(tag = "test")
        public void test() {
        }
    }

    @OOPTestClass
    public static class TagNotTaggedNotCalledTest1 {

        @OOPTest(tag = "tag2")
        public void test1Method1() {
            OOPUnitCore.fail();
        }

        @OOPTest(tag = "tag1")
        public void test1Method2() {
        }
    }

    @OOPTestClass
    public static class TagNotTaggedNotCalledTest2
            extends TagNotTaggedNotCalledTest1 {

        @OOPExceptionRule
        OOPExpectedException expectedException = OOPExpectedException.none();

        @OOPTest(tag = "tag2")
        public void test2Method1() {
            expectedException.expect(IllegalArgumentException.class);
        }

        @OOPTest(tag = "tag1")
        public void test2Method2() {
            expectedException.expect(IllegalArgumentException.class);
            throw new ArithmeticException();
        }
    }

    @OOPTestClass
    public static class TagRunAllIgnoreTagsTest1 {

        @OOPTest(tag = "tag2")
        public void test1Method1() {
        }

        @OOPTest(tag = "tag1")
        public void test1Method2() {
        }
    }

    @OOPTestClass
    public static class TagRunAllIgnoreTagsTest2
            extends TagRunAllIgnoreTagsTest1 {

        @OOPTest(tag = "tag2")
        public void test2Method1() {
        }

        @OOPTest(tag = "tag1")
        public void test2Method2() {
        }
    }

    @OOPTestClass
    public static class TagRunAllIgnoreTagsTest3
            extends TagRunAllIgnoreTagsTest2 {

        @OOPTest(tag = "tag2")
        public void test3Method1() {
        }

        @OOPTest(tag = "tag1")
        public void test3Method2() {
        }
    }

    @OOPTestClass
    public static class OrderAndTagTest1 {

        protected int called;

        @OOPTest(tag = "tag2", order = 2)
        public void test1Method1() {
            called++;
            OOPUnitCore.assertEquals(2, called);
        }

        @OOPTest(tag = "tag1", order = 2)
        public void test1Method2() {
            called++;
            OOPUnitCore.assertEquals(2, called);
        }
    }

    @OOPTestClass
    public static class OrderAndTagTest2
            extends OrderAndTagTest1 {

        @OOPTest(tag = "tag2", order = 1)
        public void test2Method1() {
            called++;
            OOPUnitCore.assertEquals(1, called);
        }

        @OOPTest(tag = "tag1", order = 1)
        public void test2Method2() {
            called++;
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class OrderAndTagTest3
            extends OrderAndTagTest2 {

        @OOPTest(tag = "tag2", order = 3)
        public void test3Method1() {
            called++;
            OOPUnitCore.assertEquals(3, called);
        }

        @OOPTest(tag = "tag1", order = 3)
        public void test3Method2() {
            called++;
            OOPUnitCore.assertEquals(3, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class RecoveryBeforePrimitiveTest {

        private int called;

        @OOPBefore("test1")
        public void before() {
            called++;
            throw new IllegalArgumentException();
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.fail();
        }

        @OOPTest(order = 2)
        public void test2() {
            called++;
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class RecoveryAfterPrimitiveTest {

        private int called;

        @OOPAfter("test1")
        public void after() {
            called++;
            throw new IllegalArgumentException();
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.assertEquals(0, called);
        }

        @OOPTest(order = 2)
        public void test2() {
            called++;
            OOPUnitCore.assertEquals(1, called);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class RecoveryBeforeCloneableTest {

        private Date date = new Date(1);

        @OOPBefore("test1")
        public void before() {
            date = new Date(2);
            throw new IllegalArgumentException();
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.fail();
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals(new Date(1), date);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class RecoveryAfterCloneableTest {

        private Date date = new Date(1);

        @OOPAfter("test1")
        public void after() {
            date = new Date(2);
            throw new IllegalArgumentException();
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.assertEquals(new Date(1), date);
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals(new Date(1), date);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class RecoveryBeforeCopyConstructorTest {

        private String string = "1";

        @OOPBefore("test1")
        public void before() {
            string = "2";
            throw new IllegalArgumentException();
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.fail();
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals("1", string);
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class RecoveryAfterCopyConstructorTest {

        private String string = "1";

        @OOPAfter("test1")
        public void after() {
            string = "2";
            throw new IllegalArgumentException();
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.assertEquals("1", string);
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals("1", string);
        }
    }

    @OOPTestClass
    public static class RecoveryBeforeNotCloneableOrCopy {

        private int number;

        public RecoveryBeforeNotCloneableOrCopy(int number) {
            this.number = number;
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class RecoveryBeforeNotCloneableOrCopyTest {

        private RecoveryBeforeNotCloneableOrCopy value = new RecoveryBeforeNotCloneableOrCopy(1);
        private RecoveryBeforeNotCloneableOrCopy saved = value;

        @OOPBefore("test1")
        public void before() {
            value = new RecoveryBeforeNotCloneableOrCopy(1);
            throw new IllegalArgumentException();
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.fail();
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals(saved, value);
        }
    }

    @OOPTestClass
    public static class RecoveryAfterNotCloneableOrCopy {

        private int number;

        public RecoveryAfterNotCloneableOrCopy(int number) {
            this.number = number;
        }
    }

    @OOPTestClass(OOPTestClass.OOPTestClassType.ORDERED)
    public static class RecoveryAfterNotCloneableOrCopyTest {

        private RecoveryAfterNotCloneableOrCopy value = new RecoveryAfterNotCloneableOrCopy(1);
        private RecoveryAfterNotCloneableOrCopy saved = value;

        @OOPAfter("test1")
        public void after() {
            value = new RecoveryAfterNotCloneableOrCopy(1);
            throw new IllegalArgumentException();
        }

        @OOPTest(order = 1)
        public void test1() {
            OOPUnitCore.assertEquals(saved, value);
        }

        @OOPTest(order = 2)
        public void test2() {
            OOPUnitCore.assertEquals(saved, value);
        }
    }
}
