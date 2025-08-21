package com.example.tripbookingapp.model;

public class Hotel {
    private String name;
    private String location;
    private String imageUrl;
    private double price;

    public Hotel() {
        // Needed for Firebase
    }

    public Hotel(String name, String location, String imageUrl, double price) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getImageUrl() { return imageUrl; }
    public double getPrice() { return price; }
}
