package com.yzh._02;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: yzh
 * @date: 2023-10-16 08:05:31
 * @Description: TODO 描述该类的功能
 */
public class Server {
    public static void main(String[] args) {
        try{
            Server server = new Server();
            server.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void start() throws Exception{
        Selector selector = Selector.open(); ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9999));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                System.out.println("????????");
                if (selector.select()==0)continue;
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable())acceptHandler(serverSocketChannel,selector);
                    if(key.isReadable())readHandler(key,selector);
                }
            }

    }

    void acceptHandler(ServerSocketChannel serverSocketChannel,Selector selector) throws Exception{
        SocketChannel channel = serverSocketChannel.accept();
        channel.configureBlocking(false);
        channel.register(selector,SelectionKey.OP_READ);
        channel.write(StandardCharsets.UTF_8.encode("_01 world"));
    }

    void readHandler(SelectionKey key ,Selector selector) throws  Exception {
       SocketChannel socketChannel=(SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder sb = new StringBuilder();
        while (socketChannel.read(buffer)>0){
            buffer.flip();
            sb.append(StandardCharsets.UTF_8.decode(buffer));
        }
        socketChannel.register(selector,SelectionKey.OP_READ);
        if (sb.length()>0) System.out.println(socketChannel.getRemoteAddress()+":"+sb.toString());
    }
}
