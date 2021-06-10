package main.java.tangenttesting.testing.tests;

import main.java.tangenttesting.TestMarshall;
import main.java.tangenttesting.core.BaseClass;
import main.java.tangenttesting.entities.KeywordAnnotation;
import main.java.tangenttesting.entities.TestEntity;
import main.java.tangenttesting.entities.testresult.TestResult;
import main.java.tangenttesting.reporting.Narrator;
import main.java.tangenttesting.testing.pageObjects.VumatelReusableObjects;
import main.java.tangenttesting.utilities.SeleniumDriverUtility;

/**
 * @author nditema
 */
@KeywordAnnotation(
        Keyword ="VumatelLogin",
        createNewBrowserInstance = true
 )
public class T1_VumatelLoginTest extends BaseClass
{
    private TestEntity testData;

    public T1_VumatelLoginTest() {
        SeleniumDriverInstance = new SeleniumDriverUtility(SelectedBrowser);
    }

    public TestResult executeTest()  {
        if(!SeleniumDriverUtility.navigateToURL(VumatelReusableObjects.URL())) {
            return Narrator.testFailed("Failed to navigate to "+VumatelReusableObjects.URL());
        }

        Narrator.stepPassedWithScreenShot("Navigated to Vumatel");
        
        if(!SeleniumDriverUtility.enterText(VumatelReusableObjects.emailInput(), TestMarshall.getTestData().getData("Email"))) {
            return Narrator.testFailed("Failed to enter email ");
        }

        Narrator.stepPassedWithScreenShot("Email entered");

        if(!SeleniumDriverUtility.enterText(VumatelReusableObjects.passwordInput(), TestMarshall.getTestData().getData("Password"))) {
            return Narrator.testFailed("Failed to enter password");
        }

        Narrator.stepPassedWithScreenShot("Password entered");

        if(!SeleniumDriverUtility.clickElement(VumatelReusableObjects.loginBtn())) {
            return Narrator.testFailed("Failed to click search button ");
        }

        if (!SeleniumDriverUtility.validateElementText(VumatelReusableObjects.vumaPortalDiv(), "VUMA Admin Portal")) {
            return Narrator.testFailed("Failed to navigate to page selection page ");
        }

        return Narrator.finalizeTest("Login Successful");
    }
}
