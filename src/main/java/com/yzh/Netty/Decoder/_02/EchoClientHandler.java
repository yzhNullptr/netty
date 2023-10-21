package com.yzh.Netty.Decoder._02;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * @author: yzh
 * @date: 2023-10-21 20:51:07
 * @Description: TODO 描述该类的功能
 */
public class EchoClientHandler extends ChannelHandlerAdapter {
    private int Count=0;
    public static final String ECHO_REQ="Hi,Yangzhihong.Welcome to Netty.$_";
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<10;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes(StandardCharsets.UTF_8)));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("This is "+ ++Count+" times receive server : "+"["+msg+"]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
