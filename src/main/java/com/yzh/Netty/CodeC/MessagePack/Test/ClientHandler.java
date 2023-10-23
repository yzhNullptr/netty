package com.yzh.Netty.CodeC.Test;

import com.yzh.Netty.CodeC.Pojo.User;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: yzh
 * @date: 2023-10-22 16:40:30
 * @Description: TODO 描述该类的功能
 */
public class ClientHandler extends ChannelHandlerAdapter {
    private final int sendNumber;
    public ClientHandler(int sendNumber) {
        this.sendNumber=sendNumber;
    }

    private User[] User(){
        User[] users = new User[sendNumber];
        for(int i=0;i<sendNumber;i++){
            users[i]=new User("A"+i,"B"+i);
        }
        return users;
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功");
        User[] users = User();
        for(User user:users){
            ctx.writeAndFlush(user);
        }
//        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive the message : "+msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
