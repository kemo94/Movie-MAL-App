package com.example.kemos.fainalmoviemalapp.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseWrapper extends SQLiteOpenHelper {

    public static final String Movies = "Movies";
    public static final String Movie_POSTER_URL = "posterURL";
    public static final String Movie_ID = "id";
    public static final String Movie_TITLE = "title";
    public static final String Movie_DATE = "date";
    public static final String Movie_OVERVIEW = "overview";
    public static final String Movie_RATE = "rate";
    public static final String Movie_FAVORITES = "favorite";
    public static final String Movie_WATCH_LIST = "watchlist";
    private static final String DATABASE_NAME = "Movies.db";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table " + Movies

            + "(" + Movie_ID + " text primary key , "
            + Movie_TITLE + " text not null , "
            + Movie_POSTER_URL + " text not null, "
            + Movie_DATE + " text not null, "
            + Movie_RATE + " text not null, "
            + Movie_OVERVIEW + " text not null, "
            + Movie_FAVORITES + " boolean , "
            + Movie_WATCH_LIST + " boolean  );" ;

    public DataBaseWrapper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Movies);

        onCreate(db);

    }

}
