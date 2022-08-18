package com.voteroid.SAG.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.voteroid.SAG.configurations.ApplicationPropertiesConfiguration;
import com.voteroid.SAG.configurations.JWTUtility;
import com.voteroid.SAG.dtos.Constants;
import com.voteroid.SAG.dtos.LicenseDataDTO;
import com.voteroid.SAG.dtos.Response;
import com.voteroid.SAG.entities.APITable;
import com.voteroid.SAG.exceptions.NoAPIProviderRecieved;
import com.voteroid.SAG.exceptions.NoAccessAPIListRecieved;
import com.voteroid.SAG.exceptions.NoClientIdRecieved;
import com.voteroid.SAG.exceptions.NoLicenseDataRecieved;
import com.voteroid.SAG.exceptions.NoLicenseUserIdRecieved;
import com.voteroid.SAG.exceptions.NoLicenseUserNameRecieved;
import com.voteroid.SAG.exceptions.NoSubscriptionDurationRecieved;
import com.voteroid.SAG.exceptions.SAGAccessAuthenticationFailed;
import com.voteroid.SAG.repositories.APITblRepository;

@RestController
public class LicenseController {

	@Autowired
	public JWTUtility jwtUtility;
	
	@Autowired
	public ApplicationPropertiesConfiguration application;
	
	@Autowired
	public APITblRepository repository;
	
	@PostMapping("/sag/generateLicenseKey")
	public String generateLicenseKeyFromGateway(@RequestHeader("accessKey") String accessKey,@RequestBody LicenseDataDTO licenseDataDTO) {
        if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		if(licenseDataDTO==null) 
        	throw new NoLicenseDataRecieved();
		if(licenseDataDTO.getApiIds()==null || licenseDataDTO.getApiIds().isEmpty())
			throw new NoAccessAPIListRecieved();
        if(licenseDataDTO.getUserId()==null || licenseDataDTO.getUserId().isEmpty())
        	throw new NoLicenseUserIdRecieved();
        if(licenseDataDTO.getUserName()==null || licenseDataDTO.getUserName().isEmpty())
        	throw new NoLicenseUserNameRecieved();
        if(licenseDataDTO.getSubscriptionDurationinDays()==null||licenseDataDTO.getSubscriptionDurationinDays()==0)
        	throw new NoSubscriptionDurationRecieved();
        if(licenseDataDTO.getApiProvider()==null || licenseDataDTO.getApiProvider().isEmpty())
        	throw new NoAPIProviderRecieved();
        licenseDataDTO.setTokenId(UUID.randomUUID().toString());
        licenseDataDTO.setCreatedOn(System.currentTimeMillis());
        
		return jwtUtility.generateLicenseKeyinFormOfTokenFromClient(licenseDataDTO);
	}
	
	@PostMapping("/sag/registerStandardApi")
	public boolean registerStandardApi(@RequestHeader("accessKey") String accessKey,@RequestBody APITable apiTbl) {
		if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		apiTbl.setClientId(apiTbl.getApiId()/100000);
		repository.save(apiTbl);
		return true;
	}
	
	@GetMapping("/sag/apisList/{clientId}")
	public List<APITable> fetchApiListFromClientId(@RequestHeader("accessKey") String accessKey,@PathVariable(name="clientId") int clientId) {
		if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		if(clientId==0)
			throw new NoClientIdRecieved();
		return repository.findByClientId(clientId);
	}
	
	@PutMapping("/sag/blockSubscriptions/{apiId}")
	public Response blockNewSubscriptions(@RequestHeader("accessKey") String accessKey,@PathVariable(name="apiId") int apiId) {
		if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		Response response = new Response();
		Optional<APITable> apiTable = repository.findById(apiId);
		if(!apiTable.isPresent()) {
			response.setSuccessfull(false);
			response.setMessage(Constants.Messages.INVALID_API_ID+" : "+apiId);
			return response;
		}
		apiTable.get().setBlocked(true);
		repository.save(apiTable.get());
		response.setSuccessfull(true);
		response.setMessage(Constants.Messages.API_BLOCKED_SUCCESSFULLY+" : "+apiId);
		return response;
	}
	
	@PutMapping("/sag/unblockSubscriptions/{apiId}")
	public Response unblockNewSubscriptions(@RequestHeader("accessKey") String accessKey,@PathVariable(name="apiId") int apiId) {
		if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		Response response = new Response();
		Optional<APITable> apiTable = repository.findById(apiId);
		if(!apiTable.isPresent()) {
			response.setSuccessfull(false);
			response.setMessage(Constants.Messages.INVALID_API_ID+" : "+apiId);
			return response;
		}
		if(!apiTable.get().isBlocked()) {
			response.setSuccessfull(false);
			response.setMessage(Constants.Messages.ALREADY_NOT_BLOCKED+" : "+apiId);
			return response;
		}
		apiTable.get().setBlocked(false);
		repository.save(apiTable.get());
		response.setSuccessfull(true);
		response.setMessage(Constants.Messages.API_UN_BLOCKED_SUCCESSFULLY+" : "+apiId);
		return response;
	}
	
	@GetMapping("/sag/apisListUser/{clientId}")
	public Response fetchAvailableApiListforUsersFromClientId(@RequestHeader("accessKey") String accessKey,@PathVariable(name="clientId") int clientId) {
		if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		if(clientId==0)
			throw new NoClientIdRecieved();
		Response response = new Response();
		List<APITable> apiTables =  repository.findByClientIdAndBlocked(clientId,false);
		if(apiTables.size()==0) {
			response.setSuccessfull(false);
			response.setMessage(Constants.Messages.NO_API_REGISTERED_BY_CLIENT_YET);
			return response;
		}
		response.setSuccessfull(true);
		response.setMessage("API'S List Sent Successfully");
		response.setData(apiTables);
		return response;
	}
}
