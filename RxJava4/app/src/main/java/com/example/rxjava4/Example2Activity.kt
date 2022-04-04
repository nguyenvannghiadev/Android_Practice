package com.example.rxjava4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class Example2Activity : AppCompatActivity() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example2)

        // add to Composite observable
        // .map operator is used to turn the note into all uppercase letters
        compositeDisposable.add(getNotesObservable()
            .subscribeOn(Schedulers.io())
            .map { note ->
                note.note = note.note.toUpperCase(Locale.ROOT)
                note
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getNoteObserver())
        )
    }

    private fun getNoteObserver(): DisposableObserver<Note> {
        return object : DisposableObserver<Note>() {
            override fun onNext(note: Note) {
                Log.d(TAG, "Note: " + note.note)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete: All notes are emitted!");
            }

        }
    }

    private fun getNotesObservable(): Observable<Note> {
        val notes: MutableList<Note> = prepareNotes()
        return Observable.create(ObservableOnSubscribe { emitter ->
            for (note in notes) {
                if (!emitter.isDisposed) {
                    emitter.onNext(note)
                }
            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        })
    }

    private fun prepareNotes(): MutableList<Note> {
        val notes: MutableList<Note> = mutableListOf()
        notes.add(Note(1, "Buy tooth paste!"))
        notes.add(Note(2, "Call brother!"))
        notes.add(Note(3, "Watch narcos tonight!"))
        notes.add(Note(4, "Pay power bill!"))
        return notes
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        const val TAG: String = "Example2Activity"
    }

}