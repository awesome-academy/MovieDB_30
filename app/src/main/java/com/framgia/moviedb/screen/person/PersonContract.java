package com.framgia.moviedb.screen.person;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.model.Person;

import java.util.List;

public interface PersonContract {
    interface View {
        void showInfo(Person person);

        void showInfoError(Exception e);

        void showRelatedMovie(List<Movie> movies);
    }

    interface Presenter {
        void getPersonInfo(int id);

        void getMovieByPersonId(int id, int page);
    }
}
