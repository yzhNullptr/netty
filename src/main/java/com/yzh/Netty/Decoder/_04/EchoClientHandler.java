package com.yzh.Netty.Decoder._04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author: yzh
 * @date: 2023-10-22 13:59:48
 * @Description: TODO 描述该类的功能
 */
public class EchoClientHandler extends ChannelHandlerAdapter {
    private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message;
        message=br.readLine();
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        int len=bytes.length;
        ByteBuf buf = Unpooled.copyInt(len);
        buf.writeBytes(bytes);
        System.out.println(Arrays.toString(buf.array()));
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
    }
}
