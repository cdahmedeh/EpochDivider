package org.cdahmedeh.orgapp.types.time;

import org.joda.time.Duration;

/**
 * Container class for three Duration objects.
 *  
 * @author Ahmed El-Hajjar
 */
public class TripleDurationInfo {
	private Duration totalPassed;
	public Duration getTotalPassed() {return totalPassed;}
	
	private Duration totalScheduled;
	public Duration getTotalScheduled() {return totalScheduled;}
	
	private Duration estimate;
	public Duration getEstimate() {return estimate;}

	public TripleDurationInfo(Duration totalPassed, Duration totalScheduled, Duration estimate) {
		this.totalPassed = totalPassed;
		this.totalScheduled = totalScheduled;
		this.estimate = estimate;
	}
}
