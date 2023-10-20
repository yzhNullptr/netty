package com.yzh.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: yzh
 * @date: 2023-10-18 10:17:15
 * @Description: TODO 描述该类的功能
 */
public class AsyncTimeClientHandler implements CompletionHandler<Void,AsyncTimeClientHandler> ,Runnable{
    private AsynchronousSocketChannel channel;

    private String host;
    private int port;
    private CountDownLatch latch;

    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try{
            channel=AsynchronousSocketChannel.open();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        System.out.println("hello");
        ByteBuffer flip = StandardCharsets.UTF_8.encode("QUERY TIME ORDER");
        flip.flip();
        channel.write(flip, flip, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()){
                    channel.write(flip,flip,this);
                }else{
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            buffer.flip();
                            try {
                                String body = StandardCharsets.UTF_8.decode(buffer).toString();
                                System.out.println("Now is : "+body);
                                latch.countDown();
                            } catch (UnsupportedCharsetException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try{
                                channel.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try{
                    channel.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        exc.printStackTrace();
        try{
            channel.close();
            latch.countDown();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch=new CountDownLatch(1);
        channel.connect(new InetSocketAddress(host,port),this,this);
        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        try {
            channel.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
