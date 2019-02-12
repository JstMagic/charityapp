package com.kodemakers.charity.model;

import com.google.gson.annotations.SerializedName;

public class StatusResponse {

	@SerializedName("message")
	private String message;

	@SerializedName("registration_amount")
	private String registration_amount;

	@SerializedName("status")
	private String status;

	public String getRegistration_amount() {
		return registration_amount;
	}

	public void setRegistration_amount(String registration_amount) {
		this.registration_amount = registration_amount;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}