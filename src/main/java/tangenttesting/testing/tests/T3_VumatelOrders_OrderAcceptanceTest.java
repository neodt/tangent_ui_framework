/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.testing.tests;

import main.java.tangenttesting.TestMarshall;
import main.java.tangenttesting.core.BaseClass;
import main.java.tangenttesting.entities.KeywordAnnotation;
import main.java.tangenttesting.entities.TestEntity;
import main.java.tangenttesting.entities.testresult.TestResult;
import main.java.tangenttesting.reporting.Narrator;
import main.java.tangenttesting.testing.pageObjects.VumatelOrderAcceptanceObjects;
import main.java.tangenttesting.testing.pageObjects.VumatelReusableObjects;
import main.java.tangenttesting.utilities.SeleniumDriverUtility;

/**
 * @author nditema
 */
@KeywordAnnotation(
        Keyword ="Orders_OrderAcceptance",
        createNewBrowserInstance = true
 )
public class T3_VumatelOrders_OrderAcceptanceTest extends BaseClass
{
    private TestEntity testData;

    public T3_VumatelOrders_OrderAcceptanceTest() {
        SeleniumDriverInstance = new SeleniumDriverUtility(SelectedBrowser);
    }

    public TestResult executeTest()  {
        if(!SeleniumDriverUtility.navigateToURL(VumatelReusableObjects.URL())) {
            return Narrator.testFailed("Failed to navigate to "+VumatelReusableObjects.URL());
        }

        if(!SeleniumDriverUtility.enterText(VumatelReusableObjects.emailInput(), TestMarshall.getTestData().getData("Email"))) {
            return Narrator.testFailed("Failed to enter email ");
        }

        if(!SeleniumDriverUtility.enterText(VumatelReusableObjects.passwordInput(), TestMarshall.getTestData().getData("Password"))) {
            return Narrator.testFailed("Failed to enter password");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelReusableObjects.loginBtn())) {
            return Narrator.testFailed("Failed to click search button ");
        }

        if (!SeleniumDriverUtility.clickElement(VumatelReusableObjects.vumaPortalDiv())) {
            return Narrator.testFailed("Failed to navigate to page selection page ");
        }

        //Navigate to OrderAcceptance
        if(!SeleniumDriverUtility.clickElement(VumatelReusableObjects.hamburgerMenu())) {
            return Narrator.testFailed("Failed to click menu ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.ordersDropdown())) {
            return Narrator.testFailed("Failed to click 'Orders' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.orderAcceptanceDiv())) {
            return Narrator.testFailed("Failed to click 'Order Acceptance Div' ");
        }

        if (!SeleniumDriverUtility.waitForElement(VumatelOrderAcceptanceObjects.orderAcceptancePageHeading())) {
            if (!SeleniumDriverUtility.validateElementText(VumatelOrderAcceptanceObjects.orderAcceptancePageHeading(), " View, accept, and reject orders below ")) {
                return Narrator.testFailed("Failed to navigate to 'Order Acceptance' page ");
            }
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Successfully navigated to 'Order Acceptance' page");

        //Filter date
        if(!SeleniumDriverUtility.enterText(VumatelOrderAcceptanceObjects.startDate(), TestMarshall.getTestData().getData("StartDate"))) {
            return Narrator.testFailed("Failed enter start date");
        }

        if(!SeleniumDriverUtility.enterText(VumatelOrderAcceptanceObjects.endDate(), TestMarshall.getTestData().getData("EndDate"))) {
            return Narrator.testFailed("Failed enter end date ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.validateDate())) {
            return Narrator.testFailed("Failed to filter by date ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Successfully filtered orders by date from '"+TestMarshall.getTestData().getData("StartDate")+"' to '"+TestMarshall.getTestData().getData("EndDate")+"'");

        //Search by orderRef
        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.searchInput())) {
            return Narrator.testFailed("Failed to click search box ");
        }

        if(!SeleniumDriverUtility.enterText(VumatelOrderAcceptanceObjects.searchInput(), TestMarshall.getTestData().getData("OrderRef"))) {
            return Narrator.testFailed("Failed to enter 'network_identifier' in search box ");
        }
        if(!SeleniumDriverUtility.waitForElement(VumatelOrderAcceptanceObjects.btnView(TestMarshall.getTestData().getData("OrderRef")))) {
            return Narrator.testFailed("Failed to click view button ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Successfully returned the correct order from search '"+TestMarshall.getTestData().getData("OrderRef")+"'");

//        //View Order
//        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.btnView(TestMarshall.getTestData().getData("OrderRef")))) {
//            return Narrator.testFailed("Failed to view button ");
//        }
//
//        if(!SeleniumDriverUtility.waitForElement(VumatelOrderAcceptanceObjects.validateIfSearchWasCorrect(TestMarshall.getTestData().getData("SearchBox")))) {
//            return Narrator.testFailed("Failed to view correct Order ");
//        }

        //Download CSV file
        SeleniumDriverUtility.refreshPage();

//        SeleniumDriverInstance.pause(2000);
//        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.downloadCSV())) {
//            return Narrator.testFailed("Failed to download CSV file ");
//        }
//
//        Narrator.stepPassedWithScreenShot("Successfully downloaded CSV File ");

        //Accept Order
        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.acceptOrder())) {
            return Narrator.testFailed("Failed to click Accept Order image");
        }

        if(!SeleniumDriverUtility.waitForElement(VumatelOrderAcceptanceObjects.acceptOrderPopUp())) {
            return Narrator.testFailed("Failed show Accept order popUp ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Accept Order PopUp successful ");

        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.acceptOrderButton())) {
            return Narrator.testFailed("Failed to click Accept Order button ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Order successfully accepted ");

        //Reject Order
        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.rejectOrder())) {
            return Narrator.testFailed("Failed to click Reject Order image");
        }

        if(!SeleniumDriverUtility.waitForElement(VumatelOrderAcceptanceObjects.rejectOrderPopUp())) {
            return Narrator.testFailed("Failed show Reject order popUp ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Reject Order PopUp successful ");

        if(!SeleniumDriverUtility.clickElement(VumatelOrderAcceptanceObjects.rejectOrderButton())) {
            return Narrator.testFailed("Failed to click Reject Order button ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Order successfully rejected ");

        return Narrator.finalizeTest("Order Acceptance Search successful");
    }
}
