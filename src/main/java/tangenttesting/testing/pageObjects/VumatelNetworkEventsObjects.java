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

public class VumatelNetworkEventsObjects {
    public static String networkEvents() { return "//div[contains(text(),' Network Events ')]"; }

    public static String networkEventsHeading() { return "//div[contains(text(),' Vumatel Network Related Event Notices ')]"; }

    public static String openTab() { return "//div[@role='tablist']//div[contains(text(),'Open')]"; }

    public static String futureTab() { return "//div[contains(text(),'Future')]"; }

    public static String historicTab() { return "//div[contains(text(),'Historic')]"; }

    public static String filterCityDropdown() { return "//input[@placeholder='Filter By City']/../..//i[contains(text(),'arrow_drop_down')]"; }

    public static String selectItemfromListBox(String item) { return "//div[@role='listbox']//div[(text()='"+item+"')]"; }

    public static String filterProductDropdown() { return "//input[@placeholder='Filter By Product Type']/../..//i[contains(text(),'arrow_drop_down')]"; }

    public static String filterGradeDropdown() { return "//input[@placeholder='Filter By Grade']/../..//i[contains(text(),'arrow_drop_down')]"; }

    public static String viewEvent() { return "//i[contains(text(),'visibility')]"; }

    public static String openEventsTitle() { return "//div[contains(text(),'Open Events')]"; }

    public static String futureEventsTitle() { return "//div[contains(text(),'Future Events')]"; }

    public static String historicEventsTitle() { return "//div[contains(text(),'Historic Events')]"; }

    public static String checkItem(String item) { return "//*[@id=\"app\"]/div[1]/main/div/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr[1]//td[contains(text(), '"+item+"')]"; }
}
