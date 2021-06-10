package main.java.tangenttesting.testing.pageObjects;

/**
 *
 * @author nditema
 */

public class VumatelOrderAcceptanceObjects
{
    public static String searchInput() { return "//input[@data-lpignore='true']"; }
    
    public static String ordersDropdown() {
        return "//div[text()=' Orders ']";
    }
    
    public static String orderAcceptanceDiv() {
        return "//div[text()=' Order Acceptance ']";
    }

    public static String orderAcceptancePageHeading() { return "//div[text()=' View, accept, and reject orders below ']"; }

    public static String noDataAvailable() { return "//td[text()='No data available']"; }

    public static String filteredOrderReference(String network_identifier) { return "//td[text()='"+network_identifier+"']"; }

    public static String startDate() { return "//input[@placeholder='Start Date']"; }

    public static String endDate() { return "//input[@placeholder='End Date']"; }

    public static String validateDate() { return "//td[contains(text(),'2019-02-13')]"; }

    public static String btnView(String orderRef) { return "//td[contains(text(), '"+orderRef+"')]/..//span[contains(text(), 'View')]"; }

    public static String validateIfSearchWasCorrect(String orderRef) { return "//div[contains(text(),'"+orderRef+"')]"; }

    public static String downloadCSV() { return "//i[contains(text(),'save_alt')]"; }

    public static String acceptOrder() { return "//i[contains(text(),'done')]"; }

    public static String acceptOrderPopUp() { return "//div[contains(text(),' You are about to accept the following order:')]"; }

    public static String acceptOrderButton() { return "//span[text() = ' Accept Order ']"; }

    public static String rejectOrder() { return "//i[text() = 'close']"; }

    public static String rejectOrderPopUp() { return "//div[contains(text(),' Are you sure you want to reject order:')]"; }

    public static String rejectOrderButton() { return "//span[contains(text(),' Reject Order ')]"; }


}
