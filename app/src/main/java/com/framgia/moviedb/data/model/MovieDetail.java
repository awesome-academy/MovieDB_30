package com.framgia.moviedb.data.model;

import java.util.ArrayList;
import java.util.List;

public class MovieDetail {
    private List<Person> mPeople;
    private String mReleaseDate;
    private List<Movie> mRelatedMovies;
    private String mTrailerUrl;
    private List<Genre> mGenres;

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }

    public String getTrailerUrl() {
        return mTrailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        mTrailerUrl = trailerUrl;
    }

    public List<Person> getPeople() {
        return mPeople;
    }

    public void setPeople(List<Person> people) {
        mPeople = new ArrayList<>(people);
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public List<Movie> getRelatedMovies() {
        return mRelatedMovies;
    }

    public void setRelatedMovies(List<Movie> relatedMovies) {
        mRelatedMovies = relatedMovies;
    }

    public static class MovieDetailJsonKey {
        public static final String JSON_KEY_RELEASE_DATE = "release_date";
        public static final String JSON_KEY_YOUTUBE_KEY = "key";
    }
}
