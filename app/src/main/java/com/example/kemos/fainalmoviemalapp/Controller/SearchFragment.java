package com.example.kemos.fainalmoviemalapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.kemos.fainalmoviemalapp.Model.FetchMovieTask;
import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.R;
import com.example.kemos.fainalmoviemalapp.View.Adapter.CustomGridAdapter;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    final String MOVIE_DATA = "movie_data";
    GridView gridview;
    ArrayList<MovieItem> movieItemArray;
    FetchMovieTask movieTask;
    int curPosition = 0 ;
    String query ;
    boolean chosen = false;
    ISecondaryFragment secondaryFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            movieItemArray = savedInstanceState.getParcelableArrayList("moviesItem");

        else
        movieItemArray = new ArrayList<MovieItem>();
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        setRetainInstance(true);

        return setView(rootView);
    }

    View setView(View rootView) {
        final Intent intent = new Intent(getActivity(), DetailActivity.class);
        gridview = (GridView) rootView.findViewById(R.id.grid);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosen = true;
                curPosition = position ;
                if (movieTask.getMovieDataArray() != null)
                    movieItemArray = movieTask.getMovieDataArray();
                if (secondaryFragment != null && movieItemArray.size() != 0) {

                    secondaryFragment.setMovieItem(movieItemArray.get(position));
                    getActivity().setTitle(movieItemArray.get(position).getTitle() + " ("
                            + movieItemArray.get(position).getDate().substring(0, 4) + ")");
                } else if (movieItemArray.size() != 0) {
                    intent.putExtra("movieItem", movieItemArray.get(position));
                    startActivity(intent);
                 }
            }


        });
        if ( movieItemArray.size() > 0 )
            gridview.setAdapter(new CustomGridAdapter(getActivity(), movieItemArray));


        return rootView;
    }

    public void delay() {

        final Handler h = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("TimerExample", "Going for... " + chosen);
                if ( !movieTask.checkSearchResult() ) {
                    Toast.makeText(getActivity(), "No such movie with this name", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity() ,MainActivity.class));
                    chosen = true;

                }
                if ( movieTask.getMovieDataArray() != null)
                    movieItemArray = movieTask.getMovieDataArray();


                if ( movieItemArray.size() > 0 && secondaryFragment != null) {

                        secondaryFragment.setMovieItem(movieItemArray.get(0));
                        getActivity().setTitle(movieItemArray.get(0).getTitle() + " ("
                                + movieItemArray.get(0).getDate().substring(0, 4) + ")");
                        chosen = true;

                }

                if (!chosen)
                    h.postDelayed(this, 0);
            }
        };
        h.postDelayed(runnable, 1000);
        if (chosen)
            h.removeCallbacks(runnable);

    }

    public void updateMovies() {

        String SEARCH_MOVIE_URL="http://api.themoviedb.org/3/search/movie?query=" + query;


        movieTask = new FetchMovieTask(getActivity(), SEARCH_MOVIE_URL, MOVIE_DATA, gridview);
        movieTask.execute("");

            delay();
    }

    public void setSecondaryFragment(ISecondaryFragment secondaryFragment) {
        this.secondaryFragment = secondaryFragment;
    }
    public void setQuery(String query){
        this.query = query ;
        updateMovies();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if ( movieItemArray.size() > 0 ) {
            outState.putParcelableArrayList("moviesItem", movieItemArray);
            outState.putParcelable("movieItem", movieItemArray.get(curPosition));
        }
        super.onSaveInstanceState(outState);
    }

}
