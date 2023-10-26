package com.yzh.Netty.protocol.http.xml.codec;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author: yzh
 * @date: 2023-10-24 15:23:52
 * @Description: 封装的xml request类
 */
public class HttpXmlRequest {
    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    public final FullHttpRequest getRequest() {
        return request;
    }

    public final void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }
}
