package OOP.Solution;

import OOP.Provided.OOPExpectedException;

import java.util.HashSet;
import java.util.Set;

public class OOPExpectedExceptionImpl implements OOP.Provided.OOPExpectedException {

    Class<? extends Exception> expectedClass;
    Set<String> msgSet;

    OOPExpectedExceptionImpl(){
        expectedClass = null;
        msgSet = new HashSet();
    }

    @Override
    public Class<? extends Exception> getExpectedException() {
        return expectedClass;
    }

    @Override
    public OOP.Provided.OOPExpectedException expect(Class<? extends Exception> expected) {
        expectedClass = expected;
        return this;
    }

    @Override
    public OOP.Provided.OOPExpectedException expectMessage(String msg) {
        msgSet.add(msg);
        return this;
    }

    @Override
    public boolean assertExpected(Exception e) {
//        if (e == null || expectedClass == null || !expectedClass.isInstance(e.getCause())){
        if (e == null || expectedClass == null || !expectedClass.isInstance(e)){
            return false;
        }
//        String exp_msg = e.getCause().getMessage();
        String exp_msg = e.getMessage();
        if (exp_msg == null && !msgSet.isEmpty()){
            return false;
        }
        if (exp_msg != null) {
            for (String msg : msgSet) {
                if (!exp_msg.contains(msg)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static OOPExpectedException none() {
        return new OOPExpectedExceptionImpl();
    }
}
