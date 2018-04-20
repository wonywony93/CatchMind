package chat.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class CanvasDemo extends Canvas {

		static int x = -10, y = -10, w = 10, h = 10;
		static int sw = 0;
		Color color = Color.black;
	
		public void update(Graphics g) {
			paint(g);
		}
	
		public void paint(Graphics g) {
			if (sw == 0) {
				g.setColor(color);
				g.fillOval(x, y, w, h); // ¿ø Ãâ·Â
			} else if (sw == 1) {
				g.clearRect(0, 0, 850, 500);
			}
		}

}
