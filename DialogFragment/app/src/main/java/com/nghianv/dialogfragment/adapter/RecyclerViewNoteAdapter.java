package com.nghianv.dialogfragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nghianv.dialogfragment.MainActivity;
import com.nghianv.dialogfragment.R;
import com.nghianv.dialogfragment.listener.RecyclerViewListener;
import com.nghianv.dialogfragment.model.Job;

import java.util.List;

public class RecyclerViewNoteAdapter extends RecyclerView.Adapter<RecyclerViewNoteAdapter.JobHolder>{
	private MainActivity context;
	private List<Job> jobList;
	private RecyclerViewListener recyclerViewListener;

	public RecyclerViewNoteAdapter(MainActivity context, List<Job> jobList, RecyclerViewListener recyclerViewListener) {
		this.context = context;
		this.jobList = jobList;
		this.recyclerViewListener = recyclerViewListener;
	}

	@NonNull
	@Override
	public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_row_job, parent, false);

		return new JobHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull JobHolder holder, int position) {
		final Job job = jobList.get(position);
		holder.tvNamJob.setText(job.getNameJob());
		holder.tvIdJob.setText(job.getIdJob() + "");
		String creatDay = job.getCreatDate();
		holder.tvCreatDay.setText(creatDay);
		//
		holder.layoutInfor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, job.getNameJob(), Toast.LENGTH_SHORT).show();
				recyclerViewListener.updateJobListener(job.getNameJob(),job.getIdJob());
			}
		});

		holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recyclerViewListener.deleteJobListener(job.getNameJob(),job.getIdJob());

			}
		});
	}

	@Override
	public int getItemCount() {
		return jobList.size();
	}

	class JobHolder extends RecyclerView.ViewHolder {
		TextView tvNamJob, tvCreatDay, tvIdJob;
		RelativeLayout layoutDelete;
		LinearLayout layoutInfor;

		public JobHolder(@NonNull View itemView) {
			super(itemView);
			tvNamJob = itemView.findViewById(R.id.tv_namJob);
			tvCreatDay = itemView.findViewById(R.id.tv_creat_day);
			tvIdJob = itemView.findViewById(R.id.tv_id_job);
			layoutDelete = itemView.findViewById(R.id.btn_delete);
			layoutInfor = itemView.findViewById(R.id.layout_info_job);
		}
	}
}
