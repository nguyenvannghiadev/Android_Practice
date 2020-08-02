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
import com.nghianv.musiclibrary.adapter.GenresAdapter;

public class GenresFragment extends BaseFragment {

	private static final String TAG = "AlbumFragment";
	private GenresAdapter genresAdapter;
	private GridView gridView;
	//
	private static GenresFragment genresFragment;

	public static GenresFragment getInstance() {
		if (genresFragment == null) {
			return new GenresFragment();
		}
		return genresFragment;
	}
	//

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		genresAdapter = new GenresAdapter(activity);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_genres, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		gridView = view.findViewById(R.id.grid_view_genres);
		gridView.setAdapter(genresAdapter);
	}
}
