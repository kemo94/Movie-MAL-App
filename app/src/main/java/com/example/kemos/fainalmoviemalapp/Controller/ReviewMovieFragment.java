package com.example.kemos.fainalmoviemalapp.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kemos.fainalmoviemalapp.Model.FetchMovieTask;
import com.example.kemos.fainalmoviemalapp.R;

/**
 * Created by kemos on 16/04/16.
 */
public class ReviewMovieFragment extends Fragment {

    final  String MOVIE_REVIEW = "movie_review";
    ListView reviewsList;
    FetchMovieTask movieReviewsTask ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.review_view, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {

        reviewsList = (ListView) rootView.findViewById(R.id.reviewList);

    }

    public void populatListView(String movieId) {

        String MOVIE_REVIEWS_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?";
        movieReviewsTask = new FetchMovieTask(getActivity() , MOVIE_REVIEWS_URL , MOVIE_REVIEW  , reviewsList);
        movieReviewsTask.execute("");


    }


}
