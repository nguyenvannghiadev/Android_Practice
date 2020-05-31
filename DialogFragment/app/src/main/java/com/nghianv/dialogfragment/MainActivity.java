package com.nghianv.dialogfragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nghianv.dialogfragment.adapter.RecyclerViewNoteAdapter;
import com.nghianv.dialogfragment.database.Database;
import com.nghianv.dialogfragment.fragment.DialogBoxAddFragment;
import com.nghianv.dialogfragment.fragment.DialogBoxUpdateFragment;
import com.nghianv.dialogfragment.listener.DialogAddFragmentListener;
import com.nghianv.dialogfragment.listener.DialogUpdateFragmentListener;
import com.nghianv.dialogfragment.listener.RecyclerViewListener;
import com.nghianv.dialogfragment.model.Job;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DialogUpdateFragmentListener, DialogAddFragmentListener {
	private final String TAG = "MainActivity";
	public static final String BROADCAST_DATA_CHANGE = "dataChange";

	private Database database;
	private static String nameDatabase = "note.sqlite";
	private static String nameTableDatabase = "Note";

	//
	private List<Job> jobList;
	private RecyclerViewNoteAdapter noteAdapter;
	private RecyclerView rvNoteData;
	private ImageView btnAddJod;

	RecyclerViewListener recyclerViewListener = new RecyclerViewListener() {

		@Override
		public void updateJobListener(String nameJob, int idJob) {
			// send namejobOld to DialogFragment -> edit name
			Bundle bundle = new Bundle();
			bundle.putString("nameJobUpdate", nameJob);
			bundle.putInt("idJob", idJob);
			// set Fragmentclass Arguments
			DialogBoxUpdateFragment fragobj = new DialogBoxUpdateFragment();
			fragobj.setArguments(bundle);
			FragmentManager fm = getSupportFragmentManager();
			fragobj.show(fm, null);
		}

		@Override
		public void deleteJobListener(String nameJob, int idJob) {
			deleteJobDialog(nameJob, idJob);
			getDataInDatabase();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//
		rvNoteData = findViewById(R.id.rv_note);
		jobList = new ArrayList<>();
		noteAdapter = new RecyclerViewNoteAdapter(this, jobList, recyclerViewListener);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		rvNoteData.setAdapter(noteAdapter);
		rvNoteData.setLayoutManager(linearLayoutManager);
		//
		database = new Database(this, nameDatabase, null, 1);
		creatTableDatabase();
//		insertDataInDataBase("Code Project");
		getDataInDatabase();
		//
		btnAddJod = findViewById(R.id.btn_add);
		btnAddJod.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialogBoxFragment();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	public void getDataInDatabase() {
		// select data
		Cursor dataJob = database.getData("SELECT * FROM " + nameTableDatabase);
		jobList.clear();
		while (dataJob.moveToNext()) {
			int id = dataJob.getInt(0);
			String namJob = dataJob.getString(1);
			String date = dataJob.getString(2);
			jobList.add(new Job(id, namJob, date));
		}
		noteAdapter.notifyDataSetChanged();
	}

	private void showDialogBoxFragment() {
		try {
			FragmentManager fm = getSupportFragmentManager();
			DialogBoxAddFragment dialogBoxAddFragment = new DialogBoxAddFragment();
			dialogBoxAddFragment.show(fm, null);

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("TAG", "showDialogBoxFragment" + e);
		}
	}

	private void creatTableDatabase() {
		// Create table Note
		database.queryData("CREATE TABLE IF NOT EXISTS " + nameTableDatabase + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, nameJob VARCHAR(200), creatDate VARCHAR(200))");
	}

	private void insertDataInDataBase(String nameQuery) {
		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
		String creatDay = formatter.format(date);
		database.queryData("INSERT INTO '" + nameTableDatabase + "' VALUES(null,'" + nameQuery + "','" + creatDay + "')");
	}

	private void insertDataInDataBase(String nameQuery, int Id) {
		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
		String creatDay = formatter.format(date);
		database.queryData("INSERT INTO '" + nameTableDatabase + "' VALUES('" + Id + "','" + nameQuery + "','" + creatDay + "')");
	}

	private void updateDataInDatabase(String name, int Id) {
		database.queryData("UPDATE " + nameTableDatabase + " SET nameJob = '" + name + "' WHERE Id = '" + Id + "'");
	}

	private void deleteDataInDatabase(int Id) {
		database.queryData("DELETE FROM " + nameTableDatabase + " WHERE Id = '" + Id + "'");
	}

	private void searchDataInDatabase(String nameSearch) {
		database.queryData("SELECT * FROM " + nameTableDatabase + " WHERE nameJob = '" + nameSearch + "'");
	}


	public void deleteJobDialog(final String nameJob, final int Id) {
		final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
		deleteDialog.setMessage("Are you want to delete  " + nameJob + "  ?");
		deleteDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteDataInDatabase(Id);
				Toast.makeText(MainActivity.this, "Delete " + nameJob + "", Toast.LENGTH_SHORT).show();
				getDataInDatabase();
			}
		});

		deleteDialog.setNegativeButton("Cance", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		deleteDialog.show();
	}

	@Override
	public void updateNameJob(String nameJobNew, int idJob) {
		updateDataInDatabase(nameJobNew, idJob);
		getDataInDatabase();
	}

	@Override
	public void insertNewJOb(String namJobNew) {
		int idJobNew = jobList.size() + 1;
		insertDataInDataBase(namJobNew, idJobNew);
		getDataInDatabase();
	}

}
