package com.voteroid.SAG.dtos;

import java.util.Set;

public class LicenseDataDTO {
	
	private String userId;
	private String userName;
	private Long createdOn;
	private String expiryDate;
	private Long subscriptionDurationinDays;
	private Long accessLimitPerDay;
	private String apiProvider;
	private String tokenId;
	private Set<Integer> apiIds;
	private int clientId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Long getSubscriptionDurationinDays() {
		return subscriptionDurationinDays;
	}
	public void setSubscriptionDurationinDays(Long subscriptionDurationinDays) {
		this.subscriptionDurationinDays = subscriptionDurationinDays;
	}
	public Long getAccessLimitPerDay() {
		return accessLimitPerDay;
	}
	public void setAccessLimitPerDay(Long accessLimitPerDay) {
		this.accessLimitPerDay = accessLimitPerDay;
	}
	public String getApiProvider() {
		return apiProvider;
	}
	public void setApiProvider(String apiProvider) {
		this.apiProvider = apiProvider;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Set<Integer> getApiIds() {
		return apiIds;
	}
	public void setApiIds(Set<Integer> apiIds) {
		this.apiIds = apiIds;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	
}
