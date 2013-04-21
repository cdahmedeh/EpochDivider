package org.cdahmedeh.orgapp.types.container;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Context;

/**
 * In memory version of data references used by main UI. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	private ArrayList<Context> contexts;
	public ArrayList<Context> getContexts() {return contexts;}
	public void setContexts(ArrayList<Context> contexts) {this.contexts = contexts;}
}
