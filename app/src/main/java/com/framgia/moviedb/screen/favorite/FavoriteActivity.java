package com.framgia.moviedb.screen.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.moviedb.R;

public class FavoriteActivity extends AppCompatActivity {
    public static Intent getIntent(Context context) {
        return new Intent(context, FavoriteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
    }

}
