package com.nghianv.musiclibrary.model;

import java.io.Serializable;

public class Song implements Serializable {
	private String dataPath, title, displayName,album, albumID, artist;
	private int duration;
	private boolean isSelected;

	public Song() {
	}

	public Song(String dataPath, String title, String displayName, String album, String albumID, String artist, int duration, boolean isSelected) {
		this.dataPath = dataPath;
		this.title = title;
		this.displayName = displayName;
		this.album = album;
		this.albumID = albumID;
		this.artist = artist;
		this.duration = duration;
		this.isSelected = isSelected;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAlbumID() {
		return albumID;
	}

	public void setAlbumID(String albumID) {
		this.albumID = albumID;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}
