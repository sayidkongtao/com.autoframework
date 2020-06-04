package autoframework.demo;

import autoframework.common.webutils.WebUtils;
import autoframework.pojo.User;
import autoframework.testcaes.basetest.BaseTest;
import autoframework.utils.Utils;
import io.appium.java_client.MobileElement;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import pro.truongsinh.appium_flutter.FlutterFinder;
import pro.truongsinh.appium_flutter.finder.FlutterElement;
import java.net.MalformedURLException;
import java.util.HashMap;

import static io.qameta.allure.Allure.step;

/**
 * @author Sayid
 * 测试Demo
 */
@Epic("Allure 测试例子")
@Feature("支持Testng")
public class TestDemoModule  extends BaseTest {
    private Logger logger = LogManager.getLogger(TestDemoModule.class);

    @Severity(SeverityLevel.CRITICAL)
    @Story("确保用户正常登录")
    @Test(description = "[Case number]: <Case Summary:> 确保用户登录")
    @Description("Case Steps: " +
            "1. xxxxxxxxxxx " +
            "2. xxxxxxxxxxx " +
            "3. xxxxxxxxxxx " +
            "4. xxxxxxxxxxx " +
            "Expected Result: xxxxxxxxxxxxxxxxxxx")
    public void simpleTestOne() {
        User user = Utils.deserialize("users/username.json", User.class);
        step("step 1: xxxxxxxxxxxxxxxxxxxx");

        logger.info("登录用户： " + Utils.toJSON(user));
        step("step 2: xxxxxxxxxxxxxxxxxxxxx");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("确保用户正常登出")
    @Test(description = "[Case number]: <Case Summary:> 确保用户退出")
    @Description("Case Steps: " +
            "1. xxxxxxxxxxx " +
            "2. xxxxxxxxxxx " +
            "3. xxxxxxxxxxx " +
            "4. xxxxxxxxxxx " +
            "Expected Result: xxxxxxxxxxxxxxxxxxx")
    public void simpleTestTwo() {
        User user = Utils.deserialize("users/username.json", User.class);
        step("step 1: xxxxxxxxxxxxxxxxxxxx");

        int a = 1/0;
        step("step 2");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Flutter 测试集成")
    @Test(description = "[Case number]: <Case Summary:> Flutter测试集成")
    @Description("Case Steps: " +
            "1. xxxxxxxxxxx " +
            "2. xxxxxxxxxxx " +
            "3. xxxxxxxxxxx " +
            "4. xxxxxxxxxxx " +
            "Expected Result: xxxxxxxxxxxxxxxxxxx")
    public void simpleTestThree() throws MalformedURLException {

        initAndroidDriver();

        // 切换成本地的代码
        driver.context("NATIVE_APP");

        MobileElement openNativeWebElement = driver.findElementById("com.taobao.idlefish.flutterboostexample:id/open_flutter");

        openNativeWebElement.click();
        logger.info("点击完成跳转至flutter页面完成");

        // 切换成flutter端的代码
        driver.context("FLUTTER");

        FlutterFinder find = new FlutterFinder(driver);
        FlutterElement buttonFinder = find.byValueKey("openFirstPage");
        buttonFinder.click();

        driver.executeScript("flutter:scroll", find.byType("ListView"), new HashMap<String, Object>() {{
            put("item", find.byType("TextField"));
            put("dx", 50);
            put("dy", 100);
            put("durationMilliseconds", 200);
            put("frequency", 30);
        }});

        logger.info("openFirstPage页面完成");

        WebUtils webUtils = new WebUtils(driver);
        webUtils.isElementVisible(openNativeWebElement);

        driver.quit();
    }

}
