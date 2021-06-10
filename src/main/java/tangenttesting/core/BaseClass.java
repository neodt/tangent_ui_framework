package main.java.tangenttesting.core;

import main.java.tangenttesting.entities.Enums;
import main.java.tangenttesting.utilities.SeleniumDriverUtility;

public class BaseClass
{
    public static SeleniumDriverUtility SeleniumDriverInstance;

    private static String _curEnvironment;
    public static void setCurrentEnvironment(String environment)
    {
        _curEnvironment = environment;
    }
    public static String getCurrentEnvironment()
    {
        return _curEnvironment;
    }
    public static Enums.BrowserType SelectedBrowser;
}
