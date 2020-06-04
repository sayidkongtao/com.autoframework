package autoframework.common.webutils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pro.truongsinh.appium_flutter.finder.FlutterElement;

public class WebUtils {

    private Logger logger = LogManager.getLogger(this.getClass());
    private WebDriverWait wait;
    private AppiumDriver<MobileElement> driver;
    private final long timeOutInSeconds = 15;
    private final long sleepInMillis = 500;

    public WebUtils(AppiumDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, timeOutInSeconds, sleepInMillis);
    }

    /**
     *
     * @param webElement
     * @return
     */
    public boolean isElementVisible(WebElement webElement) {
        try {
            return !ExpectedConditions.invisibilityOf(webElement).apply(driver);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     *
     * @param by
     * @return
     */
    public boolean isElementVisible(By by) {
        try {
            return !ExpectedConditions.invisibilityOfElementLocated(by).apply(driver);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     *
     * @param flutterElement
     * @return
     */
    public boolean isElementVisible(FlutterElement flutterElement) {
        //todo: just a workaround way, need to update this method
        // after issue fixed: https://github.com/truongsinh/appium-flutter-driver/issues/84
        try {
            waitForElementVisible(flutterElement, 0.2);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * @param flutterElement
     * @param second         eg: 0.1 means 0.1 second
     * @return true or false
     */
    public void waitForElementVisible(FlutterElement flutterElement, double second) {
        //todo: just a workaround way, need to update this method
        // after issue fixed: https://github.com/truongsinh/appium-flutter-driver/issues/84
        if (second <= 0) {
            second = 0.1;
        }
        driver.executeScript("flutter:waitFor", flutterElement, second);
    }

    /**
     *
     * @param flutterElement
     */
    public void waitForElementVisible(FlutterElement flutterElement) {
        waitForElementVisible(flutterElement, timeOutInSeconds);
    }

    /**
     *
     * @param by
     */
    public WebElement waitForElementVisible(By by) throws Exception {
        logger.info("Wait for element: " + by);
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));

        if (webElement == null) {
            throw new Exception("Failed to find the element" + by);
        }

        return webElement;
    }

    /**
     *
     * @param by
     */
    public WebElement waitForElementVisible(By by, long timeOutInSeconds, long sleepInMillis) throws Exception {
        logger.info("Wait for element: " + by);
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeOutInSeconds, sleepInMillis);
        WebElement webElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));

        if (webElement == null) {
            throw new Exception("Failed to find the element" + by);
        }

        return webElement;
    }

}
