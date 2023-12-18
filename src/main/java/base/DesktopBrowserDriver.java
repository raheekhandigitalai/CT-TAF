package base;

import helpers.CommonDriverHelper;
import helpers.PropertiesReader;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DesktopBrowserDriver {

    protected ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<DesiredCapabilities> desiredCapabilities = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    protected CommonDriverHelper helper;

    protected RemoteWebDriver getDriver() {
        return driver.get();
    }

    protected WebDriverWait getWait() {
        return wait.get();
    }

    @BeforeMethod
    @Parameters({"browser.name"})
    public void setUp(@Optional ITestContext context, @Optional Method method, @Optional String browserName) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("experitest:testName", method.getName());
        caps.setCapability("experitest:accessKey", new PropertiesReader().getProperty("ct.accessKey"));

        desiredCapabilities.set(setWebDriver(browserName, caps));
        driver.set(new RemoteWebDriver(new URL(new PropertiesReader().getProperty("ct.cloudUrl")), caps));
        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
        helper = new CommonDriverHelper(getDriver());

        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        try {
            String suiteName = helper.getSuiteName(context);
            helper.addPropertyForReporting("executionType", suiteName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            helper.addPropertyForReporting("browserType", "web_" + browserName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"browser.name"})
    public void tearDown(@Optional ITestResult result, @Optional ITestContext context, @Optional String browserName) {

        try {

            if (result.getStatus() == ITestResult.SUCCESS) {
                helper.setReportStatus("Passed", "Test Passed");
            } else if (result.getStatus() == ITestResult.FAILURE) {
                helper.setReportStatus("Failed", "Test Failed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                helper.setReportStatus("Skipped", "Test Skipped");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //        System.out.println(getDriver().getCapabilities().getCapability("reportTestId")); // will fetch individual test id

        getDriver().quit();
        driver.remove();
        wait.remove();
    }

    protected DesiredCapabilities setWebDriver(String browserName, DesiredCapabilities caps) {
        caps.setCapability("browserName", browserName);
        return caps;
    }

}
