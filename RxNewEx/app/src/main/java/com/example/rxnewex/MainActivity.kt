package com.example.rxnewex

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var compositeDisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()
        //
        btnEx1.setOnClickListener ({ startStream() })
        btnEx2.setOnClickListener({ startStream2() })
        btnEx3.setOnClickListener ({ startStream3() })
        btnEx4.setOnClickListener({ startStream4() })
        btnEx5.setOnClickListener(View.OnClickListener { startStream5() })
        bufferButton.setOnClickListener({ startSteam6() })
        mapButton.setOnClickListener({startStream7()})
        btnEx8.setOnClickListener { startStream8() }
        btnZip.setOnClickListener({startStream9()})

    }

    @SuppressLint("CheckResult")
    private fun startStream() {
        val list: List<String> = listOf("1", "2", "3", "4", "5", "6")
        list.toObservable()
            .subscribe(
                { value -> println(value) },
                { error -> println("Error: ${error.printStackTrace()}") },
                { println("onComplete") }
            ).addTo(compositeDisposable)
    }

    private fun startStream2() {
        Observable.just("Hello", "world","How", "are", "you")
            .subscribe(
                {value -> println("Received: $value")},
                { error -> println("Error: $error")},
                { println("onComplete") }
            ).addTo(compositeDisposable)
    }

    private fun startStream3() {
        Observable.fromArray("Apple", "Orange", "Banana")
            .subscribe(
                { value -> println(value) },
                { error -> println("Error: ${error.printStackTrace()}") },
                { println("Done") }
            )
            .addTo(compositeDisposable)
    }

    private fun startStream4() {
        Observable.fromIterable(listOf("Titan", "Fastarck", "Sonata"))
            .subscribe(
                { value -> println("Received: $value") },
                { error -> println("Error: ${error.printStackTrace()}") },
                { println("Done") }
            )
            .addTo(compositeDisposable)
    }


    private fun startStream5() {
        getObservaleFromList(listOf("Summer", "Winter", "MonSoon"))
            .subscribe(
                { value -> println("Received: $value") },
                { error -> println("Error: ") },
                { println("Done") }
            ).addTo(compositeDisposable)
    }

    private fun getObservaleFromList(myList: List<String>) =
        Observable.create<String>({ emmiter ->
            myList.forEach { kind ->
                if (kind.isEmpty()) {
                    emmiter.onError(Exception("Nothing to show"))
                }
                emmiter.onNext(kind)
            }
            emmiter.onComplete()
        })

    private fun startSteam6() {
        Observable.just("A", "B", "C","D", "E", "F", "G", "H")
            .buffer(2)
            .subscribe(
                {value -> println("Received: $value") },
                {error -> println("Error: $error")},
                { println("Done")}
            ).addTo(compositeDisposable)
    }

    private fun startStream7() {
        val observable = Observable.fromArray(1, 2, 3, 4)
        observable.map { value ->
            value * 2
        }.filter {
            it > 2
        }.subscribe(
            { value -> println("Received: $value") },
            { error -> println("Error: ${error.printStackTrace()}") },
            { println("Done") }
        ).addTo(compositeDisposable)
    }

    private fun startStream8() {
        Observable.range(1, 20)
            .filter { value ->
                value % 2 == 0
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value -> println("Received: $value") },
                { error -> println("Error: ${error.printStackTrace()}") },
                { println("Done") }
            ).addTo(compositeDisposable)

    }

    private fun startStream9() {
        val list: List<String> = listOf("1", "2", "3", "4", "5", "6", "7")
        val numObservable = list.toObservable()
        val charObservale = Observable.just("A", "B", "C", "D", "E", "F", "G", "H")
        val zipper = BiFunction<String, String, String> { t1: String, t2: String ->
            "$t1-$t2"
        }
        Observable.zip(numObservable, charObservale, zipper)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value -> println("Received: $value") },
                { error -> println("Error: ${error.printStackTrace()}") },
                { println("Done") }
            ).addTo(compositeDisposable)
    }



    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}