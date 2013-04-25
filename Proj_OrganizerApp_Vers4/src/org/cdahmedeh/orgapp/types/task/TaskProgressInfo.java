package org.cdahmedeh.orgapp.types.task;

import org.joda.time.Duration;

public class TaskProgressInfo {
	private Duration totalPassed;
	private Duration totalScheduled;
	private Duration estimate;

	public TaskProgressInfo(Duration totalPassed, Duration totalScheduled, Duration estimate) {
		this.totalPassed = totalPassed;
		this.totalScheduled = totalScheduled;
		this.estimate = estimate;
	}

	public Duration getTotalPassed() {
		return totalPassed;
	}

	public Duration getTotalScheduled() {
		return totalScheduled;
	}

	public Duration getEstimate() {
		return estimate;
	}
}
