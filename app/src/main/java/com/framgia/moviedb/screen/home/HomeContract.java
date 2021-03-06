package com.framgia.moviedb.screen.home;

import com.framgia.moviedb.data.model.GroupMovie;
import com.framgia.moviedb.data.model.Movie;

import java.util.List;

public interface HomeContract {
    interface View {
        void showMovies(List<GroupMovie> groupMovies);

        void showMoviesError(Exception e);

        void showSliderPager(List<Movie> movies);

        void showSliderPagerError(Exception e);

    }

    interface Presenter {
        void loadMovies();
    }
}
