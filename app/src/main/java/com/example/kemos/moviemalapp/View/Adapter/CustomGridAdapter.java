package com.example.kemos.moviemalapp.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kemos.moviemalapp.Model.MovieItem;
import com.example.kemos.moviemalapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    private final ArrayList<MovieItem> movieItemArray;

    public CustomGridAdapter(Context c,ArrayList<MovieItem> movieItemArray ) {
        this.context = c;
        this.movieItemArray = movieItemArray;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_cell, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.poster);
        String url =  "http://image.tmdb.org/t/p/w185"+ movieItemArray.get(position).getPosterURL();

        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        Picasso.with(context).load(url).placeholder(R.drawable.load).resize(screenWidth/2,screenHeight/2).into(imageView);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        String movieTitle = movieItemArray.get(position).getTitle() ;
        if ( movieTitle.indexOf(":") != -1)
            movieTitle = movieTitle.substring(0 , movieTitle.indexOf(":"));
        title.setText(movieTitle);
        return convertView;
    }


    @Override
    public int getCount() {
        return movieItemArray.size();
    }

    @Override
    public Object getItem(int position) {
        return movieItemArray.get(position).getPosterURL();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}