package main.java.tangenttesting.reporting;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;

//import com.inspiredtesting.models.*;
//import com.inspiredtesting.report.InspiredReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.err;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.tangenttesting.TestMarshall;
import main.java.tangenttesting.entities.Enums;
import main.java.tangenttesting.entities.testresult.TestResult;
import main.java.tangenttesting.utilities.SeleniumDriverUtility;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.apache.commons.codec.binary.Base64;

/**
 * @author nditema
 * */

public class Narrator {

    private static String _reportDirectory;
    private static ExtentReports extentReport = null;
    private static ExtentReports klovReport = null;
//    private static TangentReport TangentReport = null;

    private static ExtentTest currentTest;
    private static ExtentTest klovTest;
//    private static TangentTest currentTangentTest;

    private static ExtentKlovReporter klovReporter;
    private static String _extentFile = null;
    private static String _tangentFile = null;
    private static DateTime startTime;
    private static boolean _isJenkins = false;

    private static String _error;
    private static String narratorLog;
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:ms");
    private static String _absoluteScreenshot;

    private static String projectName = "Vumatel";
    private static String reportName = "Testing";
    private static String description = "";

    public static void setDescription(String name) {
        description = name;
    }
    
    public static void setProjectName(String name) {
        projectName = name;
    }

    public static String getProjectName() {
        return projectName;
    }

    public static void setAbsScreenshotPath(String path) {
        _absoluteScreenshot = path;
    }

    public static String getAbsScreenshotPath() {
        return _absoluteScreenshot;
    }

    public static void setReportName(String name) {
        reportName = name;
    }

    public static String getReportName() {
        return reportName;
    }

    public static void setError(String error) {
        _error = error;
    }

    public static String getError() {
        return _error;
    }

    public static void checkReporters() {
        if (reportName == null || reportName == "") {
            reportName = "Unknown Test";
//            setup(reportName);
//            addTest();
        }
//        if (extentReport == null && TangentReport == null) {
//            setup(reportName);
//            addTest();
//        }
    }
    
    public static void checkOnlyReporters()    {
         if (reportName == null || reportName == "") {
            //reportName = "Unknown Test";
             setup(reportName);
        }
//        if (extentReport == null && TangentReport == null) {
//            setup(reportName);
//        }
    }

    public static void setup(String testpack) {
        _reportDirectory = System.getProperty("user.dir") + "\\reports\\" + getFileName(testpack) + "\\" + getCurTime() + "\\";
        _extentFile = "extentReport.html";
        _tangentFile = "TangentReport.html";
        narratorLog = _reportDirectory + "\\Narrator_Log.txt";
        new File(_reportDirectory).mkdirs();

        createNarratorLog();

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(_reportDirectory + _extentFile);
        htmlReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss a");
        Locale.setDefault(Locale.ENGLISH);

        extentReport = new ExtentReports();

        _isJenkins = TestMarshall.getIsJenkins();

        if (_isJenkins) {
            initialiseKlovReporter(getProjectName(), getReportName());
        }

        extentReport.attachReporter(htmlReporter);

        extentReport.setSystemInfo("OS", System.getProperty("os.name"));

        if (SeleniumDriverUtility.getBrowser() != null) {
            extentReport.setSystemInfo("Browser", SeleniumDriverUtility.getBrowser().name());
            if (_isJenkins) {
                klovReport.setSystemInfo("Browser", SeleniumDriverUtility.getBrowser().name());
            }
        }

        extentReport.setAnalysisStrategy(AnalysisStrategy.TEST);
        extentReport.flush();


        /**
         * *
         * inspiredReport
         */
//        extentReport = new ExtentReports(_reportDirectory);
//        tangentReport.setLogo("https://cdn.bitrix24.com/b9962577/landing/9ff/9ff3ee5909ca734863859094bc366184/tangent_logo_full_colour_400_1x.png");
//        tangentReport.setReportName(_tangentFile).setProjectName(projectName);

    }

    private static void createNarratorLog() {
        try {
            File logFile = new File(getNarratorLog());
            if (!logFile.exists()) {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            }
        } catch (Exception e) {
        }
    }

    public static String getExtenReport() {
        return _extentFile;
    }

//    public TangentReport getInspiredReport() {
//        return tangentReport;
//    }

    public String getInspiredReportName() {
        return _tangentFile;
    }

    public static void setNarratorLog(String path) {
        narratorLog = path;
    }

    public static String getNarratorLog() {
        return narratorLog;
    }

    public static ExtentTest getCurrentTest() {
        return currentTest;
    }

//    public TangentTest getCurrentnzTest() {
//        return currentTangentTest;
//    }

    public static void stepPassed(String message) {
        
        checkReporters();
        currentTest.log(Status.PASS, message);

        if (_isJenkins) {
            klovTest.log(Status.PASS, message);
        }

        logPass(message);

        extentReport.flush();

        /**
         * inspiredReport
         */
//        currentTangentTest.pass(message);
//        tangentReport.flush();
    }

    public static void stepPassed(String messageToFormat, CodeLanguage codeLanguage) {

        checkReporters();
        currentTest.log(Status.PASS, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));

        if (_isJenkins) {
            klovTest.log(Status.PASS, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));
        }

        logPass(messageToFormat);

        extentReport.flush();

        /**
         * tangentReport
         */
//        currentTangentTest.pass(messageToFormat);
//        tangentReport.flush();
    }

    public static void stepPassed(String message, ExtentTest test) {
                       
        test.log(Status.PASS, message);

        if (_isJenkins) {
            klovTest.log(Status.PASS, message);
        }

        logPass(message);

        extentReport.flush();
    }

    public static void stepPassed(String messageToFormat, CodeLanguage codeLanguage,  ExtentTest test) {

        test.log(Status.PASS, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));

        if (_isJenkins) {
            klovTest.log(Status.PASS, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));
        }

        logPass(messageToFormat);

        extentReport.flush();
    }

    public static void warning(String message) {
        
        checkReporters();
        currentTest.log(Status.WARNING, message);

        if (_isJenkins) {
            klovTest.log(Status.WARNING, message);
        }

        logInfo(message);
        /**
         * inspiredReport
         */
//        currentTangentTest.warning(message);
    }

    public static void warning(String messageToFormat, CodeLanguage codeLanguage) {

        checkReporters();
        currentTest.log(Status.WARNING, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));

        if (_isJenkins) {
            klovTest.log(Status.WARNING, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));
        }

        logInfo(messageToFormat);
        /**
         * inspiredReport
         */
//        currentTangentTest.warning(messageToFormat);
    }

    public static void warning(String message, ExtentTest test) {
        
        test.log(Status.WARNING, message);
      
        logInfo(message);
    }

    public static void warning(String messageToFormat, CodeLanguage codeLanguage, ExtentTest test) {

        test.log(Status.WARNING, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));

        logInfo(messageToFormat);
    }

    public static void information(String messageToFormat, CodeLanguage codeLanguage) {
        checkReporters();
        currentTest.log(Status.INFO, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));

        if (_isJenkins) {
            klovTest.log(Status.INFO, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));
        }

        logInfo(messageToFormat);
        /**
         * inspiredReport
         */
//        currentTangentTest.info(messageToFormat);
    }

    public static void information(String message) {

        checkReporters();
        currentTest.log(Status.INFO, message);

        if (_isJenkins) {
            klovTest.log(Status.WARNING, message);
        }

        logInfo(message);
        /**
         * inspiredReport
         */
//        currentTangentTest.info(message);
    }

    public static void information(String message, ExtentTest test) {

        test.log(Status.INFO, message);

        logInfo(message);
    }

    public static void information(String messageToFormat, CodeLanguage codeLanguage, ExtentTest test) {

        test.log(Status.INFO, MarkupHelper.createCodeBlock(messageToFormat, codeLanguage));

        logInfo(messageToFormat);
    }

    public static void stepPassedWithScreenShot(String message) {
        checkReporters();
        try {
            String image = takeScreenshot(message, false);

            currentTest.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(image).build());
            /**
             * tangentReport
             */

//                currentTangentTest.pass(message, image);

            if (_isJenkins) {
                klovTest.log(Status.PASS, "<p>" + message + "</p><img width='200' height='100' src='data:image/png;base64, " + convertPNGToBase64(image) + "' />");
                klovReport.flush();
            }
            logPass(message);
            extentReport.flush();
//            tangentReport.flush();
        } catch (IOException ex) {
            Logger.getLogger(Narrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void flushReport() {
        extentReport.flush();
    }
    
    public static void stepPassedWithScreenShot(String message,String image, ExtentTest test) {
       
        try {
            test.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(image).build());
            logPass(message);
            extentReport.flush();
        } catch (IOException ex) {
            Logger.getLogger(Narrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void blockedTest() {
        checkReporters();
        currentTest.skip("Test was blocked by previous test failure.");
        /**
         * inspiredReport
         */
//        currentTangentTest.skip("Test was blocked by previous test failure.");

        if (_isJenkins) {
            klovTest.skip("Test was blocked by previous test failure.");
            klovReport.flush();
        }

        extentReport.flush();
//        tangentReport.flush();
        logFailure("Test was blocked due to previous test failure");
    }

    public static ExtentTest createTest(String reportName)    {
        if(extentReport == null) checkOnlyReporters();
        return extentReport.createTest(reportName);
    }

    public static TestResult testFailed(String message) {
        try {
            checkReporters();
            String image = takeScreenshot(message, false);

            if (image.length() < 1) {
                currentTest.fail(message);
                /**
                 * inspiredReport
                 */
//                currentTangentTest.fail(message);
            } else {
                currentTest.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(image).build());
                
                /**
                 * inspiredReport
                 */
//                    currentTangentTest.fail(message, image);
            }

            if (_isJenkins) {
                klovTest.log(Status.FAIL, "<p>" + message + "</p><img width='200' height='100' src='data:image/png;base64, " + convertPNGToBase64(image) + "' />");
                klovReport.flush();

            }

            setError(message);

            logFailure(message);
        } catch (IOException ex) {
            if (currentTest != null) {
                currentTest.fail(message);
            }
//            if (currentTangentTest != null) {
//                /**
//                 * tangentReport
//                 */
////                currentTangentTest.fail(message);
//            }
        }
        if (extentReport != null) {
            extentReport.flush();
        }
//        if (tangentReport != null) {
//            tangentReport.flush();
//        }
        TestResult result = new TestResult(TestMarshall.getTestData(), Enums.ResultStatus.FAIL, message, getTotalExecutionTime());
        TestMarshall.addToTestResultsList(result);
        
        return result;
    }

    public static TestResult testFailed(String message, String image, ExtentTest test) {
        try {

            if (image.length() < 1) {
                test.fail(message);
               
            } else {
                test.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(image).build());
            }          
            setError(message);

            logFailure(message,test.getModel().getName());
        } catch (IOException ex) {
            if (currentTest != null) {
                currentTest.fail(message);
            }           
        }
        if (extentReport != null) {
            extentReport.flush();
        }
        TestResult result = new TestResult(TestMarshall.getTestData(), Enums.ResultStatus.FAIL, message, getTotalExecutionTime());
        TestMarshall.addToTestResultsList(result);
        return result;
    }

    private static String takeScreenshot(String message, boolean testStatus) {
        try {
            return SeleniumDriverUtility.takeScreenshot(message, testStatus, SeleniumDriverUtility.getDriver());

        } catch (Exception e) {

        }

        return "";
    }
    
    private static void addExtractedParameters() {
        ArrayList keys = new ArrayList();
        ArrayList values = new ArrayList();
        ArrayList status = new ArrayList();
        if (TestMarshall.getTestData().getExtractedParams() != null) {
            String logMessage = "Extracted Parameters:";

            String extractedParameters = "<span style='font-weight:bold;font-family: Georgia;'>" + logMessage + "</span></br><table>";

            for (String key : TestMarshall.getTestData().getExtractedParams().keySet()) {
                keys.add(key);
                for (String value : TestMarshall.getTestData().getExtractedParams().get(key)) {
                    status.add(TestMarshall.getTestData().getExtractedParams().get(key).get(1));
                    values.add(value);

                }
            }

            for (int i = 0; i < keys.size(); i++) {
                if (status.get(i).equals("PASS")) {
                    extractedParameters += "<tr style='background: #60A84D;'><td>" + keys.get(i) + "</td><td>" + values.get(i) + "</td></tr>";
                } else if (status.get(i).equals("FAIL")) {
                    extractedParameters += "<tr style='background: #FF4536;'><td>" + keys.get(i) + "</td><td>" + values.get(i) + "</td></tr>";
                } else if (status.get(i).equals("WARNING")) {
                    extractedParameters += "<tr style='background: #FF8E1A;'><td>" + keys.get(i) + "</td><td>" + values.get(i) + "</td></tr>";
                } else {
                    extractedParameters += "<tr><td>" + keys.get(i) + "</td><td>" + values.get(i) + "</td></tr>";
                }
            }

            extractedParameters += "</table>";
            currentTest.log(Status.INFO, extractedParameters);

            if (_isJenkins) {
                klovTest.log(Status.INFO, extractedParameters);
            }
        }
    }

    public static TestResult finalizeTest(String message) {
        try {
            checkReporters();
            String image = takeScreenshot(message, true);

            if (image.length() < 1) {
                currentTest.pass(message);
            } else {
                currentTest.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(image).build());
            }

//            if (_isJenkins) {
//                klovTest.log(Status.PASS, "<p>" + message + "</p><img width='200' height='100' src='data:image/png;base64, " + convertPNGToBase64(image) + "' />");
//                klovReport.flush();
//            }

            addExtractedParameters();
            extentReport.flush();
            logPass("Test Complete.");

        } catch (Exception e) {

        }

        return new TestResult(TestMarshall.getTestData(), Enums.ResultStatus.PASS, message, getTotalExecutionTime());
    }

    public static TestResult finalizeTest(String message, ExtentTest test) {
        try {
            
            String image = takeScreenshot(message, true);

            if (image.length() < 1) {
                test.pass(message);
             
            } else {
                test.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(image).build());
               
            }
        
            addExtractedParameters();

            extentReport.flush();
            logPass("Test Complete.");

        } catch (Exception e) {

        }

        TestResult result = new TestResult(TestMarshall.getTestData(), Enums.ResultStatus.PASS, message, getTotalExecutionTime());
        TestMarshall.addToTestResultsList(result);
        return result;
    }
    
    public static void addTest()    {
        try {
            
            checkReporters();
            currentTest = extentReport.createTest(reportName, description);

            extentReport.flush();

            if (_isJenkins) {
                klovTest = klovReport.createTest(reportName, description);
            }
//            currentTangentTest = tangentReport.createTest(reportName, description);
            //inspiredReport.flush();

            setStartTime();

        } catch (Exception e) {
            err.println("No report object exists!" + e.getMessage());
        }
    }

    public static void addTest(String name, String description) {
        try {
            
            checkReporters();
            currentTest = extentReport.createTest(name, description);

            extentReport.flush();

            if (_isJenkins) {
                klovTest = klovReport.createTest(name, description);
            }

            /**
             * tangentReport
             */
//            currentTangentTest = tangentReport.createTest(name, description);
//            tangentReport.flush();

            setStartTime();
            int totalTest = TestMarshall.getTestDataList().size();
            logInfo("Starting Test -- " + TestMarshall.getIteration() + " out of " + totalTest + " -- " + name);

        } catch (Exception e) {
            err.println("No report object exists!" + e.getMessage());
        }
    }

    public static void skipTestTestPackDisabled(String name, String description) {
        extentReport.flush();
//        tangentReport.flush();

        currentTest = extentReport.createTest(name, description);

        currentTest.skip("Test Skipped in test pack");

        if (_isJenkins) {
            klovTest = klovReport.createTest(name, description);

            klovTest.skip("Test Skipped in test pack");
        }

        extentReport.flush();

        /**
         * inspiredReport
         */
        currentTest = extentReport.createTest(name, description);
//        currentTest.skip("Test Skipped in test pack");
//        tangentReport.flush();

    }

    private static void initialiseKlovReporter(String projectName, String reportName) {
        try {
            klovReport = new ExtentReports();

            klovReporter = new ExtentKlovReporter();

            //Override the data below with setup-specific variables
            klovReporter.initMongoDbConnection("10.20.10.63", 27017);

            //projectName comes from app config
            klovReporter.setProjectName(projectName);

            klovReporter.setReportName(reportName);

            klovReporter.initKlovServerConnection("http://dvt-sqa47:82");

            klovReport.attachReporter(klovReporter);

            klovReport.setSystemInfo("OS", System.getProperty("os.name"));
            klovReport.setAnalysisStrategy(AnalysisStrategy.TEST);
            klovReport.flush();
        } catch (Exception ex) {
            err.println("ERROR - Failed to initialise Klov reporting, please check connection settings or ignore if disabled | " + ex.getMessage());
        }
    }

//    public void setCategory(TangentCategory category) {
//        tangentReport.setCategory(category);
//    }

//    public void assignDevices(TangentDevice... devices) {
//        currentTangentTest.assignDevice(devices);
//    }

//    public void assignTags(TangentTag... tags) {
//        currentTest.assignTags(tags);
//    }
    public static void setReportDirectory(String directory) {
        _reportDirectory = directory;
    }

    public static String getReportDirectory() {
        return _reportDirectory;
    }

    private static String getCurTime() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");

        return ft.format(date);
    }

    private static String getFileName(String filepath) {
        try {
            File f = new File(filepath);
            return f.getName().split("\\.")[0];
        } catch (Exception e) {
            return null;
        }
    }

    public static void setStartTime() {
        startTime = new DateTime();
    }

    public static long getTotalExecutionTime() {
        DateTime endTime = new DateTime();
        Duration testDuration = new Duration(startTime, endTime);
        return testDuration.getStandardSeconds();
    }

    public static void logError(String error) {

        try {
            writeToLogFile("- [EROR] " + error);
        } catch (IOException ex) {
            Logger.getLogger(Narrator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void logDebug(String debug) {
        try {
            writeToLogFile("- [DBUG] " + debug);
        } catch (IOException ex) {
            Logger.getLogger(Narrator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void logPass(String pass) {
        try {
            writeToLogFile("- [PASS] " + pass);
        } catch (IOException ex) {
            Logger.getLogger(Narrator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void logFailure(String failure) {
        try {
            writeToLogFile("- [FAIL] " + failure);
        } catch (IOException ex) {
            Logger.getLogger(Narrator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void logFailure(String failure, String testName) {
        try {
            writeToLogFile("- [FAIL] Test - "+testName+" : " + failure);
        } catch (IOException ex) {
            Logger.getLogger(Narrator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void logInfo(String info) {
        try {
            writeToLogFile("- [INFO] " + info);
        } catch (IOException ex) {
            Logger.getLogger(Narrator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void writeToLogFile(String logMessage) throws IOException {

        File file = new File(getNarratorLog());
        String formatStr = "%n%-24s %-20s %-60s %-25s";
        if (!file.exists()) {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(file, true));
            writer.println(String.format(formatStr, "", "-- NARRATOR LOG FILE --", "", ""));
            writer.close();
        }

        //Writes info to the text file        
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file, true));
            writer.println(String.format(formatStr, dateFormat.format(new Date()), logMessage, "", ""));
            writer.close();
        } catch (IOException e) {
            System.out.printf(e.getMessage());
        }
    }

    private static String convertPNGToBase64(String imageFilePath) {
        String base64ReturnString = "";

        try {
            System.out.println("[INFO] Converting error screenshot to Base64 format...");
            File image = new File(getAbsScreenshotPath());

            FileInputStream imageInputStream = new FileInputStream(image);

            byte imageByteArray[] = new byte[(int) image.length()];

            imageInputStream.read(imageByteArray);

            base64ReturnString = Base64.encodeBase64String(imageByteArray);

            System.out.println("[INFO] Converting completed, image ready for embedding.");
            imageInputStream.close();
        } catch (Exception ex) {
            System.out.println("[ERROR] Failed to convert image to Base64 format - " + ex.getMessage());
        }

        return base64ReturnString;
    }
}
