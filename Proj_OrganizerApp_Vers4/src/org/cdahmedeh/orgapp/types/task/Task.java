package org.cdahmedeh.orgapp.types.task;

import org.cdahmedeh.orgapp.tools.MiscHelper;

/**
 * Data type class for Tasks. 
 * 
 * @author Ahmed El-Hajjar
 */
public class Task {
	
	/* ---- Constructs ---- */
	
	public Task(String title) {this.setTitle(title);}
	
	
	/* ---- Main Data ---- */
	
	private String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = MiscHelper.safeTrim(title);}
}
