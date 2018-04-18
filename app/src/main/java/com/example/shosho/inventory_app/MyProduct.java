package com.example.shosho.inventory_app;

import java.io.Serializable;

public class MyProduct implements Serializable {
    private int my_id;
    private String name;
    private int quantity;
    private int price;
    private String image;
    private String supplierMail;

    public MyProduct() {
    }

    public void setMy_id(int my_id) {
        this.my_id = my_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSupplierMail(String supplierMail) {
        this.supplierMail = supplierMail;
    }

    public int getMy_id() {

        return my_id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getSupplierMail() {
        return supplierMail;
    }
}


