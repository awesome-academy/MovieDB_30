package com.framgia.moviedb.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Genre {
    private String mId;
    private String mName;

    public Genre(JSONObject jsonObject) throws JSONException {
        mId = jsonObject.getString(jsonObject.getString(GenreJsonKey.GENRE_ID));
        mName = jsonObject.getString(jsonObject.getString(GenreJsonKey.GENRE_NAME));
    }

    public static class GenreJsonKey {
        public static final String GENRE_ID = "id";
        public static final String GENRE_NAME = "name";
    }
}
