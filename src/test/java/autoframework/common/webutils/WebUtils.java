package autoframework.common.webutils;

import autoframework.utils.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pro.truongsinh.appium_flutter.finder.FlutterElement;
import java.time.Duration;
import java.util.Set;

public class WebUtils {

    private final long timeOutInSeconds = 15;
    private final long sleepInMillis = 500;
    private Logger logger = LogManager.getLogger(this.getClass());
    private AppiumDriver<MobileElement> driver;

    public WebUtils(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     *
     * @param flutterElement
     */
    public void scrollIntoView(FlutterElement flutterElement) {
        logger.info("scrollIntoView: " + flutterElement);
        waitForElementVisible(flutterElement);
        driver.executeScript("flutter:scrollIntoView", flutterElement);
    }

    /**
     *
     * @param durationTime
     * @param scaleStartWidth
     * @param scaleStartHeight
     * @param scaleEndWidth
     * @param scaleEndHeight
     */
    public void swipePage(int durationTime, double scaleStartWidth, double scaleStartHeight, double scaleEndWidth, double scaleEndHeight) {
        String currentContext = driver.getContext();

        try {
            changeContextToNativeApp();
            int width = driver.manage().window().getSize().width;
            int height = driver.manage().window().getSize().height;
            Duration duration = Duration.ofMillis(durationTime);
            new TouchAction<>(driver).press(PointOption.point((int) (width * scaleStartWidth), (int) (height * scaleStartHeight)))
                    .waitAction(WaitOptions.waitOptions(duration))
                    .moveTo(PointOption.point((int) (width * scaleEndWidth), (int) (height * scaleEndHeight))).release().perform();
            logger.info("swipe page");
        } finally {
            driver.context(currentContext);
        }

    }

    /**
     *
     */
    public void reLaunchApp() {
        int count = 5;

        do {
            try {
                logger.info("Relaunch app and will try: " + count + " times if failed");
                driver.closeApp();
                Utils.sleepBySecond(3);
                driver.resetApp();
                break;
            } catch (Exception err) {
                if (err.getMessage().contains("Connection was refused to port")) {
                    logger.info("Encounter port error will try again after 20s");
                    Utils.sleepBySecond(5);
                    count = count - 1;
                } else {
                    throw err;
                }
            }
        } while (count > 0);
    }

    /**
     *
     * @throws Exception
     */
    public void changeContextToWebView(String webViewContent) throws Exception {
        logger.info("Try to switch to webview");
        int count = 5;
        boolean isSwitch = false;
        do {
            Utils.sleepBySecond(2);
            Set<String> contextSet = driver.getContextHandles();

            if (contextSet.stream().anyMatch(webViewContent::equalsIgnoreCase)) {
                driver.context(webViewContent);
                logger.info("Success to switch to webview");
                isSwitch = true;
                break;
            }

            count = count - 1;

        } while (count > 0);

        if (!isSwitch) {
            throw new Exception("Only one content: " + driver.getContext());
        }
    }

    /**
     *
     */
    public void changeContextToNativeApp(){
        logger.info("Try to switch to NATIVE_APP");
        driver.context("NATIVE_APP");
        logger.info("Success to switch to content NATIVE_APP");
    }

    /**
     *
     */
    public void changeContextToFlutter(){
        logger.info("Try to switch to Flutter");
        driver.context("FLUTTER");
        logger.info("Success to switch to content Flutter");
    }

    /**
     *
     * @param flutterElement
     */
    public void click(FlutterElement flutterElement) {
        logger.info("Click the flutterElement: " + flutterElement);
        //Todo: Have to make sure element presence at current page since the flutter-appium issue:
        //https://github.com/truongsinh/appium-flutter-driver/issues/59
        waitForElementVisible(flutterElement);
        flutterElement.click();
    }

    /**
     *
     * @param webElement
     */
    public void click(WebElement webElement) {
        click(webElement, false);
    }

    /**
     *
     * @param webElement
     * @param needToWaitVisible
     */
    public void click(WebElement webElement, boolean needToWaitVisible) {

        if (needToWaitVisible) {
            click(webElement, timeOutInSeconds, sleepInMillis);
        } else {
            logger.info("Click the webElement: " + webElement);
            webElement.click();
        }
    }

    /**
     *
     * @param webElement
     * @param timeOutInSeconds
     * @param sleepInMillis
     */
    public void click(WebElement webElement, long timeOutInSeconds, long sleepInMillis) {
        logger.info("Click the webElement: " + webElement);
        waitForElementVisible(webElement, timeOutInSeconds, sleepInMillis);
        webElement.click();
    }

    /**
     *
     * @param flutterElement
     * @param text
     */
    public void inputText(FlutterElement flutterElement, String text) {
        logger.info("Input the text: " + text + " into the flutterElement: " + flutterElement);
        waitForElementVisible(flutterElement);
        flutterElement.sendKeys(text);
    }

    /**
     *
     * @param webElement
     */
    public void inputText(WebElement webElement, String text) {
        inputText(webElement, text, false);
    }

    /**
     *
     * @param webElement
     * @param needToWaitVisible
     * @return
     */
    public void inputText(WebElement webElement, String text, boolean needToWaitVisible) {

        if (needToWaitVisible) {
            inputText(webElement, text, timeOutInSeconds, sleepInMillis);
        } else {
            logger.info("Input the text of the webElement: " + webElement);
            webElement.sendKeys(text);
        }
    }

    /**
     *
     * @param webElement
     * @param timeOutInSeconds
     * @param sleepInMillis
     * @return
     */
    public void inputText(WebElement webElement, String text, long timeOutInSeconds, long sleepInMillis) {
        logger.info("Input the text of the webElement: " + webElement);
        waitForElementVisible(webElement, timeOutInSeconds, sleepInMillis);
        webElement.sendKeys(text);
    }

    /**
     *
     * @param webElement
     */
    public String getText(WebElement webElement) {
        return getText(webElement, false);
    }

    /**
     *
     * @param webElement
     * @param needToWaitVisible
     * @return
     */
    public String getText(WebElement webElement, boolean needToWaitVisible) {

        if (needToWaitVisible) {
            return getText(webElement, timeOutInSeconds, sleepInMillis);
        } else {
            logger.info("Get the text of the webElement: " + webElement);
            return webElement.getText();
        }
    }

    /**
     *
     * @param webElement
     * @param timeOutInSeconds
     * @param sleepInMillis
     * @return
     */
    public String getText(WebElement webElement, long timeOutInSeconds, long sleepInMillis) {
        logger.info("Get the text of the webElement: " + webElement);
        waitForElementVisible(webElement, timeOutInSeconds, sleepInMillis);
        return webElement.getText();
    }

    /**
     *
     * @param flutterElement
     */
    public void clearText(FlutterElement flutterElement) {
        logger.info("Clear the text of the flutterElement: " + flutterElement);
        waitForElementVisible(flutterElement);
        flutterElement.clear();
    }

    /**
     *
     * @param webElement
     */
    public void clearText(WebElement webElement) {
        clearText(webElement, false);
    }

    /**
     *
     * @param webElement
     * @param needToWaitVisible
     * @return
     */
    public void clearText(WebElement webElement, boolean needToWaitVisible) {

        if (needToWaitVisible) {
            clearText(webElement, timeOutInSeconds, sleepInMillis);
        } else {
            logger.info("Clear the text of the webElement: " + webElement);
            webElement.clear();
        }
    }

    /**
     *
     * @param webElement
     * @param timeOutInSeconds
     * @param sleepInMillis
     * @return
     */
    public void clearText(WebElement webElement, long timeOutInSeconds, long sleepInMillis) {
        logger.info("Clear the text of the webElement: " + webElement);
        waitForElementVisible(webElement, timeOutInSeconds, sleepInMillis);
        webElement.clear();
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
     *
     * @param webElement
     * @return
     */
    public void waitForElementVisible(WebElement webElement) {
        logger.info("Wait for element: " + webElement);
        waitForElementVisible(webElement, timeOutInSeconds, sleepInMillis);
    }

    /**
     *
     * @param webElement
     * @param timeOutInSeconds
     * @param sleepInMillis
     * @return
     */
    public void waitForElementVisible(WebElement webElement, long timeOutInSeconds, long sleepInMillis) {
        logger.info("Wait for element: " + webElement);
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeOutInSeconds, sleepInMillis);
        webDriverWait.until(visibilityOf(webElement));
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
    public WebElement waitForElementVisible(By by) {
        return waitForElementVisible(by, timeOutInSeconds, sleepInMillis);
    }

    /**
     *
     * @param by
     * @param timeOutInSeconds
     * @param sleepInMillis
     * @return
     * @throws Exception
     */
    public WebElement waitForElementVisible(By by, long timeOutInSeconds, long sleepInMillis) {
        logger.info("Wait for element: " + by);
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeOutInSeconds, sleepInMillis);
        WebElement webElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));

        return webElement;
    }

    /**
     *
     * @param webElement
     */
    public void waitForElementDisappear(WebElement webElement) {
        waitForElementDisappear(webElement, timeOutInSeconds, sleepInMillis, 1);
    }

    /**
     *
     * @param webElement
     * @param timeOutInSeconds
     * @param sleepInMillis
     * @param timeWaitForVisibleFirst
     */
    public void waitForElementDisappear(WebElement webElement, long timeOutInSeconds, long sleepInMillis, long timeWaitForVisibleFirst) {
        logger.info("Wait for element disappear" + webElement);

        WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeOutInSeconds, sleepInMillis);

        try {
            waitForElementVisible(webElement, timeWaitForVisibleFirst, 1);
        } catch (Exception ignore) {

        }
        webDriverWait.until((new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver driver) {
                try {
                    return !(webElement.isDisplayed());

                } catch (NoSuchElementException e) {
                    // Returns true because the element is not present in DOM. The
                    // try block checks if the element is present but is invisible.
                    return true;
                } catch (StaleElementReferenceException ignored) {
                    // We can assume a stale element isn't displayed.
                    return true;
                }
            }
        }));
    }

    /**
     *
     * @param by
     */
    public void waitForElementDisappear(By by) {
        waitForElementDisappear(by, timeOutInSeconds, sleepInMillis, 1);
    }

    /**
     *
     * @param by
     * @param timeOutInSeconds
     * @param sleepInMillis
     */
    public void waitForElementDisappear(By by, long timeOutInSeconds, long sleepInMillis, long timeWaitForVisibleFirst) {
        logger.info("Wait for element disappear " + by);
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeOutInSeconds, sleepInMillis);

        try {
            waitForElementVisible(by, timeWaitForVisibleFirst, 1);
        } catch (Exception ignore) {
            //no code here
        }

        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     *
     * @param flutterElement
     */
    public void waitForElementDisappear(FlutterElement flutterElement) {
        waitForElementDisappear(flutterElement, timeOutInSeconds, 0.5);
    }

    /**
     *
     * @param flutterElement
     * @param second
     */
    public void waitForElementDisappear(FlutterElement flutterElement, double second, double timeWaitForVisibleFirst) {
        //todo: just a workaround way, need to update this method
        // after issue fixed: https://github.com/truongsinh/appium-flutter-driver/issues/84
        if (second <= 0) {
            second = 0.1;
        }

        try {
            waitForElementVisible(flutterElement, timeWaitForVisibleFirst);
        } catch (Exception ignore) {

        }

        driver.executeScript("flutter:waitForAbsent", flutterElement, second);
    }

    /**
     * An expectation for checking that an element, known to be present on the DOM of a page, is
     * visible. Visibility means that the element is not only displayed but also has a height and
     * width that is greater than 0.
     *
     * @param element the WebElement
     * @return the (same) WebElement once it is visible
     */
    public org.openqa.selenium.support.ui.ExpectedCondition<WebElement> visibilityOf(final WebElement element) {
        return new org.openqa.selenium.support.ui.ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return elementIfVisible(element);
            }

            @Override
            public String toString() {
                return "visibility of " + element;
            }
        };
    }

    /**
     * @return the given element if it is visible and has non-zero size, otherwise null.
     */
    private WebElement elementIfVisible(WebElement element) {
        boolean status = false;
        try {
            status = element.isDisplayed();
        } catch (Exception err) {
            //logger.info("wait for element " + element);
        }
        return status ? element : null;
    }
}
