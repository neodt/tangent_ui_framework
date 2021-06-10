/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.testing.tests;

import com.github.javafaker.Faker;
import main.java.tangenttesting.TestMarshall;
import main.java.tangenttesting.core.BaseClass;
import main.java.tangenttesting.entities.KeywordAnnotation;
import main.java.tangenttesting.entities.TestEntity;
import main.java.tangenttesting.entities.testresult.TestResult;
import main.java.tangenttesting.reporting.Narrator;
import main.java.tangenttesting.testing.pageObjects.VumatelOrganizationUserManagementObjects;
import main.java.tangenttesting.testing.pageObjects.VumatelReusableObjects;
import main.java.tangenttesting.utilities.SeleniumDriverUtility;

/**
 * @author nditema
 */
@KeywordAnnotation(
        Keyword ="Organization_UserManagement",
        createNewBrowserInstance = true
 )
public class T4_VumatelOrganization_UserManagementTest extends BaseClass
{
    private TestEntity testData;

    public T4_VumatelOrganization_UserManagementTest() {
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

        Narrator.stepPassedWithScreenShot("Successfully navigated to Vumatel portal page");

        if(!SeleniumDriverUtility.clickElement(VumatelReusableObjects.hamburgerMenu())) {
            return Narrator.testFailed("Failed to click menu ");
        }

        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.organizationDiv())) {
            return Narrator.testFailed("Failed to click Organization tab ");
        }

        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.userManagementDiv())) {
            return Narrator.testFailed("Failed to click User Management tab ");
        }

        //Create User
        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.addUserIcon())) {
            return Narrator.testFailed("Failed to click add user floating button ");
        }

        if (!SeleniumDriverUtility.waitForElement(VumatelOrganizationUserManagementObjects.createNewUserPopUp())) {
            return Narrator.testFailed("Failed to show Create user Pop up ");
        }

        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        if (!SeleniumDriverUtility.enterText(VumatelOrganizationUserManagementObjects.firstNameField(), firstName)) {
            return Narrator.testFailed("Failed to enter First name ");
        }

        if (!SeleniumDriverUtility.enterText(VumatelOrganizationUserManagementObjects.lastNameField(), lastName)) {
            return Narrator.testFailed("Failed to enter Last name ");
        }

        if (!SeleniumDriverUtility.enterText(VumatelOrganizationUserManagementObjects.emailField(), email)) {
            return Narrator.testFailed("Failed to enter Email ");
        }

        Narrator.stepPassedWithScreenShot("User to be added ");

        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.createUserBtn())) {
            return Narrator.testFailed("Failed to click Create user button ");
        }

        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.doneButton())) {
            return Narrator.testFailed("Failed to click done button ");
        }

        SeleniumDriverUtility.refreshPage();

        SeleniumDriverInstance.pause(2000);
        if (!SeleniumDriverUtility.enterText(VumatelOrganizationUserManagementObjects.searchBox(), lastName)) {
            return Narrator.testFailed("Failed to enter search text ");
        }

        Narrator.stepPassedWithScreenShot("Successfully added and searched for user ");

        //Reset password
        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.passwordImage())) {
            return Narrator.testFailed("Failed to click password icon ");
        }

        if (!SeleniumDriverUtility.waitForElement(VumatelOrganizationUserManagementObjects.resetPasswordPopUp())) {
            return Narrator.testFailed("Failed to find Reset Password popUp ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Reset password PopOp");

        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.resetButton())) {
            return Narrator.testFailed("Failed to click reset button ");
        }

        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.doneButton())) {
            return Narrator.testFailed("Failed to click done button ");
        }

//        SeleniumDriverInstance.pause(2000);
//        Narrator.stepPassedWithScreenShot("Password reset successfully ");

        //Edit User
        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.editImage())) {
            return Narrator.testFailed("Failed to click edit icon ");
        }

        if (!SeleniumDriverUtility.waitForElement(VumatelOrganizationUserManagementObjects.editUserPopUp())) {
            return Narrator.testFailed("Failed to find Edit User popUp ");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("Edit User popUp");

        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.afriHostSalesSwitch())) {
            return Narrator.testFailed("Failed to click AfriHostSales Switch");
        }

        SeleniumDriverInstance.pause(2000);
        Narrator.stepPassedWithScreenShot("AfriHost Sales switch activated");

        if (!SeleniumDriverUtility.clickElement(VumatelOrganizationUserManagementObjects.doneButton())) {
            return Narrator.testFailed("Failed to click done button ");
        }

        return Narrator.finalizeTest("Organization User Acceptance successful");
    }
}
