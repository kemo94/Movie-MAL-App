package com.example.kemos.fainalmoviemalapp.Model;

public class MovieItem {
    private String posterURL ;
    private String title ;
    private String overview ;
    private String trailerName ;
    private String authorReviews ;
    private String contentReviews;
    private String date ;
    private String rate ;
    private String movieId ;
    private String trailerId ;


    public String getTrailerId() {

        return trailerId;

    }
    public void setTrailerId(String trailerId) {

        this.trailerId = trailerId;

    }

    public String getOverview() {

        return overview;

    }
    public void setOverview(String overview) {

        this.overview = overview;

    }

    public String getMovieId() {

        return movieId;

    }
    public void setMovieId(String movieId) {

        this.movieId = movieId;

    }

    public String getTitle() {

        return title;
    }
    public void setTitle(String title) {

        this.title = title;

    }

    public String getPosterURL() {

        return posterURL;
    }
    public void setPosterURL(String posterURL) {

        this.posterURL = posterURL;

    }

    public String getDate() {

        return date;
    }
    public void setDate(String date) {

        this.date = date;

    }

    public String getRate() {

        return rate;
    }
    public void setRate(String rate) {

        this.rate = rate;

    }

    public String getTrailerName() {

        return trailerName;
    }
    public void setTrailerName(String trailerName) {

        this.trailerName = trailerName;

    }

    public String getAuthorReviews() {

        return authorReviews;
    }
    public void setAuthorReviews(String authorReviews) {

        this.authorReviews = authorReviews;

    }


    public String getContentReviews() {

        return contentReviews;
    }
    public void setContentReviews(String contentReviews) {

        this.contentReviews = contentReviews;

    }

}
