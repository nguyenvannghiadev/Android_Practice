package com.nghianv.rxjava3;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NoteActivity extends AppCompatActivity {
	private static final String TAG = NoteActivity.class.getSimpleName();
	private CompositeDisposable compositeDisposable = new CompositeDisposable();

	@Override
	protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// add to Composite observable
		// operator map() is used to turn the note into all uppercase letters
		compositeDisposable.add(getNotesObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.map(new Function<Note, Note>() {
					@Override
					public Note apply(@NotNull Note note) throws Exception {
						note.setNote(note.getNote().toLowerCase());
						return note;
					}
				})
				.subscribeWith(getNotesObserver())
		);

	}

	private DisposableObserver<Note> getNotesObserver() {
		return new DisposableObserver<Note>() {
			@Override
			public void onNext(@NotNull Note note) {
				Log.d(TAG, "Note: " + note.getNote());
			}

			@Override
			public void onError(@NotNull Throwable e) {
				Log.d(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete: All notes are emmited!");
			}
		};
	}

	private Observable<Note> getNotesObservable() {
		final List<Note> notes = prepareNotes();
		return Observable.create(new ObservableOnSubscribe<Note>() {
			@Override
			public void subscribe(@NotNull ObservableEmitter<Note> emitter) throws Exception {
				for (Note note : notes) {
					if (!emitter.isDisposed()) {
						emitter.onNext(note);
					}
				}
				if (!emitter.isDisposed()) {
					emitter.onComplete();
				}
			}
		});
	}

	private List<Note> prepareNotes() {
		List<Note> notes = new ArrayList<>();
		notes.add(new Note(1, "Buy tooth paste!"));
		notes.add(new Note(2, "Call brother!"));
		notes.add(new Note(3, "Play football"));
		notes.add(new Note(4, "Watching movie tonight!"));
		notes.add(new Note(5, "Shopping"));
		return notes;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		compositeDisposable.clear();
	}
}
