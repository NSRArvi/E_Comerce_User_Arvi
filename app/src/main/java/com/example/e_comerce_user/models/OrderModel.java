package com.example.e_comerce_user.models;

public class OrderModel {
    private String orderId;
    private String userId;
    private long orderTimeStamp;
    private int orderDay;
    private int orderMonth;
    private int orderYear;
    private double grandTotal;
    private double discount;
    private double vat;
    private double deliveryCharge;
    private String orderStatus;
    private String shippingAddress;

    public OrderModel() { }

    public OrderModel(String orderId, String userId, long orderTimeStamp, int orderDay, int orderMonth, int orderYear, double grandTotal, double discount, double vat, double deliveryCharge, String orderStatus, String shippingAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderTimeStamp = orderTimeStamp;
        this.orderDay = orderDay;
        this.orderMonth = orderMonth;
        this.orderYear = orderYear;
        this.grandTotal = grandTotal;
        this.discount = discount;
        this.vat = vat;
        this.deliveryCharge = deliveryCharge;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getOrderTimeStamp() {
        return orderTimeStamp;
    }

    public void setOrderTimeStamp(long orderTimeStamp) {
        this.orderTimeStamp = orderTimeStamp;
    }

    public int getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(int orderDay) {
        this.orderDay = orderDay;
    }

    public int getOrderMonth() {
        return orderMonth;
    }

    public void setOrderMonth(int orderMonth) {
        this.orderMonth = orderMonth;
    }

    public int getOrderYear() {
        return orderYear;
    }

    public void setOrderYear(int orderYear) {
        this.orderYear = orderYear;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
