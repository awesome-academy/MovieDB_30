package com.framgia.moviedb.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Genre {
    private int mId;
    private String mName;

    public Genre(JSONObject jsonObject) throws JSONException {
        mId = jsonObject.getInt(GenreJsonKey.GENRE_ID);
        mName = jsonObject.getString(GenreJsonKey.GENRE_NAME);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public static class GenreJsonKey {
        public static final String GENRE_ID = "id";
        public static final String GENRE_NAME = "name";
        public static final String JSON_KEY_GENRES = "genres";
    }
}
