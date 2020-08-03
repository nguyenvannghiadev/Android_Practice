package com.nghianv.musiclibrary;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nghianv.musiclibrary.adapter.RecyclerViewSongAdapter;
import com.nghianv.musiclibrary.adapter.ViewPagerAdapter;
import com.nghianv.musiclibrary.listener.OnPlayMusic;
import com.nghianv.musiclibrary.media.MediaManager;
import com.nghianv.musiclibrary.model.Song;

import java.util.List;

import static com.nghianv.musiclibrary.DetailActivity.keyPositionSong;
import static com.nghianv.musiclibrary.DetailActivity.keySong;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
	private static final String TAG = "MainActivity";
	private ViewPager viewPager;
	private DrawerLayout drawer;
	private NavigationView navigationView;
	private ImageView imgMenu, imgSetting;
	private MediaManager mediaManager;

	private View viewLayoutFooter;
	private LinearLayout layoutTitleSong;
	private ImageView imgIConSong, imgPausePlay;
	private TextView tvTitleSong, tvArtistName;
	public Song song;
	public int currentPlaySong;

	protected RecyclerView recyclerView;

	private Handler handler = new Handler();

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setOnclick();
		mediaManager = MediaManager.getInstance(this);
		MainActivity.this.runOnUiThread(runnable);
		showLayoutFooter(false);
	}

	private void initView() {
		drawer = findViewById(R.id.drawer_layout);
		navigationView = findViewById(R.id.nav_view);
		imgMenu = findViewById(R.id.icon_menu);
		imgSetting = findViewById(R.id.icon_setting);
		// Layout footer
		viewLayoutFooter = findViewById(R.id.layout_footer);
		layoutTitleSong = findViewById(R.id.layout_title_song);
		imgIConSong = findViewById(R.id.imv_image_song);
		tvTitleSong = findViewById(R.id.tv_footer_title_song);
		tvArtistName = findViewById(R.id.tv_footer_name_artist);
		imgPausePlay = findViewById(R.id.img_pause_play_footer);
		//
		ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
		viewPager = findViewById(R.id.viewPager);
		viewPager.setAdapter(viewPagerAdapter);
		TabLayout tabs = findViewById(R.id.tabs);
		tabs.setupWithViewPager(viewPager);
		//
		if (mediaManager == null) {
			mediaManager = MediaManager.getInstance(this);
		}
		mediaManager.setmListSong(MediaManager.getInstance(this).getAllAudioFilesExternal(null, null));
		if (mediaManager.getmListSong().size() > 0) {
			Song song = mediaManager.getmListSong().get(mediaManager.getCurrentSongIndex());
			setInforLayoutFooter(song.getDisplayName(), song.getArtist());
		}
	}

	private void setOnclick() {
		navigationView.setNavigationItemSelectedListener(this);
		imgMenu.setOnClickListener(this);
		imgSetting.setOnClickListener(this);
		//
		layoutTitleSong.setOnClickListener(this);
		imgIConSong.setOnClickListener(this);
		imgPausePlay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imv_image_song:
			case R.id.layout_title_song:
				goToDetailActivity(song,true, currentPlaySong);
				break;
			case R.id.img_pause_play_footer:
				mediaManager.playSong();
				break;
			case R.id.icon_menu:
				drawer.openDrawer(GravityCompat.START);
				break;
			case R.id.icon_setting:
				break;
		}

	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
		int itemId = menuItem.getItemId();
		// Handle navigation view item clicks here.
		switch (itemId) {
			case R.id.menu_song:
				viewPager.setCurrentItem(0);
				break;
			case R.id.menu_albums:
				viewPager.setCurrentItem(1);
				break;
			case R.id.menu_artist:
				viewPager.setCurrentItem(2);
				break;
			case R.id.menu_genres:
				viewPager.setCurrentItem(3);
				break;
		}
		drawer.closeDrawer(GravityCompat.START);
		return false;
	}

	@Override
	public void onBackPressed() {
		if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	private int getCurrentSongIndex(String songName) {
		List<Song> arrList = mediaManager.getmListSong();
		int position = 0;
		for (Song song : arrList) {
			if (song.getDisplayName().equals(songName)) {
				return position;
			}
			position++;
		}
		return -1;
	}

	public void setInforLayoutFooter(String nameSong, String nameArtist) {
		tvTitleSong.setText(nameSong);
		tvArtistName.setText(nameArtist);
	}

	public void showLayoutFooter(boolean isShow) {
		if (isShow) {
			viewLayoutFooter.setVisibility(View.VISIBLE);
		} else {
			viewLayoutFooter.setVisibility(View.GONE);
		}
	}
	protected void goToDetailActivity(Song song, boolean isPlay, int position) {
		//Go to Detail screen
		if (mediaManager.getMediaPlayer().isPlaying()) {
			mediaManager.getMediaPlayer().stop();
		}
		Intent intent = new Intent(this, DetailActivity.class);
		String toRingtone = new Gson().toJson(song);
		intent.putExtra(keySong, toRingtone);
		intent.putExtra(keyPositionSong, position);
		intent.putExtra("isPlay", isPlay);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		try {
			if (recyclerView != null) {
				RecyclerViewSongAdapter songAdapter = (RecyclerViewSongAdapter) recyclerView.getAdapter();
				if (songAdapter != null) {
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "goToDetailActivity: " + e);
		}
	}

	private void updateLayoutFooter() {
		if (mediaManager.getMediaPlayer().isPlaying()) {
			imgPausePlay.setImageResource(R.drawable.ic_pause);
		} else {
			imgPausePlay.setImageResource(R.drawable.ic_play);
		}
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			updateLayoutFooter();
			handler.postDelayed(this, 200);
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}