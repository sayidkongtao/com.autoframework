package autoframework.demo;

import autoframework.demo.page.FirstPage;
import autoframework.pojo.User;
import autoframework.testcaes.basetest.BaseTest;
import autoframework.utils.Utils;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.MalformedURLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
    public void simpleTestOne() throws IOException {
        Utils.addAttachment("登录用户", Utils.getAbsolutePath("users/username.json"));
        User user = Utils.deserialize("users/username.json", User.class);
        loginWith(user);
        goToShopCart();
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
        loginWith(user);
        goToShopCart();
        checkShopCart();
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
        step1();
        step2();
    }

    @Step("点击完成跳转至flutter页面")
    public void step1(){
        FirstPage firstPage = new FirstPage(driver);
        firstPage.goToFlutterPage();
    }

    @Step("打开FirstPage页面")
    public void step2(){
        FirstPage firstPage = new FirstPage(driver);
        firstPage.goToOpenFirstPage();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Flutter 测试集成-失败截图")
    @Test(description = "[Case number]: <Case Summary:> Flutter测试集成-失败截图")
    @Description("Case Steps: " +
            "1. xxxxxxxxxxx " +
            "2. xxxxxxxxxxx " +
            "3. xxxxxxxxxxx " +
            "4. xxxxxxxxxxx " +
            "Expected Result: xxxxxxxxxxxxxxxxxxx")
    public void simpleTestFour() throws MalformedURLException {

        step1();
        checkShopCart();
        step2();
    }


    @Step("登录用户")
    public void loginWith(User user) {
        logger.info("登录用户： " + Utils.toJSON(user));
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("进入购物车页面.")
    public void goToShopCart() {
        logger.info("进入购物车页面");
    }

    @Step("检查购物车页面.")
    public void checkShopCart() {
        assertThat("购物车商品A，显示数量不对", 1, is(2));
    }
}
