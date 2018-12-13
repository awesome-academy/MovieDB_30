package com.framgia.moviedb.data.repository;

import android.os.AsyncTask;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MoviesDataSource;
import com.framgia.moviedb.utils.APIUtils;
import com.framgia.moviedb.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchMovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {
    private MoviesDataSource.SearchMovieCallback mCallback;
    private Exception mException;

    public SearchMovieAsyncTask(MoviesDataSource.SearchMovieCallback callback) {
        mCallback = callback;
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        List<Movie> movies = new ArrayList<>();
        FetchData fetchData = new FetchData();
        String query = strings[0];
        String url = APIUtils.searchMovieUrl(query, APIUtils.FIRST_PAGE_NUMBER);
        try {
            String json = fetchData.getJsonData(url);
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null) {
                JSONArray jsonMovies = jsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
                for (int i = 0; i < jsonMovies.length(); i++) {
                    movies.add(new Movie(jsonMovies.getJSONObject(i)));
                }
            }
        } catch (IOException e) {
            mException = e;
        } catch (JSONException e) {
            mException = e;
        } catch (Exception e) {
            mException = e;
        }
        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (mException != null) {
            return;
        }
        if (movies.size() == 0) {
            mCallback.onNoResult();
        } else {
            mCallback.onSearchSuccess(movies);
        }
    }
}
