package com.nghianv.musiclibrary.model;

public class Genres {
	private int genresId;
	private String genresName;

	public Genres(int genresId, String genresName) {
		this.genresId = genresId;
		this.genresName = genresName;
	}

	public int getGenresId() {
		return genresId;
	}

	public void setGenresId(int genresId) {
		this.genresId = genresId;
	}

	public String getGenresName() {
		return genresName;
	}

	public void setGenresName(String genresName) {
		this.genresName = genresName;
	}
}
