package autoframework.common.basepage;

import autoframework.common.basepageobject.BasePageObject;
import autoframework.common.webutils.WebUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class BasePage {

    private BasePageObject pageObject;

    protected Logger logger = LogManager.getLogger(this.getClass());
    protected WebUtils webUtils;
    protected AppiumDriver<MobileElement> driver;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.webUtils = new WebUtils(driver);
        pageObject = new BasePageObject(driver, webUtils);
    }



    /**
     * @return the given element if it is visible and has non-zero size, otherwise null.
     */
    private <T> WebElement elementIfVisible(WebElement element) {
        boolean status = false;
        try {
            status = element.isDisplayed();
        } catch (Exception err) {
            //logger.info("wait for element " + element);
        }
        return status ? element : null;
    }
}
