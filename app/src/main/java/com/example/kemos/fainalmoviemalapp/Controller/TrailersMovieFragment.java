package com.example.kemos.fainalmoviemalapp.Controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kemos.fainalmoviemalapp.Model.CheckDeviceStatus;
import com.example.kemos.fainalmoviemalapp.Model.FetchMovieTask;
import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.R;

import java.util.ArrayList;

/**
 * Created by kemos on 16/04/16.
 */
public class TrailersMovieFragment extends Fragment {

    final  String MOVIE_TRAILER = "movie_trailer";
    ListView trailersList;
    ArrayList<MovieItem> movieTrailersArray;
    FetchMovieTask movieTrailersTask ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.trailer_view, container, false);
        initViews(rootView);
        return rootView;
    }

    // Initialize the view
    private void initViews(View rootView) {

        trailersList = (ListView) rootView.findViewById(R.id.trailerList);
        trailersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movieTrailersArray = movieTrailersTask.getMovieDataArray();
                if (CheckDeviceStatus.isNetworkAvailable(getActivity())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + movieTrailersArray.get(position).getTrailerId()));
                    startActivity(intent);
                }
                else
                    Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void populatListView(String movieId) {

        String MOVIE_TRAILER_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?";
        movieTrailersTask = new FetchMovieTask(getActivity() , MOVIE_TRAILER_URL , MOVIE_TRAILER  , trailersList);
        movieTrailersTask.execute("");

    }


}
