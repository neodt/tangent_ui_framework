package main.java.tangenttesting.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestEntity {

    // Properties -
    // TestMethod
    // TestParameters
    // Methods - 
    // constructors
    // addParameter
    // -----------------------------------------------------------------------
    // Serves as a container for each test found in the spreadsheet/test input 
    // document.
    private String TestCaseId;
    private String TestMethod;
    private String TestDescription;
    private Map<String, String> TestParameters;
    private Map<String, ArrayList<String>> ExtractedParameters;
  
    public TestEntity() {
        
    }

    public TestEntity(String testCaseId, String testMethod, String testDescription, LinkedHashMap<String, ArrayList<String>> extractedParameters) {
        this.TestCaseId = testCaseId;
        this.TestMethod = testMethod;
        this.TestDescription = testDescription;        
        this.ExtractedParameters = extractedParameters;
    }

    public TestEntity(String testCaseId, String testDescription, LinkedHashMap<String, ArrayList<String>> extractedParameters) {
        this.TestCaseId = testCaseId;       
        this.TestDescription = testDescription;
        this.ExtractedParameters = extractedParameters;
    }

    public TestEntity(String testCaseId, String testDescription) {
        this.TestCaseId = testCaseId;
        this.TestDescription = testDescription;
    }
    
    public TestEntity(Map<String, String> params) {
        this.TestParameters = params;
    }
    
    public Map<String, String> getTestParameters(){return this.TestParameters;}
    
    public void setTestCaseID(String id){this.TestCaseId = id;}
    public void setTestMethod(String testMethod){this.TestMethod = testMethod;}
    public void setTestDescription(String description){this.TestDescription = description;}

    public String getTestCaseID(){return this.TestCaseId;}
    public String getTestMethod(){
        return this.TestMethod;
    }
    public String getTestDescription(){return this.TestDescription;}
    public Map<String, ArrayList<String>> getExtractedParams(){return this.ExtractedParameters;}
        
    public String getData(String parameterName) {
        String returnedValue = this.TestParameters.get(parameterName);

        if (returnedValue == null) {
            System.err.println(" Parameter ' " + parameterName + " ' not found");
            returnedValue = "";
        }
        return returnedValue;
    }

    public void addParameter(String parameterName, String parameterValue) {
        if (TestParameters == null) {
            this.TestParameters = new HashMap<String, String>();
        }
        this.TestParameters.put(parameterName, parameterValue);
    }

    public void updateParameter(String parameterName, String newParameterValue) {
        this.TestParameters.put(parameterName, newParameterValue);
    }

    public void addParameterAsFirst(String parameterName, String parameterValue) {
        Map<String, String> tempParamList = new HashMap<String, String>();
        tempParamList.put(parameterName, parameterValue);
        tempParamList.putAll(this.TestParameters);

        this.TestParameters = tempParamList;
    }

    @SuppressWarnings("rawtypes")
    public String testParametersToString() {
        if (TestParameters != null && TestParameters.size() > 0) {
            String parameters = "";
            Iterator<Map.Entry<String, String>> paramIterator = TestParameters.entrySet().iterator();
            while (paramIterator.hasNext()) {
                Map.Entry pairs = paramIterator.next();
                parameters = parameters + pairs.getKey() + "|" + pairs.getValue() + ",";
                paramIterator.remove();
            }
            return parameters;
        } else {
            return "No test parameters";
        }
    }

    //HTML Detailed View
    public void extractParameter(String parameterName, String parameterValue, String parameterStatus) {
        if (ExtractedParameters == null) {
            this.ExtractedParameters = new LinkedHashMap<String, ArrayList<String>>();
        }

        String formattedParameterStatus = this.getFormattedParameterStatus(parameterStatus);//send arguments: pass,warning,fail

        ArrayList<String> tempList = new ArrayList<>();
        tempList.add(parameterValue);
        tempList.add(formattedParameterStatus);

        this.ExtractedParameters.put(parameterName, tempList);
    }

    public String getFormattedParameterStatus(String parameterStatus) {
        // <editor-fold defaultstate="collapsed" desc="making parameterStatus case-insensitive">
        String status = "";
        try
        {
            if (!parameterStatus.isEmpty())
            {
                char s = parameterStatus.toUpperCase().charAt(0);
                status = "";
                switch (s)
                {
                    case 'P':
                        status = "PASS";
                        break;
                    case 'F':
                        status = "FAIL";
                        break;
                    case 'W':
                        status = "WARNING";
                        break;
                    default:
                        status = "";
                        break;
                }
            }
        } catch (Exception e)
        {
            System.err.println("Exception thrown: " + e.toString());
        }
        // </editor-fold>
        return status;
    }

    public String getExtractedParameter(String parameterName) {
        String returnedValue = "";

        try {
            ArrayList returnedList = this.ExtractedParameters.get(parameterName);
            returnedValue = returnedList.get(0).toString();

            if (returnedValue == null) {
              //  Narrator.logError(" Extracted parameter ' " + parameterName + " ' not found");
                returnedValue = "";
            }
        } 
        catch (Exception e) {
          //  Narrator.logError(" Failed to find node by name: " + parameterName + "' - " + e.getMessage());
            return returnedValue;
        }

        return returnedValue;
    }
}
