package com.example.rxjava4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class ExampleActivity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        compositeDisposable = CompositeDisposable()
        //
        getFootballPlayers()
        //
        buttonClick.setOnClickListener { startExampleActivity() }
    }

    private fun startExampleActivity() {
        val intent = Intent(this, Example2Activity::class.java)
        startActivity(intent)
    }

    private fun getFootballPlayers() {
        compositeDisposable?.add(
            Observable.just("Messi", "Ronaldo", "Modric", "Manadona", "Salah", "Mbappe")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { player ->
                        Log.d(TAG, "Name: ${player}")
                    },
                    { error ->
                        Log.d(TAG, "Error: ${error.printStackTrace()}")
                    },
                    { Log.d(TAG, "onComplete: All items are emitted!") }
                )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    companion object {
        const val TAG: String = "ExampleActivity"
    }
}