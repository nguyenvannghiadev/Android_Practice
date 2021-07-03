package com.nghianv.rxjava3;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MovieWrapper extends AppCompatActivity {
	private static final String TAG = MovieWrapper.class.getSimpleName();
	private Disposable disposable;

	@Override
	protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		testCreateObservaleFrom();
	}

	private void testCreateObservaleFrom() {
		Integer[] array = new Integer[10];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		Observable<Integer> observable = Observable.fromArray(array);
		observable.subscribe(new Observer<Integer>() {
			@Override
			public void onSubscribe(@NotNull Disposable d) {
				disposable = d;
			}

			@Override
			public void onNext(@NotNull Integer integer) {
				Log.d(TAG, "onNext: " + integer);
			}

			@Override
			public void onError(@NotNull Throwable e) {
				Log.e(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete: All items emmited");
			}
		});
		//
		List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9,10));
		Observable<Integer> observableFromIterable = Observable.fromIterable(list);
		observableFromIterable.subscribe(new Observer<Integer>() {
			@Override
			public void onSubscribe(@NotNull Disposable d) {
				disposable = d;
			}

			@Override
			public void onNext(@NotNull Integer integer) {
				Log.d(TAG, "onNext: " + integer);
			}

			@Override
			public void onError(@NotNull Throwable e) {
				Log.e(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onCompleteFromIterable: All items emmited");
			}
		});
	}

	private void testCreatObservable2() {
		String[] stringObservable = new String[]{"1", "2", "3", "4"};
		Observable<String[]> database = Observable.just(stringObservable);

		//
		Observer<String[]> observer = new Observer<String[]>() {
			@Override
			public void onSubscribe(@NotNull Disposable d) {
				disposable = d;
			}

			@Override
			public void onNext(@NotNull String[] strings) {
				for (String str : strings) {
					Log.d(TAG, "onNext: " + str);
				}

			}

			@Override
			public void onError(@NotNull Throwable e) {
				Log.e(TAG, "onError: " + e.getMessage());

			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete: All items emmited");
			}
		};
		database.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(observer);
	}

	private void testCreatObservable1() {
		Observable.just(1, 2, 3, 4, 5)
				.filter(integer -> integer % 2 == 1)
				.map((Function<Integer, Object>) Math::sqrt)
				.subscribe(new Observer<Object>() {
					@Override
					public void onSubscribe(@NotNull Disposable d) {
						disposable = d;

					}

					@Override
					public void onNext(@NotNull Object o) {
						Log.d(TAG, "value: " + o);

					}

					@Override
					public void onError(@NotNull Throwable e) {
						Log.e(TAG, "onError: " + e.getMessage());
					}

					@Override
					public void onComplete() {
						Log.d(TAG, "onComplete: All items emmited");

					}
				});
	}

	private void testCreateObservableJust() {
		String greeting = "Hello World";
		Observable<String> observable = Observable.just(greeting);
		observable.subscribe(new Observer<String>() {
			@Override
			public void onSubscribe(@NotNull Disposable d) {
				disposable = d;
			}

			@Override
			public void onNext(@NotNull String s) {
				Log.d(TAG, "Value: " + s);

			}

			@Override
			public void onError(@NotNull Throwable e) {
				Log.d(TAG, "onError: " + e.getMessage());

			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete: All items are emmited!");

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		disposable.dispose();
	}
}
