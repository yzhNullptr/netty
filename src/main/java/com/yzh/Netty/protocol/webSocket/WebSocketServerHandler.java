package com.yzh.Netty.protocol.webSocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import org.apache.bcel.generic.IF_ACMPEQ;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;

/**
 * @author: yzh
 * @date: 2023-10-26 22:06:37
 * @Description: TODO 描述该类的功能
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    public static final Logger logger=Logger.getLogger(WebSocketServerHandler.class.getName());
    private WebSocketServerHandshaker handShaker;
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(msg instanceof FullHttpRequest){
                handleHttpRequest(ctx,(FullHttpRequest)msg);
            }else if(msg instanceof WebSocketFrame){
                handleWebSocketFrame(ctx, (WebSocketFrame)msg);
            }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request){
        if (!request.getDecoderResult().isSuccess()||(!"websocket".equals(request.headers().get("Upgrade")))){
            sendHttpResponse(ctx,request,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory=new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket",null,false);
        handShaker=wsFactory.newHandshaker(request);
        if (handShaker==null){
            WebSocketServerHandshakerFactory
                    .sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else{
            handShaker.handshake(ctx.channel(),request);
        }
    }


    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        if (frame instanceof CloseWebSocketFrame){
            handShaker.close(ctx.channel(),((CloseWebSocketFrame) frame).retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame){
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return ;
        }
        if (!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(String.format("%s frame types not supported",frame.getClass().getName()));
        }
        String request = ((TextWebSocketFrame) frame).text();
        if (logger.isLoggable(Level.FINE)){
            logger.fine(String.format("%s  received %s",ctx.close(),request));
        }
        ctx.channel().write(
                new TextWebSocketFrame(request+" ,欢迎使用netty WebSocket 服务，现在时刻：" + LocalDateTime.now().toString())
        );
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response){
        if (response.getStatus().code()!=200){
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), StandardCharsets.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            setContentLength(response,response.content().readableBytes());
        }
        ChannelFuture f = ctx.channel().writeAndFlush(response);
        if (isKeepAlive(request)||response.getStatus().code()!=200){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
