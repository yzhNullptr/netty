package com.yzh.Netty.protocol.http.xml.pojo;

/**
 * @author: yzh
 * @date: 2023-10-24 10:56:20
 * @Description: Order工厂类
 */
public class OrderFactory {
    public static Order create(long orderId){
        Order order=new Order();
        order.setOrderNumber(orderId);
        order.setTotal(9999.999f);
        Address address = new Address();
        address.setCity("南京市");
        address.setCountry("中国");
        address.setPostCode("123321");
        address.setProvince("江苏省");
        address.setStreet1("龙眠大道");
        order.setOrderTo(address);
        Customer customer = new Customer();
        customer.setCustomerId(orderId);
        customer.setFirstName("李");
        customer.setLastName("林峰");
        order.setCustomer(customer);
        order.setShipping(Shipping.INTERNATIONAL_MAIL);
        order.setShipTo(address);
        return order;
    }
}
