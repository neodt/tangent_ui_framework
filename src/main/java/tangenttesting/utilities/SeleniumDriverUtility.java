package main.java.tangenttesting.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import main.java.tangenttesting.TestMarshall;
import main.java.tangenttesting.entities.Enums;
import main.java.tangenttesting.reporting.Narrator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumDriverUtility {

    private static WebDriver driver;
    private static int _timeout = 30;
    private static Boolean _driverRunning = false;
    private static int screenshot_counter = 0;
    private static Enums.BrowserType selectedBrowser;

    public static WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver dvr) {
        driver = dvr;
    }

    public SeleniumDriverUtility(Enums.BrowserType browser) {
        setBrowser(browser);
    }

    public static void setBrowser(Enums.BrowserType browser) {
        selectedBrowser = browser;
    }

    public static Enums.BrowserType getBrowser() {
        return selectedBrowser;
    }

    public static WebDriver driver() {
        return driver;
    }

    public static Boolean launchDriver() {
        try {
            switch (selectedBrowser) {
                case CHROME:
                    WebDriverManager.chromedriver().setup(); //Using Web-driver to configure properties
                    driver = new ChromeDriver();
                    break;
                case IE:
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;

                case EDGE:
//                    System.setProperty("webdriver.edge.driver", "C:\\Windows\\System32\\MicrosoftWebDriver.exe");
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.err.println("[ERROR] - Failed to select the driver type - '" +selectedBrowser+ "'. Please make sure you select Firefox / Chrome / IE.");
                    return false;
            }

            driver.manage().window().maximize();
            _driverRunning = true;

            return true;
        } catch (Exception e) {
            System.err.println("[ERROR] - Failed to set the Driver, exception thrown is " + e.getMessage());
            return false;
        }
    }

    public static void quit() {
        try {
            driver.quit();
            driver = null;
            _driverRunning = false;
        } catch (Exception e) {

        }
    }

    public static void refreshPage() {
        try {
            driver.navigate().refresh();
        } catch (Exception e) {

        }
    }

    public static void setDriverRunning(Boolean isDriverRunning) {
        _driverRunning = isDriverRunning;
    }

    public static Boolean isDriverRunning() {
        return _driverRunning;
    }

    public static void setTimeout(int timeout) {
        SeleniumDriverUtility._timeout = timeout;
    }

    public static int getTimeout() {
        return SeleniumDriverUtility._timeout;
    }

    public static Boolean navigateToURL(String URL) {
        try {
            driver.navigate().to(URL);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean setPageLoadTimeout(int pageTimeout) {
        try {
            driver.manage().timeouts().pageLoadTimeout(pageTimeout, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to set timeout - " + e.getMessage());
            return false;
        }
    }

    //wait
    public static Boolean waitForElement(String xpath) {
        Boolean foundElement = false;
        try {
            int waitCount = 0;
            while (!foundElement && waitCount < getTimeout()) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                    foundElement = true;
                } catch (Exception e) {
                    waitCount++;
                }
            }
            return foundElement;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean waitForElement(String xpath, int duration) {
        Boolean foundElement = false;
        try {
            int waitCount = 0;
            while (!foundElement && waitCount < duration) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                    foundElement = true;
                } catch (Exception e) {
                    waitCount++;
                }
            }
            return foundElement;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean clickElement(String xpath) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            element.click();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean setRadioButton(String xpath) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            if (!element.isSelected()) {
                element.click();
                return true;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAlertPresent() {

        boolean presentFlag = false;

        try {
            driver.switchTo().alert();
            presentFlag = true;
        } catch (NoAlertPresentException ex) {

        }
        return presentFlag;
    }

    public static Boolean setCheckBox(String xpath, Boolean check) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            if (!element.isSelected() && check) {
                element.click();
                return true;
            } else if (element.isSelected() && !check) {
                element.click();
                return true;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //type
    public static Boolean enterText(String xpath, String text) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            element.sendKeys(text);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean selectOptionByText(String xpath, String text) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            Select selectElement = new Select(element);
            selectElement.selectByVisibleText(text);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String retrieveElementText(String xpath) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public void pause(int milisecondsToWait) {
        try {
            Thread.sleep(milisecondsToWait);
        } catch (InterruptedException e) {

        }
    }

    public static boolean scrollToElement(String elementXpath) {
        try {
            WebElement element = driver.findElement(By.xpath(elementXpath));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Narrator.logDebug("Scrolled to element - " + elementXpath);
            return true;
        } catch (Exception e) {
            Narrator.logError("Error scrolling to element - " + elementXpath + " - " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public static String retrieveElementAttribute(String xpath, String attribute) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

            return element.getAttribute(attribute);
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean validateElementText(String xpath, String text) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

            return text.equals(retrieveElementText(xpath));
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean validateElementAttribute(String xpath, String Attribute, String AttributeValue) {
        try {
            waitForElement(xpath);
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

            return AttributeValue.equals(retrieveElementAttribute(xpath, Attribute));
        } catch (Exception e) {
            return false;
        }
    }

    public static String takeScreenshot(String description, Boolean isError, WebDriver driver) {
        try {
            screenshot_counter++;
            StringBuilder imageFilePathBuilder = new StringBuilder();
            imageFilePathBuilder.append(Narrator.getReportDirectory());
            StringBuilder relativePathBuilder = new StringBuilder();
            String folderName = (TestMarshall.getTestData().getTestCaseID() == null) ? "Screenshots" : TestMarshall.getTestData().getTestCaseID();

            if (isError) {
                relativePathBuilder.append(folderName).append("\\Failed_");
            } else {
                relativePathBuilder.append(folderName).append("\\Passed_");
            }
            relativePathBuilder.append("_").append(screenshot_counter).append(".png");
//            relativePathBuilder.append(description).append("_").append(screenshot_counter).append(".png");

            imageFilePathBuilder.append(relativePathBuilder.toString());

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(imageFilePathBuilder.toString()));
            Narrator.setAbsScreenshotPath(imageFilePathBuilder.toString());

            return "./" + relativePathBuilder.toString();

        } catch (IOException | WebDriverException e) {
            Narrator.setAbsScreenshotPath("");
            return null;
        }
    }

    public static boolean switchToTabOrWindow() {
        try {
//            String winHandleBefore = SeleniumDriverInstance.Driver.getWindowHandle();
            String winHandleAfter = "";
            for (String winHandle : driver.getWindowHandles()) {
                winHandleAfter = winHandle;
                driver.switchTo().window(winHandle);
            }
            //Validate switch.
            if (!winHandleAfter.equalsIgnoreCase(driver.getWindowHandle())) {
                Narrator.logError("Failed to switch to new tab" + winHandleAfter);
                return false;
            }
        } catch (Exception ex) {
            Narrator.logError("Could not switch to new tab" + ex.getMessage());
            return false;
        }
        Narrator.logDebug("Switched to window " + driver.getTitle());
        return true;
    }

    public static boolean clearTextByXpath(String elementXpath) {
        try {
            SeleniumDriverUtility.waitForElement(elementXpath);
            WebElement elementToClear = driver.findElement(By.xpath(elementXpath));
            elementToClear.sendKeys(Keys.chord(Keys.CONTROL, "a"), "");
            elementToClear.sendKeys(Keys.chord(Keys.DELETE));
            elementToClear.clear();
            Narrator.logDebug("Cleared Text Successfully: " + elementXpath);
            return true;
        } catch (Exception e) {
            Narrator.logError(" Failed to Clear Text- " + elementXpath + " - " + e.getMessage());
            return false;
        }
    }

    public boolean clickElementbyXpathUsingJavascript(String elementXpath) {
        try {
            SeleniumDriverUtility.waitForElement(elementXpath);
            WebElement element;
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            element = driver.findElement(By.xpath(elementXpath));
            executor.executeScript("arguments[0].click();", element);
            // WebElement elementToClick = Driver.findElement(By.xpath(elementXpath));
            // element.click();
            Thread.sleep(2500);
            return true;
        } catch (InterruptedException e) {
            System.out.println("Failed to click Element by Xpath using Javascript " + e.getMessage());
            return false;
        }
    }

    public static boolean alertHandler() {
        try {
            Narrator.logDebug("Attempting to click OK in alert pop-up");
            // Get a handle to the open alert, prompt or confirmation
            Alert alert = driver.switchTo().alert();
            // Get the text of the alert or prompt
            alert.getText();
            // And acknowledge the alert (equivalent to clicking "OK")
            alert.accept();
            Narrator.logDebug("Ok Clicked successfully...proceeding");
            return true;
        } catch (Exception e) {
            Narrator.logError(" clicking OK in alert pop-up - " + e.getMessage());
            return false;
        }
    }

    public static boolean switchToFrameByID(String elementName) {
        int waitCount = 0;
        try {
            while (waitCount < getTimeout()) {
                try {
                    List<WebElement> frameMain = getDriver().findElements(By.xpath("//iframe[contains(@id, '" + elementName + "')]"));
                    getDriver().switchTo().frame(frameMain.get(0));
                    return true;
                } catch (Exception e) {
                    waitCount++;
                }
            }
            return false;
        } catch (Exception e) {
            Narrator.logError(" switching to frame by name - " + e.getMessage());
            return false;
        }
    }

    public static boolean switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            Narrator.logDebug("Switched to default content");
            return true;
        } catch (Exception e) {
            Narrator.logError(" switching to default content  - " + e.getMessage());
            return false;
        }
    }
}
