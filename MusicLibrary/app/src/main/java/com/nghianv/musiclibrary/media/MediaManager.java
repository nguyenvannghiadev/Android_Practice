package com.nghianv.musiclibrary.media;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.nghianv.musiclibrary.common.Const;
import com.nghianv.musiclibrary.model.Album;
import com.nghianv.musiclibrary.model.Artist;
import com.nghianv.musiclibrary.model.Genres;
import com.nghianv.musiclibrary.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MediaManager {
	private static final String TAG = "MediaManager";
	private static final int PERMISION_ALL = 265;
	//
	private List<Song> mListSong;
	private Context mContext;
	private MediaPlayer mediaPlayer;
	//this is current song play.
	private int currentSongIndex = 0;
	//this is state of mediaplayer
	private int mediaStatus = Const.STATUS_IDLE;
	private static MediaManager mediaManager;

	public static MediaManager getInstance(Context context) {
		if (mediaManager == null) {
			return new MediaManager(context);
		}
		return mediaManager;
	}

	public MediaManager(Context mContext) {
		this.mContext = mContext;
		initMediaPlayer();
	}

	private void initMediaPlayer() {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				mediaStatus = Const.STATUS_PLAYING;
			}
		});
	}

	public void playSong() {
		if (mediaStatus == Const.STATUS_IDLE || mediaStatus == Const.STATUS_STOP) {
			try {
				mediaPlayer.reset();
				Song song = mListSong.get(currentSongIndex);
				mediaPlayer.setDataSource(song.getDataPath());
				mediaPlayer.prepare();
				mediaPlayer.start();

				//
				mediaStatus = Const.STATUS_PLAYING;
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, "playSong(): " + e);
			}
		} else if (mediaStatus == Const.STATUS_PAUSE) {
			mediaPlayer.start();
			mediaStatus = Const.STATUS_PLAYING;
		} else if (mediaStatus == Const.STATUS_PLAYING) {
			mediaPlayer.pause();
			mediaStatus = Const.STATUS_PAUSE;
		}
	}

	public void stop() {
		if (mediaStatus == Const.STATUS_IDLE) {
			return;
		}
		mediaPlayer.stop();
		mediaStatus = Const.STATUS_STOP;
	}

	public void next() {
		if (currentSongIndex >= mListSong.size() - 1) {
			currentSongIndex = 0;
		} else {
			currentSongIndex++;
		}
		playSong();
	}

	public void previous() {
		if (currentSongIndex <= 0) {
			currentSongIndex = mListSong.size() - 1;
		} else {
			currentSongIndex--;
		}
		playSong();
	}

	public List<Song> getmListSong() {
		return mListSong;
	}

	public void setmListSong(List<Song> mListSong) {
		this.mListSong = mListSong;
	}

	public int getCurrentSongIndex() {
		return currentSongIndex;
	}

	public void setCurrentSongIndex(int currentSongIndex) {
		this.currentSongIndex = currentSongIndex;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public List<Song> getAllAudioFilesExternal(String filed, String value[]) {
		mListSong = new ArrayList<>();

		String coloumsName[] = new String[]{
				MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.ALBUM_ID,
				MediaStore.Audio.Media.ARTIST};

		if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE};
			ActivityCompat.requestPermissions((Activity) mContext, PERMISSION, PERMISION_ALL);
		} else {
			Cursor cursor = mContext.getContentResolver()
					.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, coloumsName, filed, value, null, null);
			if (cursor == null) {
				return null;
			}
			//
			int indexDataPath = cursor.getColumnIndex(coloumsName[0]);
			int indexTitle = cursor.getColumnIndex(coloumsName[1]);
			int indexDisplayName = cursor.getColumnIndex(coloumsName[2]);
			int indexDuration = cursor.getColumnIndex(coloumsName[3]);
			int indexAlbum = cursor.getColumnIndex(coloumsName[4]);
			int indexAlbumID = cursor.getColumnIndex(coloumsName[5]);
			int indexArtist = cursor.getColumnIndex(coloumsName[6]);
			//
			String dataPath, title, displayName, album, albumID, artist;
			int duration;
			cursor.moveToFirst();
			mListSong.clear();
			while (!cursor.isAfterLast()) {
				dataPath = cursor.getString(indexDataPath);
				title = cursor.getString(indexTitle);
				displayName = cursor.getString(indexDisplayName);
				duration = cursor.getInt(indexDuration);
				album = cursor.getString(indexAlbum);
				albumID = cursor.getString(indexAlbumID);
				artist = cursor.getString(indexArtist);

				Song song = new Song(dataPath, title, displayName, album, albumID, artist, duration, false);
				mListSong.add(song);
				cursor.moveToNext();
			}
			cursor.close();
		}
		Log.d(">>>>>", "mListSongSize: " + mListSong.size());
		return mListSong;
	}

	public List<Album> getAllAlbums(String filed, String value[]) {
		List<Album> mListAlbum = new ArrayList<>();
		String coloumsName[] = new String[]{
				MediaStore.Audio.Albums._ID,
				MediaStore.Audio.Albums.ALBUM,
				MediaStore.Audio.Albums.ARTIST,
				MediaStore.Audio.Albums.NUMBER_OF_SONGS};
		if (ActivityCompat.checkSelfPermission(mContext,
				Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE};
			ActivityCompat.requestPermissions((Activity) mContext, PERMISSION, PERMISION_ALL);
		} else {
			Cursor cursor = mContext.getContentResolver()
					.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, coloumsName, filed, value, null);
			if (cursor == null) {
				return null;
			}
			int indexAlbumID = cursor.getColumnIndex(coloumsName[0]);
			int indexAlbum = cursor.getColumnIndex(coloumsName[1]);
			int indexAlbumArtist = cursor.getColumnIndex(coloumsName[2]);
			int indexNumofSongs = cursor.getColumnIndex(coloumsName[3]);
			String albumName, albumArtist;
			int numofSongs, albumID;
			cursor.moveToFirst();
			mListAlbum.clear();
			//
			while (!cursor.isAfterLast()) {
				albumID = cursor.getInt(indexAlbumID);
				albumName = cursor.getString(indexAlbum);
				albumArtist = cursor.getString(indexAlbumArtist);
				numofSongs = cursor.getInt(indexNumofSongs);
				//
				Album album = new Album(albumID, albumName, albumArtist, numofSongs);
				mListAlbum.add(album);
				//
				cursor.moveToNext();
			}
			cursor.close();
		}
		Log.d(">>>>>", "mListAlbum: " + mListAlbum.size());
		return mListAlbum;
	}

	public List<Artist> getAllArtist() {
		List<Artist> artirstList = new ArrayList<>();
		String coloumsName[] = new String[]{
				MediaStore.Audio.Artists._ID,
				MediaStore.Audio.Artists.ARTIST,
				MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
				MediaStore.Audio.Artists.NUMBER_OF_TRACKS};
		if (ActivityCompat.checkSelfPermission(mContext,
				Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE};
			ActivityCompat.requestPermissions((Activity) mContext, PERMISSION, PERMISION_ALL);
		} else {
			Cursor cursor = mContext.getContentResolver()
					.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
							coloumsName, null, null, null);

			if (cursor == null) {
				return null;
			}
			int indexArtistID = cursor.getColumnIndex(coloumsName[0]);
			int indexArtist = cursor.getColumnIndex(coloumsName[1]);
			int indexNumOfAlbums = cursor.getColumnIndex(coloumsName[2]);
			int indexNumOfTracks = cursor.getColumnIndex(coloumsName[3]);
			String artistName;
			int idArtist, numOfAlbums, numOfTracks;
			cursor.moveToFirst();
			artirstList.clear();
			while (!cursor.isAfterLast()) {
				idArtist = cursor.getInt(indexArtistID);
				artistName = cursor.getString(indexArtist);
				numOfAlbums = cursor.getInt(indexNumOfAlbums);
				numOfTracks = cursor.getInt(indexNumOfTracks);
				Artist artirst = new Artist(idArtist, artistName, numOfAlbums, numOfTracks);
				artirstList.add(artirst);
				cursor.moveToNext();
			}
			cursor.close();
		}
		Log.d(">>>>>", "artirstList: " + artirstList.size());
		return artirstList;
	}

	public List<Genres> getAllGenres() {
		List<Genres> genresList = new ArrayList<>();
		String coloumsName[] = new String[]{
				MediaStore.Audio.Genres._ID,
				MediaStore.Audio.Genres.NAME,};
		if (ActivityCompat.checkSelfPermission(mContext,
				Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE};
			ActivityCompat.requestPermissions((Activity) mContext, PERMISSION, PERMISION_ALL);
		} else {
			Cursor cursor = mContext.getContentResolver()
					.query(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,
							coloumsName, null, null, null);

			if (cursor == null) {
				return null;
			}
			int indexGenresID = cursor.getColumnIndex(coloumsName[0]);
			int indexGenresName = cursor.getColumnIndex(coloumsName[1]);
			int genresID;
			String genresName;
			cursor.moveToFirst();
			genresList.clear();
			while (!cursor.isAfterLast()) {
				genresID = cursor.getInt(indexGenresID);
				genresName = cursor.getString(indexGenresName);
				Genres genres = new Genres(genresID, genresName);
				genresList.add(genres);
				cursor.moveToNext();
			}
			cursor.close();
		}
		Log.d(">>>>>", "genresList: " + genresList.size());
		return genresList;
	}
}
