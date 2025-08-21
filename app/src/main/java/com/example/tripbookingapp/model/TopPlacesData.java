package com.example.tripbookingapp.model;

public class TopPlacesData {

    private String placeName;
    private String countryName;
    private String price;
    private String imageUrl; // changed from Integer to String

    // Empty constructor required by Firebase
    public TopPlacesData() {
    }

    public TopPlacesData(String placeName, String countryName, String price, String imageUrl) {
        this.placeName = placeName;
        this.countryName = countryName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
