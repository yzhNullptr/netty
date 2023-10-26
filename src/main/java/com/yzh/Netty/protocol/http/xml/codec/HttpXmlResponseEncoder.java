package com.yzh.Netty.protocol.http.xml.codec;

import io.netty.buffer.ByteBuf;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author: yzh
 * @date: 2023-10-26 16:00:59
 * @Description: 回复消息编码器
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse>{
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> list)  {
        ByteBuf body = null;
        try {
            body = encode0(ctx, msg.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FullHttpResponse response = msg.getResponse();
        if (response==null){
            response=new DefaultFullHttpResponse(HTTP_1_1,OK,body);
        }else{
            response =new DefaultFullHttpResponse(msg.getResponse().getProtocolVersion(),msg.getResponse().getStatus(),body);
        }
        response.headers().set(CONTENT_TYPE,"text/xml");
        setContentLength(response,body.readableBytes());
        list.add(response);
    }


}
