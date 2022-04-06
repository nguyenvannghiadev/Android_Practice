package com.nghianv.rxjava5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;

import java.util.Locale;
import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private final CompositeDisposable compositeDisposable = new CompositeDisposable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//
		Observable<String> animalsObservable = getAnimalsObservable();
		DisposableObserver<String> animalsObserver = getAnimalsObserver();
		DisposableObserver<String> animalsObserverAllCaps = getAnimalsAllCapsObserver();

		// filter() is used to filter out the animal names starting with b
		compositeDisposable.add(
				animalsObservable
						.subscribeOn(Schedulers.io())
						.filter(s -> s.toLowerCase(Locale.ROOT).startsWith("b"))
						.observeOn(AndroidSchedulers.mainThread())
						.subscribeWith(animalsObserver)
		);

		/*
		 * filter() is used to filter out the animal names starting with c
		 * map() is used to transform all the characters to UPPER Case
		 */
		compositeDisposable.add(
				animalsObservable
						.subscribeOn(Schedulers.io())
						.filter(s -> s.toLowerCase(Locale.ROOT).startsWith("c"))
						.map(s -> s.toUpperCase(Locale.ROOT))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(animalsObserverAllCaps)
		);
	}

	private DisposableObserver<String> getAnimalsObserver() {
		return new DisposableObserver<String>() {
			@Override
			public void onNext(String s) {
				Log.d(TAG, "Name: " + s);
			}

			@Override
			public void onError(Throwable e) {
				Log.e(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "All items are emitted!");
			}
		};
	}

	private DisposableObserver<String> getAnimalsAllCapsObserver() {
		return new DisposableObserver<String>() {
			@Override
			public void onNext(String s) {
				Log.d(TAG, "Name: " + s);
			}

			@Override
			public void onError(Throwable e) {
				Log.e(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "Done: AllCaps items are emitted!");
			}
		};
	}

	private Observable<String> getAnimalsObservable() {
		return Observable.fromArray(
				"Ant", "Ape",
				"Bat", "Bee", "Bear", "Butterfly",
				"Cat", "Crab", "Cod",
				"Dog", "Dove",
				"Fox", "Frog");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		compositeDisposable.clear();
	}
}