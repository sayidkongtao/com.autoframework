package autoframework.demo.pageobject;

import autoframework.common.basepageobject.BasePageObject;
import autoframework.common.webutils.WebUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.CacheLookup;
import pro.truongsinh.appium_flutter.finder.FlutterElement;

public class FirstPageObject extends BasePageObject {
    public FirstPageObject(RemoteWebDriver remoteWebDriver, WebUtils webUtils) {
        super(remoteWebDriver, webUtils);
    }

    //For Native Element location
    @CacheLookup
    @AndroidFindBy(id = "com.taobao.idlefish.flutterboostexample:id/open_flutter")
    public MobileElement openFlutter;

    //For Flutter Element location
    public FlutterElement openFirstPage() {
        return flutterFinder.byValueKey("openFirstPage");
    }
}
