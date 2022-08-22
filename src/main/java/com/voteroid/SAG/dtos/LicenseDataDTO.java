package com.voteroid.SAG.dtos;

import java.util.List;

public class LicenseDataDTO {
	
	private int userId;
	private int subscriptionDurationinDays;
	private List<Integer> apiIds;
	private int clientId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSubscriptionDurationinDays() {
		return subscriptionDurationinDays;
	}
	public void setSubscriptionDurationinDays(int subscriptionDurationinDays) {
		this.subscriptionDurationinDays = subscriptionDurationinDays;
	}
	public List<Integer> getApiIds() {
		return apiIds;
	}
	public void setApiIds(List<Integer> apiIds) {
		this.apiIds = apiIds;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	
	
}
