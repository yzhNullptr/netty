package com.yzh.Netty.CodeC.Test;

import com.yzh.Netty.CodeC.Pojo.User;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: yzh
 * @date: 2023-10-22 16:40:36
 * @Description: TODO 描述该类的功能
 */
public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功");
        ctx.writeAndFlush(new User("123","123"));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.toString());
        ctx.writeAndFlush(msg);
    }
}
