/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.entities;

/**
 *@author nditema
 */

public class Device {
    private String descriptionName;
    private String udid;
    private String deviceName;
    private String platformVersion;
    private String systemPort;
    
    public Device(String descriptionName,String udid, String deviceName, String platformVersion, String systemPort) {
        this.deviceName = deviceName;
        this.platformVersion = platformVersion;
        this.systemPort = systemPort;
        this.descriptionName = descriptionName;
        this.udid = udid;
    }

    public Device(String descriptionName,String udid, String deviceName, String platformVersion) {
        this.deviceName = deviceName;
        this.platformVersion = platformVersion;
        this.descriptionName = descriptionName;
        this.udid = udid;
    }
    
    public String getUdid(){return this.udid;}
    public String getDeviceName(){return this.deviceName;}
    public String getDescriptionName(){return this.descriptionName;}
    public String getPlatformVersion(){return this.platformVersion;}
    public String getSystemPort(){return this.systemPort;}
}
