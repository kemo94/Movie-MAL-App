package com.example.kemos.moviemalapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.kemos.moviemalapp.Model.MovieItem;
import com.example.kemos.moviemalapp.Model.MovieOperations;
import com.example.kemos.moviemalapp.R;
import com.example.kemos.moviemalapp.View.Adapter.CustomGridAdapter;

import java.sql.SQLException;
import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    private MovieOperations movieDBOperation;
    ISecondaryFragment secondaryFragment ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        movieDBOperation = new MovieOperations(getActivity());
        try {
            movieDBOperation.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final ArrayList<MovieItem> movieItemArray = movieDBOperation.getFavoritesMovies();
        final Intent intent = new Intent(getActivity(), DetailActivity.class);
        GridView gridview = (GridView) rootView.findViewById(R.id.grid);
        gridview.setAdapter(new CustomGridAdapter(getActivity(), movieItemArray));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if ( secondaryFragment != null) {
                    secondaryFragment.setMovieItem(movieItemArray.get(position));
                    getActivity().setTitle(movieItemArray.get(position).getTitle());
                }
                else{
                    intent.putExtra("title", movieItemArray.get(position).getTitle());
                    intent.putExtra("rate", movieItemArray.get(position).getRate());
                    intent.putExtra("poster", movieItemArray.get(position).getPosterURL());
                    intent.putExtra("id", movieItemArray.get(position).getMovieId());
                    intent.putExtra("date", movieItemArray.get(position).getDate().substring(0, 4));
                    intent.putExtra("overview", movieItemArray.get(position).getOverview());
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

    public void setSecondaryFragment(ISecondaryFragment secondaryFragment) {
        this.secondaryFragment = secondaryFragment;
    }
    @Override
    public void onStart() {
        super.onStart();

    }


}
