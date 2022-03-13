package com.example.rxandretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rxandretrofit.adapter.DataAdapter;
import com.example.rxandretrofit.model.Android;
import com.example.rxandretrofit.network.RequestInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
	public static final String BASE_URL = "https://api.learn2crack.com/";
	private RecyclerView mRecyclerView;
	private CompositeDisposable compositeDisposable;
	private DataAdapter dataAdapter;
	private ArrayList<Android> mAndroidArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//
		compositeDisposable = new CompositeDisposable();
		//
		initRecyclerView();
		//
		loadJson();
	}

	private void initRecyclerView() {
		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		mRecyclerView.setHasFixedSize(true);
	}

	private void loadJson() {
		RequestInterface requestInterface = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(RequestInterface.class);

		Disposable disposable = requestInterface.register()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::handleResponse, this::handleError, this::handleSuccess);

		//     subscribe(response -> handleResponse(response), error -> handleError(error), compelete -> handleSuccess())
		compositeDisposable.add(disposable);
	}

	private void handleResponse(List<Android> androidList) {
		Log.d(">>>>>>>>>>>", "handleResponse");
		mAndroidArrayList = new ArrayList<>(androidList);
		dataAdapter = new DataAdapter(mAndroidArrayList);
		mRecyclerView.setAdapter(dataAdapter);
	}

	private void handleError(Throwable error) {
		Log.d(">>>>>>>>>>>", "handleError");
		Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
	}

	private void handleSuccess() {
		Log.d(">>>>>>>>>>>", "handleSuccess");
		Toast.makeText(this, "Get data success!", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		compositeDisposable.clear();
	}
}