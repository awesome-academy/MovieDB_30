package com.framgia.moviedb.screen.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.framgia.moviedb.BuildConfig;
import com.framgia.moviedb.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String API_KEY_YOUTUBE = BuildConfig.API_YOUTUBE_KEY;
    public static final int REQUEST_VIDEO = 1;
    public static final String EXTRA_PLAY_TRAILER = "EXTRA_PLAY_TRAILER";
    private YouTubePlayerView mPlayer;
    private String mKey = null;

    public static Intent getIntent(Context context, String key) {
        Intent intent = new Intent(context, TrailerActivity.class);
        intent.putExtra(EXTRA_PLAY_TRAILER, key);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        mPlayer = findViewById(R.id.player_view);
        mPlayer.initialize(API_KEY_YOUTUBE, this);
        mKey = getKeyFromIntent();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(mKey);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_VIDEO);
        } else {
            Toast.makeText(this, getString(R.string.error_play_trailer), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO) {
            mPlayer.initialize(API_KEY_YOUTUBE, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getKeyFromIntent() {
        Intent intent = getIntent();
        return intent.getExtras().getString(EXTRA_PLAY_TRAILER);
    }
}
