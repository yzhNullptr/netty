package com.yzh.Netty.protocol.http.xml.pojo;

import java.util.List;

/**
 * @author: yzh
 * @date: 2023-10-23 17:07:00
 * @Description: 客户信息定义
 */
public class Customer {
    /**
     * 用户id
     */
    private long customerId;
    /**
     * 姓
     */
    private String firstName;
    /**
     * 名
     */
    private String lastName;
    /**
     * 全名
     */
    private List<String> middleNames;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }

    /**
     * 重写ToString方法
     * @return 字符串输出
     */
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleNames=" + middleNames +
                '}';
    }
}
