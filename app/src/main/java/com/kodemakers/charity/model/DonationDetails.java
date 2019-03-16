package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

public class DonationDetails{

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("amount")
	private String amount;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("charity_id")
	private String charityId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("comment")
	private String comment;

	@SerializedName("donation_id")
	private String donationId;

	@SerializedName("username")
	private String username;

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setCharityId(String charityId){
		this.charityId = charityId;
	}

	public String getCharityId(){
		return charityId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setDonationId(String donationId){
		this.donationId = donationId;
	}

	public String getDonationId(){
		return donationId;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"DonationDetails{" + 
			"transaction_id = '" + transactionId + '\'' + 
			",amount = '" + amount + '\'' + 
			",user_id = '" + userId + '\'' + 
			",charity_id = '" + charityId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",comment = '" + comment + '\'' + 
			",donation_id = '" + donationId + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}