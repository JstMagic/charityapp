
package com.kodemakers.charity.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StaffDetails implements Serializable {

    @SerializedName("charity_id")
    private String mCharityId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("image")
    private String mImage;
    @SerializedName("is_active")
    private String mIsActive;
    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("staff_id")
    private String mStaffId;
    @SerializedName("type")
    private String mType;

    public String getCharityId() {
        return mCharityId;
    }

    public void setCharityId(String charityId) {
        mCharityId = charityId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getIsActive() {
        return mIsActive;
    }

    public void setIsActive(String isActive) {
        mIsActive = isActive;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getStaffId() {
        return mStaffId;
    }

    public void setStaffId(String staffId) {
        mStaffId = staffId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
