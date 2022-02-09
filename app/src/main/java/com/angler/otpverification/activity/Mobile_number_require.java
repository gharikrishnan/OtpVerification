package com.angler.otpverification.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.angler.otpverification.R;
import com.angler.otpverification.apiclient.APIService;
import com.angler.otpverification.apiclient.ApiUtils;
import com.angler.otpverification.response.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mobile_number_require extends AppCompatActivity {
    EditText phoneNumber;
    Button submit;
    public APIService myAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_verification);
        phoneNumber = findViewById(R.id.RegMobile);
        submit = findViewById(R.id.submit);
        myAPIService = ApiUtils.getAPIService();
       Log.d("MOBILE","WELCOME");
        submit.setOnClickListener(view -> {
            String mobile = phoneNumber.getText().toString().trim();
            try {


                if (!TextUtils.isEmpty(mobile) && mobile.length()==10) {
                    sendPost(mobile);
                } else {
                    phoneNumber.setError("Enter Mobile Number");
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        });

    }

    private void sendPost(String mobile) {

        myAPIService.savePost(mobile).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response)
            {

                if(response.isSuccessful()) {
                    assert response.body() != null;
                    Log.i("Successes", "id  :   " + response.body().getData().getId());
                    Log.i("Successes", "mobile  :   " + response.body().getData().getMobile());
                    Log.i("Successes", "Otp  :  " + response.body().getData().getOtp());

                    Log.i("Successes", "success  :  " + response.body().getSuccess());
                    Log.i("Successes", "message  :  " + response.body().getMessage());

                    Toast.makeText(Mobile_number_require.this,""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Mobile_number_require.this,OtpValidation.class);
                    intent.putExtra("MOBILE",mobile);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Example> call, @NonNull Throwable t) {
                Log.e("Errors", "Unable to submit post to API." +t.toString());
            }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //if user pressed "yes", then he is allowed to exit from application
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel();
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
}
