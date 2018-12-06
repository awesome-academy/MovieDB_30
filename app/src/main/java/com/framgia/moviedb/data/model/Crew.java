package com.framgia.moviedb.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Crew extends Person {
    private String mDepartment;
    private String mJob;

    public Crew(JSONObject item) throws JSONException {
        super(item);
        mDepartment = item.getString(PersonJsonKey.DEPARTMENT);
        mJob = item.getString(PersonJsonKey.JOB);
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public String getJob() {
        return mJob;
    }

    public void setJob(String job) {
        mJob = job;
    }

    public static class CrewJsonKey {
        public static final String JSON_KEY_CREW = "crew";
        public static final String JSON_KEY_DIRECTOR = "Director";
    }
}
