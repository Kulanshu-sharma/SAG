package com.voteroid.SAG.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TOKEN_TBL")
public class TokenTbl implements Serializable {

	private static final long serialVersionUID = -2564619631530102328L;
	
	@Id
	@GeneratedValue(generator = "token-sequence-generator")
	@SequenceGenerator(name = "token-sequence-generator", sequenceName = "TokenIdSequence", initialValue = 1000, allocationSize = 100)
	@Column(name="TOKEN_ID")
	private int tokenId;
	
	@Column(name="TOKEN", length=512)
	private String token;
	
	@Column(name="APIS_LIST",length=512)
	private String apiIds;
	
	@Column(name="USER_ID")
	private int userId;
	
	@Column(name="CLIENT_ID")
	private int clientId;
	
	@Column(name="VALIDITY")
	private int validity;
		
	@Column(name="SUSPENDED")
	private boolean suspended;
	
	@Column(name="CREATED_ON")
	private LocalDate createdOn;
	
	@Column(name="EXPIRES_ON")
	private LocalDate expiresOn;
	
	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public String getApiIds() {
		return apiIds;
	}

	public void setApiIds(String apiIds) {
		this.apiIds = apiIds;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public LocalDate getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(LocalDate expiresOn) {
		this.expiresOn = expiresOn;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}
	
}
