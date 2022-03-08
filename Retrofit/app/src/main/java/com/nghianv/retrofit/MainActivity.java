package com.nghianv.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
	private DemoService demoService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		demoService = DemoRetrofit.getInstance().create(DemoService.class);
	}

	public void httpGet(View view) {
		demoService.getPost().enqueue(new Callback<List<User>>() {
			@Override
			public void onResponse(Call<List<User>> call, Response<List<User>> response) {
				List<User> list = response.body();
			}

			@Override
			public void onFailure(Call<List<User>> call, Throwable t) {

			}
		});
	}

	public void httpPost(View view) {
		demoService.createUser("1", "Hello", "Huy Nguyen").enqueue(new Callback<User>() {
			@Override
			public void onResponse(Call<User> call, Response<User> response) {

			}

			@Override
			public void onFailure(Call<User> call, Throwable t) {

			}
		});
	}
}