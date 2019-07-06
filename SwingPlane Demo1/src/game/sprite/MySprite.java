package game.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.game.MyGame;
import game.util.Rect;

public class MySprite {
	BufferedImage[] imgs;
	BufferedImage img;//精灵使用的图片
	
	int x;//精灵左上角点的坐标
	int y;//精灵左上角点的坐标
	int w,h;//精灵的宽高
	int cx,cy;//精灵中心点的位置
	int vx,vy;//移动速度
	
	public Rect myRect;//碰撞框
//	精灵属性的初始化
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
//	精灵属性的初始化2
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
		x += vx;//x点根据vx速度每帧变化
		y += vy;//y点根据vy速度每帧变化
//		根据x，y的表化调整中心点坐标
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
