package com.yzh.Netty.Decoder._04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.IOException;


/**
 * @author: yzh
 * @date: 2023-10-22 13:31:42
 * @Description: 添加了LengthFiledBasedFrameDecoder
 *               自定义长度解决TCP粘包问腿
 *               参数的意义
 *                       1-maxFrameLength:发送数据帧的最大长度
 *                       2-lengthFieldOffset:定义长度域位于发送字节数组中的下标
 *                       3-lengthFiledLength:用于描述长度域的长度
 *                       4-lengthAdjustment:用于长度调整
 *                       5-initialBytesToStrip:接收到的发送数据包去除前面initialBytesToStrip位
 *                       6-failFast-true:读取到长度超过maxFrameLength的时候就抛出一个T哦哦LongFrameException。-false只有真正的读取完长度域的值表示的字节后，才会抛出TooLongFraneException，默认情况下设置为true，建议不要修改，否则可能会造成内存溢出
 *                       7-ByteOrder-数据存储采用大端还是小端模式
 *                  发送数据包长度=长度域的值+lengthFieldOffset+lengthFieldLength+lengthAdjustment
 *
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
                    channel.pipeline()
                            .addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4))
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
