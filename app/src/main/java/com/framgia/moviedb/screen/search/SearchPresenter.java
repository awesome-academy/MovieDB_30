package com.framgia.moviedb.screen.search;

import android.util.Log;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.source.MoviesDataSource;
import com.framgia.moviedb.utils.Constants;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private MoviesRepository mRepository;

    public SearchPresenter(SearchContract.View view, MoviesRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void searchMovie(String query) {
        mRepository.searchMovie(query, new MoviesDataSource.SearchMovieCallback() {
            @Override
            public void onSearchSuccess(List<Movie> movies) {
                mView.showResults(movies);
            }

            @Override
            public void onNoResult() {
                mView.showNoResult();
            }

            @Override
            public void onSearchError(Exception e) {

            }
        });
    }
}
