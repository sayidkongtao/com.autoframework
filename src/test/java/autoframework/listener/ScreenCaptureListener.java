package autoframework.listener;

import autoframework.testcaes.basetest.BaseTest;
import autoframework.utils.Utils;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ScreenCaptureListener extends TestListenerAdapter {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        logger.info("【" + tr.getName() + " Start】");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        logger.info("【" + tr.getName() + " Failure】");
        logger.error(tr.getThrowable());
        takeScreenShot(tr);
    }

    @Attachment(value = "失败截图如下：", type = "image/png")
    public byte[] takeScreenShot(ITestResult tr) {
        BaseTest bt = (BaseTest) tr.getInstance();
        AppiumDriver driver = bt.getDriver();
        String currentContext = driver.getContext();
        try {
            driver.context("NATIVE_APP");
            byte[] screenshotAs = driver.getScreenshotAs(OutputType.BYTES);
            return screenshotAs;
        } catch (Exception ignore){
            logger.error("Failed to capture the screen since: " + ignore);
            return "截图失败".getBytes();
        } finally {
            driver.context(currentContext);
        }
    }


/*    public void takeScreenShot(ITestResult tr) {
        String instanceName = tr.getInstanceName();
        String suitName = tr.getTestContext().getSuite().getName();
        String partName = (suitName + "_" + tr.getTestContext().getName() + "_" + instanceName + "_" + tr.getName()).replaceAll("\\.", "_");
        String folder = "target/surefire-reports/custom_report";
        String filename = folder + "/img/" + partName + ".png";
    }*/

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        takeScreenShot(tr);
        logger.info("【" + tr.getName() + " Skipped】");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        logger.info("【" + tr.getName() + " Success】");
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
    }
}

