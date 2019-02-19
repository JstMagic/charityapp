package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

public class LikesDetails {

	@SerializedName("user_id")
	private String userId;

	@SerializedName("likes_id")
	private String likesId;

	@SerializedName("feeds_id")
	private String feedsId;

	@SerializedName("created_at")
	private String createdAt;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setLikesId(String likesId){
		this.likesId = likesId;
	}

	public String getLikesId(){
		return likesId;
	}

	public void setFeedsId(String feedsId){
		this.feedsId = feedsId;
	}

	public String getFeedsId(){
		return feedsId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	@Override
 	public String toString(){
		return 
			"LikesItem{" + 
			"user_id = '" + userId + '\'' + 
			",likes_id = '" + likesId + '\'' + 
			",feeds_id = '" + feedsId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			"}";
		}
}