package com.voteroid.SAG.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="API_TBL")
public class APITable {

	@Id
	@Column(name="API_ID")
	public int apiId;
	
	@Column(name="CLIENT_ID")
	public int clientId;
	
	@Column(name="API_CALL")
	public String apiCall;
	
	@Column(name="RATE")
	public float rate;
	
	@Column(name="API_URL")
	public String apiURL;
	
	@Column(name="METHOD_NAME")
	public String methodName;
	
	@Column(name="BLOCKED")
	public boolean blocked;

	public APITable() {
		
	}
	
	public APITable(int apiId, int clientId, String apiCall, float rate, String apiURL, String methodName, boolean blocked) {
		super();
		this.apiId = apiId;
		this.clientId = clientId;
		this.apiCall = apiCall;
		this.rate = rate;
		this.apiURL = apiURL;
		this.methodName = methodName;
		this.blocked = blocked;
	}

	public int getApiId() {
		return apiId;
	}

	public void setApiId(int apiId) {
		this.apiId = apiId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getApiCall() {
		return apiCall;
	}

	public void setApiCall(String apiCall) {
		this.apiCall = apiCall;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getApiURL() {
		return apiURL;
	}

	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	
	
}
