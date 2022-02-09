package com.angler.otpverification.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.angler.otpverification.activity.DetailView;
import com.angler.otpverification.R;
import com.angler.otpverification.response.ImageList2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>
{

    List<ImageList2.DataList> imageList2;
    Context context;
    public static final  String TAG = "Recycle_Adapter";
    //private final ArrayList<ImageList2.DataList> arrayList;



    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public ImageView imageView;
        CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            //assign the xml view id java variable
            title =  view.findViewById(R.id.title);
            imageView = view.findViewById(R.id.item_image);
            cardView = view.findViewById(R.id.card_view);

        }
    }

    public ContactsAdapter(Context context ,List<ImageList2.DataList> imageList2)

    {
        this.context = context;
        this.imageList2 = imageList2;
        Log.d("received_size",""+imageList2.size());

    }
    public void filterList(ArrayList<ImageList2.DataList> filterList) {
        // below line is to add our filtered
        // list in our course array list.
        imageList2 = filterList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType )
            {

              View itemView = LayoutInflater.from(parent.getContext())
                      .inflate(R.layout.card_layout,parent,false);
                return new MyViewHolder(itemView);


            }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        ImageList2.DataList images = imageList2.get(position);

        holder.title.setText(images.getService_name());
        Log.d("FIRST",""+imageList2.get(position).get_id());
        Log.i("ID",""+imageList2.get(position).getId());
        //set the values for xml view field
        Log.d("IMAGE",""+imageList2.get(position).getImage_dir()+imageList2.get(position).getImage_name());

        String URL = imageList2.get(position).getImage_dir() + imageList2.get(position).getImage_name();
        //convert image using picasso library
        Picasso.get().load(imageList2.get(position).getImage_dir()+imageList2.get(position).getImage_name()).into(holder.imageView);

        Log.d("received_size",""+imageList2.size());

        //user click the view after goto next activity
        holder.cardView.setOnClickListener(v -> {
            Log.d(TAG,"OnClickSuccess");
            Toast.makeText(context, imageList2.get(position).getImage_name(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DetailView.class);
            //intent.putExtra("ImageURL",);
            //send the data send to next activity using intent
            intent.putExtra("ID",""+images.getId());
            intent.putExtra("Description",images.getService_name());
            intent.putExtra("url",URL);
            //start the intent
            context.startActivity(intent);

        });

    }



    @Override
    public int getItemCount()
    {
        return imageList2.size();

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
