package com.example.kemos.moviemalapp.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.kemos.moviemalapp.Model.FetchMovieTask;
import com.example.kemos.moviemalapp.Model.MovieItem;
import com.example.kemos.moviemalapp.R;
import com.example.kemos.moviemalapp.View.Adapter.CustomGridAdapter;

import java.util.ArrayList;


public class MovieFragment extends Fragment {

    GridView gridview;
    ArrayList<MovieItem> movieItemArray ;
    FetchMovieTask movieTask;

    ISecondaryFragment secondaryFragment ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                movieItemArray = movieTask.getMovieDataArray();

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
        updateMovies();

        return rootView;
    }

    public  void updateMovies(){

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String unitType = sharedPrefs.getString(getString(R.string.pref_units_key), getString(R.string.pref_units_pop));
        String MOVIE_BASE_URL  ;
        if (unitType.equals(getString(R.string.pref_units_pop)))
            MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
        else
            MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";

         movieTask = new FetchMovieTask(getActivity() , MOVIE_BASE_URL , "movie data"  , gridview);
         movieTask.execute("");
    }

    public void setSecondaryFragment(ISecondaryFragment secondaryFragment) {
        this.secondaryFragment = secondaryFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();

    }


}