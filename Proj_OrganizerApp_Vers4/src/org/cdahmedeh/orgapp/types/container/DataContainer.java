package org.cdahmedeh.orgapp.types.container;

import org.cdahmedeh.orgapp.types.context.Context;

/**
 * In memory version of data references used by main UI. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	private Context rootContext;
	public Context getRootContext() {return rootContext;}
	public void setRootContext(Context rootContext) {this.rootContext = rootContext;}
}
