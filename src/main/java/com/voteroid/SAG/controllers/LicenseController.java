package com.voteroid.SAG.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.voteroid.SAG.configurations.ApplicationPropertiesConfiguration;
import com.voteroid.SAG.configurations.JWTUtility;
import com.voteroid.SAG.dtos.LicenseDataDTO;
import com.voteroid.SAG.exceptions.NoAPIProviderRecieved;
import com.voteroid.SAG.exceptions.NoLicenseDataRecieved;
import com.voteroid.SAG.exceptions.NoLicenseUserIdRecieved;
import com.voteroid.SAG.exceptions.NoLicenseUserNameRecieved;
import com.voteroid.SAG.exceptions.NoSubscriptionDurationRecieved;
import com.voteroid.SAG.exceptions.SAGAccessAuthenticationFailed;

@RestController
public class LicenseController {

	@Autowired
	public JWTUtility jwtUtility;
	
	@Autowired
	public ApplicationPropertiesConfiguration application;
	
	@PostMapping("/generateLicenseKey")
	public String generateLicenseKeyFromGateway(@RequestHeader("accessKey") String accessKey,@RequestBody LicenseDataDTO licenseDataDTO) {
        if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		if(licenseDataDTO==null) 
        	throw new NoLicenseDataRecieved();
        if(licenseDataDTO.getUserId()==null || licenseDataDTO.getUserId().isEmpty())
        	throw new NoLicenseUserIdRecieved();
        if(licenseDataDTO.getUserName()==null || licenseDataDTO.getUserName().isEmpty())
        	throw new NoLicenseUserNameRecieved();
        if(licenseDataDTO.getSubscriptionDurationinDays()==null||licenseDataDTO.getSubscriptionDurationinDays().equals(0))
        	throw new NoSubscriptionDurationRecieved();
        if(licenseDataDTO.getApiProvider()==null || licenseDataDTO.getApiProvider().isEmpty())
        	throw new NoAPIProviderRecieved();
        licenseDataDTO.setTokenId(UUID.randomUUID().toString());
        licenseDataDTO.setCreatedOn(System.currentTimeMillis());
        
		return jwtUtility.generateLicenseKeyinFormOfTokenFromClient(licenseDataDTO);
	}
}
