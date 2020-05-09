package com.nghianv.dev1_ringtone_okhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nghianv.dev1_ringtone_okhttp.adapter.RecyclerviewRingtoneAdapter;
import com.nghianv.dev1_ringtone_okhttp.model.Ringtone;
import com.nghianv.dev1_ringtone_okhttp.model.SeverInfo;
import com.nghianv.dev1_ringtone_okhttp.model.SeverInfoJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
	private List<Ringtone> mListRingtone;
	private RecyclerviewRingtoneAdapter mRingtoneAdapter;
	private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//
		mListRingtone = new ArrayList<>();
		new FetchRingtoneAsyncTask().execute("https://defaultring.phungquangnam.name.vn/defaultringtones/restcache/defaultrings/fr2019secv41");
		mRingtoneAdapter = new RecyclerviewRingtoneAdapter(this, mListRingtone);

		recyclerView = findViewById(R.id.recycleview);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setAdapter(mRingtoneAdapter);
	}

	private class FetchRingtoneAsyncTask extends AsyncTask<String, Void, List<Ringtone>> {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(15, TimeUnit.SECONDS)
				.retryOnConnectionFailure(true)
				.build();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			findViewById(R.id.progress_bar_loading).setVisibility(View.VISIBLE);
		}

		@Override
		protected List<Ringtone> doInBackground(String... agrs) {
			String link = agrs[0];
			Request.Builder builder = new Request.Builder();
			builder.url(link);
			Request request = builder.build();
			//
			try {
				Response response = okHttpClient.newCall(request).execute();
				List<Ringtone> ringtone = parseJson(response.body().string());
				return ringtone;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}

		private List<Ringtone> parseJson(String json) {
			List<Ringtone> ringtones = new ArrayList<>();

			try {
				Gson gson = new Gson();
				SeverInfoJson severInfoJson = gson.fromJson(json, SeverInfoJson.class);
				SeverInfo severInfo = severInfoJson.getSeverInfo().get(0);
				List<Ringtone> ringtoneList = severInfo.getRingtones();
				for (int i = 0; i < ringtoneList.size(); i++) {
					Ringtone ringtone = ringtoneList.get(i);
					int id = ringtone.getId();
					String name = ringtone.getName();
					String count = ringtone.getCount();
					String path = ringtone.getPath();
					Ringtone ring = new Ringtone(id, name, count, path);
					ringtones.add(ring);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ringtones;
		}

		@Override
		protected void onPostExecute(List<Ringtone> ringtones) {
			super.onPostExecute(ringtones);
			findViewById(R.id.progress_bar_loading).setVisibility(View.GONE);
			mListRingtone.clear();
			mListRingtone.addAll(ringtones);
			mRingtoneAdapter.notifyDataSetChanged();
		}
	}
}
