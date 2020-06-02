package autoframework.api.base;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpClient {
    // 编码格式。发送编码格式统一用UTF-8
    private final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒。
    private final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private final int SOCKET_TIMEOUT = 6000;

    private CloseableHttpClient httpClient;
    private String baseURL;

    public HttpClient() {
        // 创建httpClient对象
        httpClient = HttpClients.createDefault();
        setUpBaseURL();
    }

    public HttpClient(String username, String password) {
        // 创建httpClient对象
        httpClient = HttpClients.createDefault();
        setUpBaseURL();
        //todo: 授权的方法
    }

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public HttpClientResult doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public HttpClientResult doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     * @throws Exception
     */
    public HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(this.baseURL + url);
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        // 执行请求并获得响应结果
        return getHttpClientResult(httpClient, httpGet);
    }

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public HttpClientResult doPost(String url) throws Exception {
        return doPost(url, null, null);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public HttpClientResult doPost(String url, Map<String, String> params) throws Exception {
        return doPost(url, null, params);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     * @throws Exception
     */
    public HttpClientResult doPost(String url, Map<String, String> headers, Map<String, String> params) throws Exception {

        // 创建http对象
        HttpPost httpPost = new HttpPost(this.baseURL + url);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        // 设置请求头
        packageHeader(headers, httpPost);

        // 封装请求参数
        packageParam(params, httpPost);

        // 执行请求并获得响应结果
        return getHttpClientResult(httpClient, httpPost);
    }

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public HttpClientResult doPut(String url) throws Exception {
        return doPut(url, null);
    }

    /**
     * 发送put请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public HttpClientResult doPut(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(this.baseURL + url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);

        packageParam(params, httpPut);

        return getHttpClientResult(httpClient, httpPut);
    }

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public HttpClientResult doDelete(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(this.baseURL + url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpDelete.setConfig(requestConfig);

        return getHttpClientResult(httpClient, httpDelete);
    }

    /**
     * 发送delete请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public HttpClientResult doDelete(String url, Map<String, String> params) throws Exception {
        if (params == null) {
            params = new HashMap<String, String>();
        }

        params.put("_method", "delete");
        return doPost(url, params);
    }

    //******************************************************************************************************************
    //xxx.setContentType("application/json");//发送json数据需要设置contentTyp

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     * @throws Exception
     */
    public HttpClientResult doGetJSON(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(this.baseURL + url);
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);
        httpGet.setHeader("Content-type", "application/json");
        // 执行请求并获得响应结果
        return getHttpClientResult(httpClient, httpGet);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url       请求地址
     * @param headers   请求头集合
     * @param jsonParam 请求参数Json
     * @return
     * @throws Exception
     */
    public HttpClientResult doPostJSON(String url, Map<String, String> headers, String jsonParam) throws Exception {

        // 创建http对象
        HttpPost httpPost = new HttpPost(this.baseURL + url);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        // 设置请求头
        packageHeader(headers, httpPost);
        httpPost.setHeader("jws", "eyJhbGciOiJSUzUxMiJ9.eyJleHAiOjE2ODMzNDM0MDksImlzcyI6Imh0dHA6Ly9hbGliYWJhY2xvdWQuY29tIiwiaWF0IjoxNTg4NzM1NDE3LCJqdGkiOiIwN2QyNTJlMS00M2FiLTQ5MzQtOGVmOS0yMmFiODk5YjYyZjMifQ.J8Kmj7PSfcBsmAccvUVMDmDmLzUB904PZZaukZgn9Jzj2WL1yXf7rng1VL2rqXcL-LozaAASh_MSLi-FJ-EUHgzJvmpALXA6MK7IRS-vBGNlWD4UPHRWa7eJ7kY_OSS-nkeGHMb2Cf1qNNubrS8aUF9PUTVW8mPquitqSsLhysuL44kkqP_QbKJO6dtiKdjvcWz4po74y1dvOOomcAg-O0ASgJ9s2wA9Shfc-CDMQ6T1ApARUB8FJsYqSy7n6CGLUMFzE5vKarAQkSo8EmWQSUAeu8CNXIhXoWIQ9LgOfXb1AXo4Je2hHxFIHU3oVRyaN72TxgEmPs1Wfsk_FB4O6T-Clf6sYM-cVA4cvha-UWB0Cxhm0lRGIHliCSHZuaBReULE_xwwlQ5_G-7thO5T7-2SNYLwqgBAiNt5_0JAnb_fq1iiqqxWug9reUjcENzEofEqNgmd3EoAVGUp9m1OmZWaB6RYEJxo6-aakSlDt8p5WlQ1b_vqYAISx_v1SspKceEADGWufQfjFaQdH6Sen0_ZBy-8ZAbipIeOG_t-BNoAgxnXw9qs0K96sWz5IU0l1cP9Gue8Lw4EVKk73-fUe8oh6kdCgx0N_Kdnf9dx7u7A7VCay3vnxjTlHpzfBY3CbxYhmJi7sVOuxtEWBNuY0XSGjEdJgJwbwAxA1-P5_kQ");

        // 封装请求参数
        StringEntity requestEntity = new StringEntity(jsonParam, ENCODING);
        requestEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(requestEntity);

        // 执行请求并获得响应结果
        return getHttpClientResult(httpClient, httpPost);
    }

    /**
     * Description: 释放资源
     *
     * @param httpClient
     * @throws IOException
     */
    public void release(CloseableHttpClient httpClient) throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    /**
     * Description: 封装请求头
     *
     * @param params
     * @param httpMethod
     */
    private void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    private void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }

    /**
     * Description: 获得响应结果
     *
     * @param httpClient
     * @param httpMethod
     * @return
     * @throws Exception
     */
    private HttpClientResult getHttpClientResult(CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
        AtomicInteger status = new AtomicInteger();
        // Create a custom response handler
        ResponseHandler<String> responseHandler = response -> {
            status.set(response.getStatusLine().getStatusCode());
            if (status.get() >= 200 && status.get() < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity, ENCODING) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };

        String responseBody = httpClient.execute(httpMethod, responseHandler);

        return new HttpClientResult(status, responseBody);
    }

    private void setUpBaseURL() {
        Environment environment = new Environment();
        this.baseURL = environment.getEnvironmentNode().getBaseURL();
    }
}
