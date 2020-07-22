package com.example.jobserviceex.model;

public class Notify {
	private String idNotify;
	private int contentNotity;
	private String description;


	public Notify() {
	}

	public Notify(String idNotify, int contentNotity,String description) {
		this.idNotify = idNotify;
		this.contentNotity = contentNotity;
		this.description = description;

	}

	public String getIdNotify() {
		return idNotify;
	}

	public void setIdNotify(String idNotify) {
		this.idNotify = idNotify;
	}

	public int getContentNotity() {
		return contentNotity;
	}

	public void setContentNotity(int contentNotity) {
		this.contentNotity = contentNotity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
