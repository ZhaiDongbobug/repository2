package game.sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Hero extends MySprite{

	public static final int TOP_FIRE = 1;
	public int hp;
	public int fireLevel;
	int currentFrame;
	int BulletVx,BulletVy;

	public Hero(BufferedImage img, int x, int y) {
		// TODO Auto-generated constructor stub
		super(img, x, y);
		hp = 3;
		fireLevel = 0;
		BulletVx = 0;
		BulletVy = -20;
		currentFrame = 0;
	}

	public void setPostion(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public void logic() {
		// TODO Auto-generated method stub
		
	}


}
