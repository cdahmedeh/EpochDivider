package org.cdahmedeh.orgapp.context;

public class NoContextContext extends Context {
	private static final long serialVersionUID = 863536904390595418L;

	public NoContextContext(String name, Context parent) {super(name, parent);}

	@Override public boolean isVisible() {return false;}
}
