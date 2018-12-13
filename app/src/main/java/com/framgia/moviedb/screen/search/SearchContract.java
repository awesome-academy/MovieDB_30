package com.framgia.moviedb.screen.search;

import com.framgia.moviedb.data.model.Movie;

import java.util.List;

public interface SearchContract {
    interface View {
        void showResults(List<Movie> movies);

        void showNoResult();
    }

    interface Presenter {
        void searchMovie(String query);
    }
}
