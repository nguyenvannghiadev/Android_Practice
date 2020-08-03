package com.nghianv.musiclibrary.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nghianv.musiclibrary.MainActivity;
import com.nghianv.musiclibrary.listener.OnPlayMusic;
import com.nghianv.musiclibrary.media.MediaManager;
import com.nghianv.musiclibrary.model.Song;

import java.util.List;

public abstract class BaseFragment extends Fragment {
	private static final String TAG = "BaseFragment";
	protected MainActivity activity;
	protected MediaManager mediaManager;
	protected int currentPlayPosition = -1;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = (MainActivity) getActivity();
		mediaManager = MediaManager.getInstance(activity);
	}

	OnPlayMusic onPlayMusic = new OnPlayMusic() {
		@Override
		public void playSong(List<Song> list, int position, String dataPath) {
			currentPlayPosition = position;
			activity.showLayoutFooter(true);
			activity.setInforLayoutFooter(list.get(position).getDisplayName(), list.get(position).getArtist());
			mediaManager.playMucsicByFilePath(dataPath);
			activity.currentPlaySong = position;
			activity.song = list.get(position);
		}
	};

}
