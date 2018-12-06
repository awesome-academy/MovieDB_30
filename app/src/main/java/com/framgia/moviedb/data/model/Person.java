package com.framgia.moviedb.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Person {
    private int mId;
    private String mName;
    private String mCreditId;
    private String mProfilePath;
    private int mGender;

    public Person() {
    }

    public Person(JSONObject item) throws JSONException {
        mId = item.getInt(PersonJsonKey.ID);
        mName = item.getString(PersonJsonKey.NAME);
        mCreditId = item.getString(PersonJsonKey.CREDIT_ID);
        mProfilePath = item.getString(PersonJsonKey.PROFILE_PATH);
        mGender = item.getInt(PersonJsonKey.GENDER);
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

    public int isGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
    }

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        mCreditId = creditId;
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

    public static class PersonJsonKey {
        public static final String ID = "id";
        public static final String CREDIT_ID = "credit_id";
        public static final String CAST_ID = "cast_id";
        public static final String GENDER = "gender";
        public static final String NAME = "name";
        public static final String DEPARTMENT = "department";
        public static final String JOB = "job";
        public static final String PROFILE_PATH = "profile_path";
        public static final String ORDER = "order";
        public static final String CHARACTER = "character";
    }
}
