package com.nghianv.addandreplacefragment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nghianv.addandreplacefragment.R;

public class FragmentB extends Fragment {
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		Log.d("Fragment B", "FragmentB: onCreate");
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.d("Fragment B", "FragmentB: onCreateView");
		return inflater.inflate(R.layout.fragment_b, container, false);
	}

	@Override
	public void onStart() {
		Log.d("Fragment B", "FragmentB: onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.d("Fragment B", "FragmentB: onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.d("Fragment B", "FragmentB: onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.d("Fragment B", "FragmentB: onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.d("Fragment B", "FragmentB: onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.d("Fragment B", "FragmentB: onDestroy");
		super.onDestroy();
	}
}
