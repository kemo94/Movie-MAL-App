package com.example.kemos.fainalmoviemalapp.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kemos.fainalmoviemalapp.Controller.DetailActivity;
import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    public static  ArrayList<MovieItem> movieItemArray;
    public static Context context;

    public RecyclerViewAdapter(Context context, ArrayList<MovieItem> arrayList) {
        this.context = context;
        this.movieItemArray = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != movieItemArray ? movieItemArray.size() : 0);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
         RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;

        String url =  "http://image.tmdb.org/t/p/w185"+ movieItemArray.get(position).getPosterURL();
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        Picasso.with(context).load(url).placeholder(R.drawable.load).resize(screenWidth/2,screenHeight/2).into(mainHolder.getImageview());

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.h_grid_cell, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;



    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public ImageView imageview;
      //  public TextView textView ;
        public RecyclerViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageview = (ImageView) view
                    .findViewById(R.id.poster);
          //  this.textView = (TextView) view.findViewById(R.id.title);

        }


     /*   public TextView getTextView() {
            return textView;
        }
       */

        public ImageView getImageview() {
            return imageview;
        }

        @Override
        public void onClick(View view) {

            final Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("movieItem", movieItemArray.get(getPosition()));
            context.startActivity(intent);
        }
    }

}
