package helpers;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonDriverHelper {

    protected RemoteWebDriver driver;

    public CommonDriverHelper(RemoteWebDriver driver) {
        this.driver = driver;
    }

    /**
     *
     * @param message
     * @param status
     *
     * This method adds a custom step in the Continuous Testing Video Report that is generated.
     */
    public void addStepInReport(String message, String status) {
        driver.executeScript("seetest:client.report", message, status);
    }

    /**
     *
     * @param status
     * @param message
     *
     * This method marks the Test as a Passed/Failed/Skipped in the Continuous Testing Reporter.
     */
    public void setReportStatus(String status, String message) {
        driver.executeScript("seetest:client.setReportStatus", status, message);
    }


    /**
     *
     * @param status
     * @param message
     * @param stackTrace
     *
     * This method marks the Test as a Passed/Failed/Skipped in the Continuous Testing Reporter.
     * In case of a Test in "Failed" status, ability to pass in Stack Trace.
     *
     * Currently only supported for Desktop Web Browser Tests (Selenium).
     */
    public void setReportStatus(String status, String message, String stackTrace) {
        driver.executeScript("seetest:client.setReportStatus", status, message, stackTrace);
    }

    /**
     *
     * @param property
     * @param value
     *
     * This method adds custom properties to an Automated Test running on the Continuous Testing Platform.
     * This allows for granular filtering in the Continuous Testing Reporter.
     */
    public void addPropertyForReporting(String property, String value) {
        driver.executeScript("seetest:client.addTestProperty", property, value);
    }

    /**
     *
     * @param testName
     *
     * This method created a "grouping" of steps within the Continuous Testing Video Report for each Test.
     * This is beneficial if the Test has a large number of steps, and you would like to separate out the steps
     * into smaller groups.
     */
    public void startGroupingOfSteps(String testName) {
        driver.executeScript("seetest:client.startStepsGroup", testName);
    }

    /**
     * This method ends the "grouping" of steps. Only needs to be called if using "startGroupingOfSteps()".
     */
    public void endGroupingOfSteps() {
        driver.executeScript("seetest:client.stopStepsGroup()");
    }

    /**
     *
     * @param context
     * @return
     *
     * This method gets the Suite Name from .xml file it is running.
     */
    public String getSuiteName(ITestContext context) {
        return context.getCurrentXmlTest().getSuite().getName();
    }

    public String getCurrentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        String currentDateAndTime = now.format(formatter);
        return currentDateAndTime;
    }

}
