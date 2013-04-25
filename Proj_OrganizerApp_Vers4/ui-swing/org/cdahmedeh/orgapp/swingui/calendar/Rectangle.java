package org.cdahmedeh.orgapp.swingui.calendar;

public class Rectangle {
	public int x;
	public int y;
	public int width;
	public int height;

	public Rectangle(int x,	int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//TODO: Taken from SWT Rectangle. Must see copyright info.
	public boolean contains(int x, int y) {
		return (x >= this.x) && (y >= this.y) && x < (this.x + width) && y < (this.y + height);
	}
	
}
