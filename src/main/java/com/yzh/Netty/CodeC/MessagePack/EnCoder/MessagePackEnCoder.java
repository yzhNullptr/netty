package com.yzh.Netty.CodeC.MessagePack.EnCoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author: yzh
 * @date: 2023-10-22 15:16:34
 * @Description: TODO 描述该类的功能
 */
public class MessagePackEnCoder extends MessageToByteEncoder<Object> {



    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] write = messagePack.write(o);
        byteBuf.writeBytes(write);
    }
}
