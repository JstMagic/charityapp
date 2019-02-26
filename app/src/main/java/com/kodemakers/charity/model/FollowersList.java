package com.kodemakers.charity.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FollowersList{

	@SerializedName("followers")
	private List<FollowersItem> followers;

	@SerializedName("status")
	private String status;

	public void setFollowers(List<FollowersItem> followers){
		this.followers = followers;
	}

	public List<FollowersItem> getFollowers(){
		return followers;
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
			"FollowersList{" + 
			"followers = '" + followers + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}