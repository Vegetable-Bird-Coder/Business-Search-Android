package com.example.businesssearch;

public class BookingInfo {
    private String id;
    private String name;
    private String date;
    private String time;
    private String email;

    public BookingInfo(String id, String name, String date, String time, String email) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "BookingInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
