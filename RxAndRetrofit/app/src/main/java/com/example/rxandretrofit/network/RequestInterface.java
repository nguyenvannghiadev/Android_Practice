package com.example.rxandretrofit.network;


import com.example.rxandretrofit.model.Android;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RequestInterface {


   @GET("android/jsonarray/")
   Observable<List<Android>> register();

}
