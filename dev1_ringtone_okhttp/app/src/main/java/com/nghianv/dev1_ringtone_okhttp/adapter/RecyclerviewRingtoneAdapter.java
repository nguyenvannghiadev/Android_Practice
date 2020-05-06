package com.nghianv.dev1_ringtone_okhttp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nghianv.dev1_ringtone_okhttp.R;
import com.nghianv.dev1_ringtone_okhttp.model.Ringtone;

import java.util.List;

public class RecyclerviewRingtoneAdapter extends RecyclerView.Adapter<RecyclerviewRingtoneAdapter.RingtoneViewHolder> {
	private Context mContext;
	private List<Ringtone> mRingtoneList;

	public RecyclerviewRingtoneAdapter(Context mContext, List<Ringtone> mRingtoneList) {
		this.mContext = mContext;
		this.mRingtoneList = mRingtoneList;
	}

	@NonNull
	@Override
	public RingtoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.ringtone_row_list, parent, false);
		return new RingtoneViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RingtoneViewHolder holder, int position) {
		Ringtone ringtone = mRingtoneList.get(position);
		holder.tvNameRingtone.setText(ringtone.getName());
	}

	@Override
	public int getItemCount() {
		return mRingtoneList.size();
	}

	class RingtoneViewHolder extends RecyclerView.ViewHolder {
		private TextView tvNameRingtone;

		public RingtoneViewHolder(@NonNull View itemView) {
			super(itemView);
			tvNameRingtone = itemView.findViewById(R.id.ringtone_name);
		}
	}
}
