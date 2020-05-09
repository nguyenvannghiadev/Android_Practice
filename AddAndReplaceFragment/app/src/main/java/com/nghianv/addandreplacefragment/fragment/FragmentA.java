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

public class FragmentA extends Fragment {
	protected int a = 0;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		Log.d("Fragment A", "FragmentA: onCreat");
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.d("Fragment A", "FragmentA: onCreateView with values a: " + a);
		return inflater.inflate(R.layout.fragment_a, container, false);
	}

	@Override
	public void onStart() {
		Log.d("Fragment A", "FragmentA: onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.d("Fragment A", "FragmentA: onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.d("Fragment A", "FragmentA: onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.d("Fragment A", "FragmentA: onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		a = 5;
		Log.d("Fragment A", "FragmentA: onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.d("Fragment A", "FragmentA: onDestroy");
		super.onDestroy();
	}
}
