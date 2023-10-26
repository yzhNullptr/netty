package com.yzh.Netty.protocol.http.xml.client;

import com.yzh.Netty.protocol.http.xml.codec.HttpXmlRequestDecoder;
import com.yzh.Netty.protocol.http.xml.codec.HttpXmlRequestEncoder;
import com.yzh.Netty.protocol.http.xml.codec.HttpXmlResponseDecoder;
import com.yzh.Netty.protocol.http.xml.pojo.Order;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.InetSocketAddress;

/**
 * @author: yzh
 * @date: 2023-10-26 16:19:48
 * @Description: 客户端
 */
public class HttpXmlClient {
    public void connect(int port)throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            ChannelFuture f = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast("http-decoder", new HttpResponseDecoder())
                                    .addLast("http-aggregator", new HttpObjectAggregator(65535))
                                    .addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true))
                                    .addLast("http-encoder", new HttpRequestEncoder())
                                    .addLast("xml-encoder", new HttpXmlRequestEncoder())
                                    .addLast("xmlClientHandler", new HttpXmlClientHandle());
                        }
                    }).connect(new InetSocketAddress("localhost",port)).sync();
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
        new HttpXmlClient().connect(port);
    }
}
