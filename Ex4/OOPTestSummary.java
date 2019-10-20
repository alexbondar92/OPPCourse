package OOP.Solution;

import OOP.Provided.OOPResult;

import java.util.HashMap;
import java.util.Map;

public class OOPTestSummary {
    Map<String, OOPResult> testMap;

    public OOPTestSummary(Map<String, OOPResult> testMap){
        if (testMap == null)
            return;
        this.testMap = new HashMap<>(testMap);
    }

    public int getNumSuccesses(){
        int counter = 0;
        if (testMap == null){
            return 0;
        }
        for(Map.Entry<String, OOPResult> entry : testMap.entrySet()){
            if (entry.getValue().getResultType() == OOPResult.OOPTestResult.SUCCESS){
                ++counter;
            }
        }
        return counter;
    }

    public int getNumFailures(){
        int counter = 0;
        if (testMap == null){
            return 0;
        }
        for(Map.Entry<String, OOPResult> entry : testMap.entrySet()){
            if (entry.getValue().getResultType() == OOPResult.OOPTestResult.FAILURE){
                ++counter;
            }
        }
        return counter;
    }

    public int getNumExceptionMismatches(){
        int counter = 0;
        if (testMap == null){
            return 0;
        }
        for(Map.Entry<String, OOPResult> entry : testMap.entrySet()){
            if (entry.getValue().getResultType() == OOPResult.OOPTestResult.EXPECTED_EXCEPTION_MISMATCH){
                ++counter;
            }
        }
        return counter;
    }

    public int getNumErrors(){
        int counter = 0;
        if (testMap == null){
            return 0;
        }
        for(Map.Entry<String, OOPResult> entry : testMap.entrySet()){
            if (entry.getValue().getResultType() == OOPResult.OOPTestResult.ERROR){
                ++counter;
            }
        }
        return counter;
    }
}
