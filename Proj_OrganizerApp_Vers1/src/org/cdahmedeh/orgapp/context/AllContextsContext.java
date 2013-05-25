package org.cdahmedeh.orgapp.context;

public class AllContextsContext extends Context {
	private static final long serialVersionUID = 8301452886753332983L;

	public AllContextsContext(String name, Context parent) {super(name, parent);}
	
	@Override public boolean isVisible() {return false;}
}
