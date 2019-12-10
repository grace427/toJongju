package com.example.iamas.travelfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    int i=0;
    ImageView imgView;
    final static String TAG = "MainActivity";

    private static View.OnClickListener onClickListener;
    Context context;
    ArrayList<Data> list;
   // Hashtable<Integer, Data> item;

    public MyAdapter(Context context, ArrayList<Data> list, View.OnClickListener onClick) {
        super();
        this.context = context;
        this.list = list;
        onClickListener = onClick;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtView.setText(list.get(position).getTitle());
        loadImageInBackground(list.get(position).getFirstImage(), context);
       // Log.d(TAG, list.get(position).getTitle()+"   "+list.get(position).getFirstImage()+"   "+(i++));
        holder.rootView.setTag(position);


//        Uri uri = Uri.parse(item.get(position).getFirstImage());
//        holder.imgView.setImageURI(uri);
//        holder.txtView.setText(item.get(position).getTitle());

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
         return list == null ? 0: list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

      //  public ImageView imgView;
        public TextView txtView;
        public View rootView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.txtView);
            imgView = itemView.findViewById(R.id.imgView);
            rootView = itemView;

            itemView.setClickable(true);
            itemView.setEnabled(true);
            itemView.setOnClickListener(onClickListener);

        }
    }

    public Data TourData(int position){

        return list != null ? list.get(position) : null;
    }

    public void loadImageInBackground(String str, Context context) {


        Target target = new Target() {

            @Override
            public void onPrepareLoad(Drawable arg0) {


        }

            @Override
            public void onBitmapLoaded(Bitmap arg0, Picasso.LoadedFrom arg1) {

                imgView.setImageBitmap(arg0);
            }

            @Override
            public void onBitmapFailed(Drawable arg0) {
                // TODO Auto-generated method stub
            }
        };

        Picasso.with(context)
                .load(str)
                .into(target);
    }
}


