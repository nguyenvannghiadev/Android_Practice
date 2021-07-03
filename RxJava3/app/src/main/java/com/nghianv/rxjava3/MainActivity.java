package com.nghianv.rxjava3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Api {
	private final static String TAG = "MainActivity";
	private Disposable disposable;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//
		//loadAndSubscribe();
		//loadAndSurcribe2();
		loadAndSurcribeFootballPlayer();
	}


	private void loadAndSurcribeFootballPlayer() {
		Observable<String> footballPlayersObservable = Observable.just("Messi", "Ronaldo", "Modric", "Manadona", "Roney");
		Observer<String> footballPlayerObserver = getFootballPlayersObserver();
		footballPlayersObservable
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.filter(new Predicate<String>() {
					@Override
					public boolean test(@NotNull String s) throws Exception {
						return s.toLowerCase().startsWith("m");
					}
				})
				.subscribe(footballPlayerObserver);

	}

	private Observer<String> getFootballPlayersObserver() {
		return new Observer<String>() {
			@Override
			public void onSubscribe(@NotNull Disposable d) {
				disposable = d;
				Log.d(TAG, "onSubscribe");
			}

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
				Log.d(TAG, "onComplete: All FootballPlayer are emmited");

			}
		};
	}

	private void loadAndSurcribe2() {
		Observable<String> countryObservable = Observable.just("VietNam", "England", "US", "Canana", "France", "Japan");
		Observer<String> countryObserver = getCountriesObserver2();
		countryObservable
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(countryObserver);
	}

	private Observer<String> getCountriesObserver2() {
		return new Observer<String>() {
			@Override
			public void onSubscribe(@org.jetbrains.annotations.NotNull Disposable d) {
				Log.d(TAG, "onSubscribe");

			}

			@Override
			public void onNext(@org.jetbrains.annotations.NotNull String s) {
				Log.d(TAG, "Country2 >>: " + s);

			}

			@Override
			public void onError(@org.jetbrains.annotations.NotNull Throwable e) {
				Log.d(TAG, "onError: " + e.getMessage());

			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete: All Countries are emitted");

			}
		};
	}


	private void loadAndSubscribe() {
		Observable<String> countryObservable = Observable.just("Africa", "Belgium", "Canada", "Denmark", "France");
		Observer<String> animalsObserver = getCountriesObserver();
		countryObservable
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(animalsObserver);
	}

	private Observer<String> getCountriesObserver() {
		return new Observer<String>() {
			@Override
			public void onSubscribe(Disposable d) {
				Log.d("MainActivity", "onSubscribe");
			}

			@Override
			public void onNext(String s) {
				Log.d("MainActivity", "Country >>: " + s);
			}

			@Override
			public void onError(Throwable e) {
				Log.e("MainActivity", "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d("MainActivity", "All Countries are emitted!");
			}
		};
	}

	@Override
	public Observable<String> searchMovie(String query) {
		return null;
	}

	@Override
	public List<Movie> parse(String json) {
		return null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// don't send events once the activity is destroy
		disposable.dispose();
	}
}