package com.framgia.moviedb.data.repository;

import android.os.AsyncTask;

import com.framgia.moviedb.data.model.Person;
import com.framgia.moviedb.data.source.PersonDataSource;
import com.framgia.moviedb.utils.APIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PersonDetailAsyncTask extends AsyncTask<Integer, Void, Person> {

    private PersonDataSource.GetPersonDetailCallback mCallback;
    private Exception mException;

    @Override
    protected Person doInBackground(Integer... integers) {
        Person person = null;
        int id = integers[0];
        FetchData fetchData = new FetchData();
        String url = APIUtils.getPersonUrlById(id);
        try {
            String json = fetchData.getJsonData(url);
            person = getPersonFromJson(json);
        } catch (IOException e) {
            mException = e;
        } catch (Exception e) {
            e.printStackTrace();
            mException = e;
        }
        return person;
    }

    public PersonDetailAsyncTask(PersonDataSource.GetPersonDetailCallback callback) {
        mCallback = callback;
    }

    private Person getPersonFromJson(String json) throws JSONException {
        Person person = null;
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject != null) {
            person = new Person(jsonObject);
        }
        return person;
    }

    @Override
    protected void onPostExecute(Person person) {
        if (mException == null) {
            mCallback.onDataLoaded(person);
        } else {
            mCallback.onDataNotAvailable(mException);
            return;
        }
        super.onPostExecute(person);
    }
}
