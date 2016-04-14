package com.example.kemos.fainalmoviemalapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kemos.fainalmoviemalapp.Model.MovieItem;
import com.example.kemos.fainalmoviemalapp.R;

public class DetailActivity extends AppCompatActivity {

    Bundle extras ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        extras = getIntent().getExtras();
         if ( extras != null ) {
             MovieItem movieItem  =  extras.getParcelable("movieItem");
             setTitle(movieItem.getTitle() + " (" + movieItem.getDate().substring(0,4) + ")");

             DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.movie_detail);
             detailFragment.setMovieItem(movieItem);
         }
    }

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


