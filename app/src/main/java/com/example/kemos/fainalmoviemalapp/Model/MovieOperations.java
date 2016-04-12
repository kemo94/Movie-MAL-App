package com.example.kemos.fainalmoviemalapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;


public class MovieOperations {

    private DataBaseWrapper dbHelper;

//    private String[] Movie_TABLE_COLUMNS = { DataBaseWrapper.Movie_ID, DataBaseWrapper.Movie_TITLE  ,  DataBaseWrapper.Movie_POSTER_URL  };

    private SQLiteDatabase database;

    public MovieOperations(Context context) {

        dbHelper = new DataBaseWrapper(context);

    }

    public void open() throws SQLException {

        database = dbHelper.getWritableDatabase();

    }
    public void close() {

        dbHelper.close();

    }

    public void addMovie(String id , String title , String posterURL , String date , String rate , String overview) {
        ContentValues values = new ContentValues();

        values.put(DataBaseWrapper.Movie_ID, id);
        values.put(DataBaseWrapper.Movie_TITLE, title);
        values.put(DataBaseWrapper.Movie_POSTER_URL, posterURL);
        values.put(DataBaseWrapper.Movie_DATE, date);
        values.put(DataBaseWrapper.Movie_RATE, rate);
        values.put(DataBaseWrapper.Movie_OVERVIEW, overview);
        values.put(DataBaseWrapper.Movie_FAVORITES, 0);
        values.put(DataBaseWrapper.Movie_WATCH_LIST, 0);
        database.insert(DataBaseWrapper.Movies, null, values);

    }
    public void updateWatchList(String id ,  int value) {
        ContentValues values = new ContentValues();
        values.put(DataBaseWrapper.Movie_WATCH_LIST, value);
        database.update(DataBaseWrapper.Movies, values, "id=" +  id , null );

    }
    public void updateFavoritesList(String id , int value) {
        ContentValues values = new ContentValues();
        values.put(DataBaseWrapper.Movie_FAVORITES, value);
        database.update(DataBaseWrapper.Movies, values, "id=" +  id , null );

    }
    public  ArrayList<MovieItem> getFavoritesMovies() {
        ArrayList<MovieItem>  movieItemsList = new ArrayList<MovieItem>();

        Cursor cursor = database.query(DataBaseWrapper.Movies,

                null, DataBaseWrapper.Movie_FAVORITES + "=1" , null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            MovieItem movieItem = parseMovie(cursor);
            movieItemsList.add(movieItem );

            cursor.moveToNext();

        }
        cursor.close();
        return movieItemsList;

    }
    public ArrayList<MovieItem> getWatchList() {

        ArrayList<MovieItem>  movieItemsList = new ArrayList<MovieItem>();
        Cursor cursor = database.query(DataBaseWrapper.Movies,

                null, DataBaseWrapper.Movie_WATCH_LIST + "=1" , null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            MovieItem movieItem = parseMovie(cursor);
            movieItemsList.add(movieItem);
            cursor.moveToNext();

        }
        cursor.close();
        return movieItemsList;

    }
    public int checkIsFavorite(String movieId ) {
       Cursor cursor  = database.query(DataBaseWrapper.Movies,
                null ,
                DataBaseWrapper.Movie_ID + " = ? ",
                new String[]{movieId}, null, null, null);

            //    null, DataBaseWrapper.Movie_ID + "=" +movieId , null, null, null, null);
        cursor.moveToFirst();

        if (cursor.isAfterLast())
          return -1 ;
        String c = cursor.getString(6) ;
        if ( cursor.getString(6).equals("1") ) {
            cursor.close();
            return 1;
        }
        return 0;

    }
    public int checkIsWatchList(String movieId ) {
        Cursor cursor  = database.query(DataBaseWrapper.Movies,
                null ,
                DataBaseWrapper.Movie_ID + " = ? ",
                new String[]{movieId}, null, null, null);

        //    null, DataBaseWrapper.Movie_ID + "=" +movieId , null, null, null, null);
        cursor.moveToFirst();

        if (cursor.isAfterLast())
            return -1 ;
        String c = cursor.getString(7) ;
        if ( cursor.getString(7).equals("1") ) {
            cursor.close();
            return 1;
        }
        return 0;

    }

    private MovieItem parseMovie(Cursor cursor) {

        MovieItem movieItem = new MovieItem();

        movieItem.setMovieId(cursor.getString(0));
        movieItem.setTitle(cursor.getString(1));
        movieItem.setPosterURL(cursor.getString(2));
        movieItem.setDate(cursor.getString(3));
        movieItem.setRate(cursor.getString(4));
        movieItem.setOverview(cursor.getString(5));

        return movieItem;

    }

}
