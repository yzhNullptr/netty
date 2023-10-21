package com.yzh.Netty.Decoder._01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * @author: yzh
 * @date: 2023-10-19 22:58:12
 * @Description: TODO 描述该类的功能
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    public static final Logger logger=Logger
            .getLogger(TimeClientHandler.class.getName());
    private final byte[] bytes;
    private  int count=0;

    public TimeClientHandler() {
         bytes =( "QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes(StandardCharsets.UTF_8);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      logger.warning("Unexpected exception from downstream : "+cause.getMessage()+"and the order is : "+count);
      ctx.close();
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf= (ByteBuf) msg;
//        byte[] bytes=new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
//        String body = new String(bytes, StandardCharsets.UTF_8);
        String body= (String) msg;
        System.out.println("Now is : "+body+"; the count is : "+ ++count);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message=null;
        for(int i=0;i<100;i++){
            message=Unpooled.buffer(bytes.length);
            message.writeBytes(bytes);
            ctx.writeAndFlush(message);
        }
    }
}
