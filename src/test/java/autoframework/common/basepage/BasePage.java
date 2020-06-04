package autoframework.common.basepage;

import autoframework.common.basepageobject.BasePageObject;
import autoframework.common.webutils.WebUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
