package com.yzh.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author: yzh
 * @date: 2023-10-18 09:49:24
 * @Description: TODO 描述该类的功能
 */
public class AsyncTimeServerHandler implements  Runnable{
    private int port;
    CountDownLatch latch;
    public AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try{
            asynchronousServerSocketChannel=AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
            latch=new CountDownLatch(1);
            doAccept();
            try{
                latch.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
    }
    public void doAccept(){
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
