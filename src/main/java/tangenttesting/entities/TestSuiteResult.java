/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.entities;

import main.java.tangenttesting.entities.testresult.TestResult;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nditema
 */
public class TestSuiteResult
{
    private String _resultStatus;
    private String _message;
    private final List<TestResult> _testResults;
    
    public TestSuiteResult(String resultStatus, String resultMessage) {
        this._resultStatus = resultStatus;
        this._message = resultMessage;
        this._testResults = new ArrayList();
    }
    
    public void setResultStatus(String status) {
        this._resultStatus = status;
    }
    
    public void setResultMessage(String message) {
        this._message = message;
    }
    
    public void addTestResult(TestResult result) {
        this._testResults.add(result);
    }
    
    public List<TestResult> getTestResults() {
        return this._testResults;
    }
    
    public String getResultStatus() {
        return this._resultStatus;
    }
    
    public String getResultMessage() {
        return this._message;
    }
}
