package com.yzh.Netty.Decoder._03;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: yzh
 * @date: 2023-10-21 21:06:36
 * @Description: 使用FixedLengthFrameDecoder 固定长度解码器 它能够按照指定的长度对消息进行自动解码，开发者不需要考虑TCP的粘包/拆包问题
 * 测试：
 *      通过控制台直接连接服务端 telnet 命令
 *      telnet address port
 *      然后 ’Ctrl+]‘ 输入set localecho 然后 enter退出 可以开启输入回显
 */
public class EchoServer {
    public void bind(int port)throws IOException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes(StandardCharsets.UTF_8));
                    channel.pipeline()
                            .addLast(new FixedLengthFrameDecoder(20))
                            .addLast(new StringDecoder())
                            .addLast(new EchoServerHandler());
                }
            });
            //同步等待绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //优雅退出释放线程池资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception{
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port=Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();;
            }
        }
        new EchoServer().bind(port);
    }
}
