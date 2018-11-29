package com.framgia.moviedb.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GroupMovie implements Parcelable {
    private String mTitle;
    private int mId;
    private List<Movie> mMovies;

    public GroupMovie(String title, int id,
                      List<Movie> movies) {
        mTitle = title;
        mId = id;
        mMovies = movies;
    }

    protected GroupMovie(Parcel in) {
        mTitle = in.readString();
        mId = in.readInt();
        mMovies = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<GroupMovie> CREATOR = new Creator<GroupMovie>() {
        @Override
        public GroupMovie createFromParcel(Parcel in) {
            return new GroupMovie(in);
        }

        @Override
        public GroupMovie[] newArray(int size) {
            return new GroupMovie[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeInt(mId);
        dest.writeTypedList(mMovies);
    }
}
