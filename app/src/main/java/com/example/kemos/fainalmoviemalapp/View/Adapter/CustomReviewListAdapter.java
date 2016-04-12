package com.example.kemos.fainalmoviemalapp.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.R;

import java.util.ArrayList;

public class CustomReviewListAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    private final ArrayList<MovieItem> movieItemArray;

    public CustomReviewListAdapter(Context c, ArrayList<MovieItem> movieItemArray) {

        this.context = c;
        this.movieItemArray = movieItemArray;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(R.layout.review_list_cell, null);

         TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText(movieItemArray.get(position).getAuthorReviews() );

         TextView content = (TextView) convertView.findViewById(R.id.content);
        content.setText(movieItemArray.get(position).getContentReviews() );

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