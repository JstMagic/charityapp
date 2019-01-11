package com.kodemakers.charity.model;

public class UserDetails {

    private String name;
    private Integer image;
    private String date;

    public UserDetails(String name, Integer image, String date) {
        this.name = name;
        this.image = image;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
