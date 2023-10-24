package com.yzh.Netty.protocol.http.xml;

import com.yzh.Netty.protocol.http.xml.pojo.Order;
import com.yzh.Netty.protocol.http.xml.pojo.OrderFactory;
import org.jibx.runtime.*;
import org.jibx.runtime.impl.BindingFactoryBase;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author: yzh
 * @date: 2023-10-24 10:18:39
 * @Description: HTTP+XML POJO 测试类定义
 */
public class TestOrder {
    private IBindingFactory factory=null;
    private StringWriter writer=null;

    private StringReader reader=null;

    private final static String CHARSET_NAME="UTF-8";
    private String  encode2Xml(Order order) throws JiBXException,IOException{
        factory= BindingDirectory. getFactory(Order.class);
        writer=new StringWriter();
        IMarshallingContext matx=factory.createMarshallingContext();
        matx.setIndent(2);
        matx.marshalDocument(order,CHARSET_NAME,null,writer);
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr);
        return xmlStr;
    }
    private Order decode2Order(String xmlBody) throws JiBXException{
        reader=new StringReader(xmlBody);
        IUnmarshallingContext uctx=factory.createUnmarshallingContext();
        return  (Order) uctx.unmarshalDocument(reader);
    }

    public static void main(String[] args) throws JiBXException,IOException{
        TestOrder test = new TestOrder();
        Order order = OrderFactory.create(123);
        String body = test.encode2Xml(order);
        Order order1 = test.decode2Order(body);
        System.out.println(order1);
    }
}
