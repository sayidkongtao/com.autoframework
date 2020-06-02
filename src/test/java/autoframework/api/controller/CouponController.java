package autoframework.api.controller;

import autoframework.api.base.HttpClient;
import autoframework.api.base.HttpClientResult;
import autoframework.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CouponController {

    private static Logger logger = LogManager.getLogger(CouponController.class);

    /**
     * 创建优惠券实例【同步】
     *
     * @param httpClient
     * @param jsonBody
     * @throws Exception
     */
    public static HttpClientResult createCoupon(HttpClient httpClient, String jsonBody) throws Exception {
        String path = "market/v1/api/coupon";

        logger.info("To createCoupon");
        HttpClientResult httpClientResult = httpClient.doPostJSON(path, null, jsonBody);
        logger.info(Utils.toJSON(httpClientResult));
        return httpClientResult;
    }
}
