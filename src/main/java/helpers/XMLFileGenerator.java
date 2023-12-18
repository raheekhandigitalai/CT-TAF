package helpers;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

public class XMLFileGenerator {

    public void create_xml_file(String type, int threadCount, String xmlFileName, String suiteName) {
        XmlSuite suite = new XmlSuite();
        suite.setName(suiteName);
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        suite.setThreadCount(threadCount);

        if (type.equalsIgnoreCase("web")) {

            String webTestPackageAndClass = "tests.DesktopBrowserTests";

            for (int i = 1; i <= threadCount/4; i++) {
                XmlTest test = new XmlTest(suite);
                test.setName("Chrome_Test_" + i);

                XmlClass xmlClass = new XmlClass(webTestPackageAndClass);
                test.setParallel(XmlSuite.ParallelMode.METHODS);
                test.setThreadCount(3);
                test.setXmlClasses(Collections.singletonList(xmlClass));

                test.addParameter("browser.name", "chrome");
            }

            for (int i = 1; i <= threadCount/4; i++) {
                XmlTest test = new XmlTest(suite);
                test.setName("Firefox_Test_" + i);

                XmlClass xmlClass = new XmlClass(webTestPackageAndClass);
                test.setParallel(XmlSuite.ParallelMode.METHODS);
                test.setThreadCount(3);
                test.setXmlClasses(Collections.singletonList(xmlClass));

                test.addParameter("browser.name", "firefox");
            }

            for (int i = 1; i <= threadCount/4; i++) {
                XmlTest test = new XmlTest(suite);
                test.setName("Edge_Test_" + i);

                XmlClass xmlClass = new XmlClass(webTestPackageAndClass);
                test.setParallel(XmlSuite.ParallelMode.METHODS);
                test.setThreadCount(3);
                test.setXmlClasses(Collections.singletonList(xmlClass));

                test.addParameter("browser.name", "MicrosoftEdge");
            }

            for (int i = 1; i <= threadCount/4; i++) {
                XmlTest test = new XmlTest(suite);
                test.setName("Safari_Test_" + i);

                XmlClass xmlClass = new XmlClass(webTestPackageAndClass);
                test.setParallel(XmlSuite.ParallelMode.METHODS);
                test.setThreadCount(3);
                test.setXmlClasses(Collections.singletonList(xmlClass));

                test.addParameter("browser.name", "safari");
            }

        } else if (type.equalsIgnoreCase("mobile")) {

            String mobileTestPackageAndClass = "tests.MobileDevicesTests";

            for (int i = 1; i <= threadCount/2; i++) {
                XmlTest test = new XmlTest(suite);
                test.setName("Android_Test_" + i);

                XmlClass xmlClass = new XmlClass(mobileTestPackageAndClass);
                test.setParallel(XmlSuite.ParallelMode.METHODS);
                test.setThreadCount(3);
                test.setXmlClasses(Collections.singletonList(xmlClass));
                test.addParameter("mobile.os", "android");
            }

            for (int i = 1; i <= threadCount/2; i++) {
                XmlTest test = new XmlTest(suite);
                test.setName("iOS_Test_" + i);

                XmlClass xmlClass = new XmlClass(mobileTestPackageAndClass);
                test.setParallel(XmlSuite.ParallelMode.METHODS);
                test.setThreadCount(3);
                test.setXmlClasses(Collections.singletonList(xmlClass));
                test.addParameter("mobile.os", "ios");
            }
        }

        save_xml_suite(suite, xmlFileName);
    }

    protected void save_xml_suite(XmlSuite suite, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(suite.toXml());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
