package base;

import helpers.CommonDriverHelper;
import helpers.PropertiesReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class MobileDeviceDriver {

    protected ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<DesiredCapabilities> desiredCapabilities = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    protected RemoteWebDriver getDriver() {
        return driver.get();
    }

    protected WebDriverWait getWait() {
        return wait.get();
    }

    protected CommonDriverHelper helper;

    @BeforeMethod
    @Parameters({"mobile.os"})
    public void setUp(@Optional ITestContext context, @Optional Method method, String mobileOS) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("testName", method.getName());
        caps.setCapability("accessKey", new PropertiesReader().getProperty("ct.accessKey"));

        if (mobileOS.equalsIgnoreCase("ios")) {
                desiredCapabilities.set(setiOSDriver(caps));
                driver.set(new IOSDriver(new URL(new PropertiesReader().getProperty("ct.cloudUrl")), caps));
        } else if (mobileOS.equalsIgnoreCase("android")) {
                desiredCapabilities.set(setAndroidDriver(caps));
                driver.set(new AndroidDriver(new URL(new PropertiesReader().getProperty("ct.cloudUrl")), caps));
        }

        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
        helper = new CommonDriverHelper(getDriver());

        try {
            String suiteName = helper.getSuiteName(context);
            helper.addPropertyForReporting("executionType", suiteName);
        } catch (Exception e) {
            // Nothing
        }

        try {
            if (mobileOS.equalsIgnoreCase("ios")) {
                helper.addPropertyForReporting("browserType", "mobile_ios");
            } else if (mobileOS.equalsIgnoreCase("android")) {
                helper.addPropertyForReporting("browserType", "mobile_android");
            }
        } catch (Exception e) {
            // Nothing
        }
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"mobile.os"})
    public void tearDown(@Optional ITestResult result, @Optional ITestContext context, @Optional String mobileOS) {

        try {
            if (result.getStatus() == ITestResult.SUCCESS) {
                helper.setReportStatus("Passed", "Test Passed");
            } else if (result.getStatus() == ITestResult.FAILURE) {
                helper.setReportStatus("Failed", "Test Failed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                helper.setReportStatus("Skipped", "Test Skipped");
            }

        } catch (Exception e) {
            // Nothing
        }

//        System.out.println(getDriver().getCapabilities().getCapability("reportTestId")); // will fetch individual test id

        getDriver().quit();
        driver.remove();
        wait.remove();
    }

    protected DesiredCapabilities setAndroidDriver(DesiredCapabilities caps) {
//        caps.setCapability("deviceQuery", "@os='android' and @category='PHONE' and contains(@region, 'US')");
        caps.setCapability("deviceQuery", "@os='android' and @category='PHONE'");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        caps.setBrowserName(MobileBrowserType.CHROME);
        caps.setCapability("appiumVersion", "1.22.3");
        caps.setCapability("automationName", "UIAutomator2");
        return caps;
    }

    protected DesiredCapabilities setiOSDriver(DesiredCapabilities caps) {
//        caps.setCapability("deviceQuery", "@os='ios' and @category='PHONE' and contains(@region, 'US')");
        caps.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS);
        caps.setBrowserName(MobileBrowserType.SAFARI);
        caps.setCapability("appiumVersion", "2.1.3");
        caps.setCapability("automationName", "XCUITest");
        return caps;
    }

}
