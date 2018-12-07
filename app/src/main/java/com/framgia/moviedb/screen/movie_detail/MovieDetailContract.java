package com.framgia.moviedb.screen.movie_detail;

import com.framgia.moviedb.data.model.Cast;
import com.framgia.moviedb.data.model.Crew;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.model.MovieDetail;

import java.util.List;

public interface MovieDetailContract {
    interface View {

        void showInfoError(Exception e);

        void showReleaseDate(String releaseDate);

        void showDirectorName(Crew crew);

        void showActorsName(List<Cast> casts);

        void showRelatedMovies(List<Movie> movies);

        void showTrailer(String trailerPath);

        void showGenre(String genres);
    }

    interface Presenter {
        void getMoreInfo(String movieId);
    }
}
