package com.yzh._04;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: yzh
 * @date: 2023-10-18 10:59:32
 * @Description: TODO 描述该类的功能
 */
public class Handler implements Runnable{
    private Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        InputStream is=null;
        OutputStream os=null;
            byte[] bytes=new byte[4096];
        int len;
        try{
            is=this.socket.getInputStream();
            os=this.socket.getOutputStream();
            len=is.read(bytes);
            System.out.println(new String(bytes,0,len,StandardCharsets.UTF_8));
            Path path = Paths.get("C:/Users/27815/Desktop/index.html");
            byte[] bytes2=new byte[10240];
            InputStream inputStream = Files.newInputStream(Paths.get("C:\\Users\\27815\\Desktop\\index.html"));
            int read = inputStream.read(bytes2);
             os.write("HTTP/1.1 200 OK\nServer: myServer\n Content-Type: text/html\n\n".getBytes(StandardCharsets.UTF_8));
            os.write(bytes2,0,read);
            // os.write("HTTP/1.1 200 OK\nServer: myServer\n Content-Type: text/html\n\n<html><head><body>_01</body></head></html>".getBytes(StandardCharsets.UTF_8));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
