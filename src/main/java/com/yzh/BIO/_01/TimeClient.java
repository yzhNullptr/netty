package com.yzh.BIO._01;

import java.io.*;
import java.net.Socket;

/**
 * @author: yzh
 * @date: 2023-10-17 20:57:07
 * @Description: TODO 描述该类的功能
 */
public class TimeClient {
    public static void main(String[] args) {
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port=Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();;
            }
        }
        Socket socket=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try{
            socket=new Socket("localhost",port);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
            out.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed.");
            String resp=in.readLine();
            System.out.println("Now is : "+resp);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
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
            if (socket!=null){
                try{
                    socket.close();
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
                socket=null;
            }
        }
    }
}
