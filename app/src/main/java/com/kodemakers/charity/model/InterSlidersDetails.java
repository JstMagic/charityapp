package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InterSlidersDetails implements Serializable {

    @SerializedName("image")
    private String image;

    @SerializedName("is_active")
    private String isActive;

    @SerializedName("charity_id")
    private String charityId;

    @SerializedName("description")
    private String description;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setCharityId(String charityId) {
        this.charityId = charityId;
    }

    public String getCharityId() {
        return charityId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return
                "ResultItem{" +
                        "image = '" + image + '\'' +
                        ",is_active = '" + isActive + '\'' +
                        ",charity_id = '" + charityId + '\'' +
                        ",description = '" + description + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",id = '" + id + '\'' +
                        ",title = '" + title + '\'' +
                        "}";
    }
}