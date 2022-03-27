package com.nghianv.rxjava3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscribers.StrictSubscriber;
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
		footballPlayersObservable
				.subscribeOn(Schedulers.io())
				.filter(new Predicate<String>() {
					@Override
					public boolean test(@NotNull String s) throws Exception {
						return s.toLowerCase().startsWith("m");
					}
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						disposable = d;
						Log.d(TAG, "onSubscribe");
					}

					@Override
					public void onNext(String s) {
						Log.d(TAG, "Name: " + s);
					}

					@Override
					public void onError(Throwable e) {
						Log.d(TAG, "onError: " + e.getMessage());
					}

					@Override
					public void onComplete() {
						Log.d(TAG, "onComplete: All FootballPlayer are emmited");
					}
				});

	}

	@SuppressLint("CheckResult")
	private void loadAndSurcribe2() {
		Observable.just("VietNam", "England", "US", "Canana", "France", "Japan")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<String>() {
					           @Override
					           public void onSubscribe(Disposable d) {
						           disposable = d;
						           Log.d(TAG, "onSubscribe");
					           }

					           @Override
					           public void onNext(String s) {
						           Log.d(TAG, "Country2 >>: " + s);
					           }

					           @Override
					           public void onError(Throwable e) {
						           Log.d(TAG, "onError: " + e.getMessage());
					           }

					           @Override
					           public void onComplete() {
						           Log.d(TAG, "onComplete: All Countries are emitted");
					           }
				           }
				);
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

	@SuppressLint("CheckResult")
	public void printChar(String s) {
		Observable.range(0, 9)
				.filter(it -> {
					return it % 2 == 1;
				})
				.map(it -> it.toString())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(string -> printChar(string));

	}

	@SuppressLint("CheckResult")
	private void oparatorMap() {
		Observable.just("Hello, world!")
				.map(
						s -> s + "-Dan"
				).subscribe(System.out::println).dispose();
		Observable.just("Hello!")
				.map(
				String::hashCode
		).subscribe(System.out::println);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// don't send events once the activity is destroy
		disposable.dispose();
	}
}