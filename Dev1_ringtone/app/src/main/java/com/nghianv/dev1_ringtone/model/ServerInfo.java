package com.nghianv.dev1_ringtone.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerInfo {
	@SerializedName("country")
	private String country;
	@SerializedName("ringtones")
	private List<Ringtone> ringtones;
	@SerializedName("pageId")
	private String papeId;

	public ServerInfo() {
	}

	public ServerInfo(String country, List<Ringtone> ringtones, String papeId) {
		this.country = country;
		this.ringtones = ringtones;
		this.papeId = papeId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Ringtone> getRingtones() {
		return ringtones;
	}

	public void setRingtones(List<Ringtone> ringtones) {
		this.ringtones = ringtones;
	}

	public String getPapeId() {
		return papeId;
	}

	public void setPapeId(String papeId) {
		this.papeId = papeId;
	}
}
