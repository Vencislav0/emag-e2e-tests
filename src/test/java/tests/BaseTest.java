package tests;
import framework.utils.LoggerUtil;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.*;
import static framework.utils.Utils.*;


@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class BaseTest {
    static {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.browserCapabilities = options;
    }

    @BeforeMethod
    public void setUp(Method method){
        var testName = method.getName();
        MDC.put("current.test.name", testName);

        updateLogFile(testName);

        LoggerUtil.info("==== STARTING TEST: {} ====", testName);
        open("https://www.emag.bg/");

    }

    @AfterMethod
    public void tearDown(ITestResult result){
        String testName = result.getMethod().getMethodName();
        attachLogFile(testName);

        if(result.getStatus() == ITestResult.FAILURE){
            takeScreenshot();
            attachLogFile(testName);
        }
        LoggerUtil.info("==== FINISHED TEST: {} ====", testName);
        MDC.clear();
        com.codeborne.selenide.Selenide.closeWebDriver();

    }
}
