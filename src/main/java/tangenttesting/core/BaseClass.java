/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.core;

import main.java.tangenttesting.entities.Enums;
import main.java.tangenttesting.utilities.SeleniumDriverUtility;

/**
 *
 * @author nditema
 */
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
