package com.yzh._01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author: yzh
 * @date: 2023-10-08 20:12:39
 * @Description: TODO 服务端 练习Socket
 */
public class Server {
    public static void main(String[] args)   {
        try(ServerSocket serverSocket = new ServerSocket(6666)){
            while(true){
                System.out.println("?");
                Socket client = serverSocket.accept();
                System.out.println("connected from " + client.getRemoteSocketAddress());
                new Handler(client).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class Handler extends Thread{
    Socket client;

    public Handler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try(InputStream inputStream = client.getInputStream(); OutputStream outputStream = client.getOutputStream()){
            handler(inputStream,outputStream);
        }catch (IOException e){
            try{
                client.close();
            }catch (Exception exception){
                System.out.println("socket 关闭异常");
            }
            System.out.println("client disconnect");
        }
    }

    protected void handler(InputStream is, OutputStream os)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,StandardCharsets.UTF_8));
        bw.write("_01\n");
        bw.flush();
        while(true){
            String s = br.readLine();
            if (s.equals("bye")){
                bw.write("bye\n");
                bw.flush();
                break;
            }
            System.out.println("client:" +s);
            bw.write("ok: "+s +"\n");
            bw.flush();
        }
    }
}