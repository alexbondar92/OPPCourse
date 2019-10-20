package OOP.Solution;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import OOP.Provided.OOPAssertionFailure;
import OOP.Provided.OOPExpectedException;
import OOP.Provided.OOPResult;
import OOP.Solution.*;
import com.sun.tools.javac.comp.Annotate;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class OOPUnitCore {
    private static class Wrapper{
        public Object backup = null;
        public Object original = null;
    }

    public static void assertEquals(Object expected, Object actual){
        if (expected == null && actual != null)
            throw new OOPAssertionFailure();
        if (expected != null && !expected.equals(actual)){
            throw new OOPAssertionFailure();
        }
    }

    public static void fail(){
        throw new OOPAssertionFailure();
    }

    public static OOPTestSummary runClass(Class<?> testClass){
        if (testClass == null || !testClass.isAnnotationPresent(OOPTestClass.class)){
            throw new IllegalArgumentException();
        }

        return runClassAux(testClass,null);
    }

    public static OOPTestSummary runClass(Class<?> testClass, String tag){
        if (tag == null ||testClass == null || !testClass.isAnnotationPresent(OOPTestClass.class)){
            throw new IllegalArgumentException();
        }
        return runClassAux(testClass,tag);
    }

    private static OOPTestSummary runClassAux(Class<?> testClass, String tag){
        Wrapper wrapper = new Wrapper();
        wrapper.backup = new Object();
        wrapper.original = new Object();
        try {
            Constructor constructor = testClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            wrapper.original = constructor.newInstance();
            wrapper.backup = constructor.newInstance();
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e){
            assert false;
        }

        runStage2(wrapper);

        Map<String, OOPResult> testMap = runStage3(wrapper, tag);

        OOPTestSummary ret = new OOPTestSummary(testMap);
        return ret;
    }

    private static void runStage2(Wrapper wrapper){
        if (wrapper == null || wrapper.original == null){
            return;
        }
        List<Method> methods = Arrays.stream(getAllMethods(wrapper.original.getClass())).
                filter(m -> m.isAnnotationPresent(OOPSetup.class)).
                sorted((m1, m2) -> compareMethodsClass(m1.getDeclaringClass(), m2.getDeclaringClass())).
                collect(Collectors.toList());

        for (Method method : methods){
            try {
                    method.invoke(wrapper.original);
            } catch (IllegalAccessException | InvocationTargetException e) {
                assert false;
                e.printStackTrace();
            }
        }
    }

    private static Map<String, OOPResult> runStage3(Wrapper wrapper, String tag){
        Map<String, OOPResult> ret = new HashMap<>();
        List<Method> methods = Arrays.stream(getAllMethods(wrapper.original.getClass())).
                filter(m -> m.isAnnotationPresent(OOPTest.class)).collect(Collectors.toList());
        if (wrapper.original == null){
            return ret;
        }
        if (tag != null){
            methods = Arrays.stream(getAllMethods(wrapper.original.getClass())).
                    filter(m -> m.isAnnotationPresent(OOPTest.class) && m.getAnnotation(OOPTest.class).tag().equals(tag)).
                    collect(Collectors.toList());       // TODO: change contain to equal
        }
        if (wrapper.original.getClass().getAnnotation(OOPTestClass.class).value() == OOPTestClass.OOPTestClassType.ORDERED){
            methods.sort(Comparator.comparingInt(m -> m.getAnnotation(OOPTest.class).order()));
        }

        for (Method method : methods){
            try {
                List<Field> fields = Arrays.stream(wrapper.original.getClass().getDeclaredFields())
                        .filter(f -> f.isAnnotationPresent(OOPExceptionRule.class)).collect(Collectors.toList());
                Field expectedExp = null;
                for (Field field : fields){
                    field.setAccessible(true);
                    if (field.getType().equals(OOPExpectedException.class)){
                        expectedExp = field;
                    }
                    field.set(wrapper.original, OOPExpectedException.none());
                }
                runOOPBefore(method, wrapper);

                try {
                    wrapper.backup = createBackup(wrapper.original);
                    method.invoke(wrapper.original);
                    if (expectedExp != null && ((OOPExpectedException)expectedExp.get(wrapper.original)).getExpectedException() != null){
                        OOPResultImpl result = new OOPResultImpl(OOPResult.OOPTestResult.ERROR, ((OOPExpectedException)expectedExp.get(wrapper.original)).getExpectedException().getName());
                        ret.put(method.getName(), result);
                    } else {
                        OOPResultImpl result = new OOPResultImpl(OOPResult.OOPTestResult.SUCCESS,null);
                        ret.put(method.getName(), result);
                    }

                } catch (Exception e) {
                    if (e.getClass() == InvocationTargetException.class){
                        if (e.getCause().getClass().equals(OOPAssertionFailure.class)) {
                            OOPResultImpl result = new OOPResultImpl(OOPResult.OOPTestResult.FAILURE, e.getMessage());
                            ret.put(method.getName(), result);
                        } else {
                            if (e.getCause().getClass() != OOPAssertionFailure.class) {
                                e = (Exception) e.getCause();
                            }
                            if (expectedExp != null && ((OOPExpectedException) expectedExp.get(wrapper.original)).getExpectedException() == e.getClass() &&
                                    ((OOPExpectedException) expectedExp.get(wrapper.original)).assertExpected(e)) {
                                OOPResultImpl result = new OOPResultImpl(OOPResult.OOPTestResult.SUCCESS, null);
                                ret.put(method.getName(), result);
                            } else if (expectedExp == null || ((OOPExpectedException) expectedExp.get(wrapper.original)).getExpectedException() == null) {
                                OOPResultImpl result = new OOPResultImpl(OOPResult.OOPTestResult.ERROR, e.getClass().getName());
                                ret.put(method.getName(), result);
                            } else {
                                OOPResultImpl result = new OOPResultImpl(OOPResult.OOPTestResult.EXPECTED_EXCEPTION_MISMATCH, e.getMessage());
                                ret.put(method.getName(), result);
                            }
                        }
                    }
                }
            } catch (InvocationTargetException e){
                restoreBackup(wrapper);
                OOPResultImpl result = new OOPResultImpl(OOPResult.OOPTestResult.ERROR,e.getCause().getClass().toString());
                ret.put(method.getName(), result);
            } catch (Exception e) {
                restoreBackup(wrapper);
                assert false;
                e.printStackTrace();
            }

            try {
                runOOPAfter(method, wrapper);

            } catch (InvocationTargetException e){
                restoreBackup(wrapper);
                OOPResultImpl result = new OOPResultImpl(OOPResult.OOPTestResult.ERROR,e.getCause().getClass().toString());
                ret.put(method.getName(), result);
            } catch (Exception e) {
                restoreBackup(wrapper);
                assert false;
                e.printStackTrace();
            }
        }
        return ret;
    }

    private static void restoreBackup(Wrapper wrapper) {
        try {
            Field[] fields = wrapper.backup.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                field.set(wrapper.original, field.get(wrapper.backup));
            }
        } catch (Exception e){
            assert false;
        }
    }

    private static Object createBackup(Object orig){
        Object backup = null;
        try {
            Constructor constructor = orig.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);

            backup = constructor.newInstance();
            Field[] fields = orig.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean clonable = Arrays.stream(field.getType().getDeclaredMethods()).anyMatch(method -> method.getName().equals("clone"));
                if (field.get(orig) instanceof Cloneable && clonable) {
                    Method met = field.getType().getDeclaredMethod("clone");
                    met.setAccessible(true);
                    field.set(backup, (met.invoke(field.get(orig))));
                } else {
                    try {
                        Constructor cpyConstructor = field.getType().getDeclaredConstructor(field.getType());
                        cpyConstructor.setAccessible(true);
                        field.set(backup, cpyConstructor.newInstance(field.get(orig)));
                    } catch (Exception e) {
                        field.set(backup, field.get(orig));
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
            assert false;
        }
        return backup;
    }

    private static void runOOPBefore(Method run_method, Wrapper wrapper) throws InvocationTargetException, IllegalAccessException {
        List<Method> methods = Arrays.stream(getAllMethods(wrapper.original.getClass())).
                filter(m -> m.isAnnotationPresent(OOPBefore.class)).
                filter(m -> Arrays.stream(m.getDeclaredAnnotation(OOPBefore.class).value()).anyMatch(s -> s.equals(run_method.getName()))).
                sorted((m1, m2) -> compareMethodsClass(m1.getDeclaringClass(), m2.getDeclaringClass())).
                collect(Collectors.toList());

        for (Method met : methods){
            String[] tmp = met.getAnnotation(OOPBefore.class).value();
            for (String str : tmp){
                if (str.equals(run_method.getName())){
                    wrapper.backup = createBackup(wrapper.original);
                    met.invoke(wrapper.original);
                }
            }
        }
    }

    static int compareMethodsClass(Class class1, Class class2){
        if (class1 == class2)
            return 0;
        if (class1.isAssignableFrom(class2))
            return -1;
        return 1;
    }

    private static void runOOPAfter(Method run_method, Wrapper wrapper) throws InvocationTargetException, IllegalAccessException {
        List<Method> methods = Arrays.stream(getAllMethods(wrapper.original.getClass())).
                filter(m -> m.isAnnotationPresent(OOPAfter.class)).
                filter(m -> Arrays.stream(m.getDeclaredAnnotation(OOPAfter.class).value()).anyMatch(s -> s.equals(run_method.getName()))).
                sorted((m1, m2) -> -(compareMethodsClass(m1.getDeclaringClass(), m2.getDeclaringClass()))).
                collect(Collectors.toList());
        for (Method met : methods){
            String[] tmp = met.getAnnotation(OOPAfter.class).value();
            for (String str : tmp){
                if (str.equals(run_method.getName())){
                    wrapper.backup = createBackup(wrapper.original);
                    met.invoke(wrapper.original);
                }
            }
        }
    }

    private static Method[] getAllMethods(Class clazz){
        if (clazz == Object.class){
            return clazz.getDeclaredMethods();
        }

        List<Method> result = Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList());
        List<Method> resultToRemove = Arrays.stream(getAllMethods(clazz.getSuperclass())).collect(Collectors.toList());
        for (Method met : result){
            resultToRemove.removeAll(resultToRemove.stream().filter(m -> m.getName().equals(met.getName())).collect(Collectors.toList()));
        }

        result.addAll(resultToRemove);
        result.forEach(met -> met.setAccessible(true));
        return result.toArray(new Method[result.size()]);
    }
}
