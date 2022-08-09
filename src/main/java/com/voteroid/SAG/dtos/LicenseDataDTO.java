package com.voteroid.SAG.dtos;

public class LicenseDataDTO {
	
	private String userId;
	private String userName;
	private Long createdOn;
	private String expiryDate;
	private Long subscriptionDurationinDays;
	private Long accessLimitPerDay;
	private String apiProvider;
	private String tokenId;
	
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
	
	
}
