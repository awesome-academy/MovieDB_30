package com.framgia.moviedb.screen.more;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.moviedb.R;

public class MoreMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MORE_MOVIE = "EXTRA_MORE_MOVIE";

    public static Intent getIntent(Context context, int groupId) {
        Intent intent = new Intent(context, MoreMovieActivity.class);
        intent.putExtra(EXTRA_MORE_MOVIE, groupId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_movie);
    }
}
