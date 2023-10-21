package com.yzh.Netty.Decoder._02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * @author: yzh
 * @date: 2023-10-21 20:40:13
 * @Description: TODO 描述该类的功能
 */
public class EchoServerHandler extends ChannelHandlerAdapter {
    private int Count=0;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body= (String) msg;
        System.out.println("This is "+ ++Count+" times receive client : [" +body +"]");
        body+="$_";
        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(echo);
    }
}
