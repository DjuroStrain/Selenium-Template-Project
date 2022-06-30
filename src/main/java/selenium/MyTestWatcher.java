package selenium;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.driver.WebDriverConfig;
import utils2.ScreenshotClass;
import utils2.WebDriverProvider;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Optional;


public class MyTestWatcher implements TestWatcher {

    private final static Logger log = LoggerFactory.getLogger(MyTestWatcher.class);


    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("Uslo proslo");
        log.info("Uslo proslo log");
    }

    /*
    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
        System.out.println("Uslo aborted");

        screenshotClass.takeScreenShotWhenTestFails();
        if (getDriver() != null) {
            screenshotClass.quit();
        }
    }

    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {
        System.out.println("Uslo disabled");

        screenshotClass.takeScreenShotWhenTestFails();
        if (getDriver() != null) {
            screenshotClass.quit();
        }
    }*/

}
