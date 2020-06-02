package autoframework.testcae;

import autoframework.pojo.User;
import autoframework.utils.Utils;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

/**
 * @author Sayid
 * 测试Demo
 */
@Epic("Allure 测试例子")
@Feature("支持Testng")
public class TestDemoModule {
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

}
