package com.example.india.e_commerce;

public class ProductData {
    private String name, image;
    private int price, cat;

    public ProductData(String name, String image, int price, int cat) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.cat = cat;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public int getCat() {
        return cat;
    }
}
