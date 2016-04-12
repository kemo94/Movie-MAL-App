package com.example.kemos.fainalmoviemalapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.Model.MovieOperations;
import com.example.kemos.fainalmoviemalapp.R;
import com.example.kemos.fainalmoviemalapp.View.Adapter.CustomGridAdapter;

import java.sql.SQLException;
import java.util.ArrayList;


public class WatchListFragment extends Fragment {
    ArrayList<MovieItem> movieItemArray;
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


        movieItemArray = movieDBOperation.getWatchList();
        final Intent intent = new Intent(getActivity(), DetailActivity.class);

        if ( movieItemArray.size() == 0 ){
            Toast.makeText(getActivity() , "No watch list movies , please choose some movies !",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(),MainActivity.class));
        }
        GridView gridview = (GridView) rootView.findViewById(R.id.grid);
        gridview.setAdapter(new CustomGridAdapter(getActivity(), movieItemArray));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if ( secondaryFragment != null) {
                    secondaryFragment.setMovieItem(movieItemArray.get(position));
                    getActivity().setTitle(movieItemArray.get(position).getTitle() + " ("
                                + movieItemArray.get(position).getDate().substring(0,4) + ")");

                }
                else if ( movieItemArray.size() != 0 ) {
                        intent.putExtra("movieItem",  movieItemArray.get(position));
                        startActivity(intent);
                }
            }
        });
        return rootView;
    }

    public void setSecondaryFragment(ISecondaryFragment secondaryFragment) {
        this.secondaryFragment = secondaryFragment;
        if ( secondaryFragment != null && movieItemArray.size() != 0 ) {
            secondaryFragment.setMovieItem(movieItemArray.get(0));
            getActivity().setTitle(movieItemArray.get(0).getTitle() + " ("
                    + movieItemArray.get(0).getDate().substring(0,4) + ")");
        }
    }
    @Override
    public void onStart() {
        super.onStart();

    }


}
