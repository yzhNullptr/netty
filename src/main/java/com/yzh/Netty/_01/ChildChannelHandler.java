package com.yzh.Netty._01;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author: yzh
 * @date: 2023-10-19 22:30:58
 * @Description: TODO 描述该类的功能
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new TimeServerHandler());
    }
}
