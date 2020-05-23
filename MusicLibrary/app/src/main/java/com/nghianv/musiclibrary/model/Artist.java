package com.nghianv.musiclibrary.model;

public class Artist {
	private int artistId;
	private String artistName;
	private int numOfAlbum;
	private int numOfTracks;

	public Artist() {
	}

	public Artist(int artistId, String artistName, int numOfAlbum, int numOfTracks) {
		this.artistId = artistId;
		this.artistName = artistName;
		this.numOfAlbum = numOfAlbum;
		this.numOfTracks = numOfTracks;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public int getNumOfAlbum() {
		return numOfAlbum;
	}

	public void setNumOfAlbum(int numOfAlbum) {
		this.numOfAlbum = numOfAlbum;
	}

	public int getNumOfTracks() {
		return numOfTracks;
	}

	public void setNumOfTracks(int numOfTracks) {
		this.numOfTracks = numOfTracks;
	}
}
