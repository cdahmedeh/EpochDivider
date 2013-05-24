package org.cdahmedeh.orgapp.swingui.calendar;

public class BRectangle {
	public int x;
	public int y;
	public int oridWidth;
	public int width;
	public int height;
	
	public BRectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean isWithin(int x, int y) {
		return (x >= this.x) && (y >= this.y) && x < (this.x + width) && y < (this.y + height);
	}
}
