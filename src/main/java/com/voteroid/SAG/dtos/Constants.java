package com.voteroid.SAG.dtos;

public interface Constants {

	interface Exceptions{
		public static final String NO_LICENSE_USERID_RECIEVED = "No User Id is recieved to generate the license!!!";
		public static final String NO_LICENSE_DATA_RECIEVED = "No License Data is recieved!!! Please enter in Response body the proper arguments";
		public static final String NO_USER_NAME_RECIEVED = "No User Name of the user is recieved to generate the license!!!";
		public static final String NO_SUBSCRIPTION_DURATION_RECIEVED = "No Subscription Duration recieved to generate the license!!!";
		public static final String NO_API_PROVIDER_RECIEVED = "No API Provider data is Recieved!!!";
		public static final String SAG_UNAUTHENTICATED_ACCESS = "Invalid Access Key!!! Please Contact Voteroid";
		public static final String NO_API_ACCESS_LIST_RECIEVED = "No Access API List Recieved!!! Please read documentation of SAG API's";
	}
	
	interface Messages {
		public static final String INVALID_API_ID= "Recieved API ID is Invalid or Not Yet Registered !!!";
		public static final String API_BLOCKED_SUCCESSFULLY= "API Blocked Successfully for New Subscriptions !!!";
		public static final String NO_API_REGISTERED_BY_CLIENT_YET = "NO API'S Are Yet Registered for Your API Provider !!!";
		public static final String ALREADY_NOT_BLOCKED = "API is Not Already Blocked !!!";
		public static final String API_UN_BLOCKED_SUCCESSFULLY = "API Un-Blocked Successfully for New Subscriptions !!!";
	}
	
	interface General{
		public static final String DELIMITER=":";
	}
}
