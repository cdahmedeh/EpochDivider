package org.cdahmedeh.orgapp.types.task;

import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.NoContextContext;
import org.joda.time.DateTime;

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
	
	private Context context = new NoContextContext();
	public void setContext(Context context) {this.context = context == null ? new NoContextContext() : context;}
	public Context getContext() {return context;}
	
	private DateTime due = null;
	public DateTime getDue() {return due;}
	public void setDue(DateTime due) {this.due = due;}

}
