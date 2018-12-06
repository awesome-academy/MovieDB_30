package com.framgia.moviedb.screen.movie_detail;

import com.framgia.moviedb.data.model.Cast;
import com.framgia.moviedb.data.model.Crew;
import com.framgia.moviedb.data.model.MovieDetail;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.source.MoviesDataSource;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {
    public static final int FIRST_POSITION_OF_DATE = 0;
    public static final int FIRST_POSITION_OF_STRING = 0;
    public static final int FIRST_PERSON_POSITION = 0;
    public static final int SECOND_PERSON_POSITION = 1;
    public static final int LAST_POSITION_OF_DATE = 4;
    public static final String STRING_SEPARATOR = ", ";
    private MovieDetailContract.View mView;
    private MoviesRepository mRepository;

    public MovieDetailPresenter(MovieDetailContract.View view, MoviesRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void getMoreInfo(String movieId) {
        mRepository.getMovie(movieId, new MoviesDataSource.GetMovieCallback() {
            @Override
            public void onMovieLoaded(MovieDetail movieDetail) {
                mView.showReleaseDate(movieDetail.getReleaseDate().
                        substring(FIRST_POSITION_OF_DATE, LAST_POSITION_OF_DATE));
                mView.showDirectorName(getDirectorName(movieDetail));
                mView.showActorsName(getActorsName(movieDetail));
                mView.showTrailer(movieDetail.getTrailerUrl());
                mView.showRelatedMovies(movieDetail.getRelatedMovies());
                mView.showGenre(getGenreName(movieDetail));
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                e.printStackTrace();
                mView.showInfoError(e);
            }
        });
    }

    private String getGenreName(MovieDetail movieDetail) {
        StringBuilder genresName = new StringBuilder();
        for (int i = 0; i < movieDetail.getGenres().size(); i++) {
            genresName.append(movieDetail.getGenres().get(i).getName()).append(STRING_SEPARATOR);
        }
        return genresName.substring(FIRST_POSITION_OF_STRING,
                genresName.length() - STRING_SEPARATOR.length());
    }

    private String getDirectorName(MovieDetail movieDetail) {
        String directorName = null;
        for (int i = 0; i < movieDetail.getPeople().size(); i++) {
            if (movieDetail.getPeople().get(i) instanceof Crew
                    && ((Crew) movieDetail
                    .getPeople().get(i)).getJob()
                    .equals(Crew.CrewJsonKey.JSON_KEY_DIRECTOR)) {
                directorName = movieDetail.getPeople().get(i).getName();
                break;
            }
        }
        return directorName;
    }

    private String getActorsName(MovieDetail movieDetail) {
        StringBuilder builder = new StringBuilder();
        List<Cast> casts = new ArrayList<>();
        for (int i = 0; i < movieDetail.getPeople().size(); i++) {
            if (movieDetail.getPeople().get(i) instanceof Cast) {
                casts.add((Cast) movieDetail.getPeople().get(i));
            }
        }
        builder.append(casts.get(FIRST_PERSON_POSITION).getName())
                .append(STRING_SEPARATOR)
                .append(casts.get(SECOND_PERSON_POSITION).getName());
        return builder.toString();
    }
}
