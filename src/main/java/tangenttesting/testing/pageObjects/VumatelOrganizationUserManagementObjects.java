package main.java.tangenttesting.testing.pageObjects;

/**
 *
 * @author nditema
 */

public class VumatelOrganizationUserManagementObjects
{
    public static String organizationDiv() { return "//div[contains(text(), ' Organization ')]"; }

    public static String userManagementDiv() { return "//div[contains(text(), ' User Management ')]"; }

    public static String addUserIcon() { return "//i[contains(text(), 'person_add')]"; }

    public static String createNewUserPopUp() { return "//h1[@class='headline pl-2']"; }

    public static String firstNameField() { return "//label[contains(text(), 'First name')]/..//input"; }

    public static String lastNameField() { return "//label[contains(text(), 'Last name')]/..//input"; }

    public static String emailField() { return "//label[contains(text(), 'Email')]/..//input"; }

    public static String createUserBtn() { return "//span[contains(text(), 'Create User')]"; }

    public static String searchBox() { return "//label[contains(text(), 'Search for Users')]/..//input[@type='text']"; }

    public static String passwordImage() { return "//i[contains(text(), 'lock')]"; }

    public static String resetPasswordPopUp() { return "//h1[contains(text(), 'Reset Password')]"; }

    public static String resetButton() { return "//span[contains(text(), 'Reset')]"; }

    public static String doneButton() { return "//span[contains(text(), 'Done')]"; }

    public static String editImage() { return "//i[contains(text(), 'edit')]"; }

    public static String editUserPopUp() { return "//h1[contains(text(), 'Edit User')]"; }

    public static String afriHostSalesSwitch() { return "//div[@class='v-input--selection-controls__ripple']"; }
}
