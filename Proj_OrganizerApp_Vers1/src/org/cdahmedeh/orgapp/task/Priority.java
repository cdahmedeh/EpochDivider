package org.cdahmedeh.orgapp.task;

public enum Priority {
	NONE("None", 0),
	LOW("Low", 1),
	MEDIUM("Medium", 2),
	HIGH("High", 3);

	private String name;
	public String getName() {return name;}
	
	private int value;
	public int getValue() {return value;}
	
	private Priority(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static Priority fromValue(int value){
		switch(value){
		case 0:		return NONE;
		case 1:		return LOW;
		case 2:		return MEDIUM;
		case 3:		return HIGH;
		default:	return NONE;
		}
	}
}
