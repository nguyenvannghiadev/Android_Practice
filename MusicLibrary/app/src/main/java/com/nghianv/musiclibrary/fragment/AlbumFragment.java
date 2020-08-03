package com.nghianv.musiclibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nghianv.musiclibrary.MainActivity;
import com.nghianv.musiclibrary.R;
import com.nghianv.musiclibrary.adapter.AlbumAdapter;

public class AlbumFragment extends BaseFragment {
	private static final String TAG = "AlbumFragment";
	private AlbumAdapter albumAdapter;
	private GridView gridView;
	//
	private static AlbumFragment albumFragment;

	public static AlbumFragment getInstance() {
		if (albumFragment == null) {
			return new AlbumFragment();
		}
		return albumFragment;
	}
	//
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		albumAdapter = new AlbumAdapter(activity);
	}

	//
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_album, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		gridView = view.findViewById(R.id.grid_view_album);
		gridView.setAdapter(albumAdapter);
	}
}
