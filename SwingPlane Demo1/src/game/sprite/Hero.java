package game.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.game.MyGame;

public class Hero extends MySprite{

	public static final int TOP_FIRE = 1;
	public int hp;
	public int fireLevel;
	int currenFrame;//当前绘制的帧
	int bulletVx,bulletVy;

	public Hero(BufferedImage img[], int x, int y) {
		// TODO Auto-generated constructor stub
		super(img, x, y);
		hp = 3;
		fireLevel = 0;
		bulletVx = 0;
		bulletVy = -20;
		currenFrame = 0;
	}

	public void setPostion(int tx, int ty) {
		// TODO Auto-generated method stub
		cx = tx;
		cy = ty;
		x = cx - w/2;
		y = cy - h/2;
		myRect.setPostion(x, y, w, h);
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(imgs[currenFrame], x, y, null);
		currenFrame ++;
		if(currenFrame >= imgs.length){
			currenFrame = 0;
		}
		
		if(MyGame.DEBUG){
			g.setColor(Color.red);
			g.drawRect(myRect.left, myRect.top,
				w, h);
		}
	}

	int shootCD = 0;
	int shootTime = 2;
	@Override
	public void logic() {
		shoot();
	}

	private void shoot() {
		if(shootCD < 0){
			if(fireLevel == 0){
				MyGame.instance.addBullet(cx,cy,
						bulletVx,bulletVy);
			}else{
				MyGame.instance.addBullet(x,cy,
						bulletVx,bulletVy);
				MyGame.instance.addBullet(x + w,cy,
						bulletVx,bulletVy);
			}
			shootCD = shootTime;
		}
		shootCD --;
	}
}


