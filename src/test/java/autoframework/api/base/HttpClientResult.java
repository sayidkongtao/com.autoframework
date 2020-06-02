package autoframework.api.base;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpClientResult implements Serializable {
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(AtomicInteger statusCode, String responseBody) {
        this.code = statusCode.get();
        this.content = responseBody;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
