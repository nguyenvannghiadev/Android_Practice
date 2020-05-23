package com.nghianv.musiclibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.nghianv.musiclibrary.R;
import com.nghianv.musiclibrary.media.MediaManager;
import com.nghianv.musiclibrary.model.Album;
import com.nghianv.musiclibrary.model.Genres;

import java.util.List;

public class GenresAdapter extends BaseAdapter {
	private Context context;
	private List<Genres> mGenresList;

	public GenresAdapter(Context context) {
		this.context = context;
		this.mGenresList = fetchDataGenres();
	}

	private List<Genres> fetchDataGenres() {
		return MediaManager.getInstance(context).getAllGenres();
	}

	@Override
	public int getCount() {
		return mGenresList.size();
	}

	@Override
	public Object getItem(int position) {
		return mGenresList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GenresViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
			holder = new GenresViewHolder();
			holder.genresName = convertView.findViewById(R.id.tv_title_album);
			holder.genresID = convertView.findViewById(R.id.tv_title_name_artist);
			convertView.setTag(holder);
		} else {
			holder = (GenresViewHolder) convertView.getTag();
		}
		//
		Genres genres = mGenresList.get(position);
		holder.genresName.setText(genres.getGenresName());
		holder.genresID.setText(genres.getGenresId() + "");
		return convertView;
	}
	class GenresViewHolder{
		TextView genresName;
		TextView genresID;
	}
}
