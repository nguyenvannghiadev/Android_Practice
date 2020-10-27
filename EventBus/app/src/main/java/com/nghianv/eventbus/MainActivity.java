package com.nghianv.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nghianv.eventbus.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = findViewById(R.id.list_view);
		final String[] items = {"Android", "IOS", "Phython", "Java", "Kotlin", "C#"};
		ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
		listView.setAdapter(itemsAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Post event
				EventBus.getDefault().post(new MessageEvent(items[position]));
			}
		});
	}

	//Ham nay duoc goi sau khi event duoc post
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(MessageEvent event) {
		Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Dang ki lang nghe
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onStop() {
		// Huy dang ki lang nghe
		EventBus.getDefault().unregister(this);
		super.onStop();
	}
}