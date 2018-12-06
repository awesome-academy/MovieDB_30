package com.framgia.moviedb.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Cast extends Person {
    private int mCastId;
    private String mCharacter;
    private int mOrder;
    public Cast(JSONObject item) throws JSONException {
        super(item);
        mCastId = item.getInt(PersonJsonKey.CAST_ID);
        mCharacter = item.getString(PersonJsonKey.CHARACTER);
        mOrder = item.getInt(PersonJsonKey.ORDER);
    }

    public int getCastId() {
        return mCastId;
    }

    public void setCastId(int castId) {
        mCastId = castId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int order) {
        mOrder = order;
    }

    public static class CastJsonKey {
        public static final String JSON_KEY_CAST = "cast";
    }
}
