package tests;

import helpers.XMLFileGenerator;
import org.testng.annotations.Test;

public class CreateXmlFiles {

    /**
     *  Read the README.md to understand accepted Parameter inputs for "create_xml_file()" method
     */

    protected XMLFileGenerator xmlFileGenerator = new XMLFileGenerator();

    @Test
    public void web_xml_file_for_smoke() {
        String type = "web";
        int threadCount = 12;
        String xmlFileName = "smoke_web_tests.xml";
        String suiteName = "smoke_execution";

        xmlFileGenerator.create_xml_file(type, threadCount, xmlFileName, suiteName);
    }

    @Test
    public void mobile_xml_file_for_smoke() {
        String type = "mobile";
        int threadCount = 12;
        String xmlFileName = "smoke_mobile_tests.xml";
        String suiteName = "smoke_execution";

        xmlFileGenerator.create_xml_file(type, threadCount, xmlFileName, suiteName);
    }

    @Test
    public void web_xml_file_for_regression() {
        String type = "web";
        int threadCount = 40;
        String xmlFileName = "regression_web_tests.xml";
        String suiteName = "regression_execution";

        xmlFileGenerator.create_xml_file(type, threadCount, xmlFileName, suiteName);
    }

    @Test
    public void mobile_xml_file_for_regression() {
        String type = "mobile";
        int threadCount = 40;
        String xmlFileName = "regression_mobile_tests.xml";
        String suiteName = "regression_execution";

        xmlFileGenerator.create_xml_file(type, threadCount, xmlFileName, suiteName);
    }

}
