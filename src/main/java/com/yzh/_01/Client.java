package com.yzh._01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: yzh
 * @date: 2023-10-08 21:13:07
 * @Description: TODO 描述该类的功能
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost",6666);
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
        client.close();
        System.out.println("client disconnect");
    }
    private static void handler(InputStream is,OutputStream os)throws IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        System.out.println("[server] " + reader.readLine());
        for (;;) {
            System.out.print(">>> "); // 打印提示
            String s = scanner.nextLine(); // 读取一行输入
            writer.write(s);
            writer.newLine();
            writer.flush();
            String resp = reader.readLine();
            System.out.println("<<< " + resp);
            if (resp.equals("bye")) {
                break;
            }
        }
    }
}
