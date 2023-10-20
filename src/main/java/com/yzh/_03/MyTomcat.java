package com.yzh._03;

import com.yzh._02.Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author: yzh
 * @date: 2023-10-16 09:31:43
 * @Description: TODO 描述该类的功能
 */
public class MyTomcat {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true){
            Socket var = serverSocket.accept();
            if ((var!=null)){
                new Thread(()->{
                    try {
                        byte[] bytes=new byte[1024];
                        InputStream inputStream = var.getInputStream();
                        int len=0;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((len=inputStream.read(bytes))!=-1){
                            stringBuilder.append(new String(bytes,0,len));
                        }
                        System.out.println(stringBuilder.toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }
}
