package com.nghianv.musiclibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nghianv.musiclibrary.R;
import com.nghianv.musiclibrary.media.MediaManager;
import com.nghianv.musiclibrary.model.Album;

import java.util.List;

public class AlbumAdapter extends BaseAdapter {
	private Context context;
	private List<Album> mAlbumList;

	public AlbumAdapter(Context context) {
		this.context = context;
		this.mAlbumList = fetchDataAlbum();
	}
	public List<Album> fetchDataAlbum() {
		return MediaManager.getInstance(context).getAllAlbums(null, null);
	}

	@Override
	public int getCount() {
		return mAlbumList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAlbumList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AlbumViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
			holder = new AlbumViewHolder();
			holder.tvTitleAlbum = convertView.findViewById(R.id.tv_title_album);
			holder.tvTilteNameArtist = convertView.findViewById(R.id.tv_title_name_artist);
			holder.imgAlbum = convertView.findViewById(R.id.icon_image_album);
			convertView.setTag(holder);
		} else {
			holder = (AlbumViewHolder) convertView.getTag();
		}
		//
		final Album album = mAlbumList.get(position);
		holder.tvTitleAlbum.setText(album.getAlbumName());
		holder.tvTilteNameArtist.setText(album.getAlbumArtist());
		holder.imgAlbum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		return convertView;
	}

	class AlbumViewHolder{
		ImageView imgAlbum;
		TextView tvTitleAlbum;
		TextView tvTilteNameArtist;
	}
}
