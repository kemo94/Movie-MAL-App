package com.example.kemos.fainalmoviemalapp.Model;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;

import com.example.kemos.fainalmoviemalapp.View.Adapter.CustomGridAdapter;
import com.example.kemos.fainalmoviemalapp.View.Adapter.CustomReviewListAdapter;
import com.example.kemos.fainalmoviemalapp.View.Adapter.CustomTrailerListAdapter;
import com.example.kemos.fainalmoviemalapp.View.Adapter.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by kemos on 3/28/2016.
 */
public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<MovieItem>> {

    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    String url ;
    String targetData ;
    Context context ;
    GridView gridview;
    boolean searchResult = true ;
    ArrayList<MovieItem> movieDataArray;
    ListView listView ;
    RecyclerView recyclerView ;
    final  String MOVIE_DATA = "movie_data";
    final  String MOVIE_TRAILER = "movie_trailer";
    final  String SIMILAR_MOVIES = "SIMILAR_MOVIES";
    final  String MOVIE_REVIEW = "movie_review";

    public FetchMovieTask(Context c, String url, String targetData, RecyclerView recyclerView){
        this.url = url;
        this.context = c ;
        this.targetData = targetData;
        this.recyclerView = recyclerView ;
    }

    public FetchMovieTask(Context c, String url, String targetData, ListView listView){
        this.url = url;
        this.context = c ;
        this.targetData = targetData;
        this.listView = listView ;
    }
    public FetchMovieTask(Context c, String url, String targetData, GridView gridview){
        this.url = url;
        this.context = c ;
        this.targetData = targetData;
        this.gridview = gridview ;
    }

    public  ArrayList<MovieItem> getMovieDataArray(){
        return  this.movieDataArray ;
    }

    public ArrayList<MovieItem> getMovieTrailerFromJson(String trailerJsonStr)
            throws JSONException {

        final String TMD_LIST = "results";
        final String TMD_TRAILER_KEY = "key";
        final String TMD_TRAILER_NAME = "name";


        JSONObject forecastJson = new JSONObject(trailerJsonStr);
        JSONArray moviesArray = forecastJson.getJSONArray(TMD_LIST);
        movieDataArray = new ArrayList<MovieItem>();
        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject movieInfo = moviesArray.getJSONObject(i);
            MovieItem movieItem = new MovieItem();
            movieItem.setTrailerId( movieInfo.getString(TMD_TRAILER_KEY));

            movieItem.setTrailerName(movieInfo.getString(TMD_TRAILER_NAME));
            movieDataArray.add(movieItem);

        }
        return movieDataArray ;
    }
    public ArrayList<MovieItem> getMovieReviewsFromJson(String reviewJsonStr  )
            throws JSONException {

        final String TMD_LIST = "results";
        final String TMD_REVIEW_AUTHOR = "author";
        final String TMD_REVIEW_CONTENT = "content";

        JSONObject forecastJson = new JSONObject(reviewJsonStr);
        JSONArray moviesArray = forecastJson.getJSONArray(TMD_LIST);
        movieDataArray = new ArrayList<MovieItem>();
        for (int i = 0; i < moviesArray.length(); i++) {
            MovieItem movieItem = new MovieItem();
            JSONObject movieInfo = moviesArray.getJSONObject(i);
            movieItem.setAuthorReviews(movieInfo.getString(TMD_REVIEW_AUTHOR));
            movieItem.setContentReviews(movieInfo.getString(TMD_REVIEW_CONTENT));
            movieDataArray.add(movieItem);

        }
        return movieDataArray ;
    }

    public  ArrayList<MovieItem> getMovieDataFromJson(String dataJsonStr )
            throws JSONException {

        final String TMD_LIST = "results";
        final String TMD_POSTER = "poster_path";
        final String TMD_TITLE = "original_title";
        final String TMD_OVERVIEW = "overview";
        final String TMD_ID = "id";
        final String TMD_RATE = "vote_average";
        final String TMD_DATE = "release_date";
        final String TMD_TOTAL_RESULT = "total_results";


        JSONObject forecastJson = new JSONObject(dataJsonStr);
        JSONArray moviesArray = forecastJson.getJSONArray(TMD_LIST);
         if  (forecastJson.getInt(TMD_TOTAL_RESULT) == 0 )
             searchResult = false ;

        movieDataArray = new  ArrayList<MovieItem> ();
        for(int i = 0; i < moviesArray.length(); i++) {

            MovieItem movieItemObject = new MovieItem();
            JSONObject movieInfo = moviesArray.getJSONObject(i);
            String posters = movieInfo.getString(TMD_POSTER);
            String title = movieInfo.getString(TMD_TITLE);
            String overview = movieInfo.getString(TMD_OVERVIEW);
            String id = movieInfo.getString(TMD_ID);
            String rate = movieInfo.getString(TMD_RATE);
            String date = movieInfo.getString(TMD_DATE);

            movieItemObject.setPosterURL( posters);
            movieItemObject.setRate( rate);
            movieItemObject.setDate( date);
            movieItemObject.setMovieId( id);
            movieItemObject.setOverview(overview);
            movieItemObject.setTitle( title);


            movieDataArray.add(movieItemObject);
        }



        return movieDataArray;

    }
    public boolean checkSearchResult(){

        return searchResult;
    }
    @Override
    protected ArrayList<MovieItem> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String dataJsonStr = null;

        String APIKey = "42ff4803b000d60162080246b4f305ec";

        final String APIKey_PARAM = "api_key";
        try {

            Uri builtUri = Uri.parse(url).buildUpon()
                    .appendQueryParameter(APIKey_PARAM, APIKey)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            dataJsonStr = buffer.toString();

            Log.v(LOG_TAG, "Forecast string: " + dataJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            if ( targetData.equals(MOVIE_DATA) || targetData.equals(SIMILAR_MOVIES) )
                return getMovieDataFromJson(dataJsonStr );
            else if ( targetData.equals(MOVIE_TRAILER) )
                return getMovieTrailerFromJson(dataJsonStr );
            else if ( targetData.equals(MOVIE_REVIEW) )
                return getMovieReviewsFromJson(dataJsonStr );
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

   @Override
    protected void onPostExecute(ArrayList<MovieItem> result) {
       if ( result != null ) {
           for (int i = 0; i < result.size(); i++)
               Log.v(LOG_TAG, "Forecast entry: " + result.get(i).getTitle());

           if (targetData.equals(MOVIE_DATA))
               gridview.setAdapter(new CustomGridAdapter(context, movieDataArray));
           else if (targetData.equals(MOVIE_TRAILER))
               listView.setAdapter(new CustomTrailerListAdapter(context, movieDataArray));
           else if (targetData.equals(MOVIE_REVIEW))
               listView.setAdapter(new CustomReviewListAdapter(context, movieDataArray));
            else if (targetData.equals(SIMILAR_MOVIES))
               recyclerView.setAdapter(new RecyclerViewAdapter(context, movieDataArray));

       }

    }
}

