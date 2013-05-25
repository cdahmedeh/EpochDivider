package org.cdahmedeh.orgapp.schedule;

import java.util.ArrayList;

public class Context {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private Context parent = null;
	public Context(Context parent) {
		this.parent = parent;
	}
	
	private ArrayList<Context> subContexts = new ArrayList<>();
	public ArrayList<Context> getSubContexts() {
		return subContexts;
	}
	public void addSubContext(Context context){
		subContexts.add(context);
	}
}