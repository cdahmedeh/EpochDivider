package org.cdahmedeh.orgapp.types.context;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.tools.MiscHelper;

/**
 * Contexts can be organized into sub groups called Context Categories. For 
 * example, 'Study' and 'Course' contexts can be grouped into the 'University'
 * category.
 *   
 * @author Ahmed El-Hajjar
 */
public class ContextCategory {

	/* ---- Constructs ---- */
	
	public ContextCategory(String name) {this.setName(name);}
	

	/* ---- Main Data ---- */
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = MiscHelper.safeTrim(name);}
	
	private ArrayList<Context> contexts = new ArrayList<>();
	public ArrayList<Context> getContexts() {return contexts;}
}
