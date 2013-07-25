package com.tronicdream.epochdivider.swingui.helpers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Methods for manipulating the Graphics object. Noteworthy use is for enabling
 * anti-aliasing when drawing.
 * 
 * TODO: Take into consideration that LCD_HRGB sub-pixel anti-aliasing is not 
 * optimal for all displays. 
 *  
 * @author Ahmed El-Hajjar
 */
public class GraphicsHelper {
	
	/**
	 * Enables anti-aliasing for both text and other elements for the supplied
	 * 'g' Graphics object.
	 * 
	 * @param g Graphics object on which anti-aliasing should be enabled.
	 */
	public static void enableDefaultAASettings(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		
		graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
