package game.sprite;

import java.awt.image.BufferedImage;

public class Bee extends Enemy{

	public int dir;
	public static final int BEE_LEFT = 0;
	public static final int BEE_RIGHT = 1;
	
	int duration = 20;
	int birthy;

	public Bee(BufferedImage img, int x, int y) {
		super(img, x, y);
		birthy = cy;
		vy = -15;
		score = 500;
		type = TYPE_BEE;
	}
	public void move() {
		switch (dir) {
		case BEE_LEFT:
			x -= 30;
			break;
		case BEE_RIGHT:
			x += 30;
			break;
		}
		y += vy;
		cx = x + w/2;
		cy = y + h/2;
		
		
	}
}
