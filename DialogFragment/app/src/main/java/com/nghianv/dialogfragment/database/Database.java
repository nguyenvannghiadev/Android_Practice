package com.nghianv.dialogfragment.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
	public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	//truy van khong tra ve ket qua: CREAT,INSERT, UPDATE, DELETE...
	public void queryData(String sql) {
		SQLiteDatabase database = getWritableDatabase();
		database.execSQL(sql);
	}

	// truy van tra ve ket qua: SELECT
	public Cursor getData(String sql) {
		SQLiteDatabase database = getWritableDatabase();
		return database.rawQuery(sql, null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
