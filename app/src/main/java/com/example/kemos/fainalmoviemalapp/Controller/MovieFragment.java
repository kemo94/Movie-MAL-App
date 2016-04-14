package com.example.kemos.fainalmoviemalapp.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.kemos.fainalmoviemalapp.Model.FetchMovieTask;
import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.R;
import com.example.kemos.fainalmoviemalapp.View.Adapter.CustomGridAdapter;

import java.util.ArrayList;


public class MovieFragment extends Fragment {

    final String MOVIE_DATA = "movie_data";
    GridView gridview;
    ArrayList<MovieItem> movieItemArray;
    FetchMovieTask movieTask;
    int curPosition = 0 ;

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

        else
            updateMovies();

        return rootView;
    }

    public void delay() {

        final Handler h = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
            //    Log.d("TimerExample", "Going for... " + chosen);
                if ( movieTask.getMovieDataArray() != null)
                    movieItemArray = movieTask.getMovieDataArray();


                if ( movieItemArray.size() > 0 ) {

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

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sharedPrefsString = sharedPrefs.getString(getString(R.string.pref_units_key), getString(R.string.pref_units_pop));
        String MOVIE_BASE_URL;
        if (sharedPrefsString.equals(getString(R.string.pref_units_pop)))
            MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
        else
            MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";

        movieTask = new FetchMovieTask(getActivity(), MOVIE_BASE_URL, MOVIE_DATA, gridview);
        movieTask.execute("");
        if (secondaryFragment != null)
            delay();
    }

    public void setSecondaryFragment(ISecondaryFragment secondaryFragment) {
        this.secondaryFragment = secondaryFragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if ( movieItemArray.size() > 0 ) {
            outState.putParcelableArrayList("moviesItem", movieItemArray);
            outState.putParcelable("movieItem", movieItemArray.get(curPosition));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {

        updateMovies();
        super.onResume();
    }

}
