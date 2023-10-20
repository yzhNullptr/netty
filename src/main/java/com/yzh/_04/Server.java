package com.yzh._04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: yzh
 * @date: 2023-10-18 10:59:21
 * @Description: TODO 描述该类的功能
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new Handler(socket)).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
