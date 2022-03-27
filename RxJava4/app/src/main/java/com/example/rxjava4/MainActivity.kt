package com.example.rxjava4

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Flowable
import io.reactivex.FlowableSubscriber
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        buttonClick.setOnClickListener({ startStream2() })
        demoContcat();
    }

    @SuppressLint("CheckResult")
    private fun demoContcat() {
        val data: MutableList<Int> = mutableListOf()
        Flowable.concat(Flowable.just(getDataLocal(), getDataServer()))
            .subscribe(
                { list ->
                    for (item in list)
                    if (!data.contains(item)) data.add(item)
                },
                { error -> Log.i(TAG, "onError: ${error.printStackTrace()}") },
                {
                    for (item in data) {
                        Log.i(TAG, "onComplete: ${item}")
                    }

                }
            )
    }
    private fun getDataLocal(): Flowable<List<Int>> {
        val data: MutableList<Int> = ArrayList()
        data.add(1)
        data.add(2)
        data.add(3)
        return Flowable.just(data)
    }

    private fun getDataServer(): Flowable<List<Int>> {
        val data: MutableList<Int> = ArrayList()
        data.add(3)
        data.add(4)
        data.add(5)
        return Flowable.just(data)
    }


    private fun startStream() {
        //Create an Observable
        val myObservable = getObservable()

        //Create an Observer
        val myObserver = getObserver()

        // Subscribe myObserver to Observable
        myObservable.subscribe(myObserver)
    }

    @SuppressLint("CheckResult")
    private fun startStream2() {
        val list = listOf("1", "2", "3", "4", "5")

        Observable.fromIterable(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { println("ReceivedList: ${it}") },
                { println("Error: ${it.printStackTrace()}") },
                { println("onComplete Subcribe list") }
            )


        list.toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { println(it) },
                { println("Error: ${it.printStackTrace()}") },
                { println("onComplete") }
            )

        list.toObservable()
            .subscribeBy(
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onComplete = { println("onComplete!") }

            )
    }

    private fun getObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: String) {
                Log.d(TAG, "onNext: $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }

        }
    }

    private fun getObservable(): Observable<String> {
        return Observable.just("1", "2", "3", "4", "5")
    }

}