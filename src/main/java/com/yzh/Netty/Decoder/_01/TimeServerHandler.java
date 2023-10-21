package com.yzh.Netty.Decoder._01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author: yzh
 * @date: 2023-10-19 22:32:33
 * @Description: TODO 描述该类的功能
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    private int count=0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf= (ByteBuf) msg;
//        byte[] bytes=new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
        String body = (String) msg;
        System.out.println("the time server receive order : "+body+"the count is : "+ ++count);
        String currentTime=("QUERY TIME ORDER"+System.getProperty("line.separator")).equalsIgnoreCase(body) ? LocalDateTime.now().toString():"BAD ORDER";
        currentTime=currentTime+System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}
