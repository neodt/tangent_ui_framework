/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.tangenttesting.entities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nditema
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KeywordAnnotation {
    String Keyword();
    //Use to restart - init the selenium instance
    boolean createNewBrowserInstance() default false;    
 
    //Use to mark a test as skippable
    //This will mean the test will log a skip if the previous test failed
    boolean blockable() default false;
    
    boolean sikuliTest() default false;
    //Api testing
    boolean apiTesting() default false;
}
