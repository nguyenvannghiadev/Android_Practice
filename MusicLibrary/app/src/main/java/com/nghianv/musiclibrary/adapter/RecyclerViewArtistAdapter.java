package com.nghianv.musiclibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nghianv.musiclibrary.R;
import com.nghianv.musiclibrary.model.Artist;

import java.util.List;

public class RecyclerViewArtistAdapter extends RecyclerView.Adapter<RecyclerViewArtistAdapter.ArtistViewHolder> {
	private Context context;
	private List<Artist> mArtistList;

	public RecyclerViewArtistAdapter(Context context) {
		this.context = context;
	}

	@NonNull
	@Override
	public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);

		return new ArtistViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
		Artist artist = mArtistList.get(position);
		holder.tvArtistName.setText(artist.getArtistName());
		holder.tvArtistInfo.setText(artist.getNumOfAlbum() + " Albums   " + artist.getNumOfTracks() + " bài hát");
	}

	@Override
	public int getItemCount() {
		return mArtistList.size();
	}

	public void setArtistList(List<Artist> artistList) {
		this.mArtistList = artistList;
	}

	class ArtistViewHolder extends RecyclerView.ViewHolder {
		TextView tvArtistName;
		TextView tvArtistInfo;

		public ArtistViewHolder(@NonNull View itemView) {
			super(itemView);
			tvArtistName = itemView.findViewById(R.id.tv_item_name_song);
			tvArtistInfo = itemView.findViewById(R.id.tv_item_artist_song);
		}
	}
}
