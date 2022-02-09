package com.angler.otpverification.apiclient;

import com.angler.otpverification.response.Example;
import com.angler.otpverification.response.ImageList2;
import com.angler.otpverification.response.ResponseData;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIService {
    @POST("api/v3/customer/registerMobile")
    @FormUrlEncoded
    Call<Example> savePost(@Field("mobile") String mobile);

    @POST("api/v3/customer/verifyOtp")
    @FormUrlEncoded
    Call<ResponseData> savePost1(@Field("mobile") String mobile , @Field("otp") String otp);

    @GET("api/v3/customer/services/list")
    Call<ImageList2> savePost2(@Header("Authorization") String Token);
}
