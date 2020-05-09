package com.nghianv.dev1_ringtone_okhttp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeverInfoJson {
	@SerializedName("ServerInfo")
	public List<SeverInfo> severInfo;

	public SeverInfoJson() {
	}

	public SeverInfoJson(List<SeverInfo> severInfo) {
		this.severInfo = severInfo;
	}

	public List<SeverInfo> getSeverInfo() {
		return severInfo;
	}

	public void setSeverInfo(List<SeverInfo> severInfo) {
		this.severInfo = severInfo;
	}
}
