package com.example.kemos.fainalmoviemalapp.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kemos.fainalmoviemalapp.Model.FetchMovieTask;
import com.example.kemos.fainalmoviemalapp.R;

/**
 * Created by kemos on 16/04/16.
 */
public class SimilarMoviesFragment extends Fragment {
    private static RecyclerView recyclerView;

    FetchMovieTask similarMoviesTask ;
    public static final String SIMILAR_MOVIES = "SIMILAR_MOVIES";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.similar_view, container, false);
        initViews(rootView);
        return rootView;
    }

    // Initialize the view
    private void initViews(View rootView) {
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }


    // populate the list view by adding data to arraylist
    public void populatRecyclerView(String movieId) {
        String SIMILAR_MOVIES_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/similar?";
        similarMoviesTask = new FetchMovieTask(getActivity() , SIMILAR_MOVIES_URL , SIMILAR_MOVIES   , recyclerView);
        similarMoviesTask.execute("");
       // adapter.notifyDataSetChanged();

    }


}
