/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.entities;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * @author nditema
 */
public class Enums {
    public enum ResultStatus {PASS, FAIL, WARNING, UNCERTAIN}
    public enum BrowserType{CHROME, IE, FIREFOX, EDGE}
    public enum TestType{SELENIUM, SIKULI, DESKTOP, MOBILE}
    
    public static BrowserType resolveBrowserType(String browserName) {
        List<BrowserType> browsers = new ArrayList<BrowserType>(EnumSet.allOf(BrowserType.class));
        for (BrowserType browser: browsers ) {
            if(browser.toString().equalsIgnoreCase(browserName)) {
                return browser;
            }
        }
        return null;
    }

    public enum Environment {
        QA("https://tangentsolutions.co.za/");
        public final String URL;
        Environment(String _url)
        {
            this.URL = _url;
        }
    }

    public static Environment resolveEnvironment(String env) {
        List<Environment> envs = new ArrayList<Environment>(EnumSet.allOf(Environment.class));
        for (Environment environment: envs) {
            if(environment.toString().equalsIgnoreCase(env))
            {
                return environment;
            }
        }
        return null;
    }
}
