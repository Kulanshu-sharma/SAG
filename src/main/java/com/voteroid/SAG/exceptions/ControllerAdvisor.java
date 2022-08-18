package com.voteroid.SAG.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.voteroid.SAG.dtos.Constants;
import com.voteroid.SAG.dtos.ExceptionFieldsDTO;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{

	@ExceptionHandler(NoLicenseUserIdRecieved.class)
    public ResponseEntity<Object> handleNoLicenseUserIdRecievedException(NoLicenseUserIdRecieved ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),Constants.Exceptions.NO_LICENSE_USERID_RECIEVED);
         return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NoLicenseDataRecieved.class)
    public ResponseEntity<Object> handleNoLicenseDataRecievedException(NoLicenseDataRecieved ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),Constants.Exceptions.NO_LICENSE_DATA_RECIEVED);
         return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NoLicenseUserNameRecieved.class)
    public ResponseEntity<Object> handleNoLicenseUserNameRecievedException(NoLicenseUserNameRecieved ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),Constants.Exceptions.NO_USER_NAME_RECIEVED);
         return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NoSubscriptionDurationRecieved.class)
    public ResponseEntity<Object> handleNoLicenseSubDurationRecievedException(NoSubscriptionDurationRecieved ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),Constants.Exceptions.NO_SUBSCRIPTION_DURATION_RECIEVED);
         return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NoAPIProviderRecieved.class)
    public ResponseEntity<Object> handleNoLicenseAPIProviderRecievedException(NoAPIProviderRecieved ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),Constants.Exceptions.NO_API_PROVIDER_RECIEVED);
         return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(SAGAccessAuthenticationFailed.class)
    public ResponseEntity<Object> handleInvalidSAGAuthenticationException(SAGAccessAuthenticationFailed ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),Constants.Exceptions.SAG_UNAUTHENTICATED_ACCESS);
         return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(NoAccessAPIListRecieved.class)
    public ResponseEntity<Object> handleNoLicenseUserIdRecievedException(NoAccessAPIListRecieved ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),Constants.Exceptions.NO_API_ACCESS_LIST_RECIEVED);
         return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NoAPIExistInSAG.class)
    public ResponseEntity<Object> handleNoLicenseUserIdRecievedException(NoAPIExistInSAG ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),"OOPS :( API is Not registered in SAG!!!");
         return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(NoClientIdRecieved.class)
    public ResponseEntity<Object> handleNoLicenseUserIdRecievedException(NoClientIdRecieved ex) {
         ExceptionFieldsDTO body = new ExceptionFieldsDTO(LocalDateTime.now(),"OOPS :( No Client Id Recieved!!!");
         return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}
