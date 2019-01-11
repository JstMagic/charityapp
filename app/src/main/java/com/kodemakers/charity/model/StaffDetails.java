package com.kodemakers.charity.model;

public class StaffDetails {

    private String name;
    private Integer image;
    private String role;

    public StaffDetails(String name, Integer image, String role) {
        this.name = name;
        this.image = image;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
