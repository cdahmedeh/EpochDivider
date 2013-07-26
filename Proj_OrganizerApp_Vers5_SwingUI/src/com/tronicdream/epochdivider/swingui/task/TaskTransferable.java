package com.tronicdream.epochdivider.swingui.task;

import com.tronicdream.epochdivider.core.types.task.Task;
import com.tronicdream.epochdivider.swingui.components.ObjectTransferable;

public class TaskTransferable extends ObjectTransferable<Task> {
	public TaskTransferable(Task task) {
		super(Task.class, task);
	}
}