package com.nghianv.musiclibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nghianv.musiclibrary.MainActivity;
import com.nghianv.musiclibrary.R;
import com.nghianv.musiclibrary.adapter.RecyclerViewArtistAdapter;
import com.nghianv.musiclibrary.media.MediaManager;

public class ArtistFragment extends BaseFragment {
	private static final String TAG = "ArtistFragment";
	private RecyclerViewArtistAdapter artistAdapter;
	private RecyclerView rvArtist;
	private static ArtistFragment artistFragment;

	public static ArtistFragment getInstance() {
		if (artistFragment == null) {
			return new ArtistFragment();
		}
		return artistFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		artistAdapter = new RecyclerViewArtistAdapter(activity);
		artistAdapter.setArtistList(MediaManager.getInstance(activity).getAllArtist());
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_artist, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		rvArtist = view.findViewById(R.id.rv_artist);
		rvArtist.setAdapter(artistAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		rvArtist.setLayoutManager(linearLayoutManager);
	}

}
