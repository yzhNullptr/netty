package com.yzh.Netty.protocol.http.xml.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

/**
 * @author: yzh
 * @date: 2023-10-26 16:12:47
 * @Description: 回复消息解码器
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse>{
    public HttpXmlResponseDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    public HttpXmlResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DefaultFullHttpResponse defaultFullHttpResponse, List<Object> list) throws Exception {
        HttpXmlResponse httpXmlResponse1 = new HttpXmlResponse(defaultFullHttpResponse, decode0(channelHandlerContext, defaultFullHttpResponse.content()));
        list.add(httpXmlResponse1);

    }
}
