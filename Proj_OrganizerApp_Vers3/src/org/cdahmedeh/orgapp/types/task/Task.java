package org.cdahmedeh.orgapp.types.task;

import org.joda.time.Duration;

public class Task{
	private	String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title != null ? title.trim() : "";}
	
	public Mutability mutability = TypeConstants.DEFAULT_MUTABILITY;
	public Mutability getMutability() {return mutability;}
	public void setMutability(Mutability mutability) {this.mutability = mutability != null ? mutability : TypeConstants.DEFAULT_MUTABILITY;}
	
	public Permissibility permissibility = TypeConstants.DEFAULT_PERMISSIBILITY;
	public Permissibility getPermissibility() {return permissibility;}
	public void setPermissibility(Permissibility permissibility) {this.permissibility = permissibility != null ? permissibility : TypeConstants.DEFAULT_PERMISSIBILITY;}
		
	public Duration durationToComplete = new Duration(0);
	public Duration getDurationToComplete() {return durationToComplete;}
	public void setDurationToComplete(Duration durationToComplete) {this.durationToComplete = durationToComplete;}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getTitle());
		sb.append(" => ");
		sb.append(this.getDurationToComplete().getStandardMinutes());
		sb.append(" mins");
		return sb.toString();
	}
}
