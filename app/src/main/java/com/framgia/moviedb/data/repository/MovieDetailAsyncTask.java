package com.framgia.moviedb.data.repository;

import android.os.AsyncTask;

import com.framgia.moviedb.data.model.Cast;
import com.framgia.moviedb.data.model.Crew;
import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.model.MovieDetail;
import com.framgia.moviedb.data.model.Person;
import com.framgia.moviedb.data.source.MoviesDataSource;
import com.framgia.moviedb.utils.APIUtils;
import com.framgia.moviedb.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailAsyncTask extends AsyncTask<String, Void, MovieDetail> {
    private MoviesDataSource.GetMovieCallback mCallback;
    private Exception mException;
    private FetchData mFetchData;

    public MovieDetailAsyncTask(MoviesDataSource.GetMovieCallback callback) {
        mCallback = callback;
    }

    @Override
    protected MovieDetail doInBackground(String... strings) {
        String movieId = strings[0];
        mFetchData = new FetchData();
        MovieDetail movieDetail = null;
        try {
            movieDetail = getMovieInfo(movieId);
        } catch (IOException e) {
            mException = e;
        } catch (JSONException e) {
            mException = e;
        } catch (Exception e) {
            mException = e;
        }
        return movieDetail;
    }

    @Override
    protected void onPostExecute(MovieDetail movieDetail) {
        super.onPostExecute(movieDetail);
        if (mCallback == null) return;
        if (mException == null) {
            mCallback.onMovieLoaded(movieDetail);
        } else {
            mCallback.onDataNotAvailable(mException);
        }
    }

    private MovieDetail getMovieInfo(String movieId) throws Exception {
        MovieDetail movieDetail = new MovieDetail();
        movieDetail.setPeople(getCredits(movieId));
        movieDetail.setTrailerUrl(getTrailerPath(movieId));
        movieDetail.setReleaseDate(getReleaseDate(movieId));
        movieDetail.setRelatedMovies(getRelatedMovie(movieId));
        movieDetail.setGenres(getGenre(movieId));
        return movieDetail;
    }

    private List<Movie> getRelatedMovie(String movieId) throws Exception {
        String url = APIUtils.getRelatedMovieUrl(movieId, APIUtils.FIRST_PAGE_NUMBER);
        String json = mFetchData.getJsonData(url);
        List<Movie> movies = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject != null) {
            JSONArray jsonMovies = jsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
            for (int i = 0; i < jsonMovies.length(); i++) {
                movies.add(new Movie(jsonMovies.getJSONObject(i)));
            }
        }
        return movies;
    }

    private List<Genre> getGenre(String movieId) throws Exception {
        String url = APIUtils.getMovieUrlById(movieId);
        String json = mFetchData.getJsonData(url);
        List<Genre> genres = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.getJSONArray(Genre.GenreJsonKey.JSON_KEY_GENRES);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                genres.add(new Genre(item));
            }
        }
        return genres;
    }


    private String getReleaseDate(String movieId) throws Exception {
        String url = APIUtils.getMovieUrlById(movieId);
        String json = mFetchData.getJsonData(url);
        String releaseDate = null;
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject != null) {
            releaseDate = jsonObject.getString(MovieDetail.MovieDetailJsonKey.JSON_KEY_RELEASE_DATE);
        }
        return releaseDate;
    }

    private String getTrailerPath(String movieId) throws Exception {
        String url = APIUtils.getApiMovieInfoUrl(Constants.VIDEOS_TYPE, movieId);
        String json = mFetchData.getJsonData(url);
        String trailerKey = null;
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject != null) {
            JSONArray jsonResult = jsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
            if (jsonResult.length() > 0) {
                trailerKey = jsonResult.getJSONObject(0)
                        .getString(MovieDetail.MovieDetailJsonKey.JSON_KEY_YOUTUBE_KEY);
            }
        }
        return trailerKey;
    }

    private List<Person> getCredits(String movieId) throws Exception {
        String url = APIUtils.getApiMovieInfoUrl(Constants.CREDITS_TYPE, movieId);
        String json = mFetchData.getJsonData(url);
        List<Person> people = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject != null) {
            JSONArray jsonCasts = jsonObject.getJSONArray(Cast.CastJsonKey.JSON_KEY_CAST);
            for (int i = 0; i < jsonCasts.length(); i++) {
                JSONObject item = jsonCasts.getJSONObject(i);
                if (item != null) {
                    people.add(new Cast(item));
                }
            }
            JSONArray jsonCrews = jsonObject.getJSONArray(Crew.CrewJsonKey.JSON_KEY_CREW);
            for (int i = 0; i < jsonCrews.length(); i++) {
                JSONObject item = jsonCrews.getJSONObject(i);
                if (item != null) {
                    people.add(new Crew(item));
                }
            }
        }
        return people;
    }
}
