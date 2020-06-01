package com.nghianv.dialogfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nghianv.dialogfragment.adapter.RecyclerViewNoteAdapter;
import com.nghianv.dialogfragment.model.Job;

import java.util.ArrayList;
import java.util.List;

import static com.nghianv.dialogfragment.MainActivity.database;
import static com.nghianv.dialogfragment.MainActivity.nameTableDatabase;

public class SearchActivity extends AppCompatActivity {
	private final String TAG = "SearchActivity";
	private EditText edtSearch;
	private ImageView imgClose;
	private RecyclerViewNoteAdapter adapterSearch;
	private List<Job> searchList;
	private RecyclerView rvSearch;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		intView();
		setOnClick();
	}

	private void intView() {
		edtSearch = findViewById(R.id.edt_search);
		imgClose = findViewById(R.id.img_close);
		rvSearch = findViewById(R.id.rv_search);
		searchList = new ArrayList<>();
		adapterSearch = new RecyclerViewNoteAdapter(this, searchList);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		rvSearch.setAdapter(adapterSearch);
		rvSearch.setLayoutManager(linearLayoutManager);
		//
	}
	private void setOnClick() {
		edtSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String searchKey = edtSearch.getText().toString().trim();
				adapterSearch.isClickable= false;
				getSearchList(searchKey);
			}
		});
		imgClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void getSearchList(String keySearch) {
		if (adapterSearch == null) {
			adapterSearch = new RecyclerViewNoteAdapter(this, searchList);
			rvSearch.setAdapter(adapterSearch);
		}else{
			try {
				if (!TextUtils.isEmpty(keySearch)) {
					Cursor dataJob = database.getData("SELECT * FROM " + nameTableDatabase + " WHERE nameJob = '" + keySearch + "'");
					searchList.clear();
					while (dataJob.moveToNext()) {
						int id = dataJob.getInt(0);
						String namJob = dataJob.getString(1);
						String date = dataJob.getString(2);
						searchList.add(new Job(id, namJob, date));
					}
					if (searchList.size() == 0) {
						Toast.makeText(this, "khong tim thay ket qua", Toast.LENGTH_SHORT).show();
					}
					adapterSearch.notifyDataSetChanged();
				}else {
					Toast.makeText(this, "Plese input keysearch", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
