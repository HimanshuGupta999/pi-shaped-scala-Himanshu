package com.pishape.day1.model;

public class Order {
    private String orderId;
    private String customerName;
    private String product;

    public Order() {}

    public Order(String orderId, String customerName, String product) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.product = product;
    }

    // Getters & Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
}

