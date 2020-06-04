package autoframework.common.basepageobject;

import autoframework.common.webutils.WebUtils;
import org.openqa.selenium.SearchContext;

public class BasePageObject {

    protected SearchContext searchContext;
    protected WebUtils webUtils;

    public BasePageObject(SearchContext searchContext, WebUtils webUtils) {
        this.searchContext = searchContext;
        this.webUtils = webUtils;
    }
}
