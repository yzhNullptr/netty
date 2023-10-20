package com.yzh._04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author: yzh
 * @date: 2023-10-18 11:33:22
 * @Description: TODO 描述该类的功能
 */
public class SendMessage {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds=new DatagramSocket();
        String message;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            message=br.readLine();
            if (message.equals("exit"))System.exit(1);
            byte[] bytes = message.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, new InetSocketAddress("10.77.36.255", 8000));
            ds.send(datagramPacket);
        }

    }
}
