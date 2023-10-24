package com.yzh.Netty.protocol.http.xml.pojo;

/**
 * @author: yzh
 * @date: 2023-10-23 16:59:44
 * @Description: 订购请求消息定义表Order
 */
public class Order {
    private long orderNumber;

    private Customer customer;

    private Address orderTo;

    private Shipping shipping;

    private Address shipTo;

    private double total;

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getOrderTo() {
        return orderTo;
    }

    public void setOrderTo(Address orderTo) {
        this.orderTo = orderTo;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Address getShipTo() {
        return shipTo;
    }

    public void setShipTo(Address shipTo) {
        this.shipTo = shipTo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customer=" + customer +
                ", orderTo=" + orderTo +
                ", shipping=" + shipping +
                ", shipTo=" + shipTo +
                ", total=" + total +
                '}';
    }
}
