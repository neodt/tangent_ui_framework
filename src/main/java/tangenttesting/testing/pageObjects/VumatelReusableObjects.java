/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.testing.pageObjects;

/**
 *
 * @author nditema
 */

public class VumatelReusableObjects
{
    public static String URL(){return "https://passport-staging.vumatel.co.za/";}

    public static String emailInput() { return "//input[@name='email-input']"; }
    
    public static String passwordInput() {
        return "//input[@name='password-input']";
    }
    
    public static String loginBtn() {
        return "//button[@tabindex]//span[@class='v-btn__content']";
    }

    public static String vumaPortalDiv() {
        return "//div[text()='VUMA Admin Portal']";
    }

    public static String hamburgerMenu() {
        return "//i[text()='menu']";
    }
}
