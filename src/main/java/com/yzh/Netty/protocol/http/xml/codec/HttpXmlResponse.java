package com.yzh.Netty.protocol.http.xml.codec;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author: yzh
 * @date: 2023-10-26 15:59:34
 * @Description: 封装的回复消息类
 */
public class HttpXmlResponse {
    private FullHttpResponse response;
    private Object result;

    public HttpXmlResponse(FullHttpResponse response, Object result) {
        this.response = response;
        this.result = result;
    }

    public FullHttpResponse getResponse() {
        return response;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
