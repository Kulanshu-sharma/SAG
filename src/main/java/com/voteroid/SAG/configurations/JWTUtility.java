package com.voteroid.SAG.configurations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voteroid.SAG.dtos.LicenseDataDTO;
import com.voteroid.SAG.entities.TokenTbl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtility {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTVerificationFilter.class);
	
	@Autowired
	ApplicationPropertiesConfiguration applicationPropertiesConfiguration;
	
	@Autowired 
	GeneralOperations generalOperations;

	public String generateLicenseKeyinFormOfTokenFromClient(LicenseDataDTO license) {
		Map<String, Object> claims = new HashMap<String,Object>();
		claims.put("clientId",license.getClientId());
        if(license.getApiIds().size()<applicationPropertiesConfiguration.getApiThreshold()) {
        	LOGGER.info("Count of API's Access recieved to generate token is : ",license.getApiIds().size());
        	claims.put("accessTo",license.getApiIds());
        }
        else {
        	LOGGER.info("Count of API's Access recieved to generate token is : ",license.getApiIds().size());
        	//Go For Database Operations...
        }
		return Jwts.builder().setClaims(claims).setSubject(license.getUserId()+"").setIssuedAt(new Date(System.currentTimeMillis()))
		                     .setExpiration(new Date(System.currentTimeMillis() + license.getSubscriptionDurationinDays()*24*60*60*1000))
		                     .signWith(SignatureAlgorithm.HS512, applicationPropertiesConfiguration.getSecret()).compact();

	}
	
	public Claims verifyTokenAndSendClaims(String token) throws Exception {
		return Jwts.parser()
				   .setSigningKey(applicationPropertiesConfiguration.getSecret())
				   .parseClaimsJws(token)
				   .getBody();
	}
	
	public String fetchJSONObjectFromClaims(Claims claims) {
		JSONObject jsonObject = new JSONObject();
		claims.forEach((key,value)-> {
			jsonObject.put(key,value);
		});
		return jsonObject.toString();
	}
	public String fetchJSONObjectFromMap(Map<String,Object> claims) {
		JSONObject jsonObject = new JSONObject();
		claims.forEach((key,value)-> {
			jsonObject.put(key,value);
		});
		return jsonObject.toString();
	}
	public String getUsernameFromToken(String token) throws Exception {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) throws Exception {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws Exception {
		final Claims claims = verifyTokenAndSendClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(String userDetails) {
		Map<String, Object> claims = new HashMap<String,Object>();
		claims.put("tokenId",UUID.randomUUID().toString());
		doGenerateToken(claims, userDetails);
		return fetchJSONObjectFromMap(claims);
	}
	
	public Boolean isTokenExpired(String token) throws Exception {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(applicationPropertiesConfiguration.getJWT_TOKEN_VALIDITY()) * 1000))
				.signWith(SignatureAlgorithm.HS512, applicationPropertiesConfiguration.getSecret()).compact();
	}
}
