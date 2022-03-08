package com.nghianv.retrofit;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DemoService {

	// https://jsonplaceholder.typicode.com/posts
	@GET("posts")
	Call<List<User>> getPost();

	// https://jsonplaceholder.typicode.com/posts/todos/1
	@GET("todos/{id}")
	Call<ResponseBody> getUserInfo(@Path("id") String id);

	@FormUrlEncoded
	@POST("posts/")
	Call<User> createUser(@Field("userId") String id,
	                              @Field("title") String title,
	                              @Field("body") String body);
}
