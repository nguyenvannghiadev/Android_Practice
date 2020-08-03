package com.nghianv.musiclibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nghianv.musiclibrary.media.MediaManager;
import com.nghianv.musiclibrary.model.Song;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = "DetailActivity";
	public static final String keySong = "KeySong";
	public static final String keyPositionSong = "KeyPositionSong";
	private Song song;
	private TextView tvSongName, tvArtistName;
	private ImageView imgLoop, imgPrevious, imgPlay, imgNext, imgShuffle, imgBack, imgFavorite;
	private List<Song> listSongDetail = MediaManager.getInstance(this).getmListSong();
	private int currentPos = 0, lastPos;
	private MediaManager mediaManager;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_song);
		initView();
		setOnclick();
		mediaManager = MediaManager.getInstance(this);
		try {
			String fromRingtone = getIntent().getExtras().getString(keySong);
			song = new Gson().fromJson(fromRingtone, Song.class);
			//
			tvSongName.setText(song.getDisplayName());
			tvArtistName.setText(song.getArtist());
			Log.d(TAG, "Song: " + song.getDisplayName());
		} catch (Exception e) {
			Log.e(TAG, "Oncreate:" + e);
		}
	}

	private void initView() {
		tvSongName = findViewById(R.id.tv_song_name);
		tvArtistName = findViewById(R.id.tv_artists_name);
		imgLoop = findViewById(R.id.img_loop);
		imgPrevious = findViewById(R.id.img_previous);
		imgPlay = findViewById(R.id.img_play_pause);
		imgNext = findViewById(R.id.img_next);
		imgShuffle = findViewById(R.id.img_shuffle);
		imgBack = findViewById(R.id.img_back);
		imgFavorite = findViewById(R.id.img_favorite);

	}

	private void setOnclick() {
		imgLoop.setOnClickListener(this);
		imgPrevious.setOnClickListener(this);
		imgPlay.setOnClickListener(this);
		imgNext.setOnClickListener(this);
		imgShuffle.setOnClickListener(this);
		imgBack.setOnClickListener(this);
		imgFavorite.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int vID = v.getId();
		switch (vID) {
			case R.id.img_loop:
				break;
			case R.id.img_previous:
				break;
			case R.id.img_play_pause:
				if (!mediaManager.getMediaPlayer().isPlaying()) {
					mediaManager.playSong();
				} else {
					mediaManager.stop();
				}
				Log.d(TAG, "click imgplay");
				Toast.makeText(this, "click imgplay", Toast.LENGTH_SHORT).show();
				break;
			case R.id.img_next:
				Toast.makeText(this, "click img_next", Toast.LENGTH_SHORT).show();
				break;
			case R.id.img_shuffle:
				Toast.makeText(this, "click img_shuffle", Toast.LENGTH_SHORT).show();
				break;
			case R.id.img_back:
				onBackPressed();
				break;
			case R.id.img_favorite:
				Toast.makeText(this, "click img_favorite", Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
