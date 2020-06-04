package autoframework.common.basepageobject;

import autoframework.common.webutils.WebUtils;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import pro.truongsinh.appium_flutter.FlutterFinder;

public class BasePageObject {

    protected SearchContext searchContext;
    protected WebUtils webUtils;
    protected FlutterFinder flutterFinder;

    public BasePageObject(RemoteWebDriver remoteWebDriver, WebUtils webUtils) {
        this.searchContext = remoteWebDriver;
        this.webUtils = webUtils;
        flutterFinder = new FlutterFinder(remoteWebDriver);
        PageFactory.initElements(new AppiumFieldDecorator(searchContext), this);
    }

    public BasePageObject(RemoteWebDriver remoteWebDriver, SearchContext searchContext, WebUtils webUtils) {
        this.searchContext = searchContext;
        this.webUtils = webUtils;
        flutterFinder = new FlutterFinder(remoteWebDriver);
        PageFactory.initElements(new AppiumFieldDecorator(searchContext), this);
    }

}
