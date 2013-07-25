package com.tronicdream.epochdivider.pers.helpers;

public class SQLHelper {
	public String join(String... args){
		StringBuffer sb = new StringBuffer();
		
		for (String arg: args){
			sb.append(",");
			sb.append(arg);
		}
		
		sb.replace(0, 1, "");
		
		return sb.toString();
	}
}
