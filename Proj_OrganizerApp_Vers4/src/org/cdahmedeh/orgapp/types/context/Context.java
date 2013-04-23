package org.cdahmedeh.orgapp.types.context;

import java.util.Random;

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
	
	public boolean isSelectable() {return true;}
	
	private int color = new Random().nextInt(255);
	public int getColor() {return color;}
	public void setColor(int color) {this.color = color;}
	
	
	/* ---- Object methods ---- */
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		//TODO: For now, contexts are the same, if they have the same name.
		if (obj instanceof Context){
			return this.getName().equals(((Context) obj).getName());	
		} else {
			return false;
		}
	}
}
