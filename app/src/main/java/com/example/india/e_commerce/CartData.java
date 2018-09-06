package com.example.india.e_commerce;

public class CartData {
    String name, price;
    int img;

    public CartData(String name, String price, int img) {
        this.name = name;
        this.price = price;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImg() {
        return img;
    }
}
