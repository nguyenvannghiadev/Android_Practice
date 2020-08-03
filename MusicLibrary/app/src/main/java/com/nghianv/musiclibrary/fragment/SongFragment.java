package com.nghianv.musiclibrary.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nghianv.musiclibrary.MainActivity;
import com.nghianv.musiclibrary.R;
import com.nghianv.musiclibrary.adapter.RecyclerViewSongAdapter;
import com.nghianv.musiclibrary.listener.OnPlayMusic;
import com.nghianv.musiclibrary.media.MediaManager;
import com.nghianv.musiclibrary.model.Song;

import java.util.List;


public class SongFragment extends BaseFragment {
	private static final String TAG = "SongFragment";
	private RecyclerViewSongAdapter songAdapter;
	private RecyclerView rvSong;
	//
	private static SongFragment songFragment;

	public static SongFragment getInstance() {
		if (songFragment == null) {
			return new SongFragment();
		}
		return songFragment;
	}
	//

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		songAdapter = new RecyclerViewSongAdapter(activity, onPlayMusic);
	}
	//
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_song, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		rvSong = view.findViewById(R.id.rv_song);
		rvSong.setAdapter(songAdapter);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		rvSong.setLayoutManager(layoutManager);
		Log.d(">>>>", "da vao day : " + songAdapter.getItemCount());
	}
}
