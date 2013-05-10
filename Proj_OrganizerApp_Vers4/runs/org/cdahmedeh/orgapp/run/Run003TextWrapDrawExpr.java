package org.cdahmedeh.orgapp.run;

import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayDeque;

import javax.swing.JFrame;

import org.cdahmedeh.orgapp.tools.MiscHelper;

public class Run003TextWrapDrawExpr {

	private JPaintFrameText frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Run003TextWrapDrawExpr window = new Run003TextWrapDrawExpr();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Run003TextWrapDrawExpr() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JPaintFrameText();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class JPaintFrameText extends JFrame {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			String longString = "Hello, this is a string that I used to write things with for this example. Verylongewordthatdoesnotexist.";
			
			g.drawLine(30, 0, 30, this.getHeight());
			g.drawLine(30 + this.getWidth()-30*2, 0, 30 + this.getWidth()-30*2, this.getHeight());
			g.drawLine(0, 50+this.getHeight()-50*2, this.getWidth(), 50+this.getHeight()-50*2);
			
			drawString(g, longString, 30, 50, this.getWidth()-30*2, this.getHeight()-50*2);
		}
		
		public void drawString(Graphics g, String s, int x, int y, int width, int height)
		{
			String[] split = s.split(" ");
			ArrayDeque<String> text = MiscHelper.toArrayDeque(split);
			FontMetrics fontMetrics = g.getFontMetrics();

			int yPrintPos = y;
			
			while (!text.isEmpty()){
				int xPrintPos = x;
				int lineLength = 0;
				
				while (!text.isEmpty()) {
					String nextWord = text.pop();
					
					int nextWordWidth = fontMetrics.stringWidth(nextWord);
					
					System.out.println(nextWordWidth);
					System.out.println(width);
					
					if (nextWordWidth + 5 >= width && lineLength == 0) {
						System.out.println("Word too long: " + nextWord);
						g.drawString(drawCroppedString(fontMetrics, nextWord, width), xPrintPos+lineLength, yPrintPos);
						break;
					} else if (lineLength + nextWordWidth + 5 < width) {
						g.drawString(nextWord, xPrintPos+lineLength, yPrintPos);
					} else {
						text.push(nextWord);
						System.out.println("Word next line: " + nextWord);
						break;
					}
					
					lineLength += nextWordWidth + 5;
				}
				
				yPrintPos += 20;
				
				if (yPrintPos-y > height) break;
			}
		}

		private String drawCroppedString(FontMetrics metrics, String nextWord, int width) {
			if (nextWord.isEmpty() || metrics.stringWidth(nextWord) < width){
				return nextWord;
			} else {
				return drawCroppedString(metrics, nextWord.substring(0, nextWord.length()-1), width);
			}
		}
	}
}


