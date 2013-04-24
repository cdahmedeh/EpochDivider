package org.cdahmedeh.orgapp.swingui.helpers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GraphicsHelper {
	public static void enableDefaultAASettings(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
