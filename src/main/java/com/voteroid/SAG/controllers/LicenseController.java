package com.voteroid.SAG.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.voteroid.SAG.configurations.ApplicationPropertiesConfiguration;
import com.voteroid.SAG.configurations.JWTUtility;
import com.voteroid.SAG.dtos.Constants;
import com.voteroid.SAG.dtos.LicenseDataDTO;
import com.voteroid.SAG.dtos.Response;
import com.voteroid.SAG.entities.APITable;
import com.voteroid.SAG.entities.TokenTbl;
import com.voteroid.SAG.exceptions.NoAPIProviderRecieved;
import com.voteroid.SAG.exceptions.NoAccessAPIListRecieved;
import com.voteroid.SAG.exceptions.NoClientIdRecieved;
import com.voteroid.SAG.exceptions.NoLicenseDataRecieved;
import com.voteroid.SAG.exceptions.NoLicenseUserIdRecieved;
import com.voteroid.SAG.exceptions.NoSubscriptionDurationRecieved;
import com.voteroid.SAG.exceptions.SAGAccessAuthenticationFailed;
import com.voteroid.SAG.repositories.APITblRepository;
import com.voteroid.SAG.repositories.TokenTblRepository;

@RestController
public class LicenseController {

	@Autowired
	public JWTUtility jwtUtility;
	
	@Autowired
	public ApplicationPropertiesConfiguration application;
	
	@Autowired
	public APITblRepository repository;
	
	@Autowired
	public TokenTblRepository tokenTblRepository;
	
	@PostMapping("/sag/generateLicenseKey")
	public Response generateLicenseKeyFromGateway(@RequestHeader("accessKey") String accessKey,@RequestBody LicenseDataDTO licenseDataDTO) {
        if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
        Response response = new Response();
        TokenTbl tokenTbl = new TokenTbl();
		if(licenseDataDTO==null) 
        	throw new NoLicenseDataRecieved();
		if(licenseDataDTO.getApiIds()==null || licenseDataDTO.getApiIds().isEmpty())
			throw new NoAccessAPIListRecieved();
        if(licenseDataDTO.getUserId()==0)
        	throw new NoLicenseUserIdRecieved();
        if(licenseDataDTO.getSubscriptionDurationinDays()==0)
        	throw new NoSubscriptionDurationRecieved();
        if(licenseDataDTO.getClientId()==0)
        	throw new NoAPIProviderRecieved();
        tokenTbl.setClientId(licenseDataDTO.getClientId());
        tokenTbl.setUserId(licenseDataDTO.getUserId());
        tokenTbl.setExpiresOn(LocalDate.now().plusDays(licenseDataDTO.getSubscriptionDurationinDays()));
        tokenTbl.setSuspended(false);
        tokenTbl.setCreatedOn(LocalDate.now());
        StringBuilder apiListString= new StringBuilder();
        apiListString.append(licenseDataDTO.getApiIds().get(0));
        for(int i=1;i<licenseDataDTO.getApiIds().size();i++)
        	apiListString.append(Constants.General.DELIMITER+licenseDataDTO.getApiIds().get(i));
        tokenTbl.setApiIds(apiListString.toString());
		String token = jwtUtility.generateLicenseKeyinFormOfTokenFromClient(licenseDataDTO);
		tokenTbl.setToken(token);
		tokenTblRepository.save(tokenTbl);
		response.setSuccessfull(true);
		response.setData(token);
		response.setMessage("License Generated Successfully");
		return response;
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
	
	@PutMapping("/sag/setApiPrice/apiId/{apiId}/amount/{amount}")
	public Response setorUpdateApiPrice(@RequestHeader("accessKey") String accessKey,@PathVariable(name="apiId") int apiId,
																					 @PathVariable(name="amount") float amount) {
		if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		Response response = new Response();
		Optional<APITable> apiTable = repository.findById(apiId);
		if(!apiTable.isPresent()) {
			response.setSuccessfull(false);
			response.setMessage(Constants.Messages.INVALID_API_ID+" : "+apiId);
			return response;
		}
	    float prevPrice = apiTable.get().getRate();
		apiTable.get().setRate(amount);
		repository.save(apiTable.get());
		response.setSuccessfull(true);
		response.setMessage("Subscription Amount for API "+apiId+" is successfully changed from "+prevPrice+" to "+amount);
		return response;
	}
	
	@GetMapping("/sag/fetchTokens/client")
	public Response fetchTokensForClientId(@RequestHeader("accessKey") String accessKey,@RequestParam int clientId) {
		Response response = new Response();
		if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		TokenTbl tokenTbl = tokenTblRepository.findByClientId(clientId);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("userId",tokenTbl.getUserId());
		dataMap.put("tokenId",tokenTbl.getTokenId());
		dataMap.put("apiIds",tokenTbl.getApiIds());
		dataMap.put("expiredOn",tokenTbl.getExpiresOn());
		dataMap.put("createdOn", tokenTbl.getCreatedOn());
		response.setSuccessfull(true);
		response.setData(dataMap);
		response.setMessage("Token Data Delivered !!!");
		return response;
	}
	
	@GetMapping("/sag/fetchTokens/user")
	public Response fetchTokensForUserId(@RequestHeader("accessKey") String accessKey,@RequestParam int userId) {
		Response response = new Response();
		if(!application.getAccessKey().equals(accessKey))
        	throw new SAGAccessAuthenticationFailed();
		TokenTbl tokenTbl = tokenTblRepository.findByUserId(userId);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("clientId",tokenTbl.getClientId());
		dataMap.put("tokenId",tokenTbl.getTokenId());
		dataMap.put("apiIds",tokenTbl.getApiIds());
		dataMap.put("expiredOn",tokenTbl.getExpiresOn());
		dataMap.put("createdOn", tokenTbl.getCreatedOn());
		dataMap.put("token",tokenTbl.getToken());
		response.setSuccessfull(true);
		response.setData(dataMap);
		response.setMessage("Token Data Delivered !!!");
		return response;
	}
}
