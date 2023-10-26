package com.yzh.Netty.protocol.http.xml.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;

/**
 * @author: yzh
 * @date: 2023-10-24 16:45:20
 * @Description: HttpXmlRequestDecoder 请求消息解码器
 */
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {
    public HttpXmlRequestDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    public HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz,isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> list) throws Exception {
            if(!request.getDecoderResult().isSuccess()){
                sendError(ctx, BAD_REQUEST);
            }
        HttpXmlRequest re = new HttpXmlRequest(request, decode0(ctx, request.content()));
            list.add(re);
    }
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status, Unpooled.copiedBuffer("Failure: "+status.toString()+"\r\n", StandardCharsets.UTF_8));
        response.headers().set(CONTENT_TYPE,"text/plain;charset=utf-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
