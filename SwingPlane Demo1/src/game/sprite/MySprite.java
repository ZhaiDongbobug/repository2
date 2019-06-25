package game.sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.util.Rect;

public class MySprite {

	BufferedImage img;
	
	int x,y;
	int w,h;
	int vx,vy;
	
	public Rect myRect;
	
	public  MySprite(BufferedImage img, int x, int y) {
		x = this.x;
		y = this.y;
		w = img.getWidth();
		h = img.getHeight();
		myRect = new Rect(x,y,w,h);
	}
	public void setSpeed(int tx,int ty) {
		vx = tx;
		vy = ty;
	}
	public void logic() {
		
	}
	public boolean hit(MySprite other) {
		return false ;
	}
	public void draw(Graphics g) {
		
	}
}
