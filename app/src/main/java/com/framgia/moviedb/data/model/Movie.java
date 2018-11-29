package com.framgia.moviedb.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {
    private String mTitle;
    private long mVoteAverage;
    private String mId;
    private String mPosterPath;
    private String mBackdropPath;
    private String mOverview;
    private List<String> mGenres;

    public Movie(JSONObject jsonObject) throws JSONException {
        mTitle = jsonObject.getString(MovieJsonKey.TITLE);
        mVoteAverage = jsonObject.getLong(MovieJsonKey.VOTE_AVERAGE);
        mId = jsonObject.getString(MovieJsonKey.ID);
        mPosterPath = jsonObject.getString(MovieJsonKey.POSTER_PATH);
        mBackdropPath = jsonObject.getString(MovieJsonKey.BACKDROP_PATH);
        mOverview = jsonObject.getString(MovieJsonKey.OVERVIEW);
        JSONArray genres = jsonObject.getJSONArray(MovieJsonKey.GENRES);
        if (genres != null) {
            mGenres = new ArrayList<>();
            for (int i = 0; i < genres.length(); i++) {
                mGenres.add(genres.getString(i));
            }
        }
    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mVoteAverage = in.readLong();
        mId = in.readString();
        mPosterPath = in.readString();
        mBackdropPath = in.readString();
        mOverview = in.readString();
        mGenres = in.createStringArrayList();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(long voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public List<String> getGenres() {
        return mGenres;
    }

    public void setGenres(List<String> genres) {
        mGenres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeLong(mVoteAverage);
        dest.writeString(mId);
        dest.writeString(mPosterPath);
        dest.writeString(mBackdropPath);
        dest.writeString(mOverview);
        dest.writeStringList(mGenres);
    }

    public static class MovieJsonKey {
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String GENRES = "genre_ids";
        public static final String ID = "id";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String VOTE_AVERAGE = "vote_average";
    }
}
