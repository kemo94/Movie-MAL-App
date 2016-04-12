package com.example.kemos.fainalmoviemalapp.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

    GridView gridview;
    ArrayList<MovieItem> movieItemArray ;
    FetchMovieTask movieTask;
    boolean chosen = false ;
    ISecondaryFragment secondaryFragment ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState != null )
            movieItemArray = savedInstanceState.getParcelableArrayList("moviesItem");
        else
        movieItemArray = new ArrayList<MovieItem>();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_fragment, menu);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

           final Intent intent = new Intent(getActivity(), DetailActivity.class);
           gridview = (GridView) rootView.findViewById(R.id.grid);
           gridview.setAdapter( new CustomGridAdapter(getActivity(),movieItemArray));
           gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosen = true ;
                    if (  movieTask.getMovieDataArray() != null )
                          movieItemArray = movieTask.getMovieDataArray();
                if (secondaryFragment != null && movieItemArray.size() != 0 ) {

                        secondaryFragment.setMovieItem(movieItemArray.get(position));
                        getActivity().setTitle(movieItemArray.get(position).getTitle() + " ("
                                + movieItemArray.get(position).getDate().substring(0, 4) + ")");
                    }
                     else if ( movieItemArray.size() != 0 ) {


                        intent.putExtra("movieItem",  movieItemArray.get(position));
                        startActivity(intent);
                    }
             }


        });
        updateMovies();

        return rootView;
    }
    public  void delay(){

        final Handler h = new Handler();
        final Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                if ( movieTask.getMovieDataArray() != null )
                movieItemArray = movieTask.getMovieDataArray();
                Log.d("TimerExample", "Going for... " + chosen );

                if ( movieItemArray.size() != 0 && !chosen) {
                    secondaryFragment.setMovieItem(movieItemArray.get(0));
                    getActivity().setTitle(movieItemArray.get(0).getTitle() + " ("
                            + movieItemArray.get(0).getDate().substring(0, 4) + ")");
                    chosen = true ;
                }

                if ( !chosen )
                h.postDelayed(this,0);
            }
        };
        h.postDelayed(runnable,1000);
        if ( chosen)
        h.removeCallbacks(runnable);

    }
    public  void updateMovies(){

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String unitType = sharedPrefs.getString(getString(R.string.pref_units_key), getString(R.string.pref_units_pop));
        String MOVIE_BASE_URL  ;
        if (unitType.equals(getString(R.string.pref_units_pop)))
            MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
        else
            MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";

         movieTask = new FetchMovieTask(getActivity() , MOVIE_BASE_URL , "movie_data"  , gridview);
         movieTask.execute("");

         if (secondaryFragment != null)
         delay();
    }

    public void setSecondaryFragment(ISecondaryFragment secondaryFragment) {
        this.secondaryFragment = secondaryFragment;
    }

    @Override
    public void onStart() {
        super.onStart();

       updateMovies();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("moviesItem", movieItemArray);
    }
}
