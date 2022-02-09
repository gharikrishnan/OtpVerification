package com.angler.otpverification.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.angler.otpverification.activity.Mobile_number_require;
import com.angler.otpverification.activity.Login_activity;
import com.angler.otpverification.R;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash_screen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        Log.e("ver",loginData.getString("authorization",""));

        Log.e("153","123");

       if(!loginData.getString("authorization", "").isEmpty())
       {
           Intent intent = new Intent(Splash_screen.this, Login_activity.class);
          startActivity(intent);
        }
          else{
            new Handler().postDelayed(() ->
            {
                Intent intent = new Intent(Splash_screen.this, Mobile_number_require.class);
                startActivity(intent);
                finish();
            }, 2000);
       }


    }
}
