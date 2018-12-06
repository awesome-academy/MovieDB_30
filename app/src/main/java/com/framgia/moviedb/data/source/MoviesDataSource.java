package com.framgia.moviedb.data.source;

import com.framgia.moviedb.data.model.GroupMovie;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.model.MovieDetail;

import java.util.List;

public interface MoviesDataSource {

    interface LoadMoviesCallback {

        void onMoviesLoaded(List<GroupMovie> groupMovies, List<Movie> movies);

        void onDataNotAvailable(Exception e);
    }

    interface GetMovieCallback {
        void onMovieLoaded(MovieDetail movie);

        void onDataNotAvailable(Exception e);
    }

    void getMovies(LoadMoviesCallback loadMoviesCallback);

    void getMovie(String movieId, GetMovieCallback getMovieCallback);


}
