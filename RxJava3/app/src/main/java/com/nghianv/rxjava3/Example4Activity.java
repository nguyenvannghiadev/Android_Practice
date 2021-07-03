package com.nghianv.rxjava3;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Example4Activity extends AppCompatActivity {
	private final static String TAG = Example4Activity.class.getSimpleName();
	private CompositeDisposable compositeDisposable = new CompositeDisposable();

	@Override
	protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//
		Observable<String> footballPlayersObservable = getFootballPlayersObservable();
		DisposableObserver<String> mPlayersObserver = getMPlayersObserver();
		DisposableObserver<String> rPlayersObserver = getRPlayersObserver();
		// filter is used to filter
		compositeDisposable.add(
				footballPlayersObservable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.filter(new Predicate<String>() {
							@Override
							public boolean test(@NotNull String s) throws Exception {
								return s.toLowerCase().startsWith("m");
							}
						})
						.subscribeWith(mPlayersObserver)
		);
		//
		compositeDisposable.add(
				footballPlayersObservable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.filter(s -> s.toLowerCase().startsWith("r"))
						.subscribeWith(rPlayersObserver)
		);

	}

	private DisposableObserver<String> getMPlayersObserver() {
		return new DisposableObserver<String>() {
			@Override
			public void onNext(@NotNull String s) {
				Log.d(TAG, "Name: " + s);

			}

			@Override
			public void onError(@NotNull Throwable e) {
				Log.d(TAG, "onError: " + e.getMessage());

			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete: All items are emmited!");
			}
		};
	}

	private DisposableObserver<String> getRPlayersObserver() {
		return new DisposableObserver<String>() {
			@Override
			public void onNext(@NotNull String s) {
				Log.d(TAG, "Name: " + s);
			}

			@Override
			public void onError(@NotNull Throwable e) {
				Log.d(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete: All items are emmited!");

			}
		};
	}

	private Observable<String> getFootballPlayersObservable() {
		return Observable.fromArray("Messi", "Ronaldo", "Modric", "Salah", "Manadona");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// don't send events once the activity is destroyed
		compositeDisposable.clear();
	}
}
