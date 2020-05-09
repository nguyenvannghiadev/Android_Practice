package com.nghianv.dev1_ringtone.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerInfoJson {
	@SerializedName("ServerInfo")
	public List<ServerInfo> serverInfo;

	public ServerInfoJson() {
	}

	public ServerInfoJson(List<ServerInfo> serverInfo) {
		this.serverInfo = serverInfo;
	}

	public List<ServerInfo> getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(List<ServerInfo> serverInfo) {
		this.serverInfo = serverInfo;
	}
}
