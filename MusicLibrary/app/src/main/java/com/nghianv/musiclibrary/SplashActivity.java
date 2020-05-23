package com.nghianv.musiclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nghianv.musiclibrary.media.MediaManager;


public class SplashActivity extends AppCompatActivity {

    public static final int SPLASH_DISPLAY_LENGTH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        MediaManager.getInstance(this).setmListSong(MediaManager.getInstance(this).getAllAudioFilesExternal(null, null));
        findViewById(R.id.progress_bar_loading).setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    @Override
    public void finish() {
        findViewById(R.id.progress_bar_loading).setVisibility(View.GONE);
        super.finish();
    }
}
