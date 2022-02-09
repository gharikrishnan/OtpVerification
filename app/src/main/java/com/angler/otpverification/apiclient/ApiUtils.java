package com.angler.otpverification.apiclient;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://54.160.142.9:8081/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }
}
