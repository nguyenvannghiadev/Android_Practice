package com.nghianv.musiclibrary.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nghianv.musiclibrary.R;
import com.nghianv.musiclibrary.listener.OnPlayMusic;
import com.nghianv.musiclibrary.media.MediaManager;
import com.nghianv.musiclibrary.model.Song;

import java.util.List;

public class RecyclerViewSongAdapter extends RecyclerView.Adapter<RecyclerViewSongAdapter.SongViewHolder>{
	private Context context;
	private List<Song> mSongList;
	private OnPlayMusic onPlayMusic;

	public RecyclerViewSongAdapter(Context context, OnPlayMusic onPlayMusic) {
		this.context = context;
		this.mSongList = fetchDataSong();
		MediaManager.getInstance(context).setmListSong(mSongList);
		this.onPlayMusic = onPlayMusic;
	}

	@NonNull
	@Override
	public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
		return new SongViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull SongViewHolder holder, final int position) {
		Song song = mSongList.get(position);
		String nameSong = song.getDisplayName();
//		if (nameSong.indexOf("_") > 0) {
//			nameSong = nameSong.substring(0, nameSong.indexOf("_"));
//		}
		holder.nameSong.setText(nameSong);
		holder.nameArtistSong.setText(song.getArtist());
		holder.linearLayoutItemSong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onPlayMusic.playSong(mSongList,position);
				MediaManager.getInstance(context).setCurrentSongIndex(position);

			}
		});

	}

	@Override
	public int getItemCount() {
		return mSongList.size();
	}

	public List<Song> fetchDataSong() {
		return MediaManager.getInstance(context).getAllAudioFilesExternal(null, null);
	}

	class SongViewHolder extends RecyclerView.ViewHolder {
		private LinearLayout linearLayoutItemSong;
		private TextView nameSong;
		private TextView nameArtistSong;

		public SongViewHolder(@NonNull View itemView) {
			super(itemView);
			nameSong = itemView.findViewById(R.id.tv_item_name_song);
			nameArtistSong = itemView.findViewById(R.id.tv_item_artist_song);
			linearLayoutItemSong = itemView.findViewById(R.id.ll_item_song);
		}
	}
}
