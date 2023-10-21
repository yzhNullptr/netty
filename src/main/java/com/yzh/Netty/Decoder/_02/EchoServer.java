package com.yzh.Netty.Decoder._02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: yzh
 * @date: 2023-10-21 15:51:01
 * @Description: 使用了DelimiterBasedFrameDecoder 分隔符解码器进行解码
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
                            .addLast(new DelimiterBasedFrameDecoder(1024, delimiter))
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
