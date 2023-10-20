package com.yzh.NIO._01;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * @author: yzh
 * @date: 2023-10-17 21:51:43
 * @Description: TODO NIO创建的TimeServer源码分析
 */
public class TimeServer {
    public static void main(String[] args) throws Exception{
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port=Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();;
            }
        }
    }
}
