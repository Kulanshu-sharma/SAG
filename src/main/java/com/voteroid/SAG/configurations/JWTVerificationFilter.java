package com.voteroid.SAG.configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.voteroid.SAG.entities.APITable;
import com.voteroid.SAG.exceptions.NoAPIExistInSAG;
import com.voteroid.SAG.repositories.APITblRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import reactor.core.publisher.Mono;

@Component
public class JWTVerificationFilter implements GatewayFilter{
	
	@Autowired
	JWTUtility jwtUtility;
	
	@Autowired
	APITblRepository repository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTVerificationFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
		final List<String> apiEndpoints = List.of("/home");

		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));

		if (isApiSecured.test(request)) {          //Requests that we want to authenticate(except home)
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);

				return ((ReactiveHttpOutputMessage) response).setComplete();
			}

			final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
			Claims claims = null;
			ServerHttpResponse response = null;
			try {
				claims = jwtUtility.verifyTokenAndSendClaims(token);
				//Checking API Access allowed or not as this token is already valid...
				String apiCall = request.getPath()+"";
				//Fetching Corresponding API ID for respective API Call with given Client Id
				APITable apiTable = repository.findByClientIdAndApiCall((int)claims.get("clientId"),apiCall);
				if(apiTable==null) {
					LOGGER.info(apiCall+ " request is Not yet registered with client "+claims.get("apiProvider"));
					response = (ServerHttpResponse) exchange.getResponse();
					response.setStatusCode(HttpStatus.NOT_FOUND);
					return ((ReactiveHttpOutputMessage) response).setComplete();
				}
				int apiId = apiTable.getApiId();
				
				@SuppressWarnings("unchecked")
				ArrayList<Integer> list = (ArrayList<Integer>) claims.get("accessTo");
				if(!list.contains(apiId)) {
					response = (ServerHttpResponse) exchange.getResponse();
					response.setStatusCode(HttpStatus.LOCKED);
					return ((ReactiveHttpOutputMessage) response).setComplete();
				}
			} catch (SignatureException ex) {
				response = (ServerHttpResponse) exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return ((ReactiveHttpOutputMessage) response).setComplete();
			} catch (MalformedJwtException ex) {
				response = (ServerHttpResponse) exchange.getResponse();
				response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
				return ((ReactiveHttpOutputMessage) response).setComplete();
			} catch (ExpiredJwtException ex) {
				response = (ServerHttpResponse) exchange.getResponse();
				response.setStatusCode(HttpStatus.REQUEST_TIMEOUT);
				return ((ReactiveHttpOutputMessage) response).setComplete();
			} catch (UnsupportedJwtException ex) {
				response = (ServerHttpResponse) exchange.getResponse();
				response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
				return ((ReactiveHttpOutputMessage) response).setComplete();
			} catch (IllegalArgumentException ex) {
				response = (ServerHttpResponse) exchange.getResponse();
				response.setStatusCode(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
				return ((ReactiveHttpOutputMessage) response).setComplete();
			} catch (Exception e) {
				response = (ServerHttpResponse) exchange.getResponse();
				response.setStatusCode(HttpStatus.BAD_GATEWAY);
				return ((ReactiveHttpOutputMessage) response).setComplete();	
			}
			exchange.getRequest().mutate().header("userData",jwtUtility.fetchJSONObjectFromClaims(claims)).build();
			LOGGER.info("Token Signature Verification Filter Passed Successfully");
		}
		else {       //Generate token if the request is of 'home'
			final String tokenClaims = jwtUtility.generateToken("Guest"); 
			exchange.getRequest().mutate().header("userData",tokenClaims);
		}

		return chain.filter(exchange);
	}
	
}
