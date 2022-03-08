package com.nghianv.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DemoRetrofit {
	public static Retrofit retrofit;

	public static Retrofit getInstance() {
		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
					.baseUrl("https://jsonplaceholder.typicode.com/")
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}
		return retrofit;
	}
}
