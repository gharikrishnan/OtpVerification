package com.angler.otpverification.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.angler.otpverification.R;
import com.angler.otpverification.adapter.ContactsAdapter;
import com.angler.otpverification.apiclient.APIService;
import com.angler.otpverification.apiclient.ApiUtils;
import com.angler.otpverification.database.DBHandler;
import com.angler.otpverification.response.ImageList2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


    public class Login_activity extends AppCompatActivity implements SearchView.OnQueryTextListener{

        SearchView searchViews;
        private APIService myApiServices;
        List<ImageList2.DataList> imageList2 = new ArrayList<>();
        ContactsAdapter contactsAdapter;
        RecyclerView rv;
        DBHandler dbHandler ;
        //String name = "HARI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_success);
        //hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

       // database handler class instance
        dbHandler = new DBHandler(Login_activity.this);

        searchViews = findViewById(R.id.searchView);

        //searchViews.setOnQueryTextListener(this);
        searchViews.setOnQueryTextListener(this);

        myApiServices = ApiUtils.getAPIService();
        rv = findViewById(R.id.recyclerView);
        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Log.i("authorization_check",loginData.getString("authorization", ""));

        Log.d("Hello","HELLO WORLD");

        if (isConnected()) {
            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
            tokenValidation(loginData.getString("authorization", ""));
        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            localDataRetrieve();

        }

    }
     //main function ended
     //Start the outer functions
        private void localDataRetrieve()
        {
            Log.i("NO_NETWORK","yes");
            imageList2 = dbHandler.listData();
            contactsAdapter = new ContactsAdapter(Login_activity.this,imageList2);
            rv.setAdapter(contactsAdapter);

        }


        private void tokenValidation(String authorization)
    {
        myApiServices.savePost2("Bearer " + authorization).enqueue(new Callback<ImageList2>() {

            @Override
            public void onResponse(@NonNull Call<ImageList2> call, @NonNull Response<ImageList2> response) {

                if(response.isSuccessful()) {

                    assert response.body() != null;

                     imageList2 = response.body().getData();
                     Log.e("RESPONSE_SIZE",""+imageList2.size());

                     contactsAdapter = new ContactsAdapter(Login_activity.this,imageList2);
                     rv.setAdapter(contactsAdapter);
                     rv.setHasFixedSize(true);


                    addData();


                }else
                    Log.d("Unsuccessful", "" + response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ImageList2> call, @NonNull Throwable t) {
                Log.d("Response_fail", "Authorization Fails  :    " +t.toString());
            }
        });
    }




        private void addData() {
            Log.d("SIZE", "" + imageList2.size());
            dbHandler.addNewCourse(imageList2);

        }

        public boolean isConnected()
        {
            boolean connected;
            try {
                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nInfo = cm.getActiveNetworkInfo();
                connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                return connected;
            } catch (Exception e) {
                Log.e("Connectivity Exception", e.getMessage());
            }
            return false;
        }
        public void onBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                //if user pressed "yes", then he is allowed to exit from application
                //finish();
                finishAffinity();


            });
            builder.setNegativeButton("No", (dialog, which) -> {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            });
            AlertDialog alert=builder.create();
            alert.show();
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText)
        {
            //contactsAdapter.filter(newText);
            filter(newText);
            return false;
        }

        private void filter(String text)
        {
            // creating a new array list to filter our data.
            ArrayList<ImageList2.DataList> filteredlist = new ArrayList<>();

            // running a for loop to compare elements.
            for (ImageList2.DataList item : imageList2)
            {
                // checking if the entered string matched with any item of our recycler view.
                if (item.getService_name().toLowerCase().contains(text.toLowerCase()))
                {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item);
                }
            }
            if (filteredlist.isEmpty()) {
                // if no item is added in filtered list we are
                // displaying a toast message as no data found.
                Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
            } else {
                // at last we are passing that filtered
                // list to our adapter class.
                contactsAdapter.filterList(filteredlist);
            }
        }

    }
