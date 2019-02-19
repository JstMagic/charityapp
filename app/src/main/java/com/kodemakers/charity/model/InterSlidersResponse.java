package com.kodemakers.charity.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class InterSlidersResponse{

	@SerializedName("result")
	private List<InterSlidersDetails> result;

	@SerializedName("total")
	private int total;

	@SerializedName("status")
	private int status;

	public void setResult(List<InterSlidersDetails> result){
		this.result = result;
	}

	public List<InterSlidersDetails> getResult(){
		return result;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
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
			"InterSlidersResponse{" + 
			"result = '" + result + '\'' + 
			",total = '" + total + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}