package com.example.india.e_commerce;

public class MyOrdersData {
    private String product, price, status, address, landmark, city, areacode, contact, image;

    public MyOrdersData(String product, String price, String status, String address, String landmark, String city, String areacode, String contact, String image) {
        this.product = product;
        this.price = price;
        this.status = status;
        this.address = address;
        this.landmark = landmark;
        this.city = city;
        this.areacode = areacode;
        this.contact = contact;
        this.image = image;
    }

    public String getProduct() {
        return product;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getCity() {
        return city;
    }

    public String getAreacode() {
        return areacode;
    }

    public String getContact() {
        return contact;
    }

    public String getImage() {
        return image;
    }
}
