package com.kodemakers.charity.model;

public class StoryDetails {

    private String title;
    private Integer image;
    private String date;

    public StoryDetails(String title, Integer image, String date) {
        this.title = title;
        this.image = image;
        this.date = date;
    }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
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
