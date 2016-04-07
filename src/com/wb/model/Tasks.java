package com.wb.model;

/*
 * model for task data
 */
public class Tasks {

	private Long 	id;
	private long 	projectId;
	private String  title;
	private int 	task;

	public Tasks() {
	}

	public Tasks(Long id, long projectId, String title, int task) {
		this.id = id;
		this.projectId = projectId;
		this.title = title;
		this.task = task;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}

}// Task
