package com.yzh.Netty.Decoder._03;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: yzh
 * @date: 2023-10-21 21:07:16
 * @Description: TODO 描述该类的功能
 */
public class EchoServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive client : [ "+ msg +" ]");
    }
}
