package com.angler.otpverification.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.angler.otpverification.R;
import com.angler.otpverification.database.DBHandler;

public class Update_activity extends AppCompatActivity {
    final static  String TAG ="Update_activity";

    EditText updateTextView;
    Button save;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_activiy);
        //Track the activity
        Log.d(TAG,"START");
        Log.d(TAG,getIntent().getStringExtra("d"));
        Log.d("UPDATED_ID",getIntent().getStringExtra("id"));

        dbHandler = new DBHandler(this);
        //assign a values
        updateTextView = findViewById(R.id.updateTextView);
        save = findViewById(R.id.saveTextView);

        updateTextView.setText(getIntent().getStringExtra("d"));

        save.setOnClickListener(v -> {
            String service_name = updateTextView.getText().toString().trim();
            Log.d("UPDATED_DES",service_name);
            dbHandler.updateData(getIntent().getStringExtra("id"),service_name);
            Intent intent = new Intent(Update_activity.this,Login_activity.class);
            startActivity(intent);
            Toast.makeText(Update_activity.this,"data updated successful",Toast.LENGTH_SHORT).show();
        });


    }
}
