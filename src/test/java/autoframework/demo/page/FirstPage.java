package autoframework.demo.page;

import autoframework.common.basepage.BasePage;
import autoframework.demo.pageobject.FirstPageObject;
import io.appium.java_client.AppiumDriver;

public class FirstPage extends BasePage {

    private FirstPageObject pageObject;

    public FirstPage(AppiumDriver driver) {
        super(driver);
        pageObject = new FirstPageObject(driver, webUtils);
    }

    public void goToFlutterPage() {
        // 切换成本地的代码
        driver.context("NATIVE_APP");
        logger.info("通过点击 'open flutter button' 进入到 flutter 页面");
        webUtils.click(pageObject.openFlutter, true);
    }

    public void goToOpenFirstPage() {
        // 切换成flutter端的代码
        driver.context("FLUTTER");
        logger.info("通过点击 'open flutter button' 进入到 flutter 页面");
        webUtils.click(pageObject.openFirstPage());
    }
}
