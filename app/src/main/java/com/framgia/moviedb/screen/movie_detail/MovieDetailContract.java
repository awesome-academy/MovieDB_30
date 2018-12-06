package com.framgia.moviedb.screen.movie_detail;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.model.MovieDetail;

import java.util.List;

public interface MovieDetailContract {
    interface View {

        void showInfoError(Exception e);

        void showReleaseDate(String releaseDate);

        void showDirectorName(String directorName);

        void showActorsName(String actorsName);

        void showRelatedMovies(List<Movie> movies);

        void showTrailer(String trailerPath);

        void showGenre(String genres);
    }

    interface Presenter {
        void getMoreInfo(String movieId);
    }
}
