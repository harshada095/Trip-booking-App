package com.example.tripbookingapp.model;

public class BookingModel {
    private String placeName;
    private String date;
    private String price;
    private String description;
    private String rating;

    public BookingModel() {
        // Empty constructor required for Firestore
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }
}
