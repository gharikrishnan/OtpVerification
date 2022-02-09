package com.angler.otpverification.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angler.otpverification.R;
import com.angler.otpverification.database.DBHandler;
import com.squareup.picasso.Picasso;

public class DetailView extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
   TextView description;
   ImageView imageViews;
   Button update , delete;
   DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Log.d(TAG,"Start_activity");
        dbHandler = new DBHandler(this);
        update = findViewById(R.id.updateTextView);
        delete = findViewById(R.id.deleteTextView);

        //get the value from login activity
        String descriptions = getIntent().getStringExtra("Description");
        int id = Integer.parseInt(getIntent().getStringExtra("ID"));

        Log.i("GET_ID",""+id);
        //set the onclick listener
        //update the new Data in SQLite Database
        update.setOnClickListener(v -> {
            Log.d("CLICK","click_update_button");
            Intent updateIntent = new Intent(DetailView.this, Update_activity.class);

            updateIntent.putExtra("id",""+id);
            updateIntent.putExtra("d",descriptions);
            startActivity(updateIntent);
        });

        delete.setOnClickListener(v -> {
            Log.d("CLICK", "click_delete_button");
            Toast.makeText(DetailView.this,"Details deleted",Toast.LENGTH_SHORT).show();
            dbHandler.deleteData(id);
            Intent intent = new Intent(DetailView.this,Login_activity.class);
            startActivity(intent);

        });

        getIncomingIntent();
    }



    private void getIncomingIntent() {
        Log.d(TAG,"Get_Intent_Value");
        if(getIntent().hasExtra("Description"))
        {
            Log.d(TAG,"Get_The_values");
         String descriptions  = getIntent().getStringExtra("Description");
         String URL = getIntent().getStringExtra("url");
         setIntentValues(descriptions,URL);
        }
    }

    private void setIntentValues(String descriptions,String url) {
        Log.d(TAG,"Set_The_Values");
        description = findViewById(R.id.textView);
        imageViews = findViewById(R.id.imageView);
        description.setText(descriptions);

        Picasso.get().load(url).into(imageViews);

    }
}