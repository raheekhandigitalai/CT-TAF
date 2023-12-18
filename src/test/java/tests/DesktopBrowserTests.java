package tests;

import base.DesktopBrowserDriver;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class DesktopBrowserTests extends DesktopBrowserDriver {

    @Test
    public void test_01() {
        assertTrue(true, "This test will pass");
    }

    @Test
    public void test_02() {
        fail("This test will fail");
    }

    @Test
    public void test_03() {
        throw new SkipException("This test will skip");
    }

}
