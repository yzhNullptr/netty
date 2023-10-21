package com.yzh.Netty.Decoder._02;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;

/**
 * @author: yzh
 * @date: 2023-10-21 20:44:02
 * @Description: TODO 描述该类的功能
 */
public class EchoClient {
    public void connect(int port,String host)throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootS = new Bootstrap();
            bootS.group(group);
            bootS.channel(NioSocketChannel.class);
            bootS.option(ChannelOption.TCP_NODELAY, true);
            bootS.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes(StandardCharsets.UTF_8));
                    channel.pipeline()
                            .addLast(new DelimiterBasedFrameDecoder(1024,delimiter))
                            .addLast(new StringDecoder())
                            .addLast(new EchoClientHandler());
                }
            });
            //发起异步连接操作
            ChannelFuture f = bootS.connect(host, port).sync();

            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
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
        new EchoClient().connect(port,"localhost");
    }
}
