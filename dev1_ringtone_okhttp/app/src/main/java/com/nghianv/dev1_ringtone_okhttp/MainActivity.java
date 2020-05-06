package com.nghianv.dev1_ringtone_okhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.nghianv.dev1_ringtone_okhttp.adapter.RecyclerviewRingtoneAdapter;
import com.nghianv.dev1_ringtone_okhttp.model.Ringtone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
			Toast.makeText(MainActivity.this, "Start Downloading", Toast.LENGTH_SHORT).show();
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
/*
				InputStream inputStream = response.body().byteStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				StringBuilder result = new StringBuilder();
				String line = reader.readLine();
				while (line != null) {
					result.append(line);
					line = reader.readLine();
				}
				List<Ringtone> ringtone = parseJson(result.toString());

*/
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
				JSONObject root = new JSONObject(json);
				JSONArray jsonServerInfor = root.getJSONArray("ServerInfo");
				JSONObject object = jsonServerInfor.getJSONObject(0);
				JSONArray jsonRingtone = object.getJSONArray("ringtones");
				for (int i = 0; i < jsonRingtone.length(); i++) {
					JSONObject ringtone = jsonRingtone.getJSONObject(i);
					int id = ringtone.getInt("id");
					String name = ringtone.getString("name");
					String count = ringtone.getString("count");
					String path = ringtone.getString("path");
					Ringtone ring = new Ringtone(id, name, count, path);
					ringtones.add(ring);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return ringtones;
		}

		@Override
		protected void onPostExecute(List<Ringtone> ringtones) {
			super.onPostExecute(ringtones);
			mListRingtone.clear();
			mListRingtone.addAll(ringtones);
			mRingtoneAdapter.notifyDataSetChanged();
		}
	}
}
