package com.framgia.moviedb.data.repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.framgia.moviedb.utils.MethodUtils.METHOD_GET;

public class FetchData {

    public String getJsonData(String path) throws Exception {
        HttpURLConnection urlConnection;
        BufferedReader reader;
        String jsonString;
        URL url = new URL(path);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(METHOD_GET);
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        if (builder.length() == 0) {
            return null;
        }
        jsonString = builder.toString();
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        if (reader != null) {
            reader.close();
        }
        return jsonString;
    }
}
