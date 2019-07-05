package game.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.game.MyGame;
import game.util.Rect;

public class MySprite {
	BufferedImage[] imgs;
	BufferedImage img;
	
	int x;
	int y;
	int w,h;
	int cx,cy;
	int vx,vy;
	
	public Rect myRect;
	
	public  MySprite(BufferedImage img, int x, int y) {
		this.img = img;
		x = this.x;
		y = this.y;
		w = img.getWidth();
		h = img.getHeight();
		cx = x + w/2;
		cy = y + h/2;
		myRect = new Rect(x,y,w,h);
	}
	public MySprite(BufferedImage[] imgs,int x, int y) {
		this.imgs = imgs;
		this.x = x;
		this.y = y;
		w = imgs[0].getWidth();
		h = imgs[0].getHeight();
		cx = x + w/2;
		cy = y + h/2;
		myRect = new Rect(x,y,w,h);
	}
	public void setSpeed(int tx,int ty) {
		vx = tx;
		vy = ty;
	}
	public void logic() {
		move();
	}
	private void move() {
		x += vx;
		y += vy;
		cx = x + w/2;
		cy = y + h/2;
		
		myRect.setPostion(x, y, w, h);
		
	}
	public boolean hit(MySprite other) {
		return this.myRect.nineCollsion(other.myRect);
	}
	public void draw(Graphics g) {
		if(MyGame.DEBUG){
			g.setColor(Color.red);
			g.drawRect(myRect.left, myRect.top,
					w, h);
		}
		g.drawImage(img, x, y, null);
	}
	
}
