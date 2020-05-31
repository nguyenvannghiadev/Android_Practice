package com.nghianv.dialogfragment.model;

import java.util.Date;

public class Job {
	private int idJob;
	private String nameJob;
	private String creatDate;

	public Job() {
	}

	public Job(int idJob, String nameJob, String creatDate) {
		this.idJob = idJob;
		this.nameJob = nameJob;
		this.creatDate = creatDate;
	}

	public int getIdJob() {
		return idJob;
	}

	public void setIdJob(int idJob) {
		this.idJob = idJob;
	}

	public String getNameJob() {
		return nameJob;
	}

	public void setNameJob(String nameJob) {
		this.nameJob = nameJob;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}
}
