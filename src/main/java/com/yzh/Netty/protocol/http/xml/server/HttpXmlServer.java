package com.yzh.Netty.protocol.http.xml.server;

import com.yzh.Netty.protocol.http.xml.codec.HttpXmlRequestDecoder;
import com.yzh.Netty.protocol.http.xml.codec.HttpXmlRequestEncoder;
import com.yzh.Netty.protocol.http.xml.codec.HttpXmlResponseEncoder;
import com.yzh.Netty.protocol.http.xml.pojo.Order;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * @author: yzh
 * @date: 2023-10-26 16:37:12
 * @Description: 服务端主类
 */
public class HttpXmlServer {
    public void run(final int port)throws Exception{
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try{
            ChannelFuture f = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast("http-decoder", new HttpRequestDecoder())
                                    .addLast("http-aggregator", new HttpObjectAggregator(65536))
                                    .addLast("xml-decoder", new HttpXmlRequestDecoder(Order.class, true))
                                    .addLast("http-encoder", new HttpResponseEncoder())
                                    .addLast("xml-encoder", new HttpXmlResponseEncoder())
                                    .addLast("xmlServerHandler", new HttpXmlServerHandler());
                        }
                    }).bind(new InetSocketAddress(port));
            System.out.println("HTTP 订购服务器启动,网址是 ：http://localhost:"+port);
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port=Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();;
            }
        }
        new HttpXmlServer().run(port);
    }
}
