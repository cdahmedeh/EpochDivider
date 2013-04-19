package org.cdahmedeh.orgapp.types.container;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.ContextCategory;

/**
 * In memory version of data references used by main UI. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	ArrayList<ContextCategory> contextCategories = new ArrayList<>();
	public ArrayList<ContextCategory> getContextCategories() {return contextCategories;}
	public void setContextCategories(ArrayList<ContextCategory> contextCategories) {this.contextCategories = contextCategories;}
}
