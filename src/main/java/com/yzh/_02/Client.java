package com.yzh._02;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: yzh
 * @date: 2023-10-16 08:05:22
 * @Description: TODO 描述该类的功能
 */
public class Client {
    void start() throws Exception{
        SocketChannel localhost = SocketChannel.open(new InetSocketAddress("localhost", 9999));
        Scanner scanner = new Scanner(System.in);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        localhost.read(buffer);
        buffer.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buffer));
        while(scanner.hasNextLine()){
            String request = scanner.nextLine();
            if (request!=null&&request.length()>0){
                localhost.write(StandardCharsets.UTF_8.encode(request));
            }
        }
    }

    public static void main(String[] args) {
        try{
            new Client().start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
