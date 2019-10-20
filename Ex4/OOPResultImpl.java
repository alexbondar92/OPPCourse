package OOP.Solution;

import OOP.Provided.OOPResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class OOPResultImpl implements OOPResult {
    private OOPTestResult result;
    private String message;

    @Override
    public OOPTestResult getResultType() {
        return result;
    }

    public OOPResultImpl(OOPTestResult result, String message){
        this.result = result;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        OOPResult tmpObj = (OOPResult) obj;
        if (obj == this)
            return true;
        if (this.getMessage() != null)
            return this.getMessage().equals(tmpObj.getMessage()) && this.getResultType().equals(tmpObj.getResultType());
        return false;
    }
}


