package com.example.kemos.fainalmoviemalapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kemos.fainalmoviemalapp.Model.CheckDeviceStatus;
import com.example.kemos.fainalmoviemalapp.Model.MovieOperations;
import com.example.kemos.fainalmoviemalapp.R;

import java.sql.SQLException;

public class WatchListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            setContentView(R.layout.activity_search);
        else {
            if (CheckDeviceStatus.isNetworkAvailable(this)) {
                setContentView(R.layout.activity_watch_list);
                onRetainNonConfigurationInstance();
                if (!checkWatchList()) {
                    Toast.makeText(this, R.string.no_watch_list, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MainActivity.class));
                }

                if (savedInstanceState == null) {

                    WatchListFragment watchListFragment = (WatchListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movies);
                    DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.movie_details_container);
                    watchListFragment.setSecondaryFragment(detailFragment);
                }
            } else
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    boolean checkWatchList(){
        MovieOperations movieDBOperation = new MovieOperations(this);
        try {
            movieDBOperation.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (   movieDBOperation.getWatchList().size() > 0  )
            return true ;

        return  false ;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_favorite_list) {
            startActivity(new Intent(this,FavoritesActivity.class));
            return true;
        }
        if (id == R.id.action_watch_list) {
            startActivity(new Intent(this,WatchListActivity.class));
            return true;
        }

        if (id == R.id.action_activity_home) {
            startActivity(new Intent(this,MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
