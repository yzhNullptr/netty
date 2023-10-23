package com.yzh.Netty.CodeC.MessagePack.Test;

import com.yzh.Netty.CodeC.MessagePack.Decoder.MessagePackDeCoder;
import com.yzh.Netty.CodeC.MessagePack.EnCoder.MessagePackEnCoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author: yzh
 * @date: 2023-10-22 15:48:28
 * @Description: TODO 描述该类的功能
 */
public class Client {



    public void connect(int port,String host)throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootS = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                                    .addLast("MessagePack Decoder",new MessagePackDeCoder())
                                    .addLast(new LengthFieldPrepender(2))
                                    .addLast("MessagePack EnCoder",new MessagePackEnCoder())
                                    .addLast("自定义处理器",new ClientHandler(100));
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
        new Client().connect(port,"localhost");
    }
}
