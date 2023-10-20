package com.yzh._03;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author: yzh
 * @date: 2023-10-16 08:58:00
 * @Description: TODO 访问百度
 */
public class Baidu {
    public static void main(String[] args) {
        try (Socket socket = new Socket("www.baidu.com", 80)) {
            socket.getOutputStream().write(StandardCharsets.UTF_8.encode("GET / HTTP/1.1\r\nHOST: www.baidu.com:80\r\n\r\n").array());
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = socket.getInputStream().read(bytes)) != -1) {
                System.out.print(new String(bytes, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

