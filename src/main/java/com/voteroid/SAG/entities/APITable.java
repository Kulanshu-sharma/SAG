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
	
	@Column(name="CLIENT_NAME")
	public String clientName;

	public APITable() {
		
	}
	
	public APITable(int apiId, int clientId, String apiCall, float rate, String apiURL, String clientName) {
		super();
		this.apiId = apiId;
		this.clientId = clientId;
		this.apiCall = apiCall;
		this.rate = rate;
		this.apiURL = apiURL;
		this.clientName = clientName;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
}
