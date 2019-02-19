package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedsResponse{

	@SerializedName("feeds")
	private List<FeedsDetails> feeds;

	@SerializedName("likes")
	private List<LikesDetails> likes;

	@SerializedName("status")
	private int status;

	public void setFeeds(List<FeedsDetails> feeds){
		this.feeds = feeds;
	}

	public List<FeedsDetails> getFeeds(){
		return feeds;
	}

	public void setLikes(List<LikesDetails> likes){
		this.likes = likes;
	}

	public List<LikesDetails> getLikes(){
		return likes;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"FeedsResponse{" + 
			"feeds = '" + feeds + '\'' + 
			",likes = '" + likes + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}