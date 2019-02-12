package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

public class CharityResponse{

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("image")
	private String image;

	@SerializedName("charity_type")
	private String charityType;

	@SerializedName("is_active")
	private String isActive;

	@SerializedName("charity_address")
	private String charityAddress;

	@SerializedName("impact")
	private String impact;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("notification_id")
	private String notificationId;

	@SerializedName("message")
	private String message;

	@SerializedName("charity_name")
	private String charityName;

	@SerializedName("charity_details")
	private String charityDetails;

	@SerializedName("password")
	private String password;

	@SerializedName("followers")
	private String followers;

	@SerializedName("charity_id")
	private String charityId;

	@SerializedName("email")
	private String email;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("status")
	private String status;

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCharityType(String charityType){
		this.charityType = charityType;
	}

	public String getCharityType(){
		return charityType;
	}

	public void setIsActive(String isActive){
		this.isActive = isActive;
	}

	public String getIsActive(){
		return isActive;
	}

	public void setCharityAddress(String charityAddress){
		this.charityAddress = charityAddress;
	}

	public String getCharityAddress(){
		return charityAddress;
	}

	public void setImpact(String impact){
		this.impact = impact;
	}

	public String getImpact(){
		return impact;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setNotificationId(String notificationId){
		this.notificationId = notificationId;
	}

	public String getNotificationId(){
		return notificationId;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setCharityName(String charityName){
		this.charityName = charityName;
	}

	public String getCharityName(){
		return charityName;
	}

	public void setCharityDetails(String charityDetails){
		this.charityDetails = charityDetails;
	}

	public String getCharityDetails(){
		return charityDetails;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setFollowers(String followers){
		this.followers = followers;
	}

	public String getFollowers(){
		return followers;
	}

	public void setCharityId(String charityId){
		this.charityId = charityId;
	}

	public String getCharityId(){
		return charityId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"CharityResponse{" + 
			"transaction_id = '" + transactionId + '\'' + 
			",image = '" + image + '\'' + 
			",charity_type = '" + charityType + '\'' + 
			",is_active = '" + isActive + '\'' + 
			",charity_address = '" + charityAddress + '\'' + 
			",impact = '" + impact + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",notification_id = '" + notificationId + '\'' + 
			",message = '" + message + '\'' + 
			",charity_name = '" + charityName + '\'' + 
			",charity_details = '" + charityDetails + '\'' + 
			",password = '" + password + '\'' + 
			",followers = '" + followers + '\'' + 
			",charity_id = '" + charityId + '\'' + 
			",email = '" + email + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}