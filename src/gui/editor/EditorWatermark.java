package gui.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class EditorWatermark {
	/* Watermark on canvas */
	public static void WatermarkCanvas(Graphics2D g2, Graphics g, int width, int height) {
		int canvasWidth = width;
		int canvasHeight = height;
		
//		 AffineTransform at = new AffineTransform();
//		 at.rotate(-(Math.PI / 7.8));
//		 g2.setTransform(at);
//		canvasWidth = canvasWidth * 2;
//		canvasHeight = canvasHeight * 2;

		g2.setColor(new Color(200, 200, 200, 75));

		var WatermarkText = "J F L A P";
		Font f = new Font("TimesRoman", Font.BOLD, 20);
		g2.setFont(f);

		int canvasXLoop = 0, canvasYLoop = 0;
		int textWidth = g.getFontMetrics().stringWidth(WatermarkText);
		int canvasStartX = textWidth;
		int yMargin = 55, xMargin = 35;

		while (canvasHeight > canvasYLoop) {
			while (canvasWidth > canvasXLoop) {
				g2.drawString(WatermarkText, canvasXLoop, canvasYLoop);
				canvasXLoop += textWidth + xMargin;
			}
			canvasStartX -= textWidth;
			canvasXLoop = canvasStartX;
			canvasYLoop += yMargin;
		}
	}
}
