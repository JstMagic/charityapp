package com.kodemakers.charity.model;

public class DonationDetails {

    private String contributorName;
    private Integer image;
    private String donationType;
    private String amount;

    public DonationDetails(String contributorName, Integer image, String donationType, String amount) {
        this.contributorName = contributorName;
        this.image = image;
        this.donationType = donationType;
        this.amount = amount;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
