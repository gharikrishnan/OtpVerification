package com.angler.otpverification.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.angler.otpverification.R;
import com.angler.otpverification.apiclient.APIService;
import com.angler.otpverification.apiclient.ApiUtils;
import com.angler.otpverification.response.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtpValidation extends AppCompatActivity {
    EditText otpNumber;
    Button submit;
     private APIService myApiServices;
     static  String mobile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_validation);

        otpNumber = findViewById(R.id.tvOtp);
        submit = findViewById(R.id.finalSubmit);
        myApiServices = ApiUtils.getAPIService();

        //get mobile key value from mobile required activity
        Bundle bundle=getIntent().getExtras();
        mobile = bundle.getString("MOBILE");
        Log.d("MobileNum",mobile);

        submit.setOnClickListener(view ->

                validateOtp());
    }
    public void validateOtp()
    {
        String otp = otpNumber.getText().toString().trim();
        if(!TextUtils.isEmpty(otp) && otp.length() == 6)
            sendOTP(otp);
        else
        {
            otpNumber.setError("Enter Valid OTP");
        }
    }

    public void sendOTP(String otp) {

        myApiServices.savePost1(mobile,otp).enqueue(new Callback<ResponseData>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {

                if(response.isSuccessful()) {
                    assert response.body() != null;
                    Log.i("Successes", "customer_id     :   " + response.body().getData().getCustomer_id());
                    Log.i("Successes", "authorization   :   " + response.body().getData().getAuthorization());
                    Log.i("Successes", "refreshToken    :   " + response.body().getData().getRefreshToken());
                    Log.i("Successes", "mobile          :   " + response.body().getData().getMobile());
                    Log.i("Successes", "is_profile_completed  :   " + response.body().getData().is_profile_completed());
                    Log.i("Successes", "success        :   " + response.body().getSuccess());
                    Log.i("Successes", "message        :   " + response.body().getMessage());

                    String LoggedSuccess =  response.body().getMessage();

                    Toast.makeText(OtpValidation.this, LoggedSuccess, Toast.LENGTH_SHORT).show();

                    //shared preference key
                    SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    loginData.edit().putString("authorization",response.body().getData().getAuthorization()).apply();

                    Log.d("verification",loginData.getString("authorization",""));

                    Intent intent = new Intent(OtpValidation.this, Login_activity.class);
                    //intent.putExtra("authorization",Authorization);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t)
            {
                Log.e("Errors", "Unable to submit post to API." +t.toString());
            }
        });
    }

}

