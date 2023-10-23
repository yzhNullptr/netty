package com.yzh.Netty.CodeC.MessagePack.Decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author: yzh
 * @date: 2023-10-22 15:27:36
 * @Description: TODO 描述该类的功能
 */
public class MessagePackDeCoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            final byte[] array;
            final int length=byteBuf.readableBytes();
            array=new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(),array,0,length);
        MessagePack messagePack = new MessagePack();
        list.add(messagePack.read(array));
    }
}
