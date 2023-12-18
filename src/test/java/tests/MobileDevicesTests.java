package tests;

import base.MobileDeviceDriver;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class MobileDevicesTests extends MobileDeviceDriver {

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
