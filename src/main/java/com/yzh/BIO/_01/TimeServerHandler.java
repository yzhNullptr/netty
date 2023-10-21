package com.yzh.BIO._01;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * @author: yzh
 * @date: 2023-10-17 20:42:22
 * @Description: TODO 以Socket为参数构造TimeServerHandler对象
 */
public class TimeServerHandler implements Runnable{
    Socket socket;
    public TimeServerHandler(Socket socket) {
        this.socket=socket;
    }

    @Override
    public void run() {
        BufferedReader in=null;
        PrintWriter out=null;
        try{
            in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out=new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime=null;
            String body=null;
            while(true){
                body=in.readLine();
                if (body==null)break;
                System.out.println("The EchoServer receive order : "+body);
                currentTime="QUERY TIME ORDER".equalsIgnoreCase(body) ? LocalDateTime.now().toString():"BAD ORDER";
                out.println(currentTime);
            }
        }catch (Exception e){
            if (in!=null){
                try{
                    in.close();
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
            }
            if (out!=null){
                out.close();;
                out=null;
            }
            if (this.socket!=null){
                try{
                    this.socket.close();
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
                this.socket=null;
            }
        }
    }
}
