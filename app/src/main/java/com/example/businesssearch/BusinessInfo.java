package com.example.businesssearch;

public class BusinessInfo {
    private String id;
    private String name;
    private String imageUrl;
    private double rating;
    private int distance;



    public BusinessInfo(String id, String name, String imageUrl, double rating, int distance) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "BusinessInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", rating=" + rating +
                ", distance=" + distance +
                '}';
    }
}
