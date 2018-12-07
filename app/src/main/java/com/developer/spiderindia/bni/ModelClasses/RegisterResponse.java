package com.developer.spiderindia.bni.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterResponse implements Serializable {

	@SerializedName("result")
	private String result;

	@SerializedName("data")
	private int data;

	@SerializedName("message")
	private String message;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setData(int data){
		this.data = data;
	}

	public int getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}