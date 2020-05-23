package com.nghianv.musiclibrary.model;

public class Album {
	private int albumID;
	private String albumName;
	private String albumArtist;
	private int numOfSong;

	public Album() {
	}

	public Album(int albumID, String albumName, String albumArtist, int numOfSong) {
		this.albumID = albumID;
		this.albumName = albumName;
		this.albumArtist = albumArtist;
		this.numOfSong = numOfSong;

	}

	public int getAlbumID() {
		return albumID;
	}

	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getAlbumArtist() {
		return albumArtist;
	}

	public void setAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
	}

	public int getNumOfSong() {
		return numOfSong;
	}

	public void setNumOfSong(int numOfSong) {
		this.numOfSong = numOfSong;
	}
}
