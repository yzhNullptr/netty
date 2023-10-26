package com.yzh.Netty.protocol.http.xml.client;

import com.yzh.Netty.protocol.http.xml.codec.HttpXmlRequest;
import com.yzh.Netty.protocol.http.xml.codec.HttpXmlResponse;
import com.yzh.Netty.protocol.http.xml.pojo.OrderFactory;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: yzh
 * @date: 2023-10-26 16:26:37
 * @Description: 客户端信息处理类
 */
public class HttpXmlClientHandle extends SimpleChannelInboundHandler<HttpXmlResponse> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功");
        HttpXmlRequest httpXmlRequest = new HttpXmlRequest(null, OrderFactory.create(123));
        ctx.writeAndFlush(httpXmlRequest);
    }


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpXmlResponse httpXmlResponse) throws Exception {
        System.out.println("The client receive response of http header is : "+httpXmlResponse.getResponse().headers().names());
        System.out.println("The client receive response of http body is : "+httpXmlResponse.getResult());
    }
}
