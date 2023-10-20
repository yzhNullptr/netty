package com.yzh.AIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.time.LocalDateTime;

/**
 * @author: yzh
 * @date: 2023-10-18 10:01:14
 * @Description: TODO 描述该类的功能
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asynchronousSocketChannel;

    public ReadCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        if (asynchronousSocketChannel!=null){
            this.asynchronousSocketChannel = asynchronousSocketChannel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        try{
            String req = StandardCharsets.UTF_8.decode(attachment).toString();
            System.out.println("the time server receive order : "+req);
            String currentTime="QUERY TIME ORDER".equalsIgnoreCase(req) ? LocalDateTime.now().toString():"BAD ORDER";
            doWrite(currentTime);
        }catch (UnsupportedCharsetException e){
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try{
            asynchronousSocketChannel.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void doWrite(String currentTime){
        if (currentTime!=null&&currentTime.length()>0){
            ByteBuffer flip = StandardCharsets.UTF_8.encode(currentTime);
            flip.flip();
            asynchronousSocketChannel.write(flip, flip, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (flip.hasRemaining()){
                        asynchronousSocketChannel.write(flip,flip,this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try{
                        asynchronousSocketChannel.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
