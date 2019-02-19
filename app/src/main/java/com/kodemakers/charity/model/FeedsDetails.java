package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

public class FeedsDetails {

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("is_active")
	private String isActive;

	@SerializedName("charity_id")
	private String charityId;

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("details")
	private String details;

	@SerializedName("title")
	private String title;

	@SerializedName("feed_type")
	private String feedType;

	@SerializedName("feed_id")
	private String feedId;

	@SerializedName("likes")
	private String likes;

	public boolean isLiked;

	public void setVideoUrl(String videoUrl){
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public void setIsActive(String isActive){
		this.isActive = isActive;
	}

	public String getIsActive(){
		return isActive;
	}

	public void setCharityId(String charityId){
		this.charityId = charityId;
	}

	public String getCharityId(){
		return charityId;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setFeedType(String feedType){
		this.feedType = feedType;
	}

	public String getFeedType(){
		return feedType;
	}

	public void setFeedId(String feedId){
		this.feedId = feedId;
	}

	public String getFeedId(){
		return feedId;
	}

	public void setLikes(String likes){
		this.likes = likes;
	}

	public String getLikes(){
		return likes;
	}

	@Override
 	public String toString(){
		return 
			"FeedsItem{" + 
			"video_url = '" + videoUrl + '\'' + 
			",is_active = '" + isActive + '\'' + 
			",charity_id = '" + charityId + '\'' + 
			",image_url = '" + imageUrl + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",details = '" + details + '\'' + 
			",title = '" + title + '\'' + 
			",feed_type = '" + feedType + '\'' + 
			",feed_id = '" + feedId + '\'' + 
			",likes = '" + likes + '\'' + 
			"}";
		}
}