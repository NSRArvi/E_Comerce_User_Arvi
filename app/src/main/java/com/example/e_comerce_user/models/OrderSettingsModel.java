package com.example.e_comerce_user.models;

public class OrderSettingsModel {
    private double deliveryCharge;
    private double discount;
    private double vat;

    public OrderSettingsModel() {
    }

    public OrderSettingsModel(double deliveryCharge, double discount, double vat) {
        this.deliveryCharge = deliveryCharge;
        this.discount = discount;
        this.vat = vat;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
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
}
