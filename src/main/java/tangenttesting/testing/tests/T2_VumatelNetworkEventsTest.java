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
import main.java.tangenttesting.testing.pageObjects.VumatelNetworkEventsObjects;
import main.java.tangenttesting.testing.pageObjects.VumatelOrderAcceptanceObjects;
import main.java.tangenttesting.testing.pageObjects.VumatelReusableObjects;
import main.java.tangenttesting.utilities.SeleniumDriverUtility;

/**
 *
 * @author nditema
 */
@KeywordAnnotation(
        Keyword ="Network_Events",
        createNewBrowserInstance = true
 )
public class T2_VumatelNetworkEventsTest extends BaseClass
{
    private TestEntity testData;

    public T2_VumatelNetworkEventsTest() {
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
            return Narrator.testFailed("Failed to click 'Vumatel Portal Page' div ");
        }

        Narrator.stepPassedWithScreenShot("Navigated to Vumatel portal page");

        if(!SeleniumDriverUtility.clickElement(VumatelReusableObjects.hamburgerMenu())) {
            return Narrator.testFailed("Failed to click menu ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.networkEvents())) {
            return Narrator.testFailed("Failed to click 'Network Events' div ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Navigated to 'Network Events' page ");

        //Open Tab
        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.openTab())) {
            return Narrator.testFailed("Failed to click 'Open' tab ");
        }

        if (!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterCityDropdown())) {
            return Narrator.testFailed("Failed to click 'City' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("City")))) {
            return Narrator.testFailed("Failed to click 'City' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterProductDropdown())) {
            return Narrator.testFailed("Failed to click 'Product' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("Product")))) {
            return Narrator.testFailed("Failed to click 'Product' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterGradeDropdown())) {
            return Narrator.testFailed("Failed to click 'Grade' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("Grade")))) {
            return Narrator.testFailed("Failed to click 'Grade' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.openEventsTitle())) {
            return Narrator.testFailed("Failed to click open events ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Filtered Open Events ");

        //Future tab
        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.futureTab())) {
            return Narrator.testFailed("Failed to click 'Future' tab ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterCityDropdown())) {
            return Narrator.testFailed("Failed to click 'City' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("City")))) {
            return Narrator.testFailed("Failed to click 'City' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterProductDropdown())) {
            return Narrator.testFailed("Failed to click 'Product' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("Product")))) {
            return Narrator.testFailed("Failed to click 'Product' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterGradeDropdown())) {
            return Narrator.testFailed("Failed to click 'Grade' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("Grade")))) {
            return Narrator.testFailed("Failed to click 'Grade' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.futureEventsTitle())) {
            return Narrator.testFailed("Failed to click open events ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Filtered Future Events ");

        //Historic tab
        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.historicTab())) {
            return Narrator.testFailed("Failed to click 'Historic' tab ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterCityDropdown())) {
            return Narrator.testFailed("Failed to click 'City' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("City")))) {
            return Narrator.testFailed("Failed to click 'City' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterProductDropdown())) {
            return Narrator.testFailed("Failed to click 'Product' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("Product")))) {
            return Narrator.testFailed("Failed to click 'Product' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.filterGradeDropdown())) {
            return Narrator.testFailed("Failed to click 'Grade' dropdown ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.selectItemfromListBox(TestMarshall.getTestData().getData("Grade")))) {
            return Narrator.testFailed("Failed to click 'Grade' item from listbox ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.historicEventsTitle())) {
            return Narrator.testFailed("Failed to click open events ");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelNetworkEventsObjects.viewEvent())) {
            return Narrator.testFailed("Failed to click view event ");
        }

        Narrator.stepPassedWithScreenShot("Filtered Historic Events");

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Event Viewed");

        return Narrator.finalizeTest("Network Events completed successfully");
    }
}
