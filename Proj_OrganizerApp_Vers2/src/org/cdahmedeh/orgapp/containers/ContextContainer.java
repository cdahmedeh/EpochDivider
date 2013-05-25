package org.cdahmedeh.orgapp.containers;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.category.Context;

public class ContextContainer {
	public ArrayList<Context> contexts = new ArrayList<>();
	public void addContext(Context context) {contexts.add(context);}
	public ArrayList<Context> getAllContexts() {return contexts;}
	
	public ArrayList<Context> getAllSelectableContexts(){
		ArrayList<Context> con = new ArrayList<>();
		for (Context context: contexts) if (context.isSelectable()) con.add(context);
		return con;
	}
	
	public Context getContextFromId(int id){
		for (Context context: contexts) if (context.getId() == id) return context;
		return null;
	}
	
	public Context getContextFromName(String name){
		for (Context context: contexts) if (context.getName().equals(name)) return context;
		return null;
	}
}
