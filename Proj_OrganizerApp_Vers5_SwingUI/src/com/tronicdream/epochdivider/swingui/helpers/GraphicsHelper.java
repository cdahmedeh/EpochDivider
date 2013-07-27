package com.tronicdream.epochdivider.swingui.helpers;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayDeque;

import com.tronicdream.epochdivider.core.tools.MiscHelper;

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
	
	//From: http://stackoverflow.com/questions/400566/full-justification-with-a-java-graphics-drawstring-replacement
	//TODO: Improve, slow, not loop proof.
	//TODO: What if a word is just too long?
	public static void drawCroppedAndWrappedString(Graphics g, String s, int x, int y, int width, int height)
	{
		String[] split = s.split(" ");
		ArrayDeque<String> text = MiscHelper.toArrayDeque(split);
		FontMetrics fontMetrics = g.getFontMetrics();

		int yPrintPos = y;
		int spaceWidth = fontMetrics.stringWidth(" ");
		
		while (!text.isEmpty()){
			int xPrintPos = x;
			int lineLength = 0;
			
			while (!text.isEmpty()) {
				String nextWord = text.pop();
				
				int nextWordWidth = fontMetrics.stringWidth(nextWord);
				
				if (nextWordWidth + spaceWidth >= width && lineLength == 0) {
					g.drawString(drawCroppedString(fontMetrics, nextWord, width), xPrintPos+lineLength, yPrintPos);
					break;
				} else if (lineLength + nextWordWidth + spaceWidth < width) {
					g.drawString(nextWord, xPrintPos+lineLength, yPrintPos);
				} else {
					text.push(nextWord);
					break;
				}
				
				lineLength += nextWordWidth + spaceWidth;
			}
			
			yPrintPos += fontMetrics.getHeight();
			
			if (yPrintPos-y > height) break;
		}
	}

	private static String drawCroppedString(FontMetrics metrics, String nextWord, int width) {
		if (nextWord.isEmpty() || metrics.stringWidth(nextWord) < width){
			return nextWord;
		} else {
			return drawCroppedString(metrics, nextWord.substring(0, nextWord.length()-1), width);
		}
	}
}
