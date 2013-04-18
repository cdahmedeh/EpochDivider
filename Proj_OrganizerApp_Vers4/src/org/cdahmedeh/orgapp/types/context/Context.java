package org.cdahmedeh.orgapp.types.context;

import org.cdahmedeh.orgapp.tools.MiscHelper;

/**
 * A context represents a category that tasks can be assigned to. For example,
 * there can be a "Study" context that is assigned to every task is related to
 * studying. The idea is to help the user categorize their tasks.
 * 
 * @author Ahmed El-Hajjar
 */
public class Context {

	/* ---- Constructs ---- */
	
	public Context(String name) {this.setName(name);}
	
	
	/* ---- Main Data ---- */
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = MiscHelper.safeTrim(name);}
}
