package com.example.kemos.fainalmoviemalapp.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.Model.MovieOperations;
import com.example.kemos.fainalmoviemalapp.R;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.text.DecimalFormat;


public class DetailFragment extends Fragment implements ISecondaryFragment {

    private MovieOperations movieDBOperation;
    ImageView moviePoster ;
    TextView overView;
    TextView rate;
    RatingBar rating;
    MovieItem movieItem ;
    Float ratingValue;
    ImageButton btnAddFavorite;
    ImageButton btnAddWatchList;
    int isFavorite;
    int isWatchList ;
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setRetainInstance(true);
       if ( savedInstanceState != null )
           movieItem = savedInstanceState.getParcelable("movieItem");
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
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        return setView(rootView) ;
    }


    public void getTrailersAndReviews() {

        SimilarMoviesFragment similarMoviesFragment =  (SimilarMoviesFragment)
                getChildFragmentManager().findFragmentById(R.id.similar_movie_container);
        similarMoviesFragment.populatRecyclerView(movieItem.getMovieId());

        ReviewMovieFragment overviewMovieFragment =  (ReviewMovieFragment)
                getChildFragmentManager().findFragmentById(R.id.review_movie_container);
        overviewMovieFragment.populatListView(movieItem.getMovieId());

        TrailersMovieFragment trailersMovieFragment =  (TrailersMovieFragment)
                getChildFragmentManager().findFragmentById(R.id.trailer_movie_container);
        trailersMovieFragment.populatListView(movieItem.getMovieId());



    }

    void addWatchList() {
        if ( isWatchList == -1 ) {
            movieDBOperation.addMovie(movieItem.getMovieId(), movieItem.getTitle(), movieItem.getPosterURL(),
                    movieItem.getDate(), movieItem.getRate(), movieItem.getOverview());
            isWatchList = 0 ;
        }
        if ( isWatchList == 1 ) {
            movieDBOperation.updateWatchList(movieItem.getMovieId(), 0);
            btnAddWatchList.setImageResource(R.drawable.no_watch);
            isWatchList = 0 ;
        }
        else if ( isWatchList == 0 ) {
            movieDBOperation.updateWatchList(movieItem.getMovieId(), 1);
            btnAddWatchList.setImageResource(R.drawable.watch);
            isWatchList = 1 ;
        }
    }

    public View setView(View rootView){

        btnAddFavorite = (ImageButton) rootView.findViewById(R.id.addFavorite);
        btnAddWatchList =  (ImageButton) rootView.findViewById(R.id.addWatchList);
        moviePoster = (ImageView) rootView.findViewById(R.id.poster);
        rating = (RatingBar) rootView.findViewById(R.id.ratingBar);
        overView = (TextView) rootView.findViewById(R.id.overview);
        rate = (TextView) rootView.findViewById(R.id.rate);

        if ( movieItem != null )
            setMovieDataOnView();
        return rootView ;
    }

    void setMovieDataOnView(){

        isFavorite = movieDBOperation.checkIsFavorite(movieItem.getMovieId());
        isWatchList = movieDBOperation.checkIsWatchList(movieItem.getMovieId());

        if ( isFavorite == 1 )
            btnAddFavorite.setImageResource(R.drawable.button_pressed);

        if ( isWatchList == 1 )
            btnAddWatchList.setImageResource(R.drawable.watch);

        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavorite();
            }
        });
        btnAddWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWatchList();
            }
        });
        ratingValue = Float.parseFloat(movieItem.getRate());
        ratingValue /= 2 ;
        rating.setRating(ratingValue);

        rate.setText("Rate : " + new DecimalFormat("#.##").format(ratingValue) + "/5");
        String poster = movieItem.getPosterURL();
        String URL = "http://image.tmdb.org/t/p/w185" + poster;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;


        Picasso.with(getActivity()).load(URL).placeholder(R.drawable.load).resize(screenWidth/2,screenHeight/2).into(moviePoster);
        overView.setText(movieItem.getOverview());

    }

    void addFavorite() {
        if ( isFavorite == -1 ) {
            movieDBOperation.addMovie(movieItem.getMovieId(), movieItem.getTitle(), movieItem.getPosterURL(),
                    movieItem.getDate(), movieItem.getRate(), movieItem.getOverview());
            isFavorite = 0 ;
        }
        if ( isFavorite == 1 ) {
            movieDBOperation.updateFavoritesList(movieItem.getMovieId(), 0);
            btnAddFavorite.setImageResource(R.drawable.button_normal);
            isFavorite = 0 ;
        }
        else if ( isFavorite == 0 ) {
            movieDBOperation.updateFavoritesList(movieItem.getMovieId(), 1);
            btnAddFavorite.setImageResource(R.drawable.button_pressed);
            isFavorite = 1 ;
        }

    }
    @Override
    public void setMovieItem(MovieItem movieItem) {
        this.movieItem = movieItem ;
        setMovieDataOnView();
        getTrailersAndReviews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movieItem", movieItem);


    }

}
