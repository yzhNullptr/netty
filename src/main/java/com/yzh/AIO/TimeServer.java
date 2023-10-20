package com.yzh.AIO;

/**
 * @author: yzh
 * @date: 2023-10-18 09:48:33
 * @Description: TODO 描述该类的功能
 */
public class TimeServer {
    public static void main(String[] args) {
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port=Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();;
            }
        }
        AsyncTimeServerHandler timeServer=new AsyncTimeServerHandler(port);
        new Thread(timeServer,"AIO-AsyncTimeServerHandler-001").start();

    }
}
