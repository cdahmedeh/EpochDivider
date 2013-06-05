package org.cdahmedeh.orgapp.swingui.task;

import org.cdahmedeh.orgapp.swingui.components.ObjectTransferable;
import org.cdahmedeh.orgapp.types.task.Task;

public class TaskTransferable extends ObjectTransferable<Task> {
	public TaskTransferable(Task task) {
		super(Task.class, task);
	}
}