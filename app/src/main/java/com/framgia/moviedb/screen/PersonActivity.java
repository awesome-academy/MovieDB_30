package com.framgia.moviedb.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.moviedb.R;

public class PersonActivity extends AppCompatActivity {
    private static final String EXTRA_PERSON_ID = "EXTRA_PERSON_ID";

    public static Intent getIntent(Context context, int personId) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
    }
}
