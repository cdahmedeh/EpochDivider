package org.cdahmedeh.orgapp.ui.notify;

import org.cdahmedeh.orgapp.types.task.Task;

public class TaskEditRequest {
	private Task task;
	public Task getTask() {
		return task;
	}

	public TaskEditRequest(Task task) {
		this.task = task;
	}
}
