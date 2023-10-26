package com.yzh.Netty.protocol.http.xml.codec;

import com.sun.media.jfxmediaimpl.HostUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;

import java.io.StringReader;
import java.nio.charset.Charset;

/**
 * @author: yzh
 * @date: 2023-10-24 16:35:56
 * @Description: 抽象的xml消息解码器
 */
public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {
    private IBindingFactory factory;

    private StringReader reader;
    private Class<?> clazz;
    private boolean isPrint;
    private final static String CHARSET_NAME="UTF-8";
    private final static Charset UTF_8=Charset.forName(CHARSET_NAME) ;

    public AbstractHttpXmlDecoder( Class<?> clazz) {
        this(clazz,false);
    }

    public AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }
    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body)throws Exception{
        factory= BindingDirectory.getFactory(clazz);
        String content = body.toString(UTF_8);
        if (isPrint)
            System.out.println("The body is : "+body);
        reader=new StringReader(content);
        IUnmarshallingContext uct = factory.createUnmarshallingContext();
        Object result = uct.unmarshalDocument(reader);
        reader.close();
        reader=null;
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (reader!=null){
            reader.close();
            reader=null;
        }
    }
}
