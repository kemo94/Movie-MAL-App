package com.example.kemos.fainalmoviemalapp.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.R;

import java.util.ArrayList;


public class CustomTrailerListAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    private final ArrayList<MovieItem> movieItemArray;

    public CustomTrailerListAdapter(Context c, ArrayList<MovieItem> movieItemArray) {

        this.context = c;
        this.movieItemArray = movieItemArray;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(R.layout.trailer_list_cell, null);

        ImageView trailerImg = (ImageView) convertView.findViewById(R.id.play);
        trailerImg.setImageResource(R.drawable.play);

        TextView trailerName = (TextView) convertView.findViewById(R.id.trailerName);
        trailerName.setText(movieItemArray.get(position).getTrailerName());


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