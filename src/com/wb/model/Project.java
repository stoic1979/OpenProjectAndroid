package com.wb.model;

import java.io.Serializable;

/*
 * model for project id and title
 */
@SuppressWarnings("serial")
public class Project implements Serializable {

	private Long id = null;
	private String title = null;
	private Long   date = null;

	public Project() {

	}

	public Project(Long id, String title, Long date) {
		this.id = id;
		this.title = title;
		this.date = date;
	}

	public void setId(Long ID) {
		this.id = ID;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

}// Project
