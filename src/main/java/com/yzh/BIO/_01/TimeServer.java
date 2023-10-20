package com.yzh.BIO._01;

import com.yzh.BIO._02.TimeServerHandlerExecutePool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: yzh
 * @date: 2023-10-17 20:35:11
 * @Description: TODO 同步阻塞式I/0创建的TimeServer
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port=Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();;
            }
        }
        ServerSocket serverSocket=null;

        try{
            serverSocket=new ServerSocket(port);
            System.out.println("The TimeServer is start in port : "+port);
            Socket socket=null;
            while(true){
                socket=serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        }finally {
            if (serverSocket!=null){
                System.out.println("The TimeServer close");
                serverSocket.close();
                serverSocket=null;
            }
        }

    }
}
