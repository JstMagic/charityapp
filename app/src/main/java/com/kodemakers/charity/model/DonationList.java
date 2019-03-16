package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonationList {

    @SerializedName("result")
    private List<DonationDetails> donationDetails;

    @SerializedName("status")
    private String status;

    public List<DonationDetails> getDonationDetails() {
        return donationDetails;
    }

    public void setDonationDetails(List<DonationDetails> donationDetails) {
        this.donationDetails = donationDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
