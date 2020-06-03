package autoframework.testcaes.basetest;

import autoframework.utils.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class BaseTest {

    public static AppiumDriver<MobileElement> driver;
    private static Logger logger = LogManager.getLogger(AppiumDriver.class);

    public static void getAndroidDriver() throws MalformedURLException {
        logger.info("********************初始化Android Driver*******************");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", System.getProperty("APPIUM_DEVICE_NAME", "AKC7N18907000186"));
        capabilities.setCapability("platformVersion", System.getProperty("APPIUM_DEVICE_VERSION", "9"));
        capabilities.setCapability("automationName", "Flutter");
        capabilities.setCapability("platformName", System.getProperty("APPIUM_PLATFORM", "Android"));
        capabilities.setCapability("appPackage", "com.taobao.idlefish.flutterboostexample");
        capabilities.setCapability("appActivity", ".MainActivity");
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("newCommandTimeout", 7200);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);

        //ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.put("androidProcess", "com.tencent.mm:tools");
        //chromeOptions.setExperimentalOption("w3c", false);
        // capabilities.merge(chromeOptions);
        capabilities.setCapability("chromedriverChromeMappingFile", Utils.getAbsolutePath("chromdrivermap/map.json"));
        capabilities.setCapability("chromedriverExecutableDir", Utils.getAbsolutePath("chromedriver"));


        /**
         * need to add x right to file
         */
        File file = new File(Utils.getAbsolutePath("chromedriver"));
        List<File> files = Arrays.asList(file.listFiles());

        files.forEach(ele -> {
            try {
                ele.getAbsolutePath();
                Runtime.getRuntime().exec(String.format("chmod +x %s", ele.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            driver = new AndroidDriver(new URL(System.getProperty("APPIUM_URL", "http://127.0.0.1:4723/wd/hub")), capabilities);
            logger.info("Android Driver初始化成功");
        } catch (Exception err) {
            logger.info("Android Driver初始化失败，错误详细信息参考如下");
            logger.error(err);
            throw err;
        }
    }
}
