package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

public class FollowersItem{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("charity_id")
	private String charityId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private String id;

	@SerializedName("username")
	private String username;

	@SerializedName("image")
	private String image;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"FollowersItem{" + 
			"user_id = '" + userId + '\'' + 
			",charity_id = '" + charityId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}