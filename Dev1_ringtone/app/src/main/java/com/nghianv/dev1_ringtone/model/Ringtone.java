package com.nghianv.dev1_ringtone.model;

public class Ringtone {
	private int id;
	private String name;
	private String count;
	private String path;

	public Ringtone() {
	}

	public Ringtone(int id, String name, String count, String path) {
		this.id = id;
		this.name = name;
		this.count = count;
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
