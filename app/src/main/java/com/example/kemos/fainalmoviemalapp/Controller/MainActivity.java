package com.example.kemos.fainalmoviemalapp.Controller;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kemos.fainalmoviemalapp.Model.CheckDeviceStatus;
import com.example.kemos.fainalmoviemalapp.R;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        if ( savedInstanceState != null )
            setContentView(R.layout.activity_main);
        else{
            if (CheckDeviceStatus.isNetworkAvailable(this)) {

                setContentView(R.layout.activity_main);
                onRetainNonConfigurationInstance();
                if (savedInstanceState == null) {
                    MovieFragment movieFragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movies);
                    DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.movie_details_container);
                    movieFragment.setSecondaryFragment(detailFragment);

                }
            } else
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);

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
