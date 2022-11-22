package com.example.businesssearch;

public class Review {
    private String name;
    private int rating;
    private String text;
    private String time;

    public Review(String name, int rating, String text, String time) {
        this.name = name;
        this.rating = rating;
        this.text = text;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Review{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
