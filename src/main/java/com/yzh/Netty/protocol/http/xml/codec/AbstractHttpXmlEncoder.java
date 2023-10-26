package com.yzh.Netty.protocol.http.xml.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.bcel.generic.FADD;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;

import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * @author: yzh
 * @date: 2023-10-24 15:14:37
 * @Description: 抽象的xml编码器
 */
public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {
    IBindingFactory factory=null;
    StringWriter writer=null;
    final static String CHARSET_NAME="UTF-8";
    final static Charset UTF_8=Charset.forName(CHARSET_NAME);

    protected ByteBuf encode0(ChannelHandlerContext ctx,Object body)throws Exception{
        factory= BindingDirectory.getFactory(body.getClass());
        writer=new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(body,CHARSET_NAME,null,writer);
        String xmlStr = writer.toString();
        System.out.println("==================\n"+xmlStr);
        writer.close();
        writer=null;
        return Unpooled.copiedBuffer(xmlStr, UTF_8);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (writer!=null){
            writer.close();
            writer=null;
        }
    }
}
