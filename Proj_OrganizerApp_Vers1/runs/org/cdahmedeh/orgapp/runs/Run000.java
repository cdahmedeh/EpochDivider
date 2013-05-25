package org.cdahmedeh.orgapp.runs;

import org.cdahmedeh.orgapp.context.Context;

public class Run000 {
	public static void main(String[] args) {
		Context rootContext = new Context("Root", null);
		
		Context level1a = new Context("level1a", rootContext);
		rootContext.addSubContext(level1a);
		
		Context level1b = new Context("level1b", rootContext);
		rootContext.addSubContext(level1b);
		
		Context level2a = new Context("level2a", level1b);
		level1b.addSubContext(level2a);
				
		System.out.println(rootContext.getAllSubContexts());
	}
}
