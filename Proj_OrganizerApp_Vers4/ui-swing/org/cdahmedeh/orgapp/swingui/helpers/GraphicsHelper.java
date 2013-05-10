package org.cdahmedeh.orgapp.swingui.helpers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Methods for manipulating the Graphics object. Noteworthy use is for enabling
 * anti-aliasing when drawing.
 * 
 * TODO: Take into consideration that LCD_HRGB sub-pixel anti-alias is not 
 * optimal for all displays. 
 *  
 * @author Ahmed El-Hajjar
 */
public class GraphicsHelper {
	public static void enableDefaultAASettings(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
