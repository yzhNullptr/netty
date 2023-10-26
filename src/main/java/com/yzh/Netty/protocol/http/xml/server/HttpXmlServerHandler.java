package com.yzh.Netty.protocol.http.xml.server;

import com.yzh.Netty.protocol.http.xml.codec.HttpXmlRequest;
import com.yzh.Netty.protocol.http.xml.codec.HttpXmlResponse;
import com.yzh.Netty.protocol.http.xml.pojo.Address;
import com.yzh.Netty.protocol.http.xml.pojo.Order;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.eclipse.core.internal.jobs.OrderedLock;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;


/**
 * @author: yzh
 * @date: 2023-10-26 16:45:04
 * @Description: 服务端处理类
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpXmlRequest httpXmlRequest){
        HttpRequest request = httpXmlRequest.getRequest();
        Order order = (Order)httpXmlRequest.getBody();
        System.out.println("http server receive request : "+order);
        doBusiness(order);
        ChannelFuture f = channelHandlerContext.writeAndFlush(new HttpXmlResponse(null, order));
        if (!isKeepAlive(request)){
            f.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    channelHandlerContext.close();
                }
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()){
            sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
        cause.printStackTrace();
    }

    private void doBusiness(Order order){
        order.getCustomer().setFirstName("狄");
        order.getCustomer().setLastName("仁杰");
        List<String> midNames=new ArrayList<>();
        midNames.add("李元芳");
        order.getCustomer().setMiddleNames(midNames);
        Address address = order.getOrderTo();
        address.setCity("洛阳");
        address.setCountry("大唐");
        address.setProvince("河南");
        address.setPostCode("123456");
        order.setOrderTo(address);
        order.setShipTo(address);
    }
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("失败 ： " + status.toString(), StandardCharsets.UTF_8));
        response.headers().set(CONTENT_TYPE,"text/plain;charset=utf-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
