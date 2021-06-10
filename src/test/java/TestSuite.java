package test.java;

import main.java.tangenttesting.TestMarshall;
import main.java.tangenttesting.core.BaseClass;
import main.java.tangenttesting.entities.Enums;
//import org.junit.Test;
import org.testng.annotations.Test;

/**
 *
 * @author nditema
 */
public class TestSuite extends BaseClass {
    @Test
    public void Test() {
        String testPack = System.getProperty("user.dir")+"\\testpacks\\VumatelPack.xlsx";
        SelectedBrowser = Enums.BrowserType.CHROME;
        TestMarshall instance = new TestMarshall(testPack);
        instance.runTests();
    }
}
