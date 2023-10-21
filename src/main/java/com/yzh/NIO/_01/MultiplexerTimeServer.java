package com.yzh.NIO._01;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: yzh
 * @date: 2023-10-17 22:01:04
 * @Description: TODO 描述该类的功能
 */
public class MultiplexerTimeServer implements Runnable{
    private Selector selector;

    private ServerSocketChannel servChannel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try{
            selector=Selector.open();
            servChannel=ServerSocketChannel.open();
            servChannel.configureBlocking(false);
            servChannel.socket().bind(new InetSocketAddress(port),1024);
            servChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop(){
        this.stop=true;
    }

    @Override
    public void run() {
        while (!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key=null;
                while (iterator.hasNext()){
                    key=iterator.next();
                    iterator.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e){
                        if (key!=null){
                            key.cancel();
                            if (key.channel()!=null)key.channel().close();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会自动去注册并关闭，所以不需要重复的释放资源
        if (selector!=null){
            try{
                selector.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    private void handleInput(SelectionKey key)throws IOException{
        if (key.isValid()){
            if (key.isAcceptable()){
                //Accept the new connection
                ServerSocketChannel ssc=(ServerSocketChannel)key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //Add the new connection to the selector
                sc.register(selector,SelectionKey.OP_READ);
            }

            if (key.isReadable()){
                //Read the data
                SocketChannel sc=(SocketChannel)key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(buffer);
                if (readBytes>0){
                    buffer.flip();
                    byte[] bytes=new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body=new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("The EchoServer receive order : "+body);
                    String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)? LocalDateTime.now().toString():"BAD ORDER";
                    doWrite(sc,currentTime);
                }else if (readBytes<0){
                    key.cancel();
                    sc.close();
                }
            }
        }
    }
    private void doWrite(SocketChannel channel,String response)throws IOException{
        if (response!=null&&response.trim().length()>0){
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(response);
            channel.write(buffer);
        }
    }
}


