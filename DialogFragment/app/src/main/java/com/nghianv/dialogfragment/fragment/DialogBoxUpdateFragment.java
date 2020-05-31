package com.nghianv.dialogfragment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nghianv.dialogfragment.R;
import com.nghianv.dialogfragment.listener.DialogUpdateFragmentListener;

public class DialogBoxUpdateFragment extends DialogFragment {
	private final String TAG = "DialogBoxUpdateFragment";
	private EditText edtNamJob;
	private Button btnSave;
	private String oldName;
	private int idJob;
	private DialogUpdateFragmentListener fragmentListener;
	//

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof DialogUpdateFragmentListener) {
			fragmentListener = (DialogUpdateFragmentListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement DialogUpdateFragmentListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		fragmentListener = null;

	}
	//

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_custom, container, false);
		fragmentListener = (DialogUpdateFragmentListener) getActivity();
		// get Intent nameOld
		iniView(view);
		if (getArguments() != null) {
			oldName = getArguments().getString("nameJobUpdate");
			edtNamJob.setText(oldName);
			idJob = getArguments().getInt("idJob");
		}
		setOnClick();
		return view;
	}

	private void setOnClick() {
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Click btn Save", Toast.LENGTH_SHORT).show();
				//
				String nameJobUpdate = edtNamJob.getText().toString().trim();
				fragmentListener.updateNameJob(nameJobUpdate, idJob);
				dismiss();
		}
	});
}

	private void iniView(View view) {
		edtNamJob = view.findViewById(R.id.edt_name_job);
		btnSave = view.findViewById(R.id.btn_save);
	}
}
