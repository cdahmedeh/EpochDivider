package org.cdahmedeh.orgapp.types.task;

public enum TaskType {
TASK("Task"), EVENT("Event");

private String title;

private TaskType(String title) {
	this.title = title;
}

public String getTitle() {
	return title;
}
}
